package day03

/**
  * Created by wangfeng on 18/11/6.
  * apply方法通常称为注入方法,在伴生对象里做一些初始化的操作
  * apply方法的参数列表不需要和构造器的参数列表统一
  * unapply方法通常成为提取方法,使用unapply方法来提取固定数量的对象
  * unapply方法会返回一个序列(Option),内部产生了一个Some对象用来存放一些值
  * apply方法和unapply方法会被隐式的调用
  */
class ApplyDemo(val name:String,var age:Int,var faceValue:Int) {

}
object ApplyDemo{
  def apply(name: String,age: Int,gender:String,faceValue: Int): ApplyDemo = new ApplyDemo(name,age,faceValue)//注入方法

  def unapply(applyDemo: ApplyDemo): Option[(String, Int, Int)] = {
    if(applyDemo==null){
      None
    }else{
      Some(applyDemo.name,applyDemo.age,applyDemo.faceValue)
    }
  }
}
object Test3{

  def main(args: Array[String]): Unit = {
    val applyDemo = ApplyDemo("xiaohong", 12,"男", 98) //调用apply方法

    applyDemo match {
      case ApplyDemo("xiaohong", age, faceValue) => println(s"age: $age")
      case _ => println("no match nothing")
    }
  }


}
