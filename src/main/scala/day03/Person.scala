package day03

/**
  * Created by wangfeng on 18/11/6.
  */
/*
 *声明类时不需要加public关键字,默认就是public
 * 一个类文件可以声明多个类
 */
class Person {

  //用val修饰的变量是只读的,相当于只有get方法,没有set方法
  val id:String="100"

  //用var修饰的变量相当于既有get方法又有set方法
  var name:String=_

  //用private修饰的属性,该属性属于对象私有变量,只有在本类和其伴生对象中访问
  private var age:Int= _

  //用private[this]修饰后,该属性属于对象私有变量,只能本类访问,其伴生对象中不能访问
  private[this] val gender="男"

}
object Person{//伴生对象

  def main(args: Array[String]): Unit = {
    val p=new Person()

    //p.id="123"  此处编不可修改译不通过  用val修饰的属性只可读
    println(p.name)
    println(p.age)
    //println(p.gender)  private[this]修饰的,其伴生对象不能访问

    p.name="jingjing"
    p.age=26

    println(p.name)
    println(p.age)

  }
}
object test1{//不是伴生对象

  def main(args: Array[String]): Unit = {
    val p=new Person()

    //p.id="123"  此处编不可修改译不通过  用val修饰的属性只可读
    println(p.name)
    //println(p.age) 不能访问,因为不是本类也不是伴生对象

    p.name="jingjing"
    //p.age=26

    println(p.name)

  }
}
