package test;

import org.junit.Ignore;
import org.junit.Test;

public class TestJUnit {
    /**
     * this is wrong .because @Test need no parameters and no return values.
     * @param a
     * @param b
     * @return
     */
    @Ignore
    public int testJUnit(int a,int b){
        int c;
        c = a + b;
        return c;
    }

    @Test
    public void test(){
        System.out.println("This  is true.");
    }
}
