package JavaIO;

import java.io.*;
import java.nio.charset.Charset;

public class JavaIO {
    public static void main(String[] args){
        // myOutputStream();
        // myInputStream();
        // integratedApplication();
        // myCharIO();
        myBuffeerCharStrem();


    }  // main方法结尾


    /**
     * 字节输出流
     * 构造方法：
     * FileOutputStream(String str)   // 传入的是文件路径的字符串，java底层将改字符窜转换成File对象了
     * FileOutputStream(File file)    // 直接传入File对象，这样java不用自己创建了
     * 常用方法：
     * 注意：只能写入字节数
     * write(Byte byte)   // 一次写入一个字节
     * write(Byte[] byties)  // 一次写入一个字节数组
     */
    public static void myOutputStream(){
        String path = "D:\\【学            习】\\hyw.txt";

        // 在try中的对象，是不会被finally中的内容访问的，如果要在finally中能访问，必须在try外声明
        FileOutputStream fos =null;
        try {
            fos= new FileOutputStream(path,true);  // 如果参数中多出true的话，表示网文档中追加
            fos.write("hyw".getBytes());   // 实际上网文档中写的内容，只能是二进制
            fos.write("\r\n".getBytes());  // 这里表示换行，注意在linux和windows中换行的字符时不同的
            fos.write("love".getBytes());
            fos.write("杨华彬".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fos != null) {
                    fos.close();  // 这里也有个异常
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }  // 这个是myOutputStream的结尾

    /**
     * 字符输入流
     * 构造函数:
     * FileInputStream(File fils)
     * FileInputStream(String str)  // 底层将字符串实例化为一个File对象
     * 主要方法：
     * int read(byte[] bytes)   // 当读到最后时返回-1；可以读取个字节，也可以说是一个字节数组
     */
    public static void myInputStream(){
        String path = "D:\\【学            习】\\【图            书】\\CMake Practice.pdf";

        FileInputStream fis = null;  // 为了在finally中能够访问
        try {
            fis = new FileInputStream(path);
            byte[] bytes = new byte[1024];   // 一次读取的数量
            int i=0;   // 用于接收read()的返回值
            while((i=fis.read(bytes))!=-1){  // 当读取到文档末尾时，这个就会返回-1
                System.out.println(new String(bytes,0,i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }  // myInputStream()结尾

    /**
     * 综合应用
     * 将D:\【音            乐】\陈蓉晖 - 小河淌水.mp3复制到：D:\
     */
    public static void integratedApplication(){
        String inputPath = "D:\\【音            乐】\\陈蓉晖 - 小河淌水.mp3";
        String outputPath = "D:\\abc.txt";   // 写文件的话，必须文件对文件

        FileInputStream fis = null;
        FileOutputStream fos = null;

        byte[] bytes = new byte[1024*1024];
        int len=0;
        try {
            fis = new FileInputStream(inputPath);
            fos = new FileOutputStream(outputPath);
            while ((len=fis.read(bytes))!=-1){
                fos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }  // 综合应用的结尾

    /**
     * 字节流
     * 字节输出流构造：
     * FileWriter(string str)
     * FileWriter(file f)
     * 
     * 方法
     * write(chars[],0,int length)
     *
     * 字节输入流构造：
     * FileReader(string str)
     * FileReader(File f)
     *
     * 方法
     * 注意，这里是没有readLine() 方法的
     * int read(char[])     // 如果读到结尾，就返回-1
     *
     * 注意：这里没办法使用
     */
    public static void myCharIO(){
        String inputPath = "D:\\【学            习】\\hyw.txt";
        String outputPath = "D:\\【学            习】\\huabingood.txt";

        FileWriter fw = null;
        FileReader fr = null;

        try {
            // 注意这里是不能设置编码方式的。如果是英文还好，中文的话有可能会出问题
            fw = new FileWriter(outputPath,true);
            fr = new FileReader(inputPath);
            char[] chars = new char[1024*1024];
            int len = 0;
             while((len = fr.read(chars)) !=-1){
                 fw.write(chars,0,len);
             }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    } // myCharIO() 结尾


    /**
     * 字节输入流
     */
    public static void myBuffeerCharStrem(){
        String inputPath = "D:\\【学            习】\\hyw.txt";

        BufferedReader br = null;

        try {
            // 下面这种写方法不建议，因为这种写法不发指定编码方式

            br = new BufferedReader(new FileReader(inputPath));

            String line = null;
            while ((line=br.readLine())!=null){
                // 没法发，这个字节流创建字符流的编码方式太烦了，只能在输出字符串时手工转一下
                // Charset.defaultCharset() 表示获取系统的编码方式
                String newLine = new String(line.getBytes(Charset.defaultCharset()));
                System.out.println();
                // System.out.println("杨华彬");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    } // myBuffeerCharStrem() 结尾

}
