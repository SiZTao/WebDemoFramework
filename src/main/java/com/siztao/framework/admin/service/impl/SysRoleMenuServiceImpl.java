package com.siztao.framework.admin.service.impl;

import com.siztao.framework.admin.dao.SysRoleMenuDao;
import com.siztao.framework.admin.service.SysRoleMenuService;
import com.siztao.framework.common.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService{
    @Autowired
    private SysRoleMenuDao  sysRoleMenuDao;

    @Override
    @Transactional
    public void saveOrUpdate(String roleId, List<String> menuIdList) {
        if(menuIdList.size() ==0){
            return;
        }
        //先删除角色与菜单关系
        sysRoleMenuDao.delete(roleId);
        //保存角色与菜单关系
        Map<String,Object> map = new HashMap<>();
        map.put("id", IdUtil.createIdbyUUID());
        map.put("roleId",roleId);
        map.put("menuIdList",menuIdList);
        sysRoleMenuDao.save(map);
    }

    @Override
    public List<String> queryMenuIdList(String roleId) {
        return sysRoleMenuDao.queryMenuIdList(roleId);
    }
}
