package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.dao.ConclusionTwoCategoryRelateDao;
import com.thinkgem.jeesite.modules.edu.entity.ConclusionTwoCategoryRelate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测评结论与笔划形态关联的Service
 * @author ShawnXiang
 * @version 2018-04-14
 */
@Service
@Transactional(readOnly = true)
public class ConclusionTwoCategoryRelateService extends CrudService<ConclusionTwoCategoryRelateDao,ConclusionTwoCategoryRelate>{

    private ConclusionTwoCategoryRelateDao conclusionTwoCategoryRelateDao;

    @Transactional(readOnly = false)
    public void save(ConclusionTwoCategoryRelate conclusionTwoCategoryRelate) {
        super.save(conclusionTwoCategoryRelate);
    }
}
