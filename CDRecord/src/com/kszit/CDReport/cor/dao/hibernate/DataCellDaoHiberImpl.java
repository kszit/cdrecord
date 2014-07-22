package com.kszit.CDReport.cor.dao.hibernate;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;

import com.kszit.CDReport.cor.dao.DataCellDao;
import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;

public class DataCellDaoHiberImpl extends HibernateActionParent implements DataCellDao {

	private String tablename;
	
	public DataCellDaoHiberImpl(){}
	
	public DataCellDaoHiberImpl(String tablename){
		this.tablename = tablename;
	}
	
	public void createTable(String tablename){
		String droptableSql = "DROP TABLE IF EXISTS "+tablename+";";
		String createTable = "CREATE TABLE "+tablename+" (ID bigint(15) NOT NULL auto_increment,datavalue varchar(250) default NULL,dataId int(11) default NULL,vId int(11) default NULL,hId int(11) default NULL,orderIndex int(11) default NULL,PRIMARY KEY  (ID)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
		super.excecleDDL(droptableSql);
		super.excecleDDL(createTable);
		this.commitTransaction();
	}
	
	public void dropTable(String tablename){
		String droptableSql = "DROP TABLE IF EXISTS "+tablename+";";
		super.excecleDDL(droptableSql);
		this.commitTransaction();
	}
	public String insert(DataCellPO data) {
		this.insertNoCommit(data);
		return "";
	}
	
	public String inserts(List<DataCellPO> datapos) {
		super.openSession();
		for(DataCellPO po:datapos){
			this.insertNoCommit(po);
		}
		return null;
	}
	
	public String update(DataCellPO table) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deletes(String ids) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 按照数据bindid删除数据
	 */
	public boolean deletesByDataBindid(String dataBindid) {
		super.openSession();
		String sql = "delete from "
				+ tablename
				+ " where dataId=" + dataBindid;
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		return true;
	}
	
	/**
	 * 报表id获得数据列表
	 */
	@SuppressWarnings("unchecked")
	public List<DataCellPO> getListOfReportId(int dataid) {
		List<DataCellPO> return_value = new ArrayList<DataCellPO>();
		super.openSession();
		String sql = "select id,datavalue,dataId,vid,hid,orderindex from "
				+ this.tablename
				+ " where dataId="+dataid;
		SQLQuery query = session.createSQLQuery(sql);
		//query.addEntity(DataCellPO.class);
		//query.setLong(0, dataid);
		List<Object[]> poList = query.list();
		for(java.util.Iterator<Object[]> iter = poList.iterator(); iter.hasNext();){
			Object[] o = iter.next();
			DataCellPO po = new DataCellPO();
			po.setId(((BigInteger)o[0]).longValue());
			po.setDatavalue((String)o[1]);
			po.setDataId((Integer)o[2]);
			po.setvId((Integer)o[3]);
			po.sethId((Integer)o[4]);
			po.setOrderIndex((Integer)o[5]);
			return_value.add(po);	
		}
		return return_value;
	}
	
	public List<DataCellPO> getListOfVid(int dataid, int vid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DataCellPO> getListOfHid(int dataid, int hid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DataCellPO getOneById(long id) {
		String sql = "select * from "
				+ this.tablename
				+ " where id=?";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(DataCellPO.class);
		query.setLong(0, id);
		DataCellPO po = (DataCellPO) query.uniqueResult();
		return po;
	}

	public DataCellPO getOneHidAndVid(long bindid,long hid, long vid) {
		super.openSession();
		String sql = "select * from "
				+ this.tablename
				+ " where dataId="+bindid+" and hid="+hid+" and vid="+vid+"";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(DataCellPO.class);
		//query.setLong(0, bindid);
		//query.setLong(1, hid);
		//query.setLong(2, vid);
		DataCellPO po = (DataCellPO) query.uniqueResult();
		return po;
	}

	private void insertNoCommit(DataCellPO data) {
		String sql = "insert into "
				+ tablename
				+ " (dataId, vId, hId, datavalue, orderIndex) values (?, ?, ?, ?, ?)";
		SQLQuery query = session.createSQLQuery(sql);
		query.setInteger(0, data.getDataId());
		query.setInteger(1, data.getvId());
		query.setInteger(2, data.gethId());
		query.setString(3, data.getDatavalue());
		query.setInteger(4, 0);
		query.executeUpdate();
	}
	
	
	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}






	

}
