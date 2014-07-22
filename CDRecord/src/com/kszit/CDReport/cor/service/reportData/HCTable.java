/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.reportData;

/**
 *辅助类，为生成excel文件，提供头部和左侧配置信息的java对象
 * @author Administrator
 */
public class HCTable extends Table{
    private String reportBindid;
    
    public HCTable(){}
    
    public HCTable(String reportBindid){
        super(reportBindid,true);
        this.reportBindid = reportBindid;
    }
    
    
    /**
     * 使用HCTable类的相关方法不能调用getTableContent（）方法。
     * @return 
     */
    @Override
    String getTableContent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
