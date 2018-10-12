package com.thinkgem.jeesite.modules.edu.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 测评结论与笔划形态关联Entity
 * @author ShawnXiang
 * @version 2018-04-14
 */
public class ConclusionTwoCategoryRelate extends DataEntity<ConclusionTwoCategoryRelate>{
    public static final long serialVersionUID = 1L;
    private String conclusionId; //结论Id
    private String twoCategoryId; //笔划形态Id

    public ConclusionTwoCategoryRelate(){ super(); }
    public ConclusionTwoCategoryRelate(String id) { super(id); }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getConclusionId() {
        return conclusionId;
    }

    public void setConclusionId(String conclusionId) {
        this.conclusionId = conclusionId;
    }

    public String getTwoCategoryId() {
        return twoCategoryId;
    }

    public void setTwoCategoryId(String twoCategoryId) {
        this.twoCategoryId = twoCategoryId;
    }
}
