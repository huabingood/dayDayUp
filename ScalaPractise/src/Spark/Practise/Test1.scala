package Spark.Practise

import org.apache.spark.{SparkConf, SparkContext}

object Test1 {
  def main(args:Array[String]): Unit ={
    val a = "hdfs://huabingood01:9000/huabingood/test/inPath/read.txt"
    val b = "hdfs://huabingood01:9000/huabingood/test/outPath/o2"

    val conf = new SparkConf().setAppName("local").
      setMaster("spark://huabingood01:7077")

    val sc = new SparkContext(conf)

    sc.textFile(a).
      flatMap(_.split(',')).map((_,1)).reduceByKey(_+_,1)
      .foreach(println(_))



    sc.stop()

  }

}
