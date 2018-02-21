package com.siztao.framework.common.base;

import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.shiro.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getUser() {
        return (SysUserEntity) ShiroUtils.getUserEntity();
    }

    protected String getUserId() {
        return getUser().getUserId();
    }

    protected String getDeptId() {
        return getUser().getDeptId();
    }
}
