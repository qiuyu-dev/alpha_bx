package com.mysoft.alpha.service.impl;

import com.mysoft.alpha.dao.BxPromotionDao;
import com.mysoft.alpha.dao.BxUserPromotionDao;
import com.mysoft.alpha.dao.UserDao;
import com.mysoft.alpha.entity.BxPromotion;
import com.mysoft.alpha.entity.BxUserPromotion;
import com.mysoft.alpha.entity.User;
import com.mysoft.alpha.exception.CustomException;
import com.mysoft.alpha.service.BxPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * (BxPromotion)表服务实现类
 *
 * @author makejava
 * @since 2020-11-29 14:13:09
 */
@Service
public class BxPromotionServiceImpl implements BxPromotionService {
    /**
     * 推广URL服务对象
     */
    @Autowired
    private BxPromotionDao bxPromotionDao;

    /**
     * 用户-推广服务对象
     */
    @Autowired
    private BxUserPromotionDao bxUserPromotionDao;

    /**
     * 我方用户服务对象
     */
    @Autowired
    private UserDao userDao;

    @Override
    public BxPromotion findByUserid(Integer userid) {
    	System.out.println("userid:"+userid);
        BxUserPromotion bxUserPromotion = bxUserPromotionDao.findByUserId(userid);
        if(bxUserPromotion==null) {
        	return null;
        }
        return bxPromotionDao.getOne(bxUserPromotion.getPromotionId());
    }

    @Override
    public BxPromotion createByUserid(Integer userid) {
        User user = userDao.getOne(userid);
        if (user == null || user.getEnabled() == null || user.getEnabled() == 0) {
            throw new CustomException(0, "用户认证错误:无此用户或用户状态无效");
        }
        BxUserPromotion bxUserPromotion = bxUserPromotionDao.findByUserId(userid);
        BxPromotion bxPromotion = new BxPromotion();
        if (bxUserPromotion != null) {
            bxPromotion = bxPromotionDao.getOne(bxUserPromotion.getPromotionId());
        } else {
        	BxUserPromotion bxUserPromotionEntity = new BxUserPromotion();
            List<BxUserPromotion> bupList = bxUserPromotionDao.findAllByUserIdIsNotNull();
            if(bupList.stream().map(BxUserPromotion::getPromotionId).collect(Collectors.toList()).isEmpty()) {
            	bxPromotion = bxPromotionDao.findFirstByStatus(1);
            }else {
            	bxPromotion = bxPromotionDao.findFirstByStatusAndIdNotIn(1,
                        bupList.stream().map(BxUserPromotion::getPromotionId).collect(Collectors.toList()));
            }
            
            System.out.println(bupList.stream().map(BxUserPromotion::getPromotionId).collect(Collectors.toList()));
            System.out.println(bxPromotion);
            bxUserPromotionEntity.setUserId(userid);
            bxUserPromotionEntity.setPromotionId(bxPromotion.getId());
            bxUserPromotionEntity.setCreateTime(new Date());
            bxUserPromotionDao.save(bxUserPromotionEntity);
        }

        return bxPromotion;
    }
}