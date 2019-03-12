package testJave;

/**
 * Created by wangfeng on 19/1/29.
 *
 * 资源调度模式有:
 * Standalone模式(属于Spark独有的资源调度框架,不依赖其他的调度框架)
 * Local模式(单机模式)
 * On Yarn模式(Spark App运行在Yarn 资源调度框架上)
 * Messos模式(类似于Yarn,属于Apache旗下的另一款资源调度框架)
 *
 *
 * 多种资源调度框架,哪种效率最高?Standalone
 *
 * 为什么Spark App还需要运行在其它资源调度框架上?
 * 1.因为历史遗留问题,以前用的Yarn做资源调度的团队已经成型
 * 2.还有很多离线计算任务没有迁移到Spark,此时还需要用Yarn
 * 3.有实时计算的需求,要用到Storm,此时也需要用到Yarn
 * 4.为了节约运维成本
 *
 * 想要把离线任务和实时任务整合到Spark中
 *
 *  MapReduce  ->  SparkSQL
 *  Storm    ->  SparkStreaming
 *
 *
 * Spark on Yarn
 * 两种模式的区别
 *
 * cluster模式:Driver程序在Yarn中运行,应用的运行结果不能在客户端显示,所以最好运行哪些将结果最终保存在外部存储介质(如HDFS,Redis,Mysql)
 * 而非stdout输出的应用程序,客服端的终端显示的仅是作为YARN的job的简单运行状况
 *
 * client模式,Driver运行在Client上,应用程序运行结果会在客户端显示,所有适合运行结果有输出的应用程序(如spark-shell)
 *
 * 原理
 * cluster模式
 * Spark Driver首先作为一个ApplicationMaster在YARN集群中启动,客户端提交给ResourceManager的每一个job都会在集群的NodeManager节点上
 * 分配一个唯一的ApplicationMaster,由该ApplicationMaster管理全生命周期的应用。具体过程:
 *
 *  1.由client向ResourceManager提交请求,并上传jar到HDSF上
 *  这期间包括四个步骤:
 *  a).连接到RM
 *  b).从RM的ASM(ApplicationManager)中获得metric、queue和resource等信息。
 *  c).upload app jar and spark-assembly jar
 *
 *
 *
 */
public class testSparkOnYarn {
}
