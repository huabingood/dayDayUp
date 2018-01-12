package Rational

object RationalTest {

  def main(args:Array[String]) {
    val a = new RationalBean(2);
    println(a);
    val b = new RationalBean(66,42);
    println(b);
    println(a + b);
    println(a * 2);
    println(2 * b)


  }


}

