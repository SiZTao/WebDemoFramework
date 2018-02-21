package com.siztao.framework.admin.service.impl;


import com.siztao.framework.admin.dao.SysUserDao;
import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.service.SysRoleService;
import com.siztao.framework.admin.service.SysUserRoleService;
import com.siztao.framework.admin.service.SysUserService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.utils.Constant;
import com.siztao.framework.common.utils.IdUtil;
import com.siztao.framework.common.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author 李鹏军
 * @date 2016年12月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public List<String> queryAllPerms(String userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<String> queryAllMenuId(String userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return sysUserDao.queryByUserName(username);
    }

    @Override
    public SysUserEntity queryObject(String userId) {
        return sysUserDao.queryObject(userId);
    }

    @Override
    public List<SysUserEntity> queryList(Map<String, Object> map) {
        return sysUserDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUserDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysUserEntity user) {
        user.setUserId(IdUtil.createIdbyUUID());
        user.setCreateTime(new Date());
        user.setCreateUser(((SysUserEntity) ShiroUtils.getUserEntity()).getUserId());
        //sha256加密
        user.setPassWord(new Sha256Hash(Constant.DEFAULT_PASS_WORD).toHex());
        sysUserDao.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassWord())) {
            user.setPassWord(new Sha256Hash(Constant.DEFAULT_PASS_WORD).toHex());
        } else {
            user.setPassWord(new Sha256Hash(user.getPassWord()).toHex());
        }
        user.setUpdateTime(new Date());
        user.setUpdateUser(((SysUserEntity) ShiroUtils.getUserEntity()).getUserId());
        sysUserDao.update(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(String[] userId) {
        sysUserDao.deleteBatch(userId);
    }

    @Override
    public int updatePassword(String userId, String password, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);
        return sysUserDao.updatePassword(map);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user) {
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (Constant.SUPER_ADMIN.equals(user.getCreateUser())) {
            return;
        }

        //查询用户创建的角色列表
        List<String> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUser());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new RRException("新增用户所选角色，不是本人创建");
        }
    }
}
