package com.siztao.framework.admin.service.impl;

import com.siztao.framework.admin.dao.SysDeptDao;
import com.siztao.framework.admin.entity.SysDeptEntity;
import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.service.SysDeptService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService{
    @Autowired
    private SysDeptDao  sysDeptDao;

    @Override
    public SysDeptEntity queryObject(String deptId) {
        return sysDeptDao.queryObject(deptId);
    }

    @Override
    public List<SysDeptEntity> queryList(Map<String, Object> map) {
        return sysDeptDao.queryList(map);
    }

    @Override
    public void save(SysDeptEntity sysDept) {
        String parentId = sysDept.getParentId();
        String maxId = sysDeptDao.queryMaxIdByParentId(parentId);
        sysDept.setDeptId(StringUtils.addOne(parentId,maxId));
        sysDept.setCreateTime(new Date());
        sysDept.setCreateUser(((SysUserEntity) ShiroUtils.getUserEntity()).getUserId());
        sysDept.setDeptLevel(sysDept.getDeptId().length()/2);
        sysDeptDao.save(sysDept);
    }

    @Override
    public void update(SysDeptEntity sysDept) {
        sysDept.setUpdateTime(new Date());
        sysDept.setUpdateUser(((SysUserEntity) ShiroUtils.getUserEntity()).getUserId());
        sysDeptDao.update(sysDept);
    }

    @Override
    public void delete(String deptId) {
        sysDeptDao.delete(deptId);
    }

    @Override
    public List<String> queryDeptIdList(String parentId) {
        return sysDeptDao.queryDeptIdList(parentId);
    }

    @Override
    public String getSubDeptIdList(String deptId) {
        //部门及子部门ID 列表
        List<String> deptIdList = new ArrayList<String>();
        //获取子部门ID
        List<String> subIdList= queryDeptIdList(deptId);
        getDeptTreeList(subIdList,deptIdList);
        deptIdList.add(deptId);
        String deptFilter = StringUtils.join(deptIdList,",");
        return deptFilter;
    }
    public void getDeptTreeList(List<String> subIdList,List<String> deptIdList){
        for (String deptId:subIdList){
            List<String> list = queryDeptIdList(deptId);
            if(list.size() > 0){
                getDeptTreeList(list,deptIdList);
            }
            deptIdList.add(deptId);
        }
    }
}
