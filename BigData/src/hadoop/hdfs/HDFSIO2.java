package hadoop.hdfs;

import org.apache.hadoop.fs.*;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class HDFSIO2 {

    private Logger logger = Logger.getLogger("file");
    private Set<String> set = new HashSet<String>();

    public FileSystem getHadoopFileSystem() {


        FileSystem fs = null;
        Configuration conf = null;

        // mathod 1
        conf = new Configuration();
        // TODO
        conf.set("fs.defaultFS", "hdfs://huabingood01:9000");

        try {
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("",e);
        }

        // mathod 2
        /*conf  = new Configuration();
        conf.addResource("/opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/etc/hadoop/core-site.xml");
        conf.addResource("/opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/etc/hadoop/hdfs-site.xml");

        try {
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }*/


        // mathod 3
        /*conf = new Configuration();
        String hdfsUserName = "hadoop";

        URI hdfsUri = null;
        try {
            hdfsUri = new URI("hdfs://huabingood01:9000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.error(e);
        }

        try {
            fs = FileSystem.get(hdfsUri,conf,hdfsUserName);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e);
        }*/

        return fs;
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
        Path outPath = new Path("/huabingood/hadoop.tar.gz");

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
    }


}
