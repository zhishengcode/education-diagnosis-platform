package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * @ClassName Grade
 * @Description TODO
 * @Author shawn
 * @Date 2018/10/8 13:31
 * @Version 1.0
 **/
public class Grade extends DataEntity<Grade> {
    private static final long serialVersionUID = 1L;
    private String name;		//年级名称
    private String status;		//状态
    private Long sort;		    //排序列
    private Date createtime;	//创建时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
