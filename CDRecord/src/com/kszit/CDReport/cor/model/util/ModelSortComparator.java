package com.kszit.CDReport.cor.model.util;

import java.util.Comparator;

import com.kszit.CDReport.cor.model.ParentModel;
/**
 * 元素排序服务类，
 * @author Administrator
 */
public class ModelSortComparator implements Comparator<ParentModel>{

	public int compare(ParentModel o1, ParentModel o2) {
		if(o1.getOrderIndex()>o2.getOrderIndex()){
			return 1;
		}else{
			return -1;
		}
	}

}
