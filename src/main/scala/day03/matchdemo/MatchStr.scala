package day03.matchdemo

import scala.util.Random

/**
  * Created by wangfeng on 18/11/6.
  */
object MatchStr{

  def main(args: Array[String]): Unit = {

    val arr=Array("123","210","340","498")
    val name=arr(Random.nextInt(arr.length))
    println(name)

    name match{
      case "123"=>println("6")
      case "210"=>println("3")
      case "340"=>println("7")
      case "498"=>println("21")
      case  _ =>println("0")
    }

  }
}