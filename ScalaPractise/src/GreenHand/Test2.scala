package GreenHand

import java.io.PrintWriter

import scala.io.Source


object Test2 {
  // 定义main方法
  def main(args:Array[String]){
    // ×××× 测试list添加元素，生成新的集合 ××××

    /*val myList = List(2,3);
    // 往集合的头部添加元素，并生成一个新集合
    val myList1 = 1::myList;
    // 往集合的结尾添加元素，并生成一个新集合
    val myList2 = myList:+4;

    println("myList"+myList);
    println("myList1"+myList1);
    println("myList2"+myList2);*/

    // ×××× IO ××××
    // 指令式的编程方式
    // scala中读取文件 val myRead = Source.fromFile("path","encode");
    // scala中没有写文件的方法，只能调用java中写文件的方法。
    val myWrite  = new PrintWriter("/home/huabingood/write.txt"); // 这个类其实是java中的。
    for(line <- Source.fromFile("/home/huabingood/read.txt")("UTF-8").getLines){
      println(line);
      myWrite.write(line+"\n");

    }
    myWrite.close();

    // 函数式的编程风格
    println("-------- 我是分割符 --------")
    Source.fromFile("/home/huabingood/read.txt")
      .getLines().foreach(line=>println(line));
  }
}