package Spark.Practise

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

object WebHostPV {
  def main(args:Array[String]): Unit ={
    // 创建本地提交的spark任务
    val conf = new SparkConf().setAppName("WebHostPV")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    // 创建一个函数专门将url中的host取出来
    def getHost(myUrl:String):String={
      val host = new URL(myUrl).getHost()
      return host
    }

    // 获取数据，并将URL中的Host取出来
    // 好像函数中无法使用下划线，下划线不能和逗号联合使用
    val rdd1 = sc.textFile("/mnt/huabingood/abc.log")
      .map(_.split("\t")(1))
      .map(x => (getHost(x),1))  // 将处理前和处理后的放在一个元祖中

    // 统计总行数
    val rdd2 = rdd1.count()
    // 统计去重后的行数
    val rdd3 = rdd1.distinct().count()

    // 统计每个Host访问的人数
    val rdd4 = rdd1.reduceByKey((x,y)=>x+y)

    rdd1.collect().foreach(println(_))
    println(rdd2)
    println(rdd3)
    rdd4.collect().foreach(println(_))


    sc.stop()
  }

}
