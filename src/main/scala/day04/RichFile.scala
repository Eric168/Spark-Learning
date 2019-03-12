package day04

import scala.io.Source

/**
  * Created by wangfeng on 18/11/16.
  */
class RichFile(val file:String){

  def read():String={
    Source.fromFile(file).mkString
  }
}
object RichFile {

  def main(args: Array[String]): Unit = {

    //这个是显示的实现了read方法
    val file = "/Users/wangfeng/Desktop/当前流程宽表缺失字段.txt"
    val content: String = new RichFile(file).read()
    println(content)


    //隐式的实现read方法(隐式转换)
    import day04.MyPredef.fileToRichFile
    val content2=file.read()
    println(content2)



  }
}

