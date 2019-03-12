package day03

/**
  * Created by wangfeng on 18/11/6.
  * 与类名相同并且用object修饰的对象叫做伴生对象
  * 类和其伴生对象之前可以相互访问私有的方法和属性
  */
class Dog {

  private var name="二哈"
  def printName:Unit={
    //在Dog类中访问其伴生对象的私有属性
    println("dog伴生伴生对象的变量:"+Dog.CONSTANT+";name:"+name)
  }
}

/*
 *伴生对象
 */
object  Dog{
  private val CONSTANT="汪汪汪"

  def main(args: Array[String]): Unit = {

    val dog=new Dog
    dog.printName
    println("name:"+dog.name)
    dog.name="小黄"
    println("name2:"+dog.name)

  }
}


