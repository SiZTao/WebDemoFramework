package com.siztao.framework.admin.dao;

import com.siztao.framework.admin.entity.SysConfigEntity;
import com.siztao.framework.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * x系统配置信息
 */
public interface SysConfigDao extends BaseDao<SysConfigEntity>{
    String  queryByKey(String   paramKey);
    int updateValueByKey(@Param("confKey")String confKey,@Param("confValue")String confValue);
}
