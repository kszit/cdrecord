/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import com.kszit.CDReport.util.StringUtil;

/**
 * 
 * 将上传的excel文件数据导入到数据库中，
 * @author Administrator
 */
public class ExcelToDB  extends ExcelTableModel{
    
	DataReportModel dataReportModel = null;
	
	HSSFSheet sheet = null;
	
    public ExcelToDB(String tableBindid,DataReportModel dataReportModel) {
        super(tableBindid);
        this.dataReportModel = dataReportModel;
    }
    /**
     * 保存excel    
     * @param excelFile
     * @return 新保存的数据的bindid
     */
    public int dataToDb(File excelFile){
    	createExcel(excelFile);//读取excel文件
    	/*
        //从第几行开始读取数据   标题占行数+表头占行数+数据类型占行数
        int rowNum = super.titleRowNum+super.allHeaderRowNum+1;
        //从第几列开始读取数据,左侧列只占一列
        int colNum = 1;
        
        List<TableHeaderCell> headerLeafCell = tableHeader.getInputHeaderCellList();//表头
        int leafCols = super.allColnum-1;//表头叶子节点个数
        
        
        List<TableColumnCell> columnCells = tableColumn.getAllHeaderCellList();//左侧列
        int dataRows = columnCells.size();
        */
    	
    	Map<String,Object> newDataMap = new HashMap<String,Object>();
    	
    	//查找数据行
        int dataBeginRow = 0;
        for(int rowNum2=0;rowNum2<100000;rowNum2++){
        	String value = readExcel(rowNum2,0,0);
        	if(value!=null&&super.BEGIN_ROW_ONECELL.equals(value)){
        		dataBeginRow = rowNum2;
        		break;
        	}
        }
        
        //读取列表头 hbindid列表
        int hbindidRowNum = dataBeginRow;
        List<String> hibindidList = new ArrayList<String>();
        for(int columnNum=2;columnNum<100000;columnNum++){
        	String value = readExcel(hbindidRowNum,columnNum,0);
        	if(value!=null&&!"".equals(value)){
        		value = ""+StringUtil.stringToLong(value);
        		hibindidList.add(value);
        	}else{
        		break;
        	}
        }
        
        
        int dataRowNum = dataBeginRow+1;
        while (true) {//开始一行一行的读取数据
        	String colBindid = readExcel(dataRowNum,1,0);
        	if(colBindid==null||"".equals(colBindid)){
        		break;
        	}
        	colBindid = ""+StringUtil.stringToLong(colBindid);
        	
            int colNum = 2;
            for(String hbindid:hibindidList){
                    String value = readExcel(dataRowNum,colNum,0);
                    newDataMap.put(hbindid+"#"+colBindid, value);
                    colNum++;
            }
            dataRowNum++;
        }
      
        
        /*
        
        for (int j=0;j<dataRows;j++) {//开始一行一行的读取数据
            TableColumnCell colCell = columnCells.get(j);
            int excelColum = 0;
            
            //for (int i = 1; i <= leafCols; i++) {
            for(TableHeaderCell headercell:headerLeafCell){
                //TableHeaderCell headercell = headerLeafCell.get(i);
                if (headercell.isLeaf()) {
                	if(excelColum==0){
                		excelColum++;
                		continue;
                	}
                    long colBindid = colCell.getVerticalColumnConfigPO().getBindId();
                    long headerBindid = headercell.getHeaderRowConfigPO().getBindId();
System.out.println(colCell.getVerticalColumnConfigPO().getContent()+"===");
                    String value = readExcel(rowNum+j,excelColum,headercell.getHeaderRowConfigPO().getDataType());
System.out.println(headerBindid+"#"+colBindid+"==>"+value);
                    
                    newDataMap.put(headerBindid+"#"+colBindid, value);
                    excelColum++;
                }
            }
        }
        */
        ReportDataService dataService = new ReportDataService(super.tableBindid);
        return dataService.saveDataReport(dataReportModel, newDataMap);
        
    }
    
    /**
     * 生成excel对象
     * @param excelFile
     */
    private void createExcel(File excelFile){
    	POIFSFileSystem fs = null;
        try {
	        fs = new POIFSFileSystem(new FileInputStream(excelFile));
	        wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        sheet = (HSSFSheet) wb.getSheetAt(0);

    }
    
    /**
     * 读取指定单元格中的数据
     * @param rowNul
     * @param colNul
     * @return
     */
    private String readExcel(int rowNul,int colNul,int dataType){
        HSSFRow row = sheet.getRow(rowNul);
        if(row==null){
        	return null;
        }
        HSSFCell cell = row.getCell((short) colNul);
        String value = "";
        if(cell==null){
        	return value;
        }
        value = cell.toString();
        /*
        switch (dataType) {
	        case StaticVaribles.DataType_TextBindId:
	        	value = cell.getStringCellValue();
	            break;
	        case StaticVaribles.DataType_NumberBindId:
	        	value = cell.getNumericCellValue()+"";
	            break;
	            /*
	        case StaticVaribles.DataType_DateBindId:
	            
	            break;
	        case StaticVaribles.DataType_TimeBindId:
	        	
	            break;
	        case StaticVaribles.DataType_DateTimeBindId:
	            
	            break;
	            */
	        //default:
	        	
       // }
        
        
        
        return value;
    }
    
}
