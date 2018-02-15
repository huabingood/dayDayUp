package Spark.Practise

import java.sql.{Connection, DriverManager}

object Access2Mysql {
  def main(args:Array[String]): Unit ={
    val username="root"
    val passwd = "123456"
    val host = "huabingood01"
    val port = "3306"
    val dbname = "my_test"
    val dbDriver = "com.mysql.jdbc.Driver"
    val connectStr = "jdbc:mysql://"+host+":"+port+"/"+dbname+"?user="+
      username + "&password=" + passwd + "&useSSL=false"+
      "&characterEncoding=utf8"// 拼接链接mysql的url


    println(connectStr)

    // 创建链接
    var myConnection:Connection = null

    try {
      // 根据不同的数据库生成不同的驱动
      Class.forName(dbDriver)
      // 获取链接对象
      myConnection = DriverManager.getConnection(connectStr)
      // 创建操作接口：一共有三种，直接使用静态语句;带参数的语句;存储过程
      val statement = myConnection.createStatement()

      // 执行具体的操作命令，通常executeQuery表示查询，executeQuery表示增删改
      val rs = statement.executeQuery("select * from haha")
      while(rs.next()){
        val index = rs.getString("index")
        val daytime = rs.getString("daytime")
        println(index + ":" + daytime)
      }

      // 向哈哈表中添加内容,1是true,
      val sql = "insert into haha values(huabingood,'b','20180205');"
      val eu = statement.executeUpdate(sql)
      println(eu)

    } catch{
      case e:Exception =>e.printStackTrace()
    }

    // 关闭链接
    myConnection.close()





  } // main

}
