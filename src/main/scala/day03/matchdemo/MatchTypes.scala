package day03.matchdemo

import scala.util.Random

/**
  * Created by wangfeng on 18/11/6.
  */
object MatchTypes {

  def main(args: Array[String]): Unit = {
    val arr=Array("123",100,3,14,true,new MatchTest)
    val element=arr(Random.nextInt(arr.length))


    element match {
      case str:String => println(s"mathch String:$str")
      case int:Int =>println(s"match Int:$int")
      case bool:Boolean =>println(s"match Boolean:$bool")
       case matchTest:MatchTest =>println(s"match MatchTest:$matchTest")
      case _ :Any =>println("match nothing")
    }
  }

}
class MatchTest{

}
