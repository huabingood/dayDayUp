package Spark.Practise

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

/*
* 取出host，然后取出该host下的网页访问的前3
* 思想：
* 根据存在的key，过滤不同key的value，然后根据value中的数据进行排序，取值
* 如何取topN:
* 两中方法：使用sortBy+take;使用top并重写排序方法（如果排序的是key，实际上是不需要的）*/
object WebPVTopN {
  def main(args:Array[String]): Unit ={
    //
    val conf = new SparkConf().setAppName("WebPVTopN").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //
    val rdd1 = sc.textFile("/mnt/huabingood/itcast.log").map(x =>{
      val myURl = x.split("\t")(1)
      val myHost = new URL(myURl).getHost
      ((myHost,myURl),1)    // 因为reduceByKey只处理KV类型的数据
    })


    val rdd2 = rdd1.reduceByKey((x,y)=>x+y)

    // 过滤不同的值
    val myKey = Array("php.itcast.cn","java.itcast.cn","net.itcast.cn")
    for(i<-myKey){
      val rdd3 = rdd2.filter(x =>{x._1._1} == i)
      // 这里的for循环不会出问题，因为RDD是懒加载的，rdd3本质上只是上传一个数据而已
      // sortByKey 故名思意，是根据某人是根据key进行排序的。如果向指定排序的值，必须使用sortBy函数
      // val rdd4 = rdd3.sortByKey(false)
      val rdd4 = rdd3.sortBy(_._2,false)

      // 使用top函数取出前n行。
      // top默认会对key进行排序，取出key中的值。
      // 如果对非key位置的数据取topN,通常需要重写排序方法
      // top(3)(Ordering.by(e => e._2))   表示根据value 取前3
      val rdd5 = rdd3.top(3)(Ordering.by(x => x._2))
      rdd4.foreach(println(_))

    }


    sc.stop()
  }
}


