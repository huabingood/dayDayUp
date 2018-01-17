package GreenHand

import java.io.File

object Test4 {

  def main(args:Array[String]): Unit ={
    val files = (new File(".")).listFiles();
    // 各种for循环
    files.foreach(println(_));
    println("-----------");

    for(file<-files){
      file.getName match {
        case ".git" => println("git");
        case "out" => println("out");
        case _ => println(file.getAbsolutePath);
      }
      // println(file);
    }
    println("----------");


    // 带下标的循环
    for(i<-1 to 10 by 2){
      println(i);
    }

  }

}
