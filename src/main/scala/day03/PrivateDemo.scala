package day03

/**
  * Created by wangfeng on 18/11/6.
  * 类名前加private[包名] 是指包访问权限,是只有day03这个包有访问权限
  * 构造器参数列表前加private是指伴生对象的权限,只有伴生对象才能访问
  */

private [day03]class PrivateDemo private(val gender:Int,var faceValue:Int){

  private def sayHello():Unit={
    println(s"jingjing age: $gender")
  }

}
object PrivateDemo{
  def main(args: Array[String]): Unit = {

    val p=new PrivateDemo(12,89)
    p.sayHello()
  }
}
object test3{
  def main(args: Array[String]): Unit = {
    //val q=new PrivateDemo(34,100)//访问不到,非伴生对象无权限访问
    //println("q:"+q.gender)//访问不到
  }
}


