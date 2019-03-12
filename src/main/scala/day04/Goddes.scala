package day04
import java.util.Comparator

/**
  * Created by wangfeng on 18/11/16.
  */
object ImplicitContext{
  implicit  object OderingGirl extends Ordering[Girl]{
    override def compare(x:Girl,y:Girl):Int=if(x.faceValue>y.faceValue) 1 else -1
  }

}
class Girl(val name:String,val faceValue:Int){

}
class MyGirl(val name:String,val age:Int,val faceValue:Int){

}

class Goddes[T:Ordering](val v1:T,val v2:T) {

  def choose()(implicit ord:Ordering[T])=if(ord.gt(v1,v2)) v1 else v2
}

object Goddes{

  def main(args: Array[String]): Unit =  {

      import ImplicitContext.OderingGirl

      val g1=new Girl("彪子",90)
      val g2=new Girl("小二",80)

      val goddes=new Goddes(g1,g2)
      val res=goddes.choose()
      println(res.name)

  }
  /*
   *范型:
   *     [B  <: A] UpperBound 上界:B类型的上界是A类型,即B类型的父类是A类型
   *     [B  >: A] LowerBound 下界:B类型的下界是A类型,即B类型的子类是B类型
   *     [B  <% A] ViewBound 表示B类型要转换成A类型,需要一个隐式转换函数
   *     [B  : A]  ContextBound 需要一个隐式转换的值
   *
   *     [-A,+B]
   *         [-A] 逆变,作为参数类型,如果A是T的子类,那么C[B] 是C[A]的子类
   *         [+B] 协变,作为返回类型,如果B是T的子类,那么C[B] 是C[T]的子类
   */
}
