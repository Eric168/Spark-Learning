package day12

import org.apache.hadoop.hive.ql.exec.persistence.HybridHashTableContainer.HashPartition
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by wangfeng on 19/1/28.
  */
object StreamingWC {

  def main(args: Array[String]): Unit = {

    val conf=new SparkConf().setAppName("StreamingWC").setMaster("local[2]") // 一定不要写小于两个线程,对于streaming,一个是接受数据的线程,一个是处理数据的线程

    val ssc=new StreamingContext(conf,Seconds(1))

    ssc.checkpoint("/Users/wangfeng/Desktop/ck2")
    val dStream=ssc.socketTextStream("127.0.0.1",8888)
    val tuples:DStream[(String,Int)]=dStream.flatMap(_.split(" ")).map((_,1))

    val res:DStream[(String,Int)]=tuples.updateStateByKey(func,new HashPartitioner(ssc.sparkContext.defaultMinPartitions),false)

    res.print()
    ssc.start()
    ssc.awaitTermination()

  }

  val func=(it:Iterator[(String,Seq[Int],Option[Int])])=>{
    it.map(t=>{
      (t._1,t._2.sum+t._3.getOrElse(0))
    })
  }

}
