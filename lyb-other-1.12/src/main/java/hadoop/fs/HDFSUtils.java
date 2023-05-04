package hadoop.fs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDFSUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HDFSUtils.class);

    /**
     * 初始化HDFS Configuration
     *
     * @return configuration
     */
    public static Configuration initConfiguration(String confPath) {
        Configuration configuration = new Configuration();
        configuration.addResource(new Path(confPath + File.separator + "core-site.xml"));
        configuration.addResource(new Path(confPath + File.separator + "hdfs-site.xml"));
        return configuration;
    }

    /**
     * 向HDFS指定目录创建一个文件
     *
     * @param fs HDFS文件系统
     * @param dst 目标文件路径
     * @param contents 文件内容
     */
    public static void createFile(FileSystem fs, String dst, String contents) {
        try {
            Path path = new Path(dst);
            FSDataOutputStream fsDataOutputStream = fs.create(path);
            fsDataOutputStream.write(contents.getBytes());
            fsDataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传本地文件至HDFS
     *
     * @param fs HDFS文件系统
     * @param src 源文件路径
     * @param dst 目标文件路径
     */
    public static void uploadFile(FileSystem fs, String src, String dst) {
        try {
            Path srcPath = new Path(src); // 原路径
            Path dstPath = new Path(dst); // 目标路径
            // 调用文件系统的文件复制函数,前面参数是指是否删除原文件，true为删除，默认为false
            fs.copyFromLocalFile(false, srcPath, dstPath);
            // 打印文件路径
            System.out.println("------------list files------------" + "\n");
            FileStatus[] fileStatus = fs.listStatus(dstPath);
            for (FileStatus file : fileStatus) {
                System.out.println(file.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件重命名
     *
     * @param fs
     * @param oldName
     * @param newName
     * @throws IOException
     */
    public static void rename(FileSystem fs, String oldName, String newName) {
        try {
            Path oldPath = new Path(oldName);
            Path newPath = new Path(newName);
            boolean isok = fs.rename(oldPath, newPath);
            if (isok) {
                System.out.println("rename ok!");
            } else {
                System.out.println("rename failure");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param fs
     * @param filePath
     * @throws IOException
     */
    public static void delete(FileSystem fs, String filePath) {
        try {
            Path path = new Path(filePath);
            boolean isok = fs.deleteOnExit(path);
            if (isok) {
                System.out.println("delete ok!");
            } else {
                System.out.println("delete failure");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建HDFS目录
     *
     * @param fs
     * @param path
     */
    public static void mkdir(FileSystem fs, String path) {
        try {
            Path srcPath = new Path(path);
            if (fs.exists(srcPath)) {
                System.out.println("目录已存在");
                return;
            }

            boolean isok = fs.mkdirs(srcPath);
            if (isok) {
                System.out.println("create dir ok!");
            } else {
                System.out.println("create dir failure");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取HDFS文件
     *
     * @param fs
     * @param filePath 文件路径
     */
    public static void readFile(FileSystem fs, String filePath) {
        try {
            Path srcPath = new Path(filePath);
            InputStream in = null;
            in = fs.open(srcPath);
            IOUtils.copyBytes(in, System.out, 4096, false); // 复制到标准输出流

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getResourcePath(String clazz, String name) throws ClassNotFoundException {
        String path = Class.forName(clazz).getClassLoader().getResource(name).getPath();
        System.out.println(path);
        return path;
    }

    public static String getCurrentClassPath(Class clazz) {

        LOG.info(
                "No path for the flink jar passed. Using the location of "
                        + HDFSUtils.class
                        + " to locate the jar");

        String encodedJarPath = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();

        final String decodedPath;
        try {
            // we have to decode the url encoded parts of the path
            decodedPath = URLDecoder.decode(encodedJarPath, Charset.defaultCharset().name());
            LOG.info("The jar path of class: {} is {}", clazz, decodedPath);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(
                    "Couldn't decode the encoded sloth-blink jar path: " + encodedJarPath);
        }

        return decodedPath;
    }
}
