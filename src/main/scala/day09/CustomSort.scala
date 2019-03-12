package day09

import org.apache.spark.{SparkConf, SparkContext}
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
  * Created by wangfeng on 19/1/24.
  */
object MySort{
  implicit  val girlOrdering =new Ordering[Girl]{

    override def compare(x: Girl, y: Girl): Int = {
      if(x.faceValue!=y.faceValue){
        x.faceValue-y.faceValue
      }else{
        /*if(x.age>y.age){
          y.age-x.age
        }else{
          x.age-y.age
        }*/
          y.age-x.age
      }
    }

  }
}

object CustomSort{

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("CustomSort").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val girlInfo=sc.parallelize(Array(("tingting",80,26),("mimi",90,22),("ningning",70,30),("xiaofang",70,40),("xiaohei",70,20),("xiaoqi",70,50),("xiaoliu",70,10),("xiaofang",90,30)))

    val res=girlInfo.sortBy(_._2,false)

    System.out.println("res:"+res.collect().toBuffer)

    //第一种排序方式
    import  MySort.girlOrdering
    val res2=girlInfo.sortBy(x=>Girl(x._2,x._3),false)
    System.out.println("res2:"+res2.collect().toBuffer)


    //第二种排序方式
    val res3=girlInfo.sortBy(x=> Girl2(x._2,x._3),false)
    System.out.println("res3:"+res3.collect().toBuffer)


  }
}
case class Girl(faceValue:Int,age:Int)
case class Girl2(faceValue:Int,age:Int) extends Ordered[Girl2]{

  override def compare(that: Girl2): Int = {
     if(this.faceValue!=that.faceValue){
       this.faceValue-that.faceValue
     }else{
       that.age-this.age
     }
  }
}
