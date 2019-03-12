package day03

/**
  * Created by wangfeng on 18/11/6.
  * 主构造器的参数列表要放到类名的后面,和类名放在一起
  * val修饰的构造参数具有不可变性,var修饰的构造参数具有可变性
  * 此时声明的facaValue:Int 只能在本类调用,伴生对象也无法调用
  * faceValue虽然没有用val和var修饰,默认是val
  */
//对于scala而言,没有构造函数,也没有构造方法,只有构造器这么一说
class StructDemo(val name:String,var age:Int,faceValue:Int=90){
  var gender:String = _

  def getFaceValue:Int={
    //faceValue=100 编译不通过,此时值是不可更改的,默认是val修饰的
    faceValue //默认最后一行代码是返回值
  }
  //辅助构造器
  def this(name:String,age:Int,faceValue:Int,gender:String){

     this(name,age,faceValue)//辅助构造器的第一行必须先调用主构造器
     this.gender=gender
  }
}
object StructDemo{

  def main(args: Array[String]): Unit = {

    val s=new StructDemo("jingjing",26)
    println(s.name)
    println(s.age)
    //println(s.faceValue)   faceValue没用val ,var修饰,所以无法访问

    //s.name="cc"  无法赋值,因为是用val 修饰的

    s.age=31
    println(s.age)
    println("s.getFaceValue:"+s.getFaceValue)

    var q=new StructDemo("ningning",26,98,"女")
    println(q.age)
    println(q.getFaceValue)
    println(q.name)
    println(q.gender)




  }
}
