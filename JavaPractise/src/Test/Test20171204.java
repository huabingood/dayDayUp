package Test;

public class Test20171204 {
    public static void main(String[] args){
        int x,y,z;
        for(x=0;x<17;x++){
            for(y=0;y<25;y++){
                z=30-x-y;
                if(3*x+2*y+z==50){
                    System.out.println(x+","+y+","+z);
                }
            }
        }
    }
}
