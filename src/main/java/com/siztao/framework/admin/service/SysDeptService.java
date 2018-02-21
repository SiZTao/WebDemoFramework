package com.siztao.framework.admin.service;

import com.siztao.framework.admin.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
public interface SysDeptService {

    SysDeptEntity   queryObject(String deptId);
    List<SysDeptEntity> queryList(Map<String,Object> map);
    void    save(SysDeptEntity sysDept);
    void    update(SysDeptEntity    sysDept);
    void    delete(String   deptId);

    /**
     * 查询子部门ID列表
     * @param parentId
     * @return
     */
    List<String>    queryDeptIdList(String parentId);

    /**
     * 获取子部门ID
     * @param deptId
     * @return
     */
    String  getSubDeptIdList(String deptId);
}
