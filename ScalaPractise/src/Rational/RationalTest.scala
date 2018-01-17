package Rational

object RationalTest {

  def main(args:Array[String]) {
    val a = new RationalBean(2);
    println(a);
    val b = new RationalBean(16,12);
    println(b);
    println(a + b);
    println(a * 2);
    // println(2 * b)

  }


}

