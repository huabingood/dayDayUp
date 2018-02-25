package GreenHand

import GreenHand.TestBean.{Person1, Person2, Person3}

object TestPerson1 {
  def main(args: Array[String]): Unit = {
    val person = new Person1("huabingood",12)

    val p = new Person1();

    val p1 = new Person1(age=2)

    println(p)

    println(p1)



    println("----------test Person2--------")
    val p2 = new Person2()
    println(p2)
    val p3 = new Person2("haha",2)
    println(p3)
    // p3.age=2
    println(p3.age)

    println("----------test Person3--------")
    // 这个是错误的，编译是通不过
    // val p4 = new Person3()
    val p5 = new Person3("123",123)
    println(p5)
  }

}
