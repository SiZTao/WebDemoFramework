package com.siztao.framework.admin.service;


import com.siztao.framework.admin.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 */
public interface SysRoleService {

    SysRoleEntity queryObject(String roleId);

    List<SysRoleEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysRoleEntity role);

    void update(SysRoleEntity role);

    void deleteBatch(String[] roleIds);

    /**
     * 查询用户创建的角色ID列表
     */
    List<String> queryRoleIdList(String createUser);
}
