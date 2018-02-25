package GreenHand.TestBean

/**
  * 主构造上有的修为val，且有默认只
  */
class Person2(val name:String="yhb",val age:Int  = 3) {
  override def toString = name + "," + age.toString
}
