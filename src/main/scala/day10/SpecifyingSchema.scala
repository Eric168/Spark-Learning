package day10

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, _}

/**
  * Created by wangfeng on 19/1/25.
  */
object  SpecifyingSchema {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("CustomSort").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sQLContext=new SQLContext(sc)

    //获取数据
    val lineRDD=sc.textFile(args(0)).map(_.split(","))

    //通过StructType指定每个字段的schema
    val schema=StructType(
      List(
        StructField("id",IntegerType,true),//true代表字段可为空
        StructField("name",StringType,true),
        StructField("province",StringType,true),
        StructField("age",IntegerType,true)
      )
    )

    //将RDD映射到rowRDD
    val rowRDD=lineRDD.map(x=> Row(x(0).toInt,x(1),x(2),x(3).toInt))

    val personDF=sQLContext.createDataFrame(rowRDD,schema)

    //注册表
    personDF.registerTempTable("new_person_temp")

    val df:DataFrame=sQLContext.sql("select * from new_person_temp order by age desc limit 2")
    df.write.json(args(2))







    sc.stop()
  }
}
