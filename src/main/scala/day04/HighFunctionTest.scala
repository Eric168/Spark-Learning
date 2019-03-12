package day04

/**
  * Created by wangfeng on 18/11/6.
  * 高阶函数
  * 接受一个或多个函数作为输入
  * 输出一个函数
  */
class HighFunctionTest {

}
object HighFunctionTest{

  def main(args: Array[String]): Unit = {
    val res:Int=func(4)
    println(s"res:$res")

    val arr=Array(1,2,3,4,5,6)
    val result=arr.map(x=>func(x))
    println("result:"+result.toBuffer)

    val result2=arr.map(x=>m1(x))
    println("result2:"+result2.toBuffer)

    val result3=arr.map(func)
    println("result3:"+result3.toBuffer)

    val result4=arr.map(m1)
    println("result4:"+result4.toBuffer)
  }


  //函数
  val func:Int=> Int=x=>x*x
  //方法
  def m1(x:Int):Int=x*x

}