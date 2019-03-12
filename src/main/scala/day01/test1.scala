package day01

import scala.collection.immutable.HashSet
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer

/**
  * Created by wangfeng on 18/11/2.
  */

object Test01{
  def main(args: Array[String]): Unit = {
    val str="aaa"// 不可变的的变量声明   一般较推荐使用
    //str="bbb" 此处代码报错,编译不过
    println("test:"+str)

    var str2="aaa" //可变的变量声明
    str2="bbbb"
    println("test2:"+str2 )



    val str3:Int=123
    println("test4:"+str3)


    //scala的值类型有七种 (无引用类型)
      //  1.  Byte
      //  2.  Char
      //  3.  Short
      //  4.  Int
      //  5.  Long
      //  6.  Float
      //  7.  Double

    val x=3

    var y= if (x>1) 1 else -1
    println(y)

    val z=if(x<1) 1 else "error"   //混合类型的表达式
    println(z)


    val kk=if(x<1) 1      // AnyVal=()
    println(kk);


    val bb=if(x>1) 1 else if(x==1) 0 else -1

    println(bb);


    val zz=if(x<1) 1  else ()      // AnyVal=()  Unit  空 相当于java里的void
    println(zz);


    var aaa=1 to 10
    println(aaa)

    var bbb=1.to(10)
    println(bbb)

    var ccc=1 until 10
    println(ccc)

    var ddd=1.until(10)
    println(ddd)

    for(i<- 1 to 10){
      println(i)
    }

    val arr=Array("java","scala","C#");
    for(i <- arr) println(i)


    for(i<- 1 to 3; j <- 1 to 3) {if(i!=j) println(i*10+j)}
    for(i<- 1 to 3; j <- 1 to 3 if(i!=j)) println(i*10+j)
    for(i<- 1 to 3; j <- 1 to 3) if(i!=j) println(i*10+j)   //三种方式一样


    val res=for(i <- 1 until 10) yield i   // yield  关键字  会把每次循环生成的值放到集合中
    println(res)

    val  sum=1.+(2)
    println(sum)
    println(1.0+(2))

    println(m1(1000,2))

    println(f1)
    println(f1(1000,2))


    m2(f1)

    println("1111111111");

    val f2= m1 _
    m2(f2)
    println(m2(m1))   //可以隐式的将方法转化为函数作为参数使用 ,类似于上面的两个部门 98,99



    val arr3 =new Array[String](8)
    println("arr3:"+arr3);
    println("arr3:"+arr3.toBuffer);

    val arr4=Array("java","c","scala");
    println("arr4:"+arr4.toBuffer);
    println(arr4(0))

    var arr5=ArrayBuffer[Int]()
    arr5+=1
    arr5+=(2,3,4)
    arr5++=Array(5,6)
    arr5++=ArrayBuffer(7,8)
    println("arr5:"+arr5.toBuffer)

    arr5.insert(0,-1,0)
    println("arr5:"+arr5.toBuffer)

    arr5.insert(7,-3,-4)
    println("arr5:"+arr5.toBuffer)

    arr5.remove(4,4)
    println("arr5:"+arr5.toBuffer)

    var arr6=Array(1,2,3,4,5,6,7,8,9)
    for(i <- arr6) println(i)

    var arr7=Array(1,2,3,4,5,6,7,8,9)
    for(i <-0 until arr7.length) println(arr7(i))

    for(i<- (0 until arr7.length).reverse) println(arr7(i))

    val res2=for(i <- arr7) yield i*10
    println("res2:"+res2.toBuffer)

    println(arr7.max)
    println(arr7.min)

    val arr8=Array(2,8,1,3,9,0);
    println(arr8.sorted.toBuffer)

    val map1=Map("scala" -> 1,"java" -> 2,"C#" -> 3)
    println("map1:"+map1)

    val map2=Map(("sala",1),("java",2),("c#",3)) //默认引用的是scala.collection.imutable.Map 是不可变的 ,必须引用第三方的scala.collection.mutable.Map才可以重新赋值
    println(map2)

    println(map1("scala"))

    map1("scala")=1001
    println(map1("scala"))

    println(map1.getOrElse("asd",-1))//获取key值能获取到就获取,获取不到对应key的话就默认返回-1

    //val是修饰的变量是引用不可变,而不是引用对象里的值不可变,即引用对象里的值还是可变的


    //Scala的集合有三大类   Seq(序列) Set(集) Map(映射)
      //1.序列

    val list1=List(1,2,3) //不可变序列
    val list2=0::list1
    println("lsit2:"+list2)

    val list3=list1.::(4)
    println("list3:"+list3)

    val list4= -1+:list1
    println("list4:"+list4)

    val list5=list1.+:(9)
    println("list5:"+list5)

    val list6=list1:+8
    println("list6:"+list6)

    val list7=List(5,6,7)
    val list8=list1 ++ list7
    println(list8)

    val list9=list7++list1
    println(list9)

    val list10=list1++:list7
    println(list10)

    val list11=list1 :::list7
    println(list11)

    val list12=ListBuffer(1,2,3)
    list12.append(4)
    println(list12)
    val list13=ListBuffer(4,5,6)
    list13.appendAll(list12)
    println(list13)

    list12++=list13
    println(list12)
    list12+=101
    println(list12)

    //元组
    val t=("scala",100L,3.14,("spark",1))
    println(t._1)  //元组的下标是从1开始的  和数组不一样的
    println(t._4._1)
    val tt,(a1,b1,c1,d1)=("scala",100L,3.14,("spark",1))
    println(d1)

    val arr20=Array(("tingting",23),("ningning",27),("xiaoyueyue",29))
    println(arr20.toMap)

    val arr21=Array("tingting","ningning","xiaoyueyue")
    val arr22=Array(24,25,26)
    println((arr21 zip arr22).toBuffer)
    println((arr22 zip arr21).toBuffer)

    val arr23=Array("tingting","ningning")
    val arr24=Array(24,25,26)
    println((arr23 zip arr24).toBuffer)
    println((arr24 zip arr23).toBuffer)


    val set1=new HashSet[Int]()
    println(set1)

    val set2=set1+1
    println(set2)
    val set3=set2 ++ Set(2,3,4)
    println(set3)



















  }

  def m1(x:Int,y:Int):Int=x+y  //声明一个方法  在Scala中,函数是函数,方法是方法

  val f1=(x:Int,y:Int)=>x+y     //声明一个函数



  def m2(f:(Int, Int)=>Int)=f(3,4)





}
