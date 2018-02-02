package Spark.Practise

import org.apache.spark.{SparkConf, SparkContext}

object AgainSort {
  def main(args:Array[String]): Unit ={
    //
    val conf = new SparkConf()
      .setAppName("AgainSort")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    // 测试数据是默认已经进行了reduce后的数据
    val myArray = Array(("liufu",99,24),("jinagzhen",99,29),("liudehua",100,55),("baby",99,10))

    //
    val rdd1 = sc.parallelize(myArray)
      .sortBy(e=>{Person(e._2,e._3)},false)

    println(rdd1.collect().toBuffer)

    sc.stop()

  }

}

case class Person(faceScore:Int,age:Int) extends Ordered[Person] with Serializable{
  override def compare(that: Person):Int = {
    // 注意这里的this和that关键字
    // 这个方法是错误的
    /*val res = this.faceScore.compareTo(that.faceScore)
    if(res == 0){
      val res = this.age.compareTo(that.age)
    }
    return res*/

    // 不知到为什么使用comparTo方法是错误的
    if(this.faceScore-that.faceScore != 0){
      this.faceScore -that.faceScore
    }else{
      this.age-that.age
    }
  }
}
