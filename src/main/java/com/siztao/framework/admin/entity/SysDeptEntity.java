package com.siztao.framework.admin.entity;

import com.siztao.framework.common.base.BaseEntity;

import java.util.List;

/**
 * 部门管理
 */
public class SysDeptEntity extends BaseEntity{
    private static final long serialVersionUID = 2558262529248282358L;

    /**
     * 部门ID
     */
    private String  deptId;
    /**
     * 上级部门ID 一级部门ID为0
     */
    private String  parentId;

    private String  name;
    private Integer orderNum;
    private Integer deptLevel;
    private Integer status;
    private String parentName;
    private Boolean open;
    private List<?> list;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(Integer deptLevel) {
        this.deptLevel = deptLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
