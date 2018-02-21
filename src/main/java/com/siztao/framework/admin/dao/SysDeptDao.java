package com.siztao.framework.admin.dao;

import com.siztao.framework.admin.entity.SysDeptEntity;
import com.siztao.framework.common.base.BaseDao;

import java.util.List;

/**
 * 部门管理
 */
public interface SysDeptDao extends BaseDao<SysDeptEntity> {
    /**
     * 查询子部门ID列表
     * @param parentId
     * @return
     */
    List<String> queryDeptIdList(String parentId);

    String  queryMaxIdByParentId(String parentId);
}
