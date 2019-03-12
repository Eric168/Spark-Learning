package day02

import java.util.Calendar

import org.apache.hadoop.hive.metastore.api.Date


/**
  * Created by wangfeng on 18/11/5.
  */
class Excercise {

}
object Excercise{

  def main(args: Array[String]): Unit = {

    //创建一个list
    val list0=List(6,3,1,7,9,2,4,5,8)

    //将list0中每个元素乘以2后生成一个新的集合
    val list1=list0.map(_ * 2)// map遍历list中的每一个元素再乘以2
    println(list1.toBuffer)

    //将list0中的偶数取出来生成一个新的集合
    val list2=list0.filter(_ %2==0)
    println(list2.toBuffer)

    //list0排序后生成一个新的集合
    val list3=list0.sorted

    println("list3:"+list3.toBuffer)

    //反转排序
    println("list4:"+list3.reverse.toBuffer)

    //讲list0中的元素4个一组,类行为Iterator(List<Int>)
    val list=list0.grouped(4)

    println("list:"+list)

    val list5=list.toList
    println("list5:"+list5)

    //将Iterator(多个list)压扁成一个list
    val list6=list5.flatten
    println("list6:"+list6)

    //将空格切分并压平
    val lines=List("hello java hello scala","hello scala","hello python")
    val words=lines.map(_.split(" "))
    println("words:"+words.flatten)

    println("words2:"+lines.flatMap(_.split(" ")))


    //并行计算求和
    val arr=Array(1,2,3,4,5,6,7,8,9,10)

    val start1 = Calendar.getInstance.getTimeInMillis.toInt
    println("arr:"+arr.sum)
    val end1=Calendar.getInstance.getTimeInMillis.toInt

    val start2=Calendar.getInstance.getTimeInMillis.toInt
    println("par arr:"+arr.par.sum)  //并行执行   和线程有关   每个线程计算一部分 (1+2+3+4)+(5+6+7+8)+(9+10)
    val end2=Calendar.getInstance.getTimeInMillis.toInt

    println("gap1:"+(end1-start1)+";gap2:"+(end2-start2) )

    //按照特定的顺序进行聚合
    val res=arr.reduce(_+_)
    println("res:"+res)

    val res2=arr.reduceLeft(_+_)

    val res22=arr.par.reduce(_+_)

    val res222=arr.par.reduce(_-_)//因为是并行计算的,所以每次的聚合顺序maybe不一样,从而导致最后输出的结果也不一致

    println("res222:"+res222  )


    val res3=arr.fold(10)(_+_)//折叠的,没有并行计算,所以就正常的+10
    println("res3:"+res3)

    //折叠:有初始值(无特定顺序)
    val res33=arr.par.fold(10)(_+_)//每次开启的线程数不一样,所以每开一个线程,都加10,也就导致每次的值不一样
    println("res33:"+res33)

    //折叠:有初始值(有特定顺序)
    val res333=arr.par.foldLeft(10)(_+_)

    //聚合
    val list7=List(List(2,1,4),List(5,3,7,8),List(6,9,0))
    val res4=list7.flatten.reduce(_+_)//先将多个list压扁成一个list,然后聚合list进行+运算
    val res44=list7.aggregate(0)(_+_.sum,_+_)
    println("res44:"+res44)

    //求并集
    val list00=List(1,3,2)
    val list11=List(9,7,2)
    println("list-test:"+(list00 union list11))

    //求交集
    println("list-intersect:"+(list00 intersect  list11))

    //求差集
    println("list-diff:"+(list00 diff list11))


    val lines1=List("hello java hello python","hello scala","hello scala hello java hello scala")

    //切分并压平

    val wordS=lines1.flatMap(_.split(" " ))

    //把每个单词生成一个一个的pair
    val pair=wordS.map((_,1))

    //以元组的key进行分组
    val group=pair.groupBy(_._1)

    println("wordS:"+wordS)
    val wordCount=group.mapValues(_.size)


    println("pair:"+pair)

    println("group:"+group)

    println("wordCount:"+wordCount)

    val wordList=wordCount.toList.sortBy(_._2)
    println("wordList:"+wordList.reverse)

    val word=lines1.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).mapValues(_.size).toList.sortBy(_._2)
    println("word:"+word)


  }

}
