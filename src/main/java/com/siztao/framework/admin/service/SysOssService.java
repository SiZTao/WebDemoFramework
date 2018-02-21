package com.siztao.framework.admin.service;


import com.siztao.framework.admin.entity.SysOssEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 */
public interface SysOssService {

    SysOssEntity queryObject(String id);

    List<SysOssEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysOssEntity sysOss);

    void update(SysOssEntity sysOss);

    void delete(String id);

    void deleteBatch(String[] ids);
}
