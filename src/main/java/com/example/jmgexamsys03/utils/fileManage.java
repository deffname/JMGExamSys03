package com.example.jmgexamsys03.utils;

import java.io.File;

public class fileManage {

    /**
     * 删除对应路径下的文件，会自动加路径前缀
     * @param filePath
     */
    public static void deleteFile(String filePath){
        File file = new File("D:/user_app"+filePath);
        if (file.exists() && !file.isDirectory()) {
            if (file.delete()) {
                System.out.println("文件已成功删除：" + filePath);
            } else {
                System.out.println("无法删除文件：" + filePath);
            }
        } else {
            System.out.println("文件不存在或是一个目录：" + filePath);
        }
    }
}
