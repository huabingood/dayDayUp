package GreenHand

import MyBean.{PrivateClassTest, Test3Class}

object Test3 {
  def main(args:Array[String]) {
    val a = new Test3Class;
    /*a.setSum(2);
    println(a.getSum());
    println(a.f())*/

    println(a.sum);


    val b = new PrivateClassTest("yhb",28);
    //println(b.name);
    //println(b.age);
    println(b.myPrint());

  }
}
