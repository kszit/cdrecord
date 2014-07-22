package com.kszit.CDReport.cor.dao.hibernate.graph;

import java.util.List;

import com.kszit.CDReport.cor.dao.graph.GraphConfigMainDao;
import com.kszit.CDReport.cor.dao.hibernate.HibernateActionParent;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.PersionPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;

public class GraphConfigMainDaoHiberImpl extends HibernateActionParent
		implements GraphConfigMainDao {

	@Override
	public String saveOrUpdate(GraphConfigMainPO graphCMain) {
		if(graphCMain.getId()!=null){
			//super.update(graphCMain);

			String sql = "update graphconfigmain set name='"+graphCMain.getName()+"',orderIndex="+graphCMain.getOrderIndex()+",graphType="+graphCMain.getGraphType()+",XAxis='"+graphCMain.getXAxis()+"',YAxis='"+graphCMain.getYAxis()+"',canQueryDepts='"+graphCMain.getCanQueryDepts()+"',startDay="+graphCMain.getStartDaySql()+",endDay="+graphCMain.getEndDaySql()+",createUserId='"+graphCMain.getCreateUserId()+"',createDate="+graphCMain.getCreateDateSql()+",deptIds='"+graphCMain.getDeptIds()+"' where id="+graphCMain.getId();
			super.excecleDDL(sql);
			return graphCMain.getId()+"";
		}else{
			return super.insert(graphCMain);
		}
	}	
	
	@Override
	public void delete(long bindId) {
		super.deleteByBindids2("GraphConfigMainPO", bindId+"");
	}

	@Override
	public List<GraphConfigMainPO> getListOffAll() {
        super.openSession();
		List<GraphConfigMainPO> relist = session.createQuery("from GraphConfigMainPO as po").list();
		return relist;
	}

	@Override
	public GraphConfigMainPO getByBindId(long bindId) {
		GraphConfigMainPO po = (GraphConfigMainPO)super.getByBindIdCommon("GraphConfigMainPO",bindId);
		return po;
	}

}
