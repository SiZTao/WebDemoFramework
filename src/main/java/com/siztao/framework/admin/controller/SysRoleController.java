package com.siztao.framework.admin.controller;


import com.siztao.framework.admin.entity.SysRoleEntity;
import com.siztao.framework.admin.service.SysRoleDeptService;
import com.siztao.framework.admin.service.SysRoleMenuService;
import com.siztao.framework.admin.service.SysRoleService;
import com.siztao.framework.common.annotation.SysLog;
import com.siztao.framework.common.base.AbstractController;
import com.siztao.framework.common.utils.Constant;
import com.siztao.framework.common.utils.R;
import com.siztao.framework.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author lipengjun
 * @date 2017年11月18日 下午13:13:23
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    /**
     * 角色列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list (@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                   @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
                   @RequestParam(required = false, defaultValue = "", value = "search") String search,
                   @RequestParam(required = false, value = "sort") String sort,
                   @RequestParam(required = false, value = "order") String order){
        /**
         *如果不是超级管理员，则只查询自己创建的角色列表
         */
        if (!Constant.SUPER_ADMIN.equals(getUserId())) {
        }
        List<SysRoleEntity> userList = sysRoleService.queryList(null);
        long total =sysRoleService.queryTotal(null);
        return R.ok().put("rows",userList).put("total",total);
    }

//    @RequestMapping("/list")
//    @RequiresPermissions("sys:role:list")
//    public R list(@RequestParam Map<String, Object> params) {
//        //如果不是超级管理员，则只查询自己创建的角色列表
//        if (!Constant.SUPER_ADMIN.equals(getUserId())) {
//            params.put("createUser", getUserId());
//        }
//
//        //查询列表数据
//        Query query = new Query(params);
//        List<SysRoleEntity> list = sysRoleService.queryList(null);
//        int total = sysRoleService.queryTotal(query);
//
//       // PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
//
//        return R.ok().put("list",list);
//    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select() {
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (!Constant.SUPER_ADMIN.equals(getUserId())) {
            map.put("createUser", getUserId());
        }
        List<SysRoleEntity> list = sysRoleService.queryList(map);

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") String roleId) {
        SysRoleEntity role = sysRoleService.queryObject(roleId);

        //查询角色对应的菜单
        List<String> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<String> deptIdList = sysRoleDeptService.queryDeptIdList(roleId);
        role.setDeptIdList(deptIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUser(getUserId());
        sysRoleService.save(role);

        return R.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUser(getUserId());
        sysRoleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody String[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }
}
