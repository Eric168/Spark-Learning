package day07

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangfeng on 18/12/27.
  */
object SparkRDDTest {

  def main(args: Array[String]): Unit = {

    val conf:SparkConf= new SparkConf().setAppName("SparkWC").setMaster("local");
    val sc=new SparkContext(conf);

    val rdd1=sc.parallelize(List(5,6,4,3,7,8,9,10))

    //将每个元素*2 再排序
    val rdd2=rdd1.map(_ * 2).sortBy(x =>x ,true)

    println("rdd2:"+rdd2.collect().toBuffer)

    //将其过滤出大于等于10的数据

    val rdd3=rdd2.filter(_ >=10)
    println("rdd3:"+rdd3.collect().toBuffer)

    val rdd4=sc.parallelize(Array("a b c","d e f","h i j"))
    val rdd5=rdd4.flatMap(_.split(" "))
    println("rdd5:"+rdd5.collect().toBuffer)

    val rdd6=sc.parallelize(List(List("a,b,c","a d c"),List("c f g","f k j"),List("k l m","m n b")))
    val rdd7=rdd6.flatMap(_.flatMap(_.split(" ")))
    println("rdd7:"+rdd7.collect().toBuffer)


    //求并集
    val rdd8=sc.parallelize(List(3,4,5,2))
    val rdd9=sc.parallelize(List(2,1,4,3))
    val union=rdd8 union rdd9
    println("union:"+union.collect().toBuffer)

    //求交集
    val rdd10=sc.parallelize(List(("tom",1),("jefrry",4),("kitty",2)))
    val rdd11=sc.parallelize(List(("jefrry",2),("tom",12),("shuke",3)))
    println("join:"+(rdd10 join rdd11).collect().toBuffer)

    //求左连接和右连接
    val res1=rdd10.leftOuterJoin(rdd11)
    val res2=rdd10.rightOuterJoin(rdd11)

    println("res1:"+res1.collect().toBuffer);
    println("res2:"+res2.collect().toBuffer);

    val res3=rdd10 union(rdd11)
    println("res3:"+res3.groupByKey().collect().toBuffer)

    println("res4:"+res3.groupByKey().mapValues(_.sum).collect().toBuffer)

    println("res5:"+res3.reduceByKey(_ + _).collect().toBuffer)

    println("res6:"+rdd10.cogroup(rdd11).collect().toBuffer)


    val  rdd12=sc.parallelize(List(("tom",1),("jefrry",3),("kitty",2),("shuke",1)))
    val  rdd13=sc.parallelize(List(("jefrry",2),("tom",3),("shuke",2),("kitty",5)))
    val  rdd14=rdd12.union(rdd13)

    //按value的降序排列
    val res=rdd14.reduceByKey(_+_).map(t=>(t._2,t._1)).sortByKey(false).map((t=>(t._2,t._1)))
    println("res:"+res.collect().toBuffer)

    //笛卡尔积
    println(rdd12.cartesian(rdd13).collect().toBuffer)

    println("rdd14:"+rdd14.collect().toBuffer)
    //count
    println("count:"+rdd14.count())

    println("top:"+rdd14.top(10).toBuffer)

    println("take:"+rdd14.take(3).toBuffer)

    println("first:"+rdd14.first())

    println("takeOrdered:"+rdd14.takeOrdered(10).toBuffer)

    implicit val myOrd = implicitly[Ordering[Int]].reverse
    println("top2:"+rdd14.sortBy(x=>x._2,false,1).top(10).toBuffer)

    //res6:ArrayBuffer((tom,(CompactBuffer(1),CompactBuffer(12))), (shuke,(CompactBuffer(),CompactBuffer(3))), (jefrry,(CompactBuffer(4),CompactBuffer(2))), (kitty,(CompactBuffer(2),CompactBuffer())))
  }
}
