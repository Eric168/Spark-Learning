package day05

import scala.actors.Actor

/**
  * Created by wangfeng on 18/11/19.
  */
object ActorDemo1 extends Actor{
  //重写act方法
  override def act(): Unit = {
    for(i<- 1 to 20){
      println("acor:"+i)
      Thread.sleep(1000)
    }
  }

}
object ActorDemo2 extends Actor{

  override def act(): Unit = {
    for(i<- 1 to 20){
      println("acor2:"+i)
      Thread.sleep(1000)
    }
  }

}


object ActorDemoTest{

  def main(args: Array[String]): Unit = {
    //启动Actor
    ActorDemo1.start()
    ActorDemo2.start()



  }
}
