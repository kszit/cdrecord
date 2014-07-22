/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.util;

import java.io.File;

/**
 *
 * @author Administrator
 */
public class FileUtil {
    
    /**
     * 判定文件是否存在
     * @param filePath
     * @return 
     */
    public static boolean isExist(String filePath){
        if(new File(filePath).exists()){
            return true;
        }
        return false;
    }
    
    public static boolean createFolder(String floderPath){
        return new File(floderPath).mkdirs();
       
    }
    
    
}
