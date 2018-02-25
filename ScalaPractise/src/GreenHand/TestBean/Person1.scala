package GreenHand.TestBean

class Person1(var name:String=null, var age:Int=0) {

  override def toString(): String ={
    val person=name + ","+age.toString
    person
  }
}




