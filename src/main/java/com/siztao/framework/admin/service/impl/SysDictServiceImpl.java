package com.siztao.framework.admin.service.impl;

import com.siztao.framework.admin.dao.SysDictDao;
import com.siztao.framework.admin.entity.SysDictEntity;
import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.service.SysDictService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("dictService")
public class SysDictServiceImpl  implements SysDictService{
    @Autowired
    private SysDictDao  sysDictDao;
    @Override
    public SysDictEntity queryObject(String id) {
        return sysDictDao.queryObject(id);
    }

    @Override
    public List<SysDictEntity> queryList(Map<String, Object> map) {
        return sysDictDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysDictDao.queryTotal(map);
    }

    @Override
    public int save(SysDictEntity dict) {

        dict.setId(IdUtil.createIdbyUUID());
        dict.setCreateTime(new Date());
        dict.setCreateUser(((SysUserEntity)ShiroUtils.getUserEntity()).getUserId());
        sysDictDao.save(dict);
        return 1;
    }

    @Override
    public int update(SysDictEntity dict) {
        sysDictDao.update(dict);
        return 1;
    }

    @Override
    public int delete(String id) {
        return sysDictDao.delete(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return sysDictDao.deleteBatch(ids);
    }
}
