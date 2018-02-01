package Spark.Practise

import org.apache.spark.{SparkConf, SparkContext}

object WebPV {
  def main(args:Array[String]): Unit ={

    val conf = new SparkConf().setAppName("WebPV")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    // 为什么flatMap和map差别这么大
    // 理论上将flatMap返回的是一个list而map可以返回各种情况的list嵌套list
    val rdd1 = sc.textFile("/mnt/huabingood/itcast.log").map(_.split("\t")(1))
    //val rdd1 = sc.textFile("/mnt/huabingood/itcast.log").flatMap(_.split("\t")(1))

    // 将函数置换成（K ,1）的形式
    val rdd2 = rdd1.map(x=>(x,1))
    // 计算除每个key的个数
    // 传入的函数的意思是：value值的累加x代表的是目前的和，y代表的是当前的值。有时会写成reduceByKey(_+_)
    val rdd3 = rdd2.reduceByKey((x,y)=>x+y)

    rdd3.collect().foreach(println(_))

    sc.stop()


  }


}
