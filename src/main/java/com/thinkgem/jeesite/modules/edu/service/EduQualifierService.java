package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.dao.EduQualifierDao;
import com.thinkgem.jeesite.modules.edu.entity.EduQualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ShawnXiang
 * @version 2018-04-08.
 */
@Service
@Transactional(readOnly = true)
public class EduQualifierService extends CrudService<EduQualifierDao, EduQualifier> {
    public List<EduQualifier> findList(EduQualifier eduQualifier) {
        return super.findList(eduQualifier);
    }
}
