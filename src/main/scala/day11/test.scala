package day11

/**
  * Created by wangfeng on 18/11/16.
  * 为什么学习Actor(Akka就是一个类库)
  *
  *
  * 什么是Actor
  * Actor是消息并发模型
  *   scala中的Actor能够实现并行变成的强大功能,它是基于事件模型的并发机制
  *   scala是运用消息(message)的发送,接收来实现多线程的
  *   使用scala能够更容易地实现多线程应用的开发
  *
  * java并发变成与scala Actor编程的区别
  *    scala的Actor类似于java中的多线程编程,但是不同的是,scala的actor提供的模型与多线程有所不同
  *    scala的Actor尽可能地避免锁和共享状态,从而避免多线程并发时出现资源争用的情况,进而提升多线程编程的性能
  *    此外 scala Actor 的这种模型还可以避免死锁等一系列传统多线程编程的问题
  *    原因就在于java中多数使用的是可变状态的对象资源,对这些资源进行共享来实现多线程编程的话,控制好资源竞争与防止对象状态被意外修改是非常重要的,而对象状态的不变性也是较难以保证的
  *    而在scala中,我们可以通过复制不可变状态的资源(即对象,scala中一切都是对象,连函数,方法也是)的一个副本
  *    再基于Actor的消息发送,接受机制进行并行编程
  *
  * Actor 方法执行顺序
  *    1.调用start()方法启动Actor
  *    2.执行act()方法
  *    3.向Actor发送消息
  *
  * 发送消息的方式
  *   ! -->发送异步消息,没有返回值
  *   !? -->发送同步消息,等待返回值
  *   !! -->发送异步消息,返回值是 Future[Any]
  *
  *
  * 掌握的内容:
  *   1.创建Actor
  *   2.Actor的消息接受和发送
  *   3.用Actor并发编程实现WordCount
  */

/*
 *Akka简介
 *   spark的RPC是通过Akka类库实现的,Akka用scala语言开发,基于actor并发模型实现
 *   Actor是Akka中最核心的概念,它是一个封装了状态和行为的对象,actor之间可以通过交换消息的方式进行通信
 *   每个actor都有自己的收件箱(mailbox)  通过Actor能够简化锁及线程管理
 *   可以非常容易地开发出正确的并发程序和并行系统,Actor具有如下特性:
 *   1.提供了一种高级抽象,能够简化在并发(Concurrency)/并发(Parallelism)应用场景下的编程开发
 *   2.提供了异步非阻塞的、高性能的事件驱动编程模型
 *   3.超级轻易级事件处理(每GC堆内存几百万Actor)
 *
 *
 *ActorSystem
 *  在Akka中,ActorSystem是一个重量级的结构
 *  他需要分配多个线程,所以在实际应用中,ActorSystem通常是一个单例对象
 *  我们可以使用这个 ActorSystem创建很多个Actor(不仅能创建,还能监督,当创建的子Actor状态出问题了,会立马重新创建一个新actor)
 *
 *Actor
 *  在Akka中,Actor负责通信,在Actor中有一些重要的生命周期方法
 *  1.preStart()方法,该方法在Actor对象构造方法执行后执行,整个Actor生命周期中近执行一次
 *  2.receive()方法,该方法在Actor的preStart方法执行完成后执行,用于接收消息,会被反复执行
 *
 *案例需求
 *  用Akka实现rpc通信
 *  模拟实现spark启动机制
 *
 *
 *
 *
 *
 * 什么是RDD
 *   RDD(Resilient Distributed Dataset) 叫做弹性分布式数据集,是spark中最基本的数据抽象
 *   它代表一个不可变,可分区,里面的元素可并行计算的集合
 *   RDD具有数据流模型的特点:自动容错,位置感知性调度和可伸缩性
 *   RDD容许用户在执行多个查询时显示地将工作集缓存子内存中
 *   后续的查询能够重用工作集,这极大的提升了查询速度
 *
 * RDD的属性
 *   1.一组分片(partition) 即数据集的基本组成单位
 *   对于RDD来说,每个分片都会被一个计算任务处理,并决定并行计算的粒度
 *   用户可以在创建RDD时指定RDD的分片个数,如果没有指定,那么就会采用默认值
 *   默认值就是程序所分配到的cpu core的数据
 *
 *   2.一个计算每个分区的函数
 *   spark中RDD的计算是以分片为单位的,每个RDD都会实现compute函数以达到这个目的
 *   compute函数会对迭代器进行复合,不需要保存每次计算的结果
 *
 *   3.RDD之间的依赖关系
 *   RDD的每次转换都会生成一个新的RDD,所以RDD之前就会形成类似于流水线一样的前后依赖关系
 *   在部门分区数据丢失时,spark可以通过这个依赖关系重新计算丢失的分区数据,而不是对RDD的所有分区进行重新计算
 *
 *   4.一个partitioner,即RDD的分片函数
 *   当前spark中实现了两种类型的分片函数,一个是基于哈希的HashPartitioner
 *   另外一个是基于范围的RangePartitioner
 *   只有对于key-value的RDD的Partitioner的值是None
 *   Partitioner函数不但决定了RDD本身的分片数量,也决定了parent RDD shuffle 输出时的分片数量
 *
 *
 *   5.一个列表,存储存取每个Partition的优先位置(preferred location)
 *   对于一个HDFS文件来说,这个列表保存的就是每个Partition所在的块的位置
 *   按照"移动数据不如移动计算"的理念,spark在进行任务调度的时候,会尽可能地将计算任务分配到其所要处理数据快的存储位置
 *
 *
 *   spark生成RDD的两种方式:
 *   1.sc.textFile
 *   2.sc.parallerlize
 *
 *
 *
 *
 *
 * 1.为什么要做checkpoint?
 *     运行出的中间结果往往很重要,所以为了保证数据的安全性,要checkpoint
 *     最好把数据checkpoint到HDFS,这样便于该集群所有节点访问到
 *     在checkpoint之前最好先cache一下,这样先把数据放到缓存
 *     便于运行任务的调用,也便于在checkpoint的时候直接从缓存拿数据
 * 2.在什么时候做checkpoint?
 *     在发生shuffle之后做checkpoint
 *
 * 3.checkpoint步骤:
 *      1.建立checkpoint存储目录
 *            sc.setCheckpointDir("hdfs://node01:9000/ck")
 *      2.rdd1.cache()
 *      3.rdd1.checkpoint()
 *
 */
class test {

}
