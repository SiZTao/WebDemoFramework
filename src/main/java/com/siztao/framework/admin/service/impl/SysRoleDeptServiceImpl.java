package com.siztao.framework.admin.service.impl;

import com.siztao.framework.admin.dao.SysRoleDeptDao;
import com.siztao.framework.admin.service.SysRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl implements SysRoleDeptService{
    @Autowired  private SysRoleDeptDao sysRoleDeptDao;
    @Override
    @Transactional
    public void saveOrUpdate(String roleId, List<String> deptIdList) {
        //先删除角色与菜单关系
        sysRoleDeptDao.delete(roleId);
        if(deptIdList.size()==0){
            return;
        }
        //保存角色与菜单关系
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("roleId",roleId);
        map.put("deptIdList",deptIdList);
        sysRoleDeptDao.save(map);

    }

    @Override
    public List<String> queryDeptIdList(String roleId) {
        return sysRoleDeptDao.queryDeptIdList(roleId);
    }

    @Override
    public List<String> queryDeptIdListByUserId(String userId) {
        return sysRoleDeptDao.queryDeptIdListByUserId(userId);
    }
}
