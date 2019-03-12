package day10

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangfeng on 19/1/25.
  * 通过反射推断schema
  */
object InferringSchema {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("CustomSort").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sQLContext=new SQLContext(sc)

    //获取数据
    val lineRDD=sc.textFile(args(0)).map(_.split(","))

    //将RDD和class进行关联
    val personRDD=lineRDD.map(x=>Person(x(0).toInt,x(1),x(2),x(3).toInt))

    //RDD转换为DataFrame
    import sQLContext.implicits._
    val personDF=personRDD.toDF
    personDF.write.json(args(1))


    //注册表
    personDF.registerTempTable("new_person_temp")

    val df:DataFrame=sQLContext.sql("select * from new_person_temp order by age desc limit 2")
    df.write.json(args(2))







    sc.stop()
  }
}

case class Person(id:Int,name:String,province:String,age:Int)
