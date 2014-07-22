package com.kszit.CDReport.cor.controler;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.excel.ExcelService;
import com.kszit.CDReport.util.StringUtil;

/**
 * excel模版下载
 *
 * @author Administrator
 *
 */
public class ExcelDownLoad extends ReportCDSupport {

    private String reportBindid = null;
    // Report输出流   
    public InputStream reportStream;
    // 输出流Content Type   
    public String contentType;
    // 输出流的生成的文件名   
    public String fileName;

    /**
     * 下载excel填报模版 ，链接格式：/file/excelModel.do?reportBindid=1020-0
     * @return
     * @throws FileNotFoundException
     */
    public String excelModel() throws FileNotFoundException {
    	String xlsfileName = "Report";
        // 获取EXCEL流   
        //reportStream = service.getExcelStream();
                //reportStream = service.getExcelStream();
    	DataReportModel dataReportMode = new DataReportModel();
    	dataReportMode.setRepotBindid(reportBindid);
        ExcelService excelServce = new ExcelService(dataReportMode);
        Object[] excelModelReturn = excelServce.getExcelModel();
        reportStream = (InputStream)excelModelReturn[0];//new FileInputStream("D:/poiExcelfile/workbook.xls");
        xlsfileName = (String)excelModelReturn[1];
		xlsfileName = StringUtil.gbkToISO8859(xlsfileName);
        // contentType设定   
        contentType = "application/vnd.ms-excel";
        // attachment表示网页会出现保存、打开对话框   
        fileName = "attachment; filename=\""+xlsfileName+".xls\"";

        return SUCCESS;
    }

    /**
     * 下载excel数据文件，链接格式：/file/excelData.do?reportBindid=1042，reportBindid为数据记录的bindid
     * 
     * @return
     * @throws FileNotFoundException
     */
    public String excelData() throws FileNotFoundException {
    	String xlsfileName = "Report";
        // 获取EXCEL流   
        //reportStream = service.getExcelStream();
                //reportStream = service.getExcelStream();
    	DataReportModel dataReportMode = new DataReportModel();
    	dataReportMode.setBindId(Long.parseLong(reportBindid));
    	
        ExcelService excelServce = new ExcelService(dataReportMode);
        Object[] excelModelReturn = excelServce.getExcelData();
        reportStream = (InputStream)excelModelReturn[0];//new FileInputStream("D:/poiExcelfile/workbook.xls");
        xlsfileName = (String)excelModelReturn[1];
		xlsfileName = StringUtil.gbkToISO8859(xlsfileName);
        // contentType设定   
        contentType = "application/vnd.ms-excel";
        // attachment表示网页会出现保存、打开对话框   
        fileName = "attachment; filename=\""+xlsfileName+".xls\"";

        return SUCCESS;
    }
    
    
    
    public String getReportBindid() {
        return reportBindid;
    }

    public void setReportBindid(String reportBindid) {
        this.reportBindid = reportBindid;
    }

    public InputStream getReportStream() {
        return reportStream;
    }

    public void setReportStream(InputStream reportStream) {
        this.reportStream = reportStream;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    
    
}
