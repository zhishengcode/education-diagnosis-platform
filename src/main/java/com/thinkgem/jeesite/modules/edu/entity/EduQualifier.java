package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * @author ShawnXiang
 * @version 2018-04-08
 */
public class EduQualifier extends DataEntity<EduQualifier> {
    private String id;
    private String qualifierName;
    private String qualifierValue;
    private String sort;
    private String status;
    private Date createDate;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getQualifierName() {
        return qualifierName;
    }

    public void setQualifierName(String qualifierName) {
        this.qualifierName = qualifierName;
    }

    public String getQualifierValue() {
        return qualifierValue;
    }

    public void setQualifierValue(String qualifierValue) {
        this.qualifierValue = qualifierValue;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
