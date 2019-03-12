package day05

import scala.actors.{Actor, Future}

/**
  * Created by wangfeng on 18/11/19.
  */
/**
  * 为什么学习Actor(Akka就是一个类库)
  *
  *
  * 什么是Actor
  * Actor是消息并发模型
  *   scala中的Actor能够实现并行变成的强大功能,它是基于事件模型的并发机制
  *   scala是运用消息(message)的发送,接收来实现多线程的
  *   使用scala能够更容易地实现多线程应用的开发
  *
  * java并发变成与scala Actor编程的区别
  *    scala的Actor类似于java中的多线程编程,但是不同的是,scala的actor提供的模型与多线程有所不同
  *    scala的Actor尽可能地避免锁和共享状态,从而避免多线程并发时出现资源争用的情况,进而提升多线程编程的性能
  *    此外 scala Actor 的这种模型还可以避免死锁等一系列传统多线程编程的问题
  *    原因就在于java中多数使用的是可变状态的对象资源,对这些资源进行共享来实现多线程编程的话,控制好资源竞争与防止对象状态被意外修改是非常重要的,而对象状态的不变性也是较难以保证的
  *    而在scala中,我们可以通过复制不可变状态的资源(即对象,scala中一切都是对象,连函数,方法也是)的一个副本
  *    再基于Actor的消息发送,接受机制进行并行编程
  *
  * Actor 方法执行顺序
  *    1.调用start()方法启动Actor
  *    2.执行act()方法
  *    3.向Actor发送消息
  *
  * 发送消息的方式
  *   ! -->发送异步消息,没有返回值
  *   !? -->发送同步消息,等待返回值
  *   !! -->发送异步消息,返回值是 Future[Any]
  *
  *
  * 掌握的内容:
  *   1.创建Actor
  *   2.Actor的消息接受和发送
  *   3.用Actor并发编程实现WordCount
  */
class ActorDemo3 extends Actor{

  override def act(): Unit = {
    while(true){
      //偏函数
      receive{
        case "start" =>println("starting......")
        case AsynMsg(id,msg) =>{
          println(s"id:$id,AsyncMsg:$msg")
          Thread.sleep(2000)
          sender ! ReplyMsg(1,"success")
        }
        case SyncMsg(id,msg)=>{
          println(s"id:$id,SyncMsg:$msg")
          Thread.sleep(2000)
          sender ! ReplyMsg(2,"success")
        }

      }
    }
  }
}

case class AsynMsg(id:Int,msg:String)
case class SyncMsg(id:Int,msg:String)
case class ReplyMsg(id:Int,msg:String){
  println("  ")
}

object ActorDemo3{
  def main(args: Array[String]): Unit = {
    val actorDemo3:ActorDemo3=new ActorDemo3
    actorDemo3.start()

    //异步发送消息,没有返回值
    actorDemo3 !AsynMsg(1,"hi honey")
    println("没有返回值的异步消息发送完成")


    //同步发送消息,线程等待返回值
    val res:Any=actorDemo3 !? SyncMsg(2,"hi,tingting")

    println("有返回值的同步消息发送完成")
    println("content:"+res)


    //异步发送消息,有返回值,返回类型是Future[Any]

    val replay:Future[Any]=actorDemo3 !! AsynMsg(3,"hi jingjing")//   no class found Exception:scala.runtime.AbstractPartialFunction$mcVL$sp  将Scala jar包由2.11.8切换至2.10.0即可
    Thread.sleep(3000)
    if(replay.isSet){
      val value=replay.apply()
      println(value)
    }else{
      println("none")
    }
  }
}