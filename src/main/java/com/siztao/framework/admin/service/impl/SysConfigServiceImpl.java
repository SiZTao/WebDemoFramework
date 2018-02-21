package com.siztao.framework.admin.service.impl;

import com.alibaba.fastjson.JSON;

import com.siztao.framework.admin.dao.SysConfigDao;
import com.siztao.framework.admin.entity.SysConfigEntity;
import com.siztao.framework.admin.service.SysConfigService;
import com.siztao.framework.common.utils.IdUtil;
import com.siztao.framework.common.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {
	@Autowired
	private SysConfigDao sysConfigDao;
	
	@Override
	public void save(SysConfigEntity config) {
		config.setId(IdUtil.createIdbyUUID());
		config.setStatus(1);
		sysConfigDao.save(config);
	}

	@Override
	public void update(SysConfigEntity config) {
		sysConfigDao.update(config);
	}

	@Override
	public void updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
	}

	@Override
	public void deleteBatch(String[] ids) {
		sysConfigDao.deleteBatch(ids);
	}

	@Override
	public List<SysConfigEntity> queryList(Map<String, Object> map) {
		return sysConfigDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysConfigDao.queryTotal(map);
	}

	@Override
	public SysConfigEntity queryObject(String id) {
		return sysConfigDao.queryObject(id);
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String value = sysConfigDao.queryByKey(key);
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		return value;
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key, null);
		if(StringUtils.isNotBlank(value)){
			return JSON.parseObject(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
