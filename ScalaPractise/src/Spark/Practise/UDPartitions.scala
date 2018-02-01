package Spark.Practise

import java.net.URL
import java.util

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
      .map(line =>{
        val myUrl = line.split("\t")(1)
        val myHost = new URL(myUrl).getHost()
        (myHost,(myUrl,1))     // 拼接成一个二元组中嵌套二元组的形式,因为分区只能处理KV的形式
      }).persist()

    // 获取RDD中去重后的key值，并放到一个集合中
    val partitions = rdd1.map(_._1).distinct().collect()

    /*val rdd4 = rdd1.partitionBy(new HostPartition(partitions))
      .map(e => ((e._1,e._2._1),e._2._2))
      .reduceByKey(_+_)
      .foreach(println(_))*/



    //
    val rdd3 = rdd1.partitionBy(HostPartition(partitions))
      .map(ele =>((ele._1,ele._2._1),ele._2._2))
      .reduceByKey((x,y)=>x+y)
      .sortBy(_._2,false)
      .mapPartitions(it=>{
        it.toList.take(3).iterator
      })
      .foreach(println(_))

    sc.stop()
  }






}

case class HostPartition(ins:Array[String]) extends Partitioner{
  // 自定义分区，通常需要执行分区的个数
  override def numPartitions = ins.length

  // 创建一个for循环，建立分区和分区号的映射
  val myPartitions = new util.HashMap[String,Int]()
  var count = 0
  for(i <- ins){
    myPartitions.put(i,count)
    count = count + 1
  }

  override def getPartition(key: Any) = {
    myPartitions.getOrDefault(key,0)
  }
}

