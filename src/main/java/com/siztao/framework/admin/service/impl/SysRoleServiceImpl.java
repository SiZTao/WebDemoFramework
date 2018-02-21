package com.siztao.framework.admin.service.impl;

import com.siztao.framework.admin.dao.SysRoleDao;
import com.siztao.framework.admin.entity.SysRoleEntity;
import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.service.SysRoleDeptService;
import com.siztao.framework.admin.service.SysRoleMenuService;
import com.siztao.framework.admin.service.SysRoleService;
import com.siztao.framework.admin.service.SysUserService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.utils.Constant;
import com.siztao.framework.common.utils.IdUtil;
import com.siztao.framework.common.utils.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService{
    @Autowired
        private SysRoleDao  sysRoleDao;
    @Autowired
        private SysRoleMenuService  sysRoleMenuService;
    @Autowired
        private SysUserService      sysUserService;
    @Autowired
        private SysRoleDeptService  sysRoleDeptService;

    @Override
    public SysRoleEntity queryObject(String roleId) {
        return sysRoleDao.queryObject(roleId);
    }

    @Override
    public List<SysRoleEntity> queryList(Map<String, Object> map) {
        return sysRoleDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysRoleDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysRoleEntity role) {
        role.setCreateTime(new Date());
        role.setCreateUser(ShiroUtils.getUserId());
        role.setRoleId(IdUtil.createIdbyUUID());
        sysRoleDao.save(role);
        //检查权限是否越权
        checkPrems(role);
        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());
        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(),role.getDeptIdList());

    }

    @Override
    @Transactional
    public void update(SysRoleEntity role) {
        role.setUpdateTime(new Date());
        role.setUpdateUser(((SysUserEntity)ShiroUtils.getUserEntity()).getUserId());
        sysRoleDao.update(role);
        //检查权限是否越权
        checkPrems(role);
        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(String[] roleIds) {
        sysRoleDao.deleteBatch(roleIds);
    }

    @Override
    public List<String> queryRoleIdList(String createUser) {
        return sysRoleDao.queryRoleIdList(createUser);
    }


    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRoleEntity role) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (Constant.SUPER_ADMIN.equals(role.getCreateUser())) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<String> menuIdList = sysUserService.queryAllMenuId(role.getCreateUser());

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
