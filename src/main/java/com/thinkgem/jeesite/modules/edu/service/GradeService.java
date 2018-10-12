package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.dao.GradeDao;
import com.thinkgem.jeesite.modules.edu.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName ClassService
 * @Description TODO
 * @Author shawn
 * @Date 2018/10/8 13:29
 * @Version 1.0
 **/
@Service
@Transactional(readOnly = true)
public class GradeService extends CrudService<GradeDao, Grade> {

    @Autowired
    private GradeDao gradeDao;

    public Grade get(String id) {
        return super.get(id);
    }

    public List<Grade> findList(Grade grade) {
        return super.findList(grade);
    }

    public Page<Grade> findPage(Page<Grade> page, Grade grade) {
        return super.findPage(page, grade);
    }

    @Transactional(readOnly = false)
    public void save(Grade grade) {
        super.save(grade);
    }

    @Transactional(readOnly = false)
    public void delete(Grade grade) {
        super.delete(grade);
    }


    public List<Grade> findGradeList() {
        return gradeDao.findGradeList();
    }

}
