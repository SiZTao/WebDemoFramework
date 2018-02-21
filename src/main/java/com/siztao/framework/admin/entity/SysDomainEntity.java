package com.siztao.framework.admin.entity;

import com.siztao.framework.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统域对象
 */
public class SysDomainEntity extends BaseEntity {

    private static final long serialVersionUID = 8687556350329223275L;
    private String id;
    /**
     * 域编码
     */
    private String domainCode;
    /**
     * 域名城
     */
    private String domainName;
    /**
     * 域地址
     */
    private String domainUrl;
    /**
     * 状态 0 停用 1 启用
     */
    private Integer domainStatus;

    /**
     * 备注
     */
    private String remark;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public Integer getDomainStatus() {
        return domainStatus;
    }

    public void setDomainStatus(Integer domainStatus) {
        this.domainStatus = domainStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
