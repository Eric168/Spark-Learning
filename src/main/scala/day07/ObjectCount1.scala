package day07

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangfeng on 19/1/15.
  */
object  ObjectCount1 {

  def main(args: Array[String]): Unit = {
    val conf:SparkConf=new SparkConf().setAppName("ObjectCount1").setMaster("local[2]")
    val sc:SparkContext=new SparkContext(conf)

    val file:RDD[String] =sc.textFile("/Users/wangfeng/Desktop/Data/urlData.log")
    val urlAndOne:RDD[(String,Int)]=file.map(line =>{
      val filelds=line.split(" ")
      val url=filelds(1)
      (url,1)
    })
    println("urlAndOne:"+urlAndOne.collect().toBuffer)
    val sumUrl:RDD[(String,Int)]=urlAndOne.reduceByKey(_+_)

    //获取学科信息
    val project:RDD[(String,String,Int)]=sumUrl.map(x=>{
      val url=x._1   //url
      val count=x._2 //请求URL的次数
      val project=new URL(url).getHost
      (project ,url,count)
    })


    //用学科来分组,聚合后得到结果
    val silence=project.groupBy(_._1).mapValues(_.toList.sortBy(_._3).reverse.take(3))
    println("silence:"+silence.collect().toBuffer)

    val silence2=project.groupBy(_._1).mapValues(_.toList)
    println("silence2:"+silence2.collect().toBuffer)

    val silence3=project.groupBy(_._1)
    println("silence3:"+silence3.collect().toBuffer)
    sc.stop()
  }
}
