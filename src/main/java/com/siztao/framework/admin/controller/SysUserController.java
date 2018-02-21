package com.siztao.framework.admin.controller;


import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.service.SysUserRoleService;
import com.siztao.framework.admin.service.SysUserService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.annotation.SysLog;
import com.siztao.framework.common.base.AbstractController;
import com.siztao.framework.common.utils.Constant;
import com.siztao.framework.common.utils.R;
import com.siztao.framework.common.validator.Assert;
import com.siztao.framework.common.validator.ValidatorUtils;
import com.siztao.framework.common.validator.group.AddGroup;
import com.siztao.framework.common.validator.group.UpdateGroup;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author lipengjun
 * @date 2017年11月18日 下午13:13:23
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 所有用户列表
     */

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R    list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                     @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
                     @RequestParam(required = false, defaultValue = "", value = "search") String search,
                     @RequestParam(required = false, value = "sort") String sort,
                     @RequestParam(required = false, value = "order") String order){
        /**
         *如果不是超级管理员，则只查询自己创建的角色列表
         */
        Map<String,Object> params= new HashMap<>();
        if (!Constant.SUPER_ADMIN.equals(getUserId())) {
            params.put("createUser", getUserId());

        }
        List<SysUserEntity> userList = sysUserService.queryList(params);
        long total =sysUserService.queryTotal(null);
        return R.ok().put("rows",userList).put("total",total);
    }
//    @RequestMapping("/list")
//    @RequiresPermissions("sys:user:list")
//    public R list(@RequestParam Map<String, Object> params) {
//        //只有超级管理员，才能查看所有管理员列表
//        if (!Constant.SUPER_ADMIN.equals(getUserId())) {
//            params.put("createUser", getUserId());
//        }
//
//        //查询列表数据
//        Query query = new Query(params);
//        List<SysUserEntity> userList = sysUserService.queryList(query);
//        int total = sysUserService.queryTotal(query);
//
//        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
//
//        return R.ok().put("page", pageUtil);
//    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public R password(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");

        //sha256加密
        password = new Sha256Hash(password).toHex();
        //sha256加密
        newPassword = new Sha256Hash(newPassword).toHex();

        if (password.equals(newPassword)) {
            return R.error("密码不能与原密码相同");
        }

        //更新密码
        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (count == 0) {
            return R.error("原密码不正确");
        }

        //退出
        ShiroUtils.logout();

        return R.ok("密码修改成功");
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") String userId) {
        SysUserEntity user = sysUserService.queryObject(userId);

        //获取用户所属的角色列表
        List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUser(getUserId());
        sysUserService.save(user);

        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUser(getUserId());
        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody String[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }
}
