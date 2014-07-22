package com.kszit.CDReport.cor.service.graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kszit.CDReport.cor.dao.graph.GraphConfigMainDao;
import com.kszit.CDReport.cor.dao.graph.GraphConfigSubDao;
import com.kszit.CDReport.cor.dao.hibernate.graph.GraphConfigMainDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.graph.GraphConfigSubDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigSubPO;
import com.kszit.CDReport.cor.model.graph.GraphData;
import com.kszit.CDReport.cor.model.graph.GraphDataTableCell;
import com.kszit.CDReport.cor.query.QueryCondition;
import com.kszit.CDReport.cor.query.QueryReportUtil;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.util.StringUtil;

public class GraphDataService {
	GraphConfigMainDao graphConfigMainDao = new GraphConfigMainDaoHiberImpl();
	GraphConfigSubDao graphConfigSubDao = new GraphConfigSubDaoHiberImpl();
	
	
	public GraphData getGraphData(long graphConfigBindid){
		
		return null;
		
	}
	
}
