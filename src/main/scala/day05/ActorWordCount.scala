package day05

import scala.actors.{Actor, Future}
import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
  * Created by wangfeng on 18/11/19.
  */
object ActorWordCount {

  def main(args: Array[String]): Unit = {

    val files:Array[String]=Array("/Users/wangfeng/Desktop/a.txt","/Users/wangfeng/Desktop/b.txt","/Users/wangfeng/Desktop/c.txt")
    val replys:ListBuffer[Future[Any]]=new ListBuffer[Future[Any]]
    val res:ListBuffer[Map[String,Int]]=new ListBuffer[Map[String,Int]]

    for(file <- files){
    /* 单线程处理
      val lines:List[String]=Source.fromFile(file).getLines().toList
      val words:List[String]=lines.flatMap(_.split(" "))
      val wordCount:Map[String,Int]=words.map((_,1)).groupBy(_._1).mapValues(_.size)*/
      val wordCountTask:WordCountTask=new WordCountTask
      wordCountTask.start()

      //接受结果数据,异步发送消息,有返回值
      val reply:Future[Any]=wordCountTask !! SmTask(file)

      replys+=reply


      while(replys.size>0){
        //过滤每个Future对象,如果None类型的,就过滤掉
        var dones:ListBuffer[Future[Any]]=replys.filter(_.isSet)
        for(done <- dones){
          res+=done.apply().asInstanceOf[Map[String,Int]]
          replys-=done
        }


      }



    }


    println("count:"+res.flatten.groupBy(_._1).mapValues(_.foldLeft(0)(_+  _._2)))
  }

}

class WordCountTask extends Actor{

  override def act(): Unit = {
    while(true){
      receive({
        case SmTask(file) => {
          val lines:List[String]=Source.fromFile(file).getLines().toList
          val words:List[String]=lines.flatMap(_.split(" "))
          val wordCount:Map[String,Int]=words.map((_,1)).groupBy(_._1).mapValues(_.size)

          //异步发送结果数据,没有返回值
          sender ! wordCount
        }
      })
    }
  }
}
case class SmTask(file:String)
