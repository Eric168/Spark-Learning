package day08

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangfeng on 19/1/24.
  */
object CheckPointTest {


  def main(args: Array[String]): Unit = {

    //配置信息类
    val conf:SparkConf= new SparkConf().setAppName("SparkWC").setMaster("local")  //new SparkConf().setAppName("SparkWC").setMaster("local[*]") 有多少空闲cpu就启动多少线程

    //new SparkConf().setAppName("SparkWC").setMaster("local[2]") 启动两个线程

    //.setMaster("local") 打包上传前,应该去掉此句,否则永远是本机多线程模拟的在跑
    //上下文对象
    val sc:SparkContext=new SparkContext(conf)

    val res=sc.textFile("/Users/wangfeng/Desktop/a.txt").flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    sc.setCheckpointDir("/Users/wangfeng/Desktop/ck")
    res.cache()
    res.checkpoint()
    res.collect()

    sc.stop()

  }




}
