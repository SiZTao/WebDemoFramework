package com.siztao.framework.admin.service.impl;


import com.siztao.framework.admin.dao.SysLogDao;
import com.siztao.framework.admin.entity.SysLogEntity;
import com.siztao.framework.admin.service.SysLogService;
import com.siztao.framework.common.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public SysLogEntity queryObject(Long id) {
        return sysLogDao.queryObject(id);
    }

    @Override
    public List<SysLogEntity> queryList(Map<String, Object> map) {
        return sysLogDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysLogDao.queryTotal(map);
    }

    @Override
    public void save(SysLogEntity sysLog) {
        sysLog.setId(IdUtil.createIdbyUUID());
        sysLog.setCreateDate(new Date());
        sysLogDao.save(sysLog);
    }

    @Override
    public void update(SysLogEntity sysLog) {
        sysLogDao.update(sysLog);
    }

    @Override
    public void delete(Long id) {
        sysLogDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sysLogDao.deleteBatch(ids);
    }

}
