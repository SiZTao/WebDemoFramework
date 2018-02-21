package com.siztao.framework.admin.service;

import com.siztao.framework.admin.entity.SysDictEntity;

import java.util.List;
import java.util.Map;

public interface SysDictService {
    /**
     * 根据主键查询实体
     * @param id
     * @return
     */
    SysDictEntity   queryObject(String id);

    /**
     * 分页查询
     * @param map
     * @return
     */
    List<SysDictEntity> queryList(Map<String,Object> map);

    /**
     * 分页统计总数
     * @param map
     * @return
     */
    int queryTotal(Map<String,Object> map);

    /**
     * 保存实体
     * @param dict
     * @return
     */
    int save(SysDictEntity dict);

    /**
     * 根据主键更新实体
     * @param dict
     * @return
     */
    int update(SysDictEntity dict);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 根据主键批量删除
     * @param ids
     * @return
     */
    int deleteBatch(String []ids);
}
