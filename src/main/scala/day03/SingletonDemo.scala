package day03

import scala.collection.mutable.ArrayBuffer

/**
  * Created by wangfeng on 18/11/6.
  * 在Scala中是没有静态方法和静态字段的,但是可以使用object关键字加类名的语法结构实现同样的功能
  * 1.工具类,存放常量和工具方法
  * 2.实现单例模式
  */
object SingletonDemo {

  def main(args: Array[String]): Unit = {

    val factory=SessionFactory
    println(factory.getSession)
    println(factory.getSession.size)
    println(factory.getSession(0))
    println(factory.removeSession)
  }

}

object SessionFactory{

  println("SessionFactory被执行了")
  var i=5
  private val session=new ArrayBuffer[Session]()
  while(i>0){
    session += new Session
    i-=1
  }
  def getSession=session
  def removeSession:Unit={
    val s=session(0)
    session.remove(0)
    println("session被移除:"+s)

  }



}

class Session{}

