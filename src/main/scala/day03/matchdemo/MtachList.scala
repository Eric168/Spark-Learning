package day03.matchdemo

/**
  * Created by wangfeng on 18/11/6.
  * 匹配数组,元组,集合
  */
object MtachList {

  def main(args: Array[String]): Unit = {
    //匹配数组
    val arr=Array(3,2,5,7)
    arr match {
      case Array(4,a,b,c) =>println(s"case:$a,$b,$c")
      case Array(_,x,y,z)=>println(s"case:$x,$y")
      case _ =>println("Not matched")
    }

    //匹配元组
    val tup=(2,3,4)

    tup match{
      case (2,a,b)=>println(s"case:$a,$b")
      case (_,x,y)=>println(s"case:$x,$y")
      case _ =>println("not matched")
    }
    //匹配集合
    val list1=List(0,1,2,3)
    list1 match{
      case List(0,a,b,c)=>println(s"$a,$b,$c")
      case 0::Nil =>println("case:0")//Nil  List的空集合
      case a::b::c::d::Nil=>println(s"$a,$b,$c,$d")
      case List(0,a,b,c)=>println("$a,$b,$c")
      case  0::a=>println(s"$a")
      case _=>println("not matched")
    }

  }

}
