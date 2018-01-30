package Spark.Practise

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

    val rdd1 = sc.textFile("/mnt/huabingood/itcast.log")
  }




}

class HostPartition(ins:Array[String]) extends Partitioner{
  override def numPartitions = ???

  override def getPartition(key: Any) = ???
}
