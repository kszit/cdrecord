/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.openserv.role.serviceToReport;

import com.kszit.CDReport.cor.model.persionDepRole.Role2Model;
import com.kszit.CDReport.cor.service.persionDepRole.RoleService;
import com.kszit.CDReport.openserv.role.model.RoleToReport;

/**
 *
 * @author Administrator
 */
public class MyRoleServiceToReportInCom implements RoleServiceToReportI{

    @Override
    public RoleToReport getRoleById(String id) {
        RoleToReport role = null;
        RoleService service = new RoleService();
        Role2Model model = service.getRoleByBindId(id);
        role = toRoleToReport(model);
        return role;
    }
    
    
    private RoleToReport toRoleToReport(Role2Model model){
        RoleToReport role = new RoleToReport();
        role.setId(model.getBindId()+"");
        role.setRoleName(model.getName());
        return role;
    }
    
}
