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
        ((myHost,myUrl),1)     // 拼接成一个二元组中嵌套二元组的形式,因为分区只能处理KV的形式
      }).persist()

    // 获取RDD中去重后的key值，并放到一个集合中
    val partitions = rdd1.map(_._1._1).distinct().collect()
    // 重新分区(这个分区只是针对shuffle前有效)
    // 实际上只要发生shuffle，原来的partition就没有用了
    val rdd4 = rdd1.partitionBy(HostPartition(partitions))

    /*rdd4.mapPartitions(it =>{
      it.toList.sortBy(_._2).reverse.take(2).iterator
    })
      .foreach(println(_))
*/


    rdd1.reduceByKey(_+_)
      .map(x =>(x._1._1,(x._1._2,x._2)))
      .partitionBy(HostPartition(partitions))
      .mapPartitions(it =>{
        it.toList.sortBy(_._2._2).reverse.take(3).iterator
      }).foreach(println(_))


    // rdd1.reduceByKey(_+_).foreach(println(_))

    /*val rdd4 = rdd1.partitionBy(new HostPartition(partitions))
      .map(e => ((e._1,e._2._1),e._2._2))
      .reduceByKey(_+_)
      .foreach(println(_))*/




    //
/*    val rdd3 = rdd1.partitionBy(HostPartition(partitions))
      .map(ele =>((ele._1,ele._2._1),ele._2._2))
      .reduceByKey((x,y)=>x+y)
      .sortBy(_._2,false)

    val rdd6 = rdd1.partitionBy(HostPartition(partitions))

    rdd6.mapPartitions(it=>{
      it.toList.map()
    })*/


    /*val func1 = (index:Int,it:Iterator[(String,(Int,String))])=>{
      it.toList.map(x=>"{pid:"+index +",value:"+x+"}").iterator
    }
    val rdd5 = rdd1.partitionBy(HostPartition(partitions))
      .foreach(println(_))
*/
/*    val func = (index:Int,it:Iterator[((String,String),Int)])=>{
      it.toList.map(x=>"{pid:"+index +",value:"+x+"}").iterator
    }
    val rdd4 = rdd3.mapPartitionsWithIndex(func)*/



    sc.stop()
  }






}

case class HostPartition(ins:Array[String]) extends Partitioner with Serializable{
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

