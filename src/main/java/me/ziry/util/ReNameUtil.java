package me.ziry.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 重命名工具类
 * @author Ziry
 */
public class ReNameUtil {

    /**
     * 标记序号
     */
    public static final String SEQUENCE = "【序号】";

    /**
     * 标记创建时间:yyyyMMddHHmmss
     */
    public static final String CREATE_TIME_L14 = "【创建时间:yyyyMMddHHmmss】";

    /**
     * 标记创建时间:yyyyMMdd
     */
    public static final String CREATE_TIME_L8 = "【创建时间:yyyyMMdd】";

    /**
     * 标记原文件后缀
     */
    public static final String  OLD_FILE_SUFFIX = "【原文件后缀】";

    /**
     * 标记原文件名
     */
    public static final String  OLD_FILE_NAME = "【原文件名】";

    /**
     * 不是文件夹
     */
    public static final int NOT_DIRECTORY = -1;

    /**
     * 空的文件夹
     */
    public static final int DIRECTORY_EMPTY = -2;

    /**文件类型标记**/
    private static final String FILE_TYPE_MARK = ".";

    /**
     * 通过路径将文件夹内所有文件重命名
     * @param inPath 输入路径
     * @param outPath 输出路径
     * @param rule 规则：[序号][创建时间].txt
     * @return -1：输入路径不是文件夹，-2：输入路径下没有任何文件
     */
    public static int renameByPath(String inPath, String outPath, String rule) {

        File filePath = new File(inPath);

        // 是否是文件夹
        boolean isDirectory = filePath.isDirectory();
        if (!isDirectory) {
            return NOT_DIRECTORY;
        }

        String[] files = filePath.list();
        if (files==null || files.length==0) {
            return DIRECTORY_EMPTY;
        }

        File file;
        String newFileName;
        String oldFileName;

        for (int i = 0; i < files.length; i++) {

            oldFileName = files[i];

            file = new File(inPath + "/" + oldFileName);

            //跳过文件夹
            if( file.isDirectory() ) {
                continue;
            }

            /*
             * 将修改后的文件保存在指定目录下,
             * 内部会检测是否重名，重名进行序号叠加
             */
            newFileName = ReNameUtil.loadNewName(i+1, oldFileName, file, rule);
            try {
                /*
                 * 移动方式
                 * file.renameTo( constructFile(outPath,newFileName) )
                 */
                FileUtils.copyFile(file, constructFile(outPath,newFileName) );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 0;

    }

    /**
     * 根据规则加载新文件名
     * @param index 序号
     * @param rule 规则表达式
     * @return
     */
    public static String loadNewName(int index, String oldFileName, File file, String rule) {

        String newFileName = rule;

        //没有规则，返回旧名字
        if(rule==null || rule.length()==0) {
            return oldFileName;
        }

        //标记序号
        if( rule.indexOf(SEQUENCE) != -1 ){
            newFileName = newFileName.replaceAll(SEQUENCE, Integer.toString(index));
        }


        long time = file.lastModified();
        SimpleDateFormat formatter;

        //标记创建时间:yyyyMMddHHmmss
        if( rule.indexOf(CREATE_TIME_L14) != -1 ){

            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            newFileName = newFileName.replaceAll(CREATE_TIME_L14, formatter.format(new Date(time)) );

        }

        //标记创建时间:yyyyMMdd
        if( rule.indexOf(CREATE_TIME_L8) != -1 ){

            formatter = new SimpleDateFormat("yyyyMMdd");
            newFileName = newFileName.replaceAll(CREATE_TIME_L8, formatter.format(new Date(time)) );

        }

        //获取后缀标记位置
        int lastIndexOf = oldFileName.lastIndexOf(FILE_TYPE_MARK);

        //标记原文件后缀
        if( rule.indexOf(OLD_FILE_SUFFIX) != -1 ){

            String oldFileSuffix = "";
            if(lastIndexOf != -1) {
                oldFileSuffix = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
            }

            newFileName = newFileName.replaceAll(OLD_FILE_SUFFIX, oldFileSuffix );
        }

        //标记原文件名
        if( rule.indexOf(OLD_FILE_NAME) != -1 ){

            String oldFilePrefix = oldFileName;
            if(lastIndexOf != -1) {
                oldFilePrefix = oldFileName.substring(0, oldFileName.lastIndexOf("."));
            }

            newFileName = newFileName.replaceAll(OLD_FILE_NAME, oldFilePrefix );

        }

        return newFileName;
    }


    /**
     * 构造文件对象，内部会检测是否重名，重名进行序号叠加
     * @param outPath 输出路径
     * @param fileName 文件名
     * @return
     */
    public static File constructFile(String outPath, String fileName){

        File file = new File(outPath , fileName);

        if( file.exists() ) {

            String name = fileName.substring(0, fileName.lastIndexOf("."));
            String type = fileName.substring(fileName.lastIndexOf("."));

            int i = 1;
            String newName;
            while ( file.exists() ) {
                newName = name + "_" + i;
                file = new File(outPath , newName + type);
                i++;
            }

        }

        return file;
    }

}
