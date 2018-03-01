package Spark.Practise

import org.apache.spark.{SparkConf, SparkContext}

object MyCheckpoint {
  def main(args:Array[String]): Unit ={
    val conf = new SparkConf()
      .setAppName("MyCheckpoint")
      .setMaster("local[2]")
      .set("spark.executor.memory","4G")

    val sc = new SparkContext(conf)
    sc.setCheckpointDir("hdfs://huabingood01:9000/hyw/checkpoint")

    val r1 = sc.textFile("hdfs://huabingood01:9000/hyw/weibo_train_data.txt")
      .flatMap(_.split("\t"))
      .map((_,1))
      .reduceByKey((x,y)=>x+y)

    // 通常我们在checkpoint时，要将数据持久化，这个
    // RDD就不需要再次执行了
    r1.persist()
    r1.checkpoint()

    r1.collect()


    sc.stop()
  }

}
