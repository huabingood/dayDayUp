package Test;

import java.util.HashMap;

public class Test {
    // 这个是添加的注释
    public static void main(String[] args) {
        /*char ch = '中';
        System.out.print(ch);*/
        double a = 2;
        double b = 3;
        double c = 1;

        double x1,x2;
        x1 = (-b/(2*a))+(Math.sqrt(b*b-4*a*c)/(2*a));
        x2 = (-b/(2*a))-(Math.sqrt(b*b-4*a*c)/(2*a));

        System.out.println(x1);
        System.out.println(x2);

        System.out.println("-----------------------");

        HashMap map = new HashMap();
        int n = 1000000;
        long start = System.currentTimeMillis();
        for(int i = 0;i < n;i ++) {
            new HashMap();
        }
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        for(int i = 0;i < n;i ++) {
            map.clone();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
