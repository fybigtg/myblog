package com.rethx.managesys.utils;

import com.rethx.managesys.common.Constants;
import com.rethx.managesys.exception.ServiceException;

import java.io.*;

/**
 * @author YTYYYY
 * @version 1.0
 * @date 2024/5/27-16:39
 */

public class FileUtils implements Constants {

    public static boolean strToFile(String str,String filepath) {
        File f = new File(filepath);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new ServiceException(CODE_800,e.toString());
            }
        }
        BufferedReader br = new BufferedReader(new StringReader(str));
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(f));
            char[] chs = new char[1024];
            int len;
            while((len=br.read(chs)) != -1){
                bw.write(chs,0,len);
            }
            bw.flush();
            br.close();
            bw.close();
        } catch (IOException e) {
            throw new ServiceException(CODE_800,e.toString());
        }
        return true;
    }

    public static String fileToStr(String filepath){
        File f = new File(filepath);
        if(!f.exists()){
            throw new ServiceException(CODE_800, "文件读取失败");
        }
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            char[] chs = new char[1024];
            int len;
            while((len=br.read(chs)) != -1){
                sb.append(chs,0,len);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//        String s = "jashdahjd\ndafgdadadfafajfsafvaca1213131\n\ndasf";
//        String path = "C://Users/13207/Desktop/mds/de04c309a6104bc188470198e4970de5.md";
//        //ileUtils.strToFile(s,"C://Users/13207/Desktop/tmp.txt");
//        System.out.println(fileToStr(path));
//    }
}
