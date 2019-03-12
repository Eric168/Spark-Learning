package day06

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangfeng on 18/11/19.
  */
object SparkWC {

  def main(args: Array[String]): Unit = {

    //配置信息类
    val conf:SparkConf= new SparkConf().setAppName("SparkWC").setMaster("local")  //new SparkConf().setAppName("SparkWC").setMaster("local[*]") 有多少空闲cpu就启动多少线程

                                                                                 //new SparkConf().setAppName("SparkWC").setMaster("local[2]") 启动两个线程

                                                                                 //.setMaster("local") 打包上传前,应该去掉此句,否则永远是本机多线程模拟的在跑
    //上下文对象
    val sc:SparkContext=new SparkContext(conf)

    //读取数据
    val lines:RDD[String]=sc.textFile(args(0))

    //处理数据
    val words:RDD[String]=lines.flatMap(_.split(" "))
    val paired:RDD[(String,Int)]=words.map((_,1))
    val reduced:RDD[(String,Int)]=paired.reduceByKey(_+_)
    val res:RDD[(String,Int)]=reduced.sortBy(_._2,true)

    //保存
    //res.saveAsTextFile(args(1))
    //打印结果
    println(res.collect().toBuffer)

    println("lines:"+lines)

    val rdd2=sc.parallelize(Array(1,2,3,4,5,6,7))
    println("rdd2:"+rdd2)



    //结束任务
    sc.stop()

  }

}

/*
 *spark生成RDD的两种方式:
 * 1.sc.textFile
 * 2.sc.parallerlize
 */