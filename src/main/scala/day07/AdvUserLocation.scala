package day07

/**
  * Created by wangfeng on 19/1/15.
  */
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.{SparkConf, SparkContext}

object AdvUserLocation {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("AdvUserLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd0 = sc.textFile("/Users/wangfeng/Desktop/Data/logs/").map(line => {
      val fields = line.split(",")
      val eventType = fields(3)
      val time = fields(1)
      val timeLong = if (eventType == "1") -time.toLong else  time.toLong
      ((fields(0),fields(2)), timeLong)
    })
    println("rdd0:"+rdd0.collect().toBuffer)

    println("rdd00:"+rdd0.reduceByKey(_+_).collect().toBuffer)



    /*  val rdd000=rdd0.map(t =>{
        val temp=t._2
        val now = new Date()
        val timeLong= if (temp<0) now.getTime + temp else temp
        ((t._1._1,t._1._2),timeLong)
      })
      println("rdd000:"+rdd000.reduceByKey(_+_).collect().toBuffer)*/

    val rdd1 = rdd0.reduceByKey(_+_).map(t =>{
    /*val rdd1 = rdd0.reduceByKey(_+_).map(t =>{*/
      val mobile = t._1._1
      val lac = t._1._2
      val time = t._2
      val dateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
      println("time:"+time)
      val tempTime=if (time<0) dateFormat.format(new Date()).toLong + time else time
      (lac, (mobile, tempTime))
    })
    println("rdd1:"+rdd1.collect().toBuffer)

    val rdd2 = sc.textFile("/Users/wangfeng/Desktop/Data/lac_info.log").map(line1 => {
      val f = line1.split(",")
      (f(0),(f(1),f(2)))
    })
    println("rdd2:"+rdd2.collect().toBuffer)

    println("rdd1 join rdd2:"+rdd1.join(rdd2).collect().toBuffer)

    val rdd3 = rdd1.join(rdd2).map( t =>{
      val lac = t._1
      val mobile = t._2._1._1
      val time = t._2._1._2
      val x = t._2._2._1
      val y = t._2._2._2
      (mobile, lac, time, x, y)
    })
    println("rdd3:"+rdd3.collect().toBuffer)

    val rdd4 = rdd3.groupBy(_._1)
    println("rdd4:"+rdd4.collect().toBuffer)

    println("rdd44:"+rdd4.mapValues( t => {t.toList}).collect().toBuffer)
    println("rdd444:"+rdd4.mapValues( t => {t.toList.sortBy(_._3)}).collect().toBuffer)
    println("rdd4444:"+rdd4.mapValues( t => {t.toList.sortBy(_._3).reverse}).collect().toBuffer)

    val rdd5 = rdd4.mapValues( t => {
      t.toList.sortBy(_._3).reverse
        /*.take(2)*/
    })
    println("rdd5:"+rdd5.collect().toBuffer)

    sc.stop()
  }
}

