package Spark.Practise

import java.sql
import java.sql.Connection
import java.sql.{DriverManager, ResultSet}

import org.apache.spark.{SparkConf, SparkContext, sql}
import org.apache.spark.sql.{SQLContext, SparkSession}

object Spark2DB {
  def main(args:Array[String]): Unit ={
    val conf = new SparkConf()
      .setAppName("Spark2DB")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    val sqlc = SparkSession.builder().config(conf).getOrCreate()




    val username="root"
    val passwd = "123456"
    val host = "huabingood01"
    val port = "3306"
    val dbname = "my_test"
    val dbDriver = "com.mysql.jdbc.Driver"
    val connectStr = "jdbc:mysql://"+host+":"+port+"/"+dbname+"?user="+
      username + "&password=" + passwd + "&useSSL=false"+
      "&characterEncoding=utf8"

    val reader = sqlc.read.format("")
      .option("driver",dbDriver)
      .option("url",connectStr)
        .option("dbtable","haha")
      .load()

    reader.createOrReplaceTempView("haha")


    val df = sqlc.sql("select * from haha")

    df.show()










  } // main

}


/**
  * 这个是纯链接mysql的，链接spark的话是没有用处的。
  */
/*
case class GetDataFromDB() {
  // 链接mysql获取rdd
  val username="root"
  val passwd = "123456"
  val host = "huabingood01"
  val port = "3306"
  val dbname = "my_test"
  val dbDriver = "com.mysql.jdbc.Driver"
  val connectStr = "jdbc:mysql://"+host+":"+port+"/"+dbname+"?user="+
    username + "&password=" + passwd + "&useSSL=false"+
    "&characterEncoding=utf8"

  private var connection:Connection = null
  private var statement:sql.Statement = null
  private var rs:ResultSet = null

  // 获取链接
  def getConnetion2mysql(): Connection ={
    Class.forName(dbDriver)
    connection = DriverManager
      .getConnection(connectStr)
    return connection
  }

  // 关闭所有的链接
  def closeAll(): Unit ={
    if(rs != null){
      rs.close()
    }

    if(statement != null){
      statement.close()
    }

    if(connection != null){
      connection.close()
    }
  }

  // 查询数据
  def queryResult(sql:String): Unit ={
    statement = getConnetion2mysql().createStatement()
    rs = statement.executeQuery(sql)
  }

}*/
