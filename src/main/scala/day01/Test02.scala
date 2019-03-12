package day01

import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet

/**
  * Created by wangfeng on 18/11/5.
  */
object Test02 {
  def main(args: Array[String]): Unit = {

    val set1=new HashSet[Int]()
    println(set1)

    set1 +=1

    println(set1)

    set1+=2
    println(set1)

    set1.add(3)
    println(set1)

    set1 ++=Set(11,23,45)

    println(set1)

    set1-=1
    println(set1)

    set1--=Set(11,23,45)
    println(set1)

    set1.remove(2)
    println(set1)

     val map1=new HashMap[String,Int]()

     map1.put("scala",1)

     println(map1)

     map1 +=(("java",2),("c++",3))

     println(map1)

    println(map1.get("scala").get)

    println(map1.get("java").get)

    if(map1.get("c").isEmpty){
      println("error")
    }else{
      println("if else:"+map1.get("c").get)

    }












  }
}
