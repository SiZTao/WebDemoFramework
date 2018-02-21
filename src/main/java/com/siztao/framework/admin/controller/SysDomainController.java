package com.siztao.framework.admin.controller;


import com.siztao.framework.admin.entity.SysDomainEntity;
import com.siztao.framework.admin.service.SysDomainService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.utils.Constant;
import com.siztao.framework.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 域对象Controller
 *
 * @author lipengjun
 * @date 2017年11月18日 下午13:13:23
 */
@Controller
@RequestMapping("/sys/domain")
public class SysDomainController {
    @Autowired
    private SysDomainService domainService;

    /**
     * 查看列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list (@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                   @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
                   @RequestParam(required = false, defaultValue = "", value = "search") String search,
                   @RequestParam(required = false, value = "sort") String sort,
                   @RequestParam(required = false, value = "order") String order){

        List<SysDomainEntity> userList = domainService.queryList(null);
        long total =domainService.queryTotal(null);
        return R.ok().put("rows",userList).put("total",total);
    }
//    @RequestMapping("/list")
//    @RequiresPermissions("sys:domain:list")
//    @ResponseBody
//    public R list(@RequestParam Map<String, Object> params) {
//        //查询列表数据
//        Query query = new Query(params);
//
//        List<SysDomainEntity> domainList = domainService.queryList(query);
//        int total = domainService.queryTotal(query);
//
//        PageUtils pageUtil = new PageUtils(domainList, total, query.getLimit(), query.getPage());
//
//        return R.ok().put("page", pageUtil);
//    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:domain:info")
    @ResponseBody
    public R info(@PathVariable("id") String id) {
        SysDomainEntity domain = domainService.queryObject(id);

        return R.ok().put("domain", domain);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:domain:save")
    @ResponseBody
    public R save(@RequestBody SysDomainEntity domain) {
        domainService.save(domain);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:domain:update")
    @ResponseBody
    public R update(@RequestBody SysDomainEntity domain) {
        domainService.update(domain);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:domain:delete")
    @ResponseBody
    public R delete(@RequestBody String[] ids) {
        domainService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public R queryAll(@RequestParam Map<String, Object> params) {
        String userId = ShiroUtils.getUserId();
        List<SysDomainEntity> list;
        if (Constant.SUPER_ADMIN.equals(userId)) {
            list = domainService.queryList(params);
        } else {
            params.put("userId", userId);
            list = domainService.queryList(params);
        }
        return R.ok().put("list", list);
    }
}
