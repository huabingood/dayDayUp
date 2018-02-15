package FromScientificNotation2Num;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;


public class FromScientificNotation2Num {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));

        String line = null;

        while((line = br.readLine()) != null){
            String str = reverseScientificNotation2Num(line);
            // System.out.println(str);
            bw.write(str);
        }

        br.close();
        bw.close();
    }

    /**
     * 将读取的行转换为数组，然后将其中的科学计数法转为普通的计数法。
     * 然后拼接成字符窜加换行返回。
     */
    public static String reverseScientificNotation2Num(String line){

         String[] my_array= line.split(",");
         String newLine=null;

        for(int i=0;i<my_array.length;i++){

            if ((my_array[i].contains("e") || my_array[i].contains("E")) &
                    my_array[i].contains(".")) {
                BigDecimal bd = new BigDecimal(my_array[i]) ;
                my_array[i]=bd.toPlainString();
            }

            if(i == 0){
                newLine = my_array[0];
            } else {
                newLine = newLine + "," + my_array[i];
            }
        }
        newLine = newLine + '\n';
        return newLine;
    }
}
