/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.model.persionDepRole;

import com.kszit.CDReport.cor.dao.hibernate.po.DepartmentPO;
import com.kszit.CDReport.cor.model.TreeParentModel;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Administrator
 */
public class Department2Model extends TreeParentModel {

    private String name;
    
    private String text;

    private String describe2;
    //
    private String type;
    //本机构内部的数据的code值，若type为非部门，code值与父机构相同
    private String filter;
    //组织机构的code值，顶级父机构id.此父级机构id.....本机构id
    private String code;


    public Department2Model(){}
    
    public Department2Model(DepartmentPO po){
                try {
            BeanUtils.copyProperties(this, po);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.text = name;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe2() {
        return describe2;
    }

    public void setDescribe2(String describe) {
        this.describe2 = describe;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    
    
    
    
}
