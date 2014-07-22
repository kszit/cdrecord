package com.kszit.CDReport.util;

import java.text.DecimalFormat;

public class FloatUtil {

	/**
	 * float 的整数部分
	 * @param f
	 * @return
	 */
	public static int getIntegerPart(float f){
		return (int)f;
	}
	/**
	 * float 的小数部分
	 * @param f
	 * @return
	 */
	public static int getDecimalsPart(float f){
		String fStr = f+"";
		if(fStr.contains(".")){
			return Integer.parseInt(fStr.substring(fStr.indexOf(".")+1, fStr.length()));
		}else{
			return 0;
		}
		
	}
	/**
	 * 格式化输出float数据，只能将数据按照几位小数显示
	 * @param floatstyle  参数类型为 yy.xx,xx标示：几位小数，yy-xx:表示：几位整数
	 * @param data 要格式化输出的数据
	 * @return
	 */
	public static String getDecimalFormat(float floatstyle,String data){
		DecimalFormat df = new DecimalFormat();
		
		String style = getFloatStyle(floatstyle);
		
		df.applyPattern(style);
		float dataFloat = Float.parseFloat(data);
		
		return df.format(dataFloat);
		
	}
	
	/**
	 * 获取float格式户输出的风格
	 * @param floatstyle   参数类型为 yy.xx,xx标示：几位小数，yy-xx:表示：几位整数
	 * @return
	 */
	private static String getFloatStyle(float floatstyle){
		
		int decimalPart = getDecimalsPart(floatstyle);
		
		int integerPart = getIntegerPart(floatstyle)-decimalPart;
		
		StringBuffer style = new StringBuffer();
		
		for(int i=1;i<integerPart;i++){
			style.append("#");
		}
		if(decimalPart>0){
			style.append("0.");
		}else{
			style.append("0");
		}
		for(int i=1;i<=decimalPart;i++){
			style.append("0");
		}
		
		return style.toString();
	}
}
