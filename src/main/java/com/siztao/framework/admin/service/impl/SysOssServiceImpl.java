package com.siztao.framework.admin.service.impl;


import com.siztao.framework.admin.dao.SysOssDao;
import com.siztao.framework.admin.entity.SysOssEntity;
import com.siztao.framework.admin.service.SysOssService;
import com.siztao.framework.common.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysOssService")
public class SysOssServiceImpl implements SysOssService {
    @Autowired
    private SysOssDao sysOssDao;

    @Override
    public SysOssEntity queryObject(String id) {
        return sysOssDao.queryObject(id);
    }

    @Override
    public List<SysOssEntity> queryList(Map<String, Object> map) {
        return sysOssDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysOssDao.queryTotal(map);
    }

    @Override
    public void save(SysOssEntity sysOss) {
        sysOss.setId(IdUtil.createIdbyUUID());
        sysOss.setCreateTime(new Date());
        sysOssDao.save(sysOss);
    }

    @Override
    public void update(SysOssEntity sysOss) {
        sysOssDao.update(sysOss);
    }

    @Override
    public void delete(String id) {
        sysOssDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids) {
        sysOssDao.deleteBatch(ids);
    }

}
