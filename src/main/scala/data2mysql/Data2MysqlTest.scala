package data2mysql




import java.sql.{Connection, DriverManager, PreparedStatement}
import javax.sql.DataSource

import com.eric.self.{ApplicationContextHolder, UserAuthoirtyApplication}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import com.eric.self.UserAuthoirtyApplication
import mapper.UserAuthority
import org.apache.ibatis.builder.xml.XMLConfigBuilder
import org.apache.ibatis.io.Resources
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.{SqlSessionFactory, SqlSessionFactoryBuilder}
import org.apache.ibatis.transaction.TransactionFactory
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.stereotype.Controller

import scala.collection.JavaConversions

/**
  * Created by wangfeng on 19/1/22.
  */

object Data2MysqlTest {

  def main(args: Array[String]): Unit = {
    val appContext: ClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath*:springconfig/*.xml")

    val conf = new SparkConf().setAppName("Data2MysqlTest").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //读取ip信息
    val ipinfo: Array[(String, String, String)] = sc.textFile("/Users/wangfeng/Desktop/Data/history/ip.txt").map(line => {
      val arr=line.split("\\|")
      val startIp=arr(2)
      val endIp=arr(3)
      val province=arr(6)
      (startIp,endIp,province)
    }).collect()

    //将需要广播的数据广播到集群中的相应的Executor
    val broadcast:Broadcast[Array[(String,String,String)]]=sc.broadcast(ipinfo)

    //读取用户点击流日志
    val provinceAndOne:RDD[(String,Int)]=
    sc.textFile("/Users/wangfeng/Desktop/Data/history/log/http.log").map(line =>{

      val fields=line.split("\\|")
      val ip=fields(1)
      val ip2Long=ipToLong(ip)
      val arr=broadcast.value
      val ipint=binarySearch(arr,ip2Long)
      val province=arr(ipint)._3

      (province,1)
    })
    //计算结果
    val res:RDD[(String,Int)]=provinceAndOne.reduceByKey(_+_)
    //System.out.println("res:"+res.collect().toBuffer)

    //res.foreachPartition(data2Mysql)
    //为什么是用foreachPartition,而不是直接用foreach呢,原因是:用foreachPartition,一个分区就只需要建立一次数据库连接,而不需要每次都一个数据都建立链接,容易将数据库的连接池打满
    val res2:RDD[(String,Int,Int)]=res.map(it0=>{
      (it0._1,it0._2,1)
    })

    val res3:RDD[UserAuthority]=res.map(it=>{
      var temp: UserAuthority = new UserAuthority
      if(it!=null) {
        temp.setKey(it._1)
        temp.setRoleId(it._2)
      }else{
        temp=null
      }
      temp
    })
    res3.foreachPartition(data2Mysql3)

  }


  def ipToLong(ip:String):Long= {

    val foragments = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until foragments.length) {
      ipNum = foragments(i).toLong|ipNum << 8L
    }
    ipNum
  }

  def binarySearch(arr:Array[(String,String,String)],ip:Long):Int={
    var low=0
    var high=arr.length
    while(low<=high){
      val middle=(low+high)/2
      if((ip>=arr(middle)._1.toLong)&&(ip<=arr(middle)._2.toLong)){
        return middle
      }
      if(ip<arr(middle)._1.toLong){
        high=middle-1
      }else{
        low=middle+1
      }
    }
    -1
  }
  val data2Mysql3 =(it: Iterator[UserAuthority])=> {
    //方式三
    val userAuthoirtyApplication = ApplicationContextHolder.getBean("userAuthoirtyApplication").asInstanceOf[UserAuthoirtyApplication]
    userAuthoirtyApplication.insert(JavaConversions.asJavaIterator(it))
  }

  val data2Mysql=(it: Iterator[(String,Int)])=>{
    //方式三
    //val appContext: ClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath*:springconfig/*.xml")

    //@Autowired
    //val userAuthoirtyApplication=appContext.getBean("userAuthoirtyApplication").asInstanceOf[UserAuthoirtyApplication]

    //val userAuthoirtyApplication=ApplicationContextHolder.getBean("userAuthoirtyApplication").asInstanceOf[UserAuthoirtyApplication]

    //val seq2=it.toSeq

    System.out.println("seq3:"+it.toBuffer)

    //userAuthoirtyApplication.insert(it)



    //方式二
   /* val reader = Resources.getResourceAsReader("mybatis-config")//暂不可行
    /*val ds:DataSource = new SingleConnectionDataSource(GlobalConfig.priceratioJdbcUrl,GlobalConfig.priceratioJdbcUser,GlobalConfig.priceratioJdbcPwd,true);
    val transactionFactory:TransactionFactory  = new JdbcTransactionFactory()
    val environment:Environment  = new Environment("development", transactionFactory, ds)*/
    val configuration = new XMLConfigBuilder(reader).parse()
    /*configuration.setEnvironment(environment)*/
    val fac:SqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration)
    val session = fac.openSession();
    val mp:UserAuthorityMapper = session.getMapper(classOf[UserAuthorityMapper])

    mp.batchInsert(JavaConversions.seqAsJavaList(it.toSeq))*/

    //方式一 可行
   /* try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch{
      case e2:ClassNotFoundException=>e2.printStackTrace();
    }

    var conn:Connection=null
    var ps:PreparedStatement=null
    val sql ="insert into  User_Authority (`key`,roleId) values (?,?)"

    try {
      conn = DriverManager.getConnection("jdbc:mysql://10.72.234.31:3306/Hiring_Campus_ATS?userUnicode=true&characterEncodind=utf8", "hiring_campusats", "dp!@sZiFTy3Qv")
      it.foreach(line => {
        ps = conn.prepareStatement(sql)
        ps.setString(1, line._1)
        ps.setInt(2, line._2)
        ps.executeUpdate()
      })
    }catch {
      case e: Exception => println(e.printStackTrace())
    } finally {
      if (ps != null)
        ps.close()
      if (conn != null)
        conn.close()
    }*/

  }

}
