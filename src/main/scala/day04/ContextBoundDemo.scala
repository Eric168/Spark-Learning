package day04

/**
  * Created by wangfeng on 18/11/16.
  */
class ContextBoundDemo[T:Ordering]{

    def select(first:T,second:T):T={
      val ord:Ordering[T]=implicitly[Ordering[T]]
      if(ord.gt(first,second)) first else second
    }
}

object ContextBoundDemo{

  def main(args: Array[String]): Unit = {

    import day04.MyPredef.OrderingGirl

    val contextBoundDemo=new ContextBoundDemo[MyGirl]

    val g1=new MyGirl("daya",40,90)
    val g2=new MyGirl("erya",38,90)
    val res=contextBoundDemo.select(g1,g2)
    println(res.name)
  }
}