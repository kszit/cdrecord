package com.kszit.CDReport.cor.dao.hibernate.po;



public class HVConfigPO extends ParentPO{

	private String reportBindid;
	
	private long hbindid;
	
	private long vbindid;
	

	public HVConfigPO(){}
	
	public String getReportBindid() {
		return reportBindid;
	}

	public void setReportBindid(String reportBindid) {
		this.reportBindid = reportBindid;
	}

	public long getHbindid() {
		return hbindid;
	}

	public void setHbindid(long hbindid) {
		this.hbindid = hbindid;
	}

	public long getVbindid() {
		return vbindid;
	}

	public void setVbindid(long vbindid) {
		this.vbindid = vbindid;
	}

	

	@Override 
	public boolean equals(Object arg0) { 
		if (arg0 != null && arg0 instanceof HVConfigPO) { 
			HVConfigPO othroPo = (HVConfigPO)arg0;
			if(othroPo.getReportBindid().contains(this.reportBindid) && othroPo.getHbindid()==this.hbindid && othroPo.getVbindid()==this.vbindid){
				return true;
			}else{
				return false;
			}
		} 
		return false; 
	} 


	
}
