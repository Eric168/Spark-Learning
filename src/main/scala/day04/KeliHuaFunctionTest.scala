package day04

/**
  * Created by wangfeng on 18/11/7.
  * 柯里化函数  多个参数转换成一个参数输入
  */
object Context{

   implicit val a:String="java"  //只能放在被import的上面,不能放在import代码的后面  或者单独提到一个静态类里(object),import即可
}

object KeliHuaFunctionTest {

  def main(args: Array[String]): Unit = {

    val res=currying(3)(4)
    println(s"res:$res")


    val curry=currying(3)_

    val res2=curry(5)
    println(s"res2:$res2")



    println("m2:"+m2(9)) //45
    println("m22:"+m2(9)(8)) //72

    implicit val bbbbb=100
    println("m33:"+m2(4))  //400  找方法里已经定义的implicit的对应字段  不能有多个implicit,只能识别到唯一的一个



    val arr2=Array(("scala",1),("scala", 2),("scala",3))
    println(arr2.foldLeft(0)(_ +_._2))


    val func2=currying2(11)
    val cur=func2(10)
    println(cur)


    val func5=m3("Hi~")_
    println(func5)


    import  Context.a
    println(m3("Hi~"))


  }

  //定义一个方法 柯里化的第一种定义方式
  def currying(x:Int)(y:Int)=x*y


  def m1(x:Int)(y:Int)=x*y
  def m2(x:Int)(implicit y:Int=5)=x*y  //implicit 为隐式的

  //柯里化的第二种定义方式
  def currying2(x:Int)=(y:Int)=>x*y

  def m3(str:String)(implicit  name:String="scala")={
    str+name
  }

}
/*

object Context{

  implicit val a:String="java"  //只能放在被import的上面,不能放在import代码的后面  或者单独提到一个静态类里(object),import即可  放在此处不可
}*/
