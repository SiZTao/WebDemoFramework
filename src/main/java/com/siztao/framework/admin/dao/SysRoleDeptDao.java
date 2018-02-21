package com.siztao.framework.admin.dao;

import com.siztao.framework.admin.entity.SysRoleDeptEntity;
import com.siztao.framework.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与部门对应关系
 *
 */
@Mapper
public interface SysRoleDeptDao extends BaseDao<SysRoleDeptEntity> {

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<String> queryDeptIdList(String roleId);

    /**
     * 根据用户ID获取权限部门列表
     *
     * @param userId
     * @return
     */
    List<String> queryDeptIdListByUserId(String userId);
}
