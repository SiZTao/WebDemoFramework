package com.siztao.framework.admin.dao;

import com.siztao.framework.admin.entity.SysMenuEntity;
import com.siztao.framework.common.base.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 */
public interface SysMenuDao extends BaseDao<SysMenuEntity>{
    /**
     * 根据父菜单,查询子菜单
     * @param map
     * @return
     */
    List<SysMenuEntity> queryListParentId(Map<String,Object> map);

    /**
     * 获取不包含按钮的菜单列表
     * @return
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 查询用户权限列表
     * @param userId
     * @return
     */
    List<SysMenuEntity> queryUserList(String userId);


    /**
     * 查询用户的权限列表
     */
    String queryMaxIdByParentId(String parentId);
}
