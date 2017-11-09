package GreenHand

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

object MyCollectionPractise {
  // 单例对象
  def main(args: Array[String]): Unit = {
    val myMutableList = ListBuffer("c")

    myMutableList.append("a")
    // 添加元素的方式二，但是对于immutable的集合而言，这是在创建一个新的集合
    myMutableList += "hyw" // += 是添加元素的方式，对于mutable类型的函数而言
    // 尝试一下删除元素
    myMutableList -= "c"

    // 使用for循环遍历list集合
    for (i <- 0 to myMutableList.length - 1 by 1) {
      println(myMutableList(i))
    }

    // 遍历集合中的元素
    myMutableList.map(println(_))

    println("-------------this is truple practise ------------------")

    val mytuple = ('a', 1, "haha")
    /*var i:Any=null
    for(i <- mytuple){
      println(i)
    }*/
    // 便利tuple中的元素
    mytuple.productIterator.foreach(println(_))

    // 使用_i取值时，从1开始;用productElement取值时从0开始
    // 这他妈的是世界上最糟糕的语法
    println(mytuple.productElement(0))
    println(mytuple._1)

    println("------------ map practise ----------------------------")

    var m = Map(1 -> "hyw", 2 -> "yhb")
    println(m(1)) // 这里的1是键的值，并不是索引的值
    m(1) = "zjq"

    m.keys.foreach(i => println(m(i)))
    // m.keys.foreach(println(m(_)))   // 这种方式是错误的，好像值_只能用于一层函数

    println("--------------- operate string -----------------------")
    var s = "hyw&yhb";
    // 分割字符串
    val list = s.split("&")
    list.foreach(println(_))
    // 去除字符串的某个值
    println(s(2))
    println(s.substring(0,1))
    // 是否存在
    println(s.contains("hyw"))
  }

}
