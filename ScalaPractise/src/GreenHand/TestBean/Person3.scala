package GreenHand.TestBean

/**
  * 主构造上是val修饰，但是没有默认值
  */
class Person3(val name:String,val age:Int) {
  // 手动生成有产构造
  // 编译时通不过，主构造中val修饰且不给与参数初始只能生成有参构造
  /*def this(){
    this
  }*/
  override def toString = name +","+age.toString
}
