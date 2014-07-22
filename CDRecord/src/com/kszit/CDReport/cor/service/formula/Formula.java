package com.kszit.CDReport.cor.service.formula;

import net.sourceforge.jeval.EvaluationException;

import net.sourceforge.jeval.Evaluator;

/**
 * 计算公式辅助类，根据公式和提供的值计算出值
 * @author Administrator
 */
public class Formula {

	
	
	/**
	 * 根据公式和值 计算
	 * @param formula
	 * @param datas
	 * @return
	 */
	public static double compute(String formula,String[] datas){
		double result = 0;
		Evaluator eva = new Evaluator();
		try {
			//添加变量
			char[] chars = getCharArray(formula);
			for(int i=0,l=chars.length;i<l;i++){
				eva.putVariable(""+chars[i], datas[i]);
			}
			//公式计算
			result = eva.getNumberResult(trancFormula(formula));
			
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 公式中的字符数组
	 * @param formula
	 * @return
	 */
	private static char[] getCharArray(String formula){
		StringBuffer sb = new StringBuffer();
		int char_start = (int)'a';
		int char_end = (int)'z';
		char[] farmula_chars = formula.toCharArray();
		for(char c:farmula_chars){
			int char_int = (int)c;
			if(char_int>=char_start && char_int<=char_end){
				sb.append(c);
			}
		}
		return sb.toString().toCharArray();
	}
	
	/**
	 * 转换公式
	 * @param formula    例如：a+b-->#{a}+#{b}
	 * @return 
	 */
	private static String trancFormula(String formula){
		StringBuffer sb = new StringBuffer();
		int char_start = (int)'a';
		int char_end = (int)'z';
		char[] farmula_chars = formula.toCharArray();
		for(char c:farmula_chars){
			int char_int = (int)c;
			if(char_int>=char_start && char_int<=char_end){
				sb.append("#{"+c+"}");
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] a){
		System.out.println(compute("a+b",new String[]{"2","34"}));
	}
	
}
