package Spark.Practise

import org.apache.spark.sql.SparkSession

object Test2 {
  def main(args:Array[String]): Unit ={
    val spark = SparkSession
      .builder()
      .appName("Test2")
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd1 = sc.textFile("file:///home/huabingood/read.txt")
      .flatMap(_.split(","))
      .map((_,1))
      .reduceByKey((_+_))
      .persist()

      val a = 10
      val bc = sc.broadcast(a)

    rdd1.foreachPartition(it =>{
      println(bc.value)
    })

    println(rdd1.toDebugString)
    println("====================")
    println(rdd1.dependencies)


    rdd1.unpersist()
    spark.stop()


  }

}
