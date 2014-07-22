package com.kszit.CDReport.cor.service;


import com.kszit.CDReport.cor.dao.StaticIndexDao;
import com.kszit.CDReport.cor.dao.hibernate.StaticIndexDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.StaticIndexPO;

public class StaticIndexService {
	
	static StaticIndexDao indexDao = new StaticIndexDaoHiberImpl();
	
	public static long bindidIndexOld = 0;
	public static long bindidIndex = -1;
	public static long bindidIncrease = 10;
	public StaticIndexService(){}
	
	public static long getBindidIndex(){
		if(bindidIndex==-1 || bindidIndex-bindidIndexOld>=bindidIncrease){
			initBindidIndex();
		}
		return bindidIndex++;
	}
	
	private static void initBindidIndex(){
		StaticIndexPO po = indexDao.getOneByName("BINDID_INDEX");
		bindidIndexOld = bindidIndex = po.getDataIndex().longValue();
		po.setDataIndex(new Long(bindidIndex+bindidIncrease));
		indexDao.update(po);
	}
	

}
