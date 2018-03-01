package Spark.Practise

import java.sql.DriverManager

import org.apache.spark.rdd.{JdbcRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}

object Connect2MySql {
  def main(array: Array[String]): Unit ={

    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("Connect2MySql")
    val sc = new SparkContext(conf)

    // 准备链接mysql数据库
    val conection = () =>{
      Class.forName("com.mysql.jdbc.Driver").newInstance()
      DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/my_test","root","123456")
    }
    //



  }


}
