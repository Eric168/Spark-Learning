package day02

/**
  * Created by wangfeng on 18/11/5.
  */
class scalaLazyDemo{

}
object scalaLazyDemo1 {   //伴生对象

  def init():Unit={
    println("call init()")
  }
  def main(args: Array[String]): Unit = {

    val property=init();//没有用lazy关键字修饰的
    println("after init()")
    println(property)


  }


}

/*Scala中使用关键字lazy来定义惰性变量，实现延迟加载(懒加载)。
惰性变量只能是不可变变量，并且只有在调用惰性变量时，才会去实例化这个变量。*/
object scalaLazyDemo2 {   //伴生对象

  def init():Unit={
    println("call init()")
  }
  def main(args: Array[String]): Unit = {

    lazy val property=init();//使用lazy关键字修饰的
    println("after init()")
    println(property)


  }

  /*一般在java中单例模式会使用懒加载,java没有懒记载模式,需要自己实现  比如数据库创建链接时一般就是这种模式,先不加载实例,要操作数据库时再创建链接,数据库实例临时加载
  public class LazyDemo {

  private String property;

public String getProperty() {
  if (property == null) {//如果没有初始化过，那么进行初始化
    property = initProperty();
  }
  return property;
}

  private String initProperty() {
    return "property";
  }
}
   *
   */


}
