package com.kszit.CDReport.util;

import java.io.UnsupportedEncodingException;

/**
 * 字符串服务类
 * @author Administrator
 */
public class StringUtil {
	/**
	 * 判定字符串是否为空是
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		if(s==null || "".equals(s)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 字符串转换为long，字符串为null时，返回值为0.
	 * @param s
	 * @return
	 */
	public static long stringToLong(String s){
		if(s==null){
			return 0;
		}
		long returnvalue = 0;
		if(s.contains(".")){
			returnvalue = Long.parseLong(s.substring(0, s.lastIndexOf(".")));
		}else if(!"".equals(s)){
			returnvalue = Long.parseLong(s);
		}
		return returnvalue;
	}
	/**
	 * 字符串转换为double，字符串为null时，返回值为0.
	 * @param s
	 * @return
	 */
	public static double stringToDouble(String s){
		if(s==null){
			return 0;
		}
		return Double.parseDouble(s);
	}
	
	public static String gbkToISO8859(String s){
		String return_s = ""; 
        try {
			return_s = new String(s.getBytes("gbk"),"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return return_s;
	}
	
	/**
	 * 字符串解析类
	 * @param reportBindidAndHVBindid  格式：reportbindid|hbindid#vbindid
	 * @return
	 */
	public static String getReportBindid(String reportBindidAndHVBindid){
		if(reportBindidAndHVBindid==null || "".equals(reportBindidAndHVBindid)){
			return null;
		}
		if(reportBindidAndHVBindid.contains("|")){
			return reportBindidAndHVBindid.split("\\|")[0];
		}
		return null;
		
	}
	
	/**
	 * 字符串解析类
	 * @param reportBindidAndHVBindid  格式：reportbindid|hbindid#vbindid
	 * @return
	 */
	public static String getHVBindid(String reportBindidAndHVBindid){
		if(reportBindidAndHVBindid==null || "".equals(reportBindidAndHVBindid)){
			return null;
		}
		if(reportBindidAndHVBindid.contains("|")){
			return reportBindidAndHVBindid.split("\\|")[1];
		}
		return null;
		
	}
	
	/**
	 * 字符串解析类
	 * @param reportBindidAndHVBindid  格式：hbindid#vbindid
	 * @return
	 */
	public static String getHBindid(String HVBindid){
		if(HVBindid==null || "".equals(HVBindid)){
			return null;
		}
		if(HVBindid.contains("#")){
			return HVBindid.split("#")[0];
		}
		return null;
		
	}
	
	/**
	 * 字符串解析类
	 * @param reportBindidAndHVBindid  格式：hbindid#vbindid
	 * @return
	 */
	public static String getVBindid(String HVBindid){
		if(HVBindid==null || "".equals(HVBindid)){
			return null;
		}
		if(HVBindid.contains("#")){
			return HVBindid.split("#")[1];
		}
		return null;
		
	}
	
	/**
	 * 按照指定的长度 width，在字符串中添加<br>
	 * 100px长度可以存放13个汉字，
	* @Title: TextAddBr 
	* @Description:  
	* @param：
	* @return： String
	* @throws：
	 */
	public static String TextAddBr(String s,int width){
		//全部显示内容总长度
		int allLength = (s.length()/13+1)*100;
		if(allLength<=width){
			return s;
		}
		
		//每行显示汉字个数
		int lineSize = (width/100)*13;
		if(lineSize==0){
			return s;
		}
		
		StringBuffer sb = new StringBuffer();
		for(int i=0,is=(s.length()/lineSize)+1;i<is;i++){
			if(i==(is-1)){
				sb.append(s.substring(i*lineSize, s.length()));
			}else{
				sb.append(s.substring(i*lineSize, (i+1)*lineSize));
				sb.append("<br>");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 转换为处理json
	* @Title: string2Json 
	* @Description:  
	* @param：
	* @return： String
	* @throws：
	 */
	public static String string2Json(String s) {        
        StringBuffer sb = new StringBuffer();        
        for (int i=0; i<s.length(); i++) {  
            char c = s.charAt(i);    
             switch (c){  
             case '\"':        
                 sb.append("\\\"");        
                 break;        
             case '\\':        
                 sb.append("\\\\");        
                 break;        
             case '/':        
                 sb.append("\\/");        
                 break;        
             case '\b':        
                 sb.append("\\b");        
                 break;        
             case '\f':        
                 sb.append("\\f");        
                 break;        
             case '\n':        
                 sb.append("\\n");        
                 break;        
             case '\r':        
                 sb.append("\\r");        
                 break;        
             case '\t':        
                 sb.append("\\t");        
                 break;        
             default:        
                 sb.append(c);     
             }  
         }      
        return sb.toString();     
        }  
	
	/**
	 * 
	* includeCharsNum(统计字符串中包含子字符串的数量)  
	* TODO(这里描述这个方法适用条件 – 可选)  
	* TODO(这里描述这个方法的执行流程 – 可选)  
	* TODO(这里描述这个方法的使用方法 – 可选)  
	* TODO(这里描述这个方法的注意事项 – 可选)  
	* @param name  str:被查找字符串，con：子字符串
	* @param @return 设定文件  
	* @return String DOM对象  
	* @Exception 异常对象  
	* @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public static int includeCharsNum(String str,String con){
		int num = 0;
		
	    if(str.indexOf(con) != -1)  
        {  
            String[] str1 = str.split("\\"+con);  
            num = str1.length-1;  
        }  
        return num;
	}
	/**
	 * 
	* splits(适用于：|、#})  
	* TODO(这里描述这个方法适用条件 – 可选)  
	* TODO(这里描述这个方法的执行流程 – 可选)  
	* TODO(这里描述这个方法的使用方法 – 可选)  
	* TODO(这里描述这个方法的注意事项 – 可选)  
	* @param name  
	* @param @return 设定文件  
	* @return String DOM对象  
	* @Exception 异常对象  
	* @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String[] splits(String str,String splitStr){
		String[] str1 = str.split("\\"+splitStr);  
		return str1;
	}

}
