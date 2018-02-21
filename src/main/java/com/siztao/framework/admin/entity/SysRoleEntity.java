package com.siztao.framework.admin.entity;

import com.siztao.framework.common.base.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;
import java.util.List;

public class SysRoleEntity extends BaseEntity{
    private static final long serialVersionUID = 143045159121080851L;

    /**
     * 角色ID
     */
    private String  roleId;
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String  roleName;

    /**
     * 备注
     */
    private String  remark;

    /**
     * 部门ID
     */
    @NotBlank(message = "部门不能为空")
    private String deptId;

    private List<String> menuIdList;

    /**
     * 部门名称
     */
    private String deptName;
    private List<String> deptIdList;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRoleId() {
        return roleId;
    }

    public List<String> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<String> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<String> deptIdList) {
        this.deptIdList = deptIdList;
    }
}
