package com.siztao.framework.admin.dao;

import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.common.base.BaseDao;

import java.util.List;
import java.util.Map;

public interface SysUserDao extends BaseDao<SysUserEntity>{
    /**
     * 查询用户的所有权限
     * @param userId
     * @return
     */
    List<String> queryAllPerms(String userId);

    /**
     * 查询用户所有的菜单ID
     * @param userId
     * @return
     */
    List<String> queryAllMenuId(String userId);

    /**
     * 根据用户名查询系统用户
     * @param username
     * @return
     */
    SysUserEntity  queryByUserName(String username);

    /**
     * 修改密码
     * @param map
     * @return
     */
    int updatePassword(Map<String,Object> map);
}
