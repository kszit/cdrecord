package com.kszit.CDReport.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassInstallUtil {

	
	public static Object installClass(String classPathAndName){
		Object o = null;
		try {
			Class f = Class.forName(classPathAndName);
			o =  f.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public static Object installClass(String classPathAndName,Class[] classes,Object[] objects){
		Object o = null;
		try {
        Class c=Class.forName(classPathAndName);   
 
        /*以下调用带参的、私有构造函数*/   
        Constructor c1=c.getDeclaredConstructor(classes);   
        c1.setAccessible(true);   
        o = c1.newInstance(objects);  
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
}
