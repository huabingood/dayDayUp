package hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 使用javaAPI操作HDFS文件系统，包括：
 *  1.根据配置文件创建HDFS操作对象
 *  2.进行具体的操作，包括：
 *   A.文件的增删改查
 *   B.配置信息的查看，文件的判断，文件的遍历
 *   C.文件的上传下载
 *   D.文件的IO操作
 *  3.千万不要忘记关闭HDFS操作对象
 * @author huabingood@qq.com
 *
 */

public class HDFSIO2 {
    // log4j对象，用于收集日志
    private static Logger logger = Logger.getLogger("file");
    // 存放遍历出来的HDFS路径
    private static Set<String> set = new HashSet<String>();

    /**
     * 根据配置文件获取HDFS操作对象
     * 有两种方法：
     *  1.使用conf直接从本地获取配置文件创建HDFS对象
     *  2.多用于本地没有hadoop系统，但是可以远程访问。使用给定的URI和用户名，访问远程的配置文件，然后创建HDFS对象。
     * @return FileSystem
     */
    public FileSystem getHadoopFileSystem() {


        FileSystem fs = null;
        Configuration conf = null;

        // 方法一，本地有配置文件，直接获取配置文件（core-site.xml，hdfs-site.xml）
        // 根据配置文件创建HDFS对象
        // 此时必须指定hdsf的访问路径。
        conf = new Configuration();
        // 文件系统为必须设置的内容。其他配置参数可以自行设置，且优先级最高
        conf.set("fs.defaultFS", "hdfs://huabingood01:9000");

        try {
            // 根据配置文件创建HDFS对象
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("",e);
        }

        // 方法二：本地没有hadoop系统，但是可以远程访问。根据给定的URI和用户名，访问hdfs的配置参数
        // 此时的conf不需任何设置，只需读取远程的配置文件即可。
        /*conf = new Configuration();
        // Hadoop的用户名
        String hdfsUserName = "huabingood";

        URI hdfsUri = null;
        try {
            // HDFS的访问路径
            hdfsUri = new URI("hdfs://huabingood01:9000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.error(e);
        }

        try {
            // 根据远程的NN节点，获取配置信息，创建HDFS对象
            fs = FileSystem.get(hdfsUri,conf,hdfsUserName);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e);
        }*/

        // 方法三，反正我们没有搞懂。
        /*conf  = new Configuration();
        conf.addResource("/opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/etc/hadoop/core-site.xml");
        conf.addResource("/opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/etc/hadoop/hdfs-site.xml");

        try {
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }*/




        return fs;
    }


    public void showAllConf(){
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://huabingood01:9000");
        Iterator<Map.Entry<String,String>> it = conf.iterator();
        while(it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            System.out.println(entry.getKey()+"=" +entry.getValue());
        }
    }


    // create path
    public boolean myCreatePath(FileSystem fs){
        boolean b = false;

        Path path = new Path("/hyw/test/huabingood/hyw");
        try {
            // even the path exist,it can also create the path.
            b = fs.mkdirs(path);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }
        return b;
    }

    // drop path
    public boolean myDropHdfsPath(FileSystem fs){
        boolean b = false;
        // drop the last path
        Path path = new Path("/huabingood/hadoop.tar.gz");
        try {
            b = fs.delete(path,true);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }

        return b;
    }

    // query
    public void myCheck(FileSystem fs){
        boolean isExists = false;
        boolean isDirectorys = false;
        boolean isFiles = false;

        Path path = new Path("/hyw/test/huabingood01");

        try {
            isExists = fs.exists(path);
            isDirectorys = fs.isDirectory(path);
            isFiles = fs.isFile(path);
        } catch (IOException e){
            e.printStackTrace();
            logger.error(e);
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }

        if(!isExists){
            System.out.println("lu jing not cun zai.");
        }else{
            System.out.println("lu jing cun zai.");
            if(isDirectorys){
                System.out.println("Directory");
            }else if(isFiles){
                System.out.println("Files");
            }
        }



    }

    public Set<String> recursiveHdfsPath(FileSystem hdfs,Path listPath){

        /*FileStatus[] files = null;
        try {
            files = hdfs.listStatus(listPath);
            Path[] paths = FileUtil.stat2Paths(files);
            for(int i=0;i<files.length;i++){
                if(files[i].isFile()){
                    // set.add(paths[i].toString());
                    set.add(paths[i].getName());
                }else {

                    recursiveHdfsPath(hdfs,paths[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }*/

        FileStatus[] files = null;

        try {
            files = hdfs.listStatus(listPath);
            if(files.length == 0){
                set.add(listPath.toUri().getPath());
            }else {
                for (FileStatus f : files) {
                    if (files.length == 0 || f.isFile()) {
                        set.add(f.getPath().toUri().getPath());
                    } else {
                        recursiveHdfsPath(hdfs, f.getPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }


        return set;
    }

    // rename the path
    public boolean myRename(FileSystem hdfs){
        boolean b = false;
        Path oldPath = new Path("/hyw/test/huabingood");
        Path newPath = new Path("/hyw/test/huabing");

        try {
            b = hdfs.rename(oldPath,newPath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }finally {
            try {
                hdfs.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }

        return b;
    }


    // use java IO copy the file from one HDFS path to another
    public void copyFileBetweenHDFS(FileSystem hdfs){
        Path inPath = new Path("/hyw/test/hadoop-2.6.0-cdh5.10.0.tar.gz");
        Path outPath = new Path("/huabin/hadoop.tar.gz");

        byte[] ioBuffer = new byte[1024*1024*64];
        int len = 0;

        FSDataInputStream hdfsIn = null;
        FSDataOutputStream hdfsOut = null;

        try {
            hdfsIn = hdfs.open(inPath);
            hdfsOut = hdfs.create(outPath);

            IOUtils.copyBytes(hdfsIn,hdfsOut,1024*1024*64,false);

            /*while((len=hdfsIn.read(ioBuffer))!= -1){
                IOUtils.copyBytes(hdfsIn,hdfsOut,len,true);
            }*/
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }finally {
            try {
            hdfsOut.close();
            hdfsIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    // get and put
    public void getFileFromHDFS(FileSystem fs){
        Path HDFSPath = new Path("/hyw/test/hadoop-2.6.0-cdh5.10.0.tar.gz");
        Path localPath = new Path("/home/huabingood");

        try {
            fs.copyToLocalFile(HDFSPath,localPath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("zhe li you cuo wu !" ,e);
        }finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("zhe li you cuo wu !" ,e);
            }finally {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            }
        }
    }

    public void myPutFile2HDFS(FileSystem fs){

        boolean pathExists = false;
        // it can create path
        // the last path is the file name
        Path localPath = new Path("/home/huabingood/绣春刀.rmbv");
        Path hdfsPath = new Path("/hyw/test/huabingood/abc/efg/绣春刀.rmbv");

        /*try {
            b = fs.exists(hdfsPath);
            if(!b){
                logger.error("path not exists!");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            fs.copyFromLocalFile(localPath,hdfsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args){
        HDFSIO2 hdfs = new HDFSIO2();
        FileSystem fs = hdfs.getHadoopFileSystem();
        //hdfs.getFileFromHDFS(hdfs.getHadoopFileSystem());

        //System.out.println(hdfs.myCreatePath(fs));

        // System.out.println(hdfs.myDropHdfsPath(fs));

        // hdfs.myCheck(fs);

        //hdfs.myPutFile2HDFS(fs);
        /*Iterator iterator = hdfs.recursiveHdfsPath(fs,new Path("/")).iterator();
        if(iterator.hasNext()){
            System.out.println(iterator.next());
        }*/

        Set<String> set = hdfs.recursiveHdfsPath(fs,new Path("/"));
        for(String path:set){
            System.out.println(path);
        }

        // System.out.println(hdfs.myRename(fs));

        // hdfs.copyFileBetweenHDFS(fs);
        hdfs.showAllConf();
    }


}
