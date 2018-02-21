package com.siztao.framework.common.oss;

import com.siztao.framework.admin.service.SysConfigService;
import com.siztao.framework.common.utils.ConfigConstant;
import com.siztao.framework.common.utils.Constant;
import com.siztao.framework.common.utils.SpringContextUtils;

/**
 * 文件上传Factory
 *
 * @author lipengjun
 * @date 2017年11月18日 下午13:13:23
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build() {
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

}
