/*
 * $Id: ExampleSupport.java 471756 2006-11-06 15:01:43Z husted $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.kszit.CDReport.cor.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.kszit.CDReport.cor.model.page.PageParam;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.openserv.persion.model.PersionToReport;
import com.kszit.CDReport.openserv.role.model.RoleToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.RoleUtil;
import com.kszit.CDReport.util.UserUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Base Action class for the Tutorial package.
 */
public class ReportCDSupport extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

    private static final long serialVersionUID = 3399841547710188990L;
    private PageParam pageParam = new PageParam();
    private DepartmentToReport department;
    private PersionToReport user;
    private RoleToReport role;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;
    
    //子类中，生成extjs的树形数据时调用
    List<Object> treeObjects = new ArrayList<Object>();
    
    String param1;
    
    String param2;
    
    String param3;
 
    public ReportCDSupport() {
    }

    @Override
    public void setServletResponse(HttpServletResponse arg0) {
        this.response = arg0;
    }

    @Override
    public void setServletRequest(HttpServletRequest arg0) {
        this.request = arg0;
    }

    @Override
    public void setSession(Map<String, Object> arg0) {
        this.session = arg0;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    /**
     * 只返回字符串时，调用此方法,
     * 
     * 调用此方法的方法的返回值为：return "";
     *
     * @param text
     */
    public void outText(String text) {
        getResponse().setCharacterEncoding("utf-8");
        PrintWriter pw = null;
        try {
            pw = getResponse().getWriter();
            pw.write(text);
        } catch (IOException e) {
        }
        pw.flush();
        pw.close();
    }

    public DepartmentToReport getDepartment() {
        if (department == null) {
            HttpSession sessoin = request.getSession();
            department = new DepartmentUtil().getDepartmentService().getCurrentDepartment(sessoin);
        }
        return department;
    }
    
        public PersionToReport getUser() {
        if (user == null) {
            HttpSession sessoin = request.getSession();
            user = new UserUtil().getUserService().getCurrentUser(sessoin);
        }
        return user;
    }

    public RoleToReport getRole() {
        if(role==null){
                       HttpSession sessoin = request.getSession();
            role = new RoleUtil().getRoleService().getRoleById(getUser().getRoleId());
        }
        return role;
    }

    public void setRole(RoleToReport role) {
        this.role = role;
    }

	public List<Object> getTreeObjects() {
		return treeObjects;
	}

	public void setTreeObjects(List<Object> treeObjects) {
		this.treeObjects = treeObjects;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public PageParam getPageParam() {
		return pageParam;
	}

	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
    
}
