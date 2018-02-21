package com.siztao.framework.admin.dao;


import com.siztao.framework.admin.entity.SysRoleMenuEntity;
import com.siztao.framework.common.base.BaseDao;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<String> queryMenuIdList(String roleId);
}
