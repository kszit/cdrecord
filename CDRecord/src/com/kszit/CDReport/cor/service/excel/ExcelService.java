/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.util.Constants;
import com.kszit.CDReport.util.FileUtil;


/**
 *
 * @author Administrator
 */
public class ExcelService {
    
	private DataReportModel dataReportModel = null;
    
    private ReportConfigPO reportConfig;
    //用户上传excel文件存放文件夹
    private String excelInputModelFolderPath = Constants.APP_FOLDER_DIR+Constants.INPUT_FOLDER;
    //模版文件存放文件夹
    private String excelModelFolderPath = Constants.APP_FOLDER_DIR+Constants.EXCEL_FOLDER;
    //数据文件存放文件夹
    private String excelDataFolderPath = Constants.APP_FOLDER_DIR+Constants.DATA_FOLDER;
    
    //模版文件存放文件夹  带年份
    private String modelFilodPathWithYear = "";
    //临时文件存放文件夹
    private String excelTempFolderPath = Constants.APP_FOLDER_DIR+Constants.TEMP_FOLDER;
    
    //excel模版文件名称
    private String fileName = "";
    //excel模版文件路径及文件名
    private String filePathAndName = "";
    

    public ExcelService(){    }
    
    public ExcelService(DataReportModel dataReportModel){
        this.dataReportModel = dataReportModel;
    }
    
    /**
     * 导入excel中的数据到数据库中
     * @param excelFile
     * @return 新添加数据的bindid
     */
    public int importExcelData(File excelFile){
        String filepathAndName = excelInputModelFolderPath+"/"+System.currentTimeMillis()+".xls";
        File excelDataFile = new File(filepathAndName);
        try {
            FileUtils.copyFile(excelFile, excelDataFile);
        } catch (IOException ex) {
            Logger.getLogger(ExcelService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ExcelToDB excelToDb = new ExcelToDB(dataReportModel.getRepotBindid(),dataReportModel);
        return excelToDb.dataToDb(excelDataFile);
    }
    
    
    /**
     * 数据导入的excel模版
     * @return
     * @throws FileNotFoundException 
     */
    public Object[] getExcelModel() throws FileNotFoundException{
    	String fileRealeName = "";
        reportConfig = ReportConfigService.getReportConfigPO(dataReportModel.getRepotBindid());
        modelFilodPathWithYear =  excelModelFolderPath+"/"+reportConfig.getRtyear();
        fileRealeName = reportConfig.getRtyear()+"年"+reportConfig.getReportName()+"模版";
        fileName = reportConfig.getReportLink()+".xls";
        filePathAndName = modelFilodPathWithYear+"/"+fileName;
        
        InputStream inputStream = null;
        if(Constants.IS_DEVELEP){
            createExcelModel();
        }else{
            if(!FileUtil.isExist(filePathAndName)){
                createExcelModel();
            }    
        }

        try{
        	inputStream = new FileInputStream(new File(filePathAndName));
        }catch(FileNotFoundException e){
           
        }
        
       return new Object[]{inputStream,fileRealeName};
    }
    
    /**
     * 下载excel数据数据文件
     * @return
     * @throws FileNotFoundException 
     */
    public Object[] getExcelData() throws FileNotFoundException{
    	String fileRealeName = "";
    	ReportDataService reportDataService = new ReportDataService();
    	DataReportPO dataReportPo = reportDataService.getDataReportByBindid(dataReportModel.getBindId());
    	
    	dataReportModel = new DataReportModel(dataReportPo);
    	//数据文件路径及文件命名：：：：数据文件夹/部门bindid/报表链接id/年份-期数.xls
        reportConfig = ReportConfigService.getReportConfigPO(dataReportPo.getRepotBindid());
        modelFilodPathWithYear =  excelDataFolderPath+"/"+dataReportModel.getCreateDepBindid()+"/"+reportConfig.getReportBindidLink();
        fileName = reportConfig.getRtyear()+"-"+dataReportModel.getPeriodsName()+".xls";
        filePathAndName = modelFilodPathWithYear+"/"+fileName;
        
        InputStream inputStream = null;
        if(Constants.IS_DEVELEP){//开发模式
            createExcelData();
        }else{//
            if(!FileUtil.isExist(filePathAndName)){
            	createExcelData();
            }    
        }

        try{
        	inputStream = new FileInputStream(new File(filePathAndName));
        }catch(FileNotFoundException e){
           
        }
        //下载给用户时的文件名称   部门--报表名称（xx年yy月）
        fileRealeName = dataReportModel.getCreateDepName()+"--"+reportConfig.getReportName()+"("+dataReportModel.getYear()+"年"+dataReportModel.getPeriodsName()+")";
        
        return new Object[]{inputStream,fileRealeName};
    }
    
    /**
     * 到缓存文件夹中找相应的文件，若找到则返回；
     * @param reportBindi 
     */
    public void createExcelData(){
        
        if(!FileUtil.isExist(modelFilodPathWithYear)){
            FileUtil.createFolder(modelFilodPathWithYear);
        }

        
        //生成excel文件
        TableDataToExcelData table2exceldata = new TableDataToExcelData(dataReportModel);
        table2exceldata.createExcel(filePathAndName);
        
        
    }
    
    /**
     * 到缓存文件夹中找相应的文件，若找到则返回；
     * @param reportBindi 
     */
    public void createExcelModel(){
        
        if(!FileUtil.isExist(modelFilodPathWithYear)){
            FileUtil.createFolder(modelFilodPathWithYear);
        }

        
        //生成excel文件
        TableToExcel table2excel = new TableToExcel(dataReportModel.getRepotBindid());
        table2excel.createExcel(filePathAndName);
        
        
    }
    
    /**
     * 初始化文件夹，在系统启动时，调用类：AppStartListener
     * excelmodel存放文件夹和临时文件（excel导入时，上传的文件）
     */
    public static void initFolde(){
    	
        String modelFolderPath = Constants.APP_FOLDER_DIR+Constants.EXCEL_FOLDER;
        String tempFolderPath = Constants.APP_FOLDER_DIR+Constants.TEMP_FOLDER;
        String inputExcelFolderPath = Constants.APP_FOLDER_DIR+Constants.INPUT_FOLDER;//用户上传模版文件存放路径
        //数据文件存放文件夹
        String excelDataFolderPath = Constants.APP_FOLDER_DIR+Constants.DATA_FOLDER;
        //用户上传图片保持路径
        String  userPicFilePath = Constants.APP_ROOT_PATH+Constants.USER_PICFILE_FOLDER;
        
        if(!FileUtil.isExist(modelFolderPath)){
            FileUtil.createFolder(modelFolderPath);
        }
        if(!FileUtil.isExist(tempFolderPath)){
            FileUtil.createFolder(tempFolderPath);
        }
        if(!FileUtil.isExist(inputExcelFolderPath)){
            FileUtil.createFolder(inputExcelFolderPath);
        }  
        if(!FileUtil.isExist(excelDataFolderPath)){
            FileUtil.createFolder(excelDataFolderPath);
        } 
        if(!FileUtil.isExist(userPicFilePath)){
            FileUtil.createFolder(userPicFilePath);
        } 
        
       
    }
    
}
