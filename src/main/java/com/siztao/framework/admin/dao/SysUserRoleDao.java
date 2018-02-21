package com.siztao.framework.admin.dao;


import com.siztao.framework.admin.entity.SysUserRoleEntity;
import com.siztao.framework.common.base.BaseDao;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 */
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<String> queryRoleIdList(String userId);
}
