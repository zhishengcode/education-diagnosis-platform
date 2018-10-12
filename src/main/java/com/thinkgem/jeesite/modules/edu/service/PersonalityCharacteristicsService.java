package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.dao.PersonalityCharacteristicsDao;
import com.thinkgem.jeesite.modules.edu.entity.PersonalityCharacteristics;
import com.thinkgem.jeesite.modules.edu.entity.StudentConclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Service
@Transactional(readOnly = true)
public class PersonalityCharacteristicsService extends CrudService<PersonalityCharacteristicsDao, PersonalityCharacteristics> {
    @Autowired
    private PersonalityCharacteristicsDao personalityCharacteristicsDao;

    public int countcharacteristicByName(Map<String, Object> map){
        return personalityCharacteristicsDao.countcharacteristicByName(map);
    }

    public List<PersonalityCharacteristics> findList(PersonalityCharacteristics personalityCharacteristics) {
        return super.findList(personalityCharacteristics);
    }

    public List<PersonalityCharacteristics> selectCharacteristicScoreByName(Map<String, Object> map){
        return personalityCharacteristicsDao.selectCharacteristicScoreByName(map);
    }
}
