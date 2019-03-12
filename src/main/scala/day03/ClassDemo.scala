package day03

/**
  * Created by wangfeng on 18/11/6.
  */
object ClassDemo {

  def main(args: Array[String]): Unit = {

    val human=new Human
    println("1:"+human.distance)
    println("2:"+human.fly)
    println("3:"+human.run)

  }
}

/*
 *特质  (和java中的接口非常像)
 */
trait Flyable{
  //声明一个没有值得字段
  val distance:Int

  //声明一个没有实现的方法
  def fight:String

  //声明一个实现的方法
  def fly:Unit={
    println("I can fly")
  }
}
/*
 *抽象类
 */
abstract class Animal{
  //声明一个没有赋值的字段
  val name:String

  //声明一个没有实现的方法
  def run:String

  //声明一个实现的方法
  def climb:String={
    "I can fly"
  }
}
class Human extends Animal with Flyable{  //with相当于java中的implements

  override val name: String = "张三"

  //重写了抽象泪中没有实现的方法
  override def run: String ="I can run"

  override val distance: Int = 1000

  //实现了抽象类中没有实现的方法 可以不用override关键字,也可以用
  def fight: String = "with 钉锤"

  //实现了特质中有实现的方法
  override def fly: Unit = println("override fly")

}
class Human2 extends Flyable{//不继承抽象类,直接定义一个特质 来实现类
  override val distance: Int =  0

  //声明一个没有实现的方法
  override def fight: String = "0"
}