package com.kszit.CDReport.util.beanUtilEx;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;

public class UtilDateConvert implements Converter {

        public Object convert(Class arg0, Object arg1) {
        	if(arg1==null){
        		return null;
        	}
            try{
                java.util.Date d = (java.util.Date)arg1;
                return new java.sql.Date(d.getTime());
            }
            catch(Exception e){
                return null;
            }
            
        }
        

}
