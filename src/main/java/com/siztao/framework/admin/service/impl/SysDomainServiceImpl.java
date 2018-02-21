package com.siztao.framework.admin.service.impl;

import com.siztao.framework.admin.dao.SysDomainDao;
import com.siztao.framework.admin.entity.SysDomainEntity;
import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.service.SysDomainService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("domainService")
public class SysDomainServiceImpl implements SysDomainService{
    @Autowired  private SysDomainDao    sysDomainDao;
    @Override
    public SysDomainEntity queryObject(String id) {
        return sysDomainDao.queryObject(id);
    }

    @Override
    public List<SysDomainEntity> queryList(Map<String, Object> map) {
        return sysDomainDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysDomainDao.queryTotal(map);
    }

    @Override
    public int save(SysDomainEntity domain) {
        domain.setId(IdUtil.createIdbyUUID());
        domain.setCreateUser(((SysUserEntity)ShiroUtils.getUserEntity()).getUserId());
        domain.setCreateTime(new Date());
        return sysDomainDao.save(domain);
    }

    @Override
    public int update(SysDomainEntity domain) {
        domain.setUpdateUser(((SysUserEntity)ShiroUtils.getUserEntity()).getUserId());
        domain.setUpdateTime(new Date());
        return sysDomainDao.update(domain);
    }

    @Override
    public int delete(String id) {
        return sysDomainDao.delete(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return sysDomainDao.deleteBatch(ids);
    }
}
