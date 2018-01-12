package Rational

class RationalBean (n:Int,b:Int){  // 直接创建了一个带参构造
  // 如果不符合的话，意味着这个类的构造就会失败。
  // 如果不符合会抛出java.lang.IllegalArgumentException异常
  require(b != 0);

  // 辅助构造器
  def this(n:Int)=this(n,1);
  // def this(b:Int)=this(a:Int,b:Int,c:Int);



  // 这里这么设计主要是为了方便访问传入的new RationalBean中的参数值
  // scala默认返回最后一个参数的值
  private val g= gcd(n,b);  // 化简
  private val number:Int = n/g;
  private val denom:Int = b/g;

  // 分数的加法，这里可以清晰的看到+实际上被理解为了方法名而已
  def +(that:RationalBean):RationalBean={
    new RationalBean(n*that.denom+b*that.number,b*that.denom);
  }

  // 分数的乘法，这里将该实现方法的重载
  def *(that:RationalBean):RationalBean={
    new RationalBean(n*that.number,b*that.denom);
  }
  // 方法重载
  def *(in:Int): RationalBean ={
    new RationalBean(number*in,denom);
  }
  // 为了实现2×RationalBean,实现隐式转换
  // 将2隐式转换，从int到RationalBean
  implicit def intToRationalBean(x:Int)=new RationalBean(x);


  // 将分数化简
  // 反正我也不知道为什么有这种神奇的化简方式
  private  def gcd(n:Int,b:Int):Int={
    if(b == 0){
      n;
    }else{
      gcd(b,n%b);    // 一个神奇的操作，反正我也不懂为什么。
    }
  }

  // toString
  override def toString = number+"/"+denom;
}
