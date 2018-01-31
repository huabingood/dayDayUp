package Spark.Practise

import java.net.URL

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
  * 用户自定义分区
  *
  *
  */
object UDPartitions {
  def main(args:Array[String]): Unit ={

    val conf = new SparkConf()
      .setAppName("UDPartitions")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rdd1 = sc.textFile("/mnt/huabingood/abc.log")
      .map(line =>{
        val myUrl = line.split("\t")(1)
        val myHost = new URL(myUrl).getHost()
        (myHost,(myUrl,1))     // 拼接成一个二元组中嵌套二元组的形式,因为分区只能处理KV的形式
      }).persist()

    // 获取RDD中去重后的key值，并放到一个集合中
    val rdd2 = rdd1.map(_._1).distinct().collect()

    //
  }






}

class HostPartition(ins:Array[String]) extends Partitioner{
  // 自定义分区，通常需要执行分区的个数
  override def numPartitions = ins.length

  // 创建一个for循环，建立分区和分区号的映射
  val count = 0
  for(i <- ins){

  }

  override def getPartition(key: Any) = ???
}
