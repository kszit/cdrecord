package com.kszit.CDReport.util;

import java.util.Iterator;
import java.util.Map;

public class QueryReportUtil {

	public static boolean correspondQueryCondition(Map<String,String> condition,String value){
		boolean return_value = false;
		if(value==null || value.equals("")){
			return false;
		}
		if(condition==null || condition.size()==0){
			return true;
		}
		
		Iterator<String> keyIter = condition.keySet().iterator();
		while(keyIter.hasNext()){
			String conKey = keyIter.next();
			String conValue = condition.get(conKey);
			if(conKey.contains("like")){
				if(value.contains(conValue)){
					return_value = true;
				}else{
					return false;
				}
			}else 
			if(conKey.contains("greater")){
				if(value.contains("-")){
					
				}else{
					double doubleConVaue = Double.parseDouble(conValue);
					double doubleValue = Double.parseDouble(value);
					if(doubleValue>doubleConVaue){
						return_value = true;
					}else{
						return false;
					}
				}
			}else 
			if(conKey.contains("less")){
				if(value.contains("-")){
					
				}else{
					double doubleConVaue = Double.parseDouble(conValue);
					double doubleValue = Double.parseDouble(value);
					if(doubleValue<doubleConVaue){
						return_value = true;
					}else{
						return false;
					}
				}
			}
		}
		return return_value;
	}
}
