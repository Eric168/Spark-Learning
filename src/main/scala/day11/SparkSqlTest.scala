package day11

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangfeng on 19/1/24.
  *
  * DataFrames
  *  1.在Spark中,DataFrame是一种以RDD为基础的分布式数据集,类似于传统数据库中的二维表格
  *  DataFrame与RDD的主要区别在于,前者带有schema元信息,
  *  即DataFrame所表示的二维表数据集的每一列都带有名称和类型,这使得Spark SQL 得以洞察更多的结构信息,
  *  从而对于DataFrame背后的数据源以及作用于DataFrame之上的变换进行了针对性的变化,
  *  最终达到大幅提升运行时效率的目标。反观RDD,由于无从得知所存数据元素的具体内部结构,
  *  Spark Core只能在stage层面进行简单、通用的流水线优化
  *
  *  Spark SQL Core
  *  Spark SQL的核心是把已又的RDD,带上schema信息,然后注册成类似SQL里的"Table",对其进行sql查询。
  *  这里面主要分两部分,一是生成schemaRDD,而是执行查询
  *  正如RDD的各种变换实际上只是在构造RDDDAG,DataFram的各种变换同样也是lazy的
  *  它们并不直接求出计算结构,而是将各种变换组装成与RDD DAG类似的逻辑查询计划
  *  如前所述,由于DataFrame带有schema元信息,SPARK SQL的查询优化器得以洞察数据和计算的精细结构
  *  从而施行具有很强针对性的优化,随后,经过优化的逻辑执行计划被翻译为物理执行计划,并最终落实为RDD DAG
  */
object SparkSqlTest {
  case class Person(id:Int,name:String,province:String,age:Int)


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    //local模式仅供在本地测试，若要运行在集群，请删除setMaster代码
    conf.setAppName("SparkSqlTest").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val line=sc.textFile("/Users/wangfeng/Desktop/Data/Person/person.txt").map(_.split(","))
    val personRDD=line.map(x => Person(x(0).trim.toInt,x(1),x(2),x(3).trim.toInt))

     // 解决方案1:
     /* val  spark =SparkSession.builder().getOrCreate();
      import spark.implicits._*/

    import sqlContext.implicits._
    val personDF=personRDD.toDF()//spark的隐式转换 必须引入spark。implicits

    personDF.show()



    personDF.registerTempTable("table_new")

    val output = sqlContext.sql("select * from table_new where id>2 ").repartition(3)

    //write to mysql
    val user:String="hiring_campusats"
    val passwd:String="dp!@sZiFTy3Qv"
    val host:String="10.72.234.31"
    val port:String="3306"
    val dbname:String="Hiring_Campus_ATS"

    val prop = new Properties()
    prop.setProperty("driver", "com.mysql.jdbc.Driver")
    prop.setProperty("user", user)
    prop.setProperty("password", passwd)
    prop.setProperty("driver", "com.mysql.jdbc.Driver")
    val url = s"jdbc:mysql://${host}:${port}/${dbname}"
    prop.setProperty("url", url)

    //output.write.mode("overwrite").jdbc(url, "t_spark_test", prop) 删除表并创建表,并插入数据
    //output.write.mode("append").jdbc(url, "t_spark_test", prop)//往表中追加记录
    /* SaveMode有三种模式,
     1.SaveMode.Append     如果表已经存在，则追加在该表中；若该表不存在，则会先创建表，再插入数据；
     2.SaveMode.Overwrite  重写模式，其实质是先将已有的表及其数据全都删除，再重新创建该表，最后插入新的数据；
     3.SaveMode.Ignore   若表不存在，则创建表，并存入数据；在表存在的情况下，直接跳过数据的存储，不会报错。
    */

    personDF.select(personDF("id"),personDF("name")).show

    personDF.filter(personDF("id")>=2).show

    personDF.groupBy(personDF("name")).count().show()







    sc.stop()
  }
}