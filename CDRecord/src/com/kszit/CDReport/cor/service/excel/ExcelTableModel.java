/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.excel;

import com.kszit.CDReport.cor.service.reportData.HCTable;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Administrator
 */
public class ExcelTableModel {
    
	//模版文件数据行记录开始的前一行第一个单元格的内容
	public static final String BEGIN_ROW_ONECELL = "NO";
	
    String tableBindid = null;
    HCTable table = null;
    TableHeader tableHeader = null;
    TableColumn tableColumn = null;
    Workbook wb = null;
    
    /**
     * 标题跨行数
     */
    int titleRowNum = 1;
    /**
     * 总垮列数
     */
    int allColnum = 0;
    /**
     * 表头总垮行数
     */
    int allHeaderRowNum = 0;

   
    
    int rowNum = 0;
    
    public ExcelTableModel(){}
    
    public ExcelTableModel(String tableBindid){
            this.tableBindid = tableBindid;
        table = new HCTable(tableBindid);
        this.tableHeader = table.tableHeader;
        
        this.tableColumn = table.tableColumn;
        
        init();
    }
    
        /**
     * 初始化参数：总垮列数、表头总垮行数
     */
    private void init(){
         for(TableHeaderCell cell:tableHeader.getInputHeaderCellList()){
             if(cell.isLeaf()){
               allColnum++;
             }
         }
         
         allHeaderRowNum = tableHeader.getHeaderRowNumReality();
    }
}
