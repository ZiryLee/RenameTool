package me.ziry;

import me.ziry.util.ReNameUtil;

import java.io.IOException;

/**
 * 终端程序
 * @author Ziry
 */
public class TerminalMain {

    public static void main(String[] args) throws IOException {

        String inPath = "D:\\tmp\\rename";
        String outPath = "D:\\tmp\\rename2";
        String rule = "【序号】_【创建时间:yyyyMMddHHmmss】_test.【原文件后缀】";

        int result = ReNameUtil.renameByPath(inPath, outPath, rule);

        if(result == ReNameUtil.NOT_DIRECTORY) {
            System.out.println(inPath+" 不是文件夹");
        }
        else if(result == ReNameUtil.DIRECTORY_EMPTY) {
            System.out.println(inPath+" 是空的文件夹");
        }
        else {
            System.out.println("完成");
        }

    }

}
