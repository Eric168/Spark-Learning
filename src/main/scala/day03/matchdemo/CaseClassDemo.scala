package day03.matchdemo

import scala.util.Random

/**
  * Created by wangfeng on 18/11/6.
  * 样例类
  */
object CaseClassDemo {
  def main(args: Array[String]): Unit = {

    val arr=Array(CheckTimeOutTask,SubmitTaks("1000","test-0001"),HeartBeat(3000))
    arr(Random.nextInt(arr.length)) match{
      case CheckTimeOutTask =>println("check")
      case SubmitTaks(id,taskName) =>println("submit")
      case HeartBeat(time)=>println("heartBeat")
    }
  }

}
case class HeartBeat(time:Long)//多例
case class SubmitTaks(id:String,taskName:String)//多例
case object CheckTimeOutTask  //单例 可以不用给参数