package com.kd.xxhyf.util;

import java.io.*;

public class FileUtil {
    public static String strChangeFile(String filePath) {
        String content = null;
        try {
            Reader reader = new InputStreamReader(new FileInputStream(filePath), "utf-8");
            StringBuffer sb = new StringBuffer();
            char[] tempchars = new char[1024];
            int len;
            while ((len = reader.read(tempchars)) != -1) {
                sb.append(tempchars,0,len);
            }
            content = sb.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static boolean saveFile(String content, String filePath) {
        return saveFile(content,new File(filePath));
    }

    public static boolean saveFile(String content, File file) {
        FileWriter fw = null;
        try {
            //目录不存在新建目录
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(file.exists()){
                file.delete();
            }
            fw = new FileWriter(file, false);
            if (content != null) {
                fw.write(content);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fw != null) {
                try {
                    fw.flush();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
