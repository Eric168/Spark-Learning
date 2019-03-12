package day07

import java.net.URL

import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * Created by wangfeng on 19/1/15.
  * 缓存机制
  * 自定义一个分区器
  * 按照每种学科数据放到不同的分区器里
  */
object ProjectCount {
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
    val cacheProject:RDD[(String,(String,Int))]=sumUrl.map(x=>{
      val url=x._1   //url
      val count=x._2 //请求URL的次数
      val project=new URL(url).getHost
      (project ,(url,count))
    }).cache()


    //用学科来分组,聚合后得到结果
    /*val silence=project.groupBy(_._1).mapValues(_.toList.sortBy(_._3).reverse.take(3))
    println("silence:"+silence.collect().toBuffer)

    val silence2=project.groupBy(_._1).mapValues(_.toList)
    println("silence2:"+silence2.collect().toBuffer)

    val silence3=project.groupBy(_._1)
    println("silence3:"+silence3.collect().toBuffer)*/

    //调用spark自带的分区器此时会发生哈希碰撞,需要自定义分区器
    val res:RDD[(String,(String,Int))]=cacheProject.partitionBy(new HashPartitioner((3)))
    res.saveAsTextFile("/Users/wangfeng/Desktop/Data/out") // 会发生数据倾斜问题

    //得到所有学科
    val projects:Array[String]=cacheProject.keys.distinct().collect()
    val partioner:ProjectPartition=new ProjectPartition(projects)
    val partitioned:RDD[(String,(String,Int))]=cacheProject.partitionBy(partioner)

    //对每个分区的数据进行排序并取top3
    val res2:RDD[(String,(String,Int))]=partitioned.mapPartitions(it => {
      println("it->tolist:" + it.toList)
      it.toList.sortBy(_._2._2).reverse.take(3).iterator
    })
    res2.saveAsTextFile("/Users/wangfeng/Desktop/Data/out2")

    partitioned.saveAsTextFile("/Users/wangfeng/Desktop/Data/out3")



    sc.stop()
  }

 class ProjectPartition(projects:Array[String]) extends Partitioner{

   private val projectsAndPartNum=new scala.collection.mutable.HashMap[String,Int]
   var n=0
   for(pro <- projects){
     projectsAndPartNum+=(pro -> n)
     n +=1
   }

   override def numPartitions: Int = projects.length

   override def getPartition(key: Any): Int = {
     projectsAndPartNum.getOrElse(key.toString,0)
   }

 }
}
