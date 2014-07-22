package com.kszit.CDReport.cor.dao.hibernate.graph;

import java.util.List;

import com.kszit.CDReport.cor.dao.graph.GraphConfigSubDao;
import com.kszit.CDReport.cor.dao.hibernate.HibernateActionParent;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigSubPO;

public class GraphConfigSubDaoHiberImpl extends HibernateActionParent
		implements GraphConfigSubDao {

	@Override
	public String saveOrUpdate(GraphConfigSubPO graphCMain) {
		if(graphCMain.getId()!=null && graphCMain.getId()!=0){
			String sql = "update graphConfigSub set orderIndex="+graphCMain.getOrderIndex()+",mainBindid="+graphCMain.getMainBindid()+",tableLinkBindid='"+graphCMain.getTableLinkBindid()+"',tableCells='"+graphCMain.getTableCells()+"',periodsType="+graphCMain.getPeriodsType()+",isAddUp="+graphCMain.getIsAddUp()+",startDay="+graphCMain.getStartDaySql()+",endDay="+graphCMain.getEndDaySql()+",deptIds='"+graphCMain.getDeptIds()+"' where id="+graphCMain.getId();
			super.excecleDDL(sql);
			return graphCMain.getId()+"";
		}else{
			return super.insert(graphCMain);
		}
	}

	@Override
	public void delete(long bindId) {
		super.deleteByBindids2("GraphConfigSubPO", bindId+"");
	}
	
	@Override
	public void deleteByGraphBindid(long graphMainBindid) {
		this.openSession();
		this.beginTransaction();
		String hql= "delete from GraphConfigSubPO where mainBindid="+graphMainBindid;
		session.createQuery(hql).executeUpdate();
	}
	

	@Override
	public List<GraphConfigSubPO> getListOfAll() {
        super.openSession();
		List<GraphConfigSubPO> relist = session.createQuery("from GraphConfigSubPO as po").list();
		return relist;
	}

	@Override
	public List<GraphConfigSubPO> getListOfMainBindid(long mainBindid) {
        super.openSession();
		List<GraphConfigSubPO> relist = session.createQuery("from GraphConfigSubPO as po where po.mainBindid="+mainBindid).list();
		return relist;
	}
	
	@Override
	public GraphConfigSubPO getByBindId(long bindId) {
		GraphConfigSubPO po = (GraphConfigSubPO)super.getByBindIdCommon("GraphConfigSubPO",bindId);
		return po;
	}

}
