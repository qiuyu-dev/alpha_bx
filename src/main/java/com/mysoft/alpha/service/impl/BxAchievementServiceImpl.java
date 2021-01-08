package com.mysoft.alpha.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysoft.alpha.dao.BxAchievementDao;
import com.mysoft.alpha.dao.BxUserPromotionDao;
import com.mysoft.alpha.entity.BxAchievement;
import com.mysoft.alpha.entity.BxUserPromotion;
import com.mysoft.alpha.service.BxAchievementService;


/**
 * (BxAchievement)表服务实现类
 *
 * @author makejava
 * @since 2020-11-29 14:13:14
 */
@Service
public class BxAchievementServiceImpl implements BxAchievementService {
    /**
     * 服务对象
     */
    @Autowired
    private BxAchievementDao bxAchievementDao;

    /**
     * 用户-推广服务对象
     */
    @Autowired
    private BxUserPromotionDao bxUserPromotionDao;


    @Override
    public Long findAmountByUserId(Integer userId) {
        BxUserPromotion bxUserPromotion = bxUserPromotionDao.findByUserId(userId);
        Long amount = new Long("0");
        if(bxUserPromotion !=null) {
            amount = bxAchievementDao.findAmountByParamsAndSort(bxUserPromotion.getPromotionId());           
        }
        return amount;
    }


	@Override
	public List<BxAchievement> findAllByPromotionId(Integer promotionId) {
		List<BxAchievement> list = bxAchievementDao.findAllByPromotionId(promotionId);
		return list;
	}


	@Override
	public Page<BxAchievement> findPageByPromotionId(Integer promotionId, Pageable pageable) {
		Page<BxAchievement> page = bxAchievementDao.findPageByPromotionId(promotionId, pageable);
		return page;
	}


	@Override
	public Map<String, Object> findSumData() {
		Map<String, Object> map = bxAchievementDao.findSumData();
		return map;
	}


	@Override
	public List<Map<String, Object>> findSumDataByDate() {
		List<Map<String, Object>> list = bxAchievementDao.findSumDataByDate();
		return list;
	}


	@Override
	public List<Map<String, Object>> findTop10DeptData() {
		List<Map<String, Object>> list = bxAchievementDao.findTop10DeptData();
		return list;
	}

	@Override
	public List<Map<String, Object>> findTop10PersonData() {
		List<Map<String, Object>> list = bxAchievementDao.findTop10PersonData();
		return list;
	}

	@Override
	public List<Map<String, Object>> findDeptDataByDay(String beginDate,  String endDate) {
		List<Map<String, Object>> list = bxAchievementDao.findDeptDataByDay(beginDate, endDate);
		return list;
	}

	@Override
	public List<Map<String, Object>> findPersonDataByDay(String beginDate, String endDate) {
		List<Map<String, Object>> list = bxAchievementDao.findPersonDataByDay(beginDate, endDate);
		return list;
	}	
	
	@Override
	public List<Map<String, Object>> findDeptData(String beginDate, String endDate, String teamOrder) {
		List<Map<String, Object>> list = bxAchievementDao.findDeptData(beginDate, endDate, teamOrder);
		return list;
	}

	@Override
	public List<Map<String, Object>> findPersonData(String beginDate, String endDate, String userId) {
		List<Map<String, Object>> list = bxAchievementDao.findPersonData(beginDate, endDate, userId);
		return list;
	}

	@Override
	public List<Map<String, Object>> findAllDept() {
		List<Map<String, Object>> list = bxAchievementDao.findAllDept();
		return list;
	}

	@Override
	public List<Map<String, Object>> findAllPerson() {
		List<Map<String, Object>> list = bxAchievementDao.findAllPerson();
		return list;
	}


}