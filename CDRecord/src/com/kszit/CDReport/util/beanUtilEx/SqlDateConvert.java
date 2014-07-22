package com.kszit.CDReport.util.beanUtilEx;



import org.apache.commons.beanutils.Converter;


public class SqlDateConvert implements Converter {

    public Object convert(Class arg0, Object arg1) {
    	if(arg1==null){
    		return null;
    	}else{
    		return arg1;
    	}
        
    }

}
