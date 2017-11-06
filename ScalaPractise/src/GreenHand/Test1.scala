package GreenHand

object Test1 {
  def main(args:Array[String]){
    val a = 123l;
    println(a);

    // scala中是不允许声明变量是不进行初始化的。但是java中是可以的
    // var l;      // 这种方式是错误的
    var la = None;
    println(la);

    // 初始化一个变量时，如果该变量未赋初始值，一定要指定起数据类型
    // var an:Int = null
    var myVar:Int=10
    var an:Int=1
    an = 200
    println(an)

    //
    var c:Int=2
    c = 10
    println(c)
  }

}
