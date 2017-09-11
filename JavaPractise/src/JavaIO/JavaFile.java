package JavaIO;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JavaFile {
    public static void main(String[] args){
        String path = "D:\\huabingood\\hyw" ;
        // System.out.print(myCreateNewFile(path));

        // 递归创建文件夹
        //System.out.println(myCreateDir(path));

        // 删除文件夹，实际上这里只能删除一个
        // System.out.println(myRemove(path));

        // file的获取功能
        System.out.println(getParams(path));

        // 递归遍历文件
        File f = new File("D:\\【工            作】");
        Set<String> hashSet = new HashSet();

        fileLists(f,hashSet);

        for(String str:hashSet){ // 引用传递，可以直接使用hasSet
            System.out.println(str);
        }

        // 注意泛型的使用不当的情况，会出现问题
        /*Set  myHashSet = new HashSet<String>();
        for(Object o: myHashSet){
            // TODO sth
        }
        */



    } // main方法结尾

    /**
     * 创建文件
     * boolean createNewFile()
     * 这里必须抛异常！
     * 这里的路径必须是不存在的才能创建
     */
    public static Boolean myCreateNewFile(String path){
        // 外界是无法操作系统的，只能向系统申请资源，让系统自己操作
        File file = new File(path);

        Boolean b = false;
        try {
            b = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("路径非法！");
        }
        return b;
    }

    /**
     * 创建文件夹
     * boolean mkdir   // 这个仅能创建一层
     * boolean mkdirs  // 这里是递归创建
     * 要创建的文件必须不存在
     */
    public static boolean myCreateDir(String path){

        File file = new File(path);

        Boolean b = false;
        // 创建单独的文件夹
        b = file.mkdir();
        // 递归创建文件夹
        if(!b){
            b = file.mkdirs();
        }
        return b;
    }

    /**
     * 删除文件或文件夹
     * boolean delete()  // 不能递归操作，如果是文件夹，不需保证没有子文件或文件夹
     * boolean renameTo(File file)  // 剪切或者重命名
     */
    public static Boolean myRemove(String path){
        Boolean b = false;
        File file = new File(path);

        b = file.delete();
        return b;
    }


    /**
     * 获取文件以及文件各种属性的功能
     * File[] listFiles // 返回当前文件夹的所有子文件
     * String getAbsolutePath // 返回的是一个字符串类型的路径
     * File getAbsoluteFile() // 返回的是一个file对象
     * String getName()       // 获取文件名
     * String getPath()       // 获取相对路径
     */
    public static String getParams(String path){
        String param = "" ;
        File file = new File(path);

        param = file.getAbsolutePath(); // 获取绝对路径
        // param = file.getName(); // 获取当前文件的文件名
        // param = file.getPath(); // 获取当前文件的相对路径

        return param;
    }

    /**
     * 递归遍历文件夹
     * 判断方法
     * boolean exists()      // 是否存在
     * boolean isDirectory() // 是否是文件夹
     * boolean isFile()      // 是否问文件夹
     * boolean canExecute()/canRead()/canWrite()   // 是否可以执行，是否可读，是否可写
     */
    public static Set<String> fileLists(File file, Set<String> hashSet){
        File[] files = file.listFiles();

        // 判断，防止空指针异常
        if(files.length>0) {
            for (File f : files) {
                if (f.isDirectory()) {
                    fileLists(f.getAbsoluteFile(), hashSet);   // 获取一个file对象
                } else {
                    hashSet.add(f.getAbsolutePath());
                }
            }
        }
        return hashSet;
    }


}
