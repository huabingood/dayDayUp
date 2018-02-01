package Spark.Practise

import org.apache.spark.{SparkConf, SparkContext}

object Test1 {
  def main(args:Array[String]): Unit ={
    val a = "file:///home/huabingood/read.txt"
    val b = "file:///home/huabingood/write.txt"

    val conf = new SparkConf().setAppName("local")
     //   .setMaster("yarn-client")
      .setMaster("spark://huabingood01:7077")
    //conf.set("spark.yarn.dist.files","ScalaPractise/src/conf/yarn-site.xml")

    val sc = new SparkContext(conf)
    sc.addJar("/opt/huabingood/practise/everyDayLanguagePractise/out/artifacts/SparkPractise_jar/SparkPractise.jar")

    sc.textFile(a).
      flatMap(_.split(',')).map((_,1)).reduceByKey(_+_,1)
      .collect().foreach(println(_))



    sc.stop()

  }

}
