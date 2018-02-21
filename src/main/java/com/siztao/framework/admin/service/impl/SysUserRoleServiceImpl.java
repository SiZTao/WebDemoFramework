package com.siztao.framework.admin.service.impl;

import com.siztao.framework.admin.dao.SysUserRoleDao;
import com.siztao.framework.admin.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户与角色对应关系
 *
 * @author lipengjun
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public void saveOrUpdate(String userId, List<String> roleIdList) {
        if (roleIdList.size() == 0) {
            return;
        }

        //先删除用户与角色关系
        sysUserRoleDao.delete(userId);

        //保存用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIdList", roleIdList);
        sysUserRoleDao.save(map);
    }

    @Override
    public List<String> queryRoleIdList(String userId) {
        return sysUserRoleDao.queryRoleIdList(userId);
    }

    @Override
    public void delete(String userId) {
        sysUserRoleDao.delete(userId);
    }
}
