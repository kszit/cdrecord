package com.kszit.CDReport.cor.controler;

import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.excel.ExcelService;
import com.kszit.CDReport.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

/**
 * 上传excel模版
 *
 * @author Administrator
 *
 */
public class ExcelUpload extends ReportCDSupport {

    // 封装上传文件域的属性
    private File excelFile;
    // 封装上传文件类型的属性
    private String excelFileContentType;
    // 封装上传文件名的属性
    private String excelFileFileName;
    
    private String num;


    private DataReportModel dataReportModel = new DataReportModel();
    
    /**
     * 
     * excel 模板数据导入
     */
    public String excelModelUpload() throws FileNotFoundException {
        ExcelService excelServce = new ExcelService(dataReportModel);
        int dataBindid= excelServce.importExcelData(excelFile);
        
        super.outText("<script>parent.callback('"+dataBindid+"')</script>");
      
        return SUCCESS;
    }
    
    public String userPicUpload() throws FileNotFoundException {
    	String fileType = excelFileFileName.substring(excelFileFileName.indexOf("."));
    	String returnStr = Constants.USER_PICFILE_FOLDER+"/"+System.currentTimeMillis()+fileType;
        String filepathAndName = Constants.APP_ROOT_PATH+returnStr;
        File excelDataFile = new File(filepathAndName);
        try {
            FileUtils.copyFile(excelFile, excelDataFile);
        } catch (IOException ex) {
            Logger.getLogger(ExcelService.class.getName()).log(Level.SEVERE, null, ex);
        }
        returnStr = returnStr.replaceAll("//", "\\");
        super.outText("<script>parent.userPicFileUpCallback('"+returnStr+"')</script>");
      
        return SUCCESS;
    }
    
    

    public File getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(File excelFile) {
        this.excelFile = excelFile;
    }

    public String getExcelFileContentType() {
        return excelFileContentType;
    }

    public void setExcelFileContentType(String excelFileContentType) {
        this.excelFileContentType = excelFileContentType;
    }

    public String getExcelFileFileName() {
        return excelFileFileName;
    }

    public void setExcelFileFileName(String excelFileFileName) {
        this.excelFileFileName = excelFileFileName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

	public DataReportModel getDataReportModel() {
		return dataReportModel;
	}

	public void setDataReportModel(DataReportModel dataReportModel) {
		this.dataReportModel = dataReportModel;
	}



    
    
}
