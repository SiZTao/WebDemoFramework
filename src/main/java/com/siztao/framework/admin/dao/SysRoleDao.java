package com.siztao.framework.admin.dao;

import com.siztao.framework.admin.entity.SysRoleEntity;
import com.siztao.framework.common.base.BaseDao;

import java.util.List;

public interface SysRoleDao extends BaseDao<SysRoleEntity>{
    List<String> queryRoleIdList(String createUser);

}
