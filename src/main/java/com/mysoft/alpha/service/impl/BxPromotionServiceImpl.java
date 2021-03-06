package com.mysoft.alpha.service.impl;

import com.mysoft.alpha.cache.CacheKeyManager;
import com.mysoft.alpha.dao.BxPromotionDao;
import com.mysoft.alpha.dao.BxUserPromotionDao;
import com.mysoft.alpha.dao.UserDao;
import com.mysoft.alpha.entity.BxPromotion;
import com.mysoft.alpha.entity.BxUserPromotion;
import com.mysoft.alpha.entity.User;
import com.mysoft.alpha.exception.CustomException;
import com.mysoft.alpha.service.BxPromotionService;
import com.mysoft.alpha.util.BaseCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    /**
     * 缓存
     */
//    @Autowired
//    private BaseCache baseCache;

    @Override
    public BxPromotion findByUserId(Integer userId) {
//        String wechatPromotion = String.format(CacheKeyManager.WECHAT_PROMOTION, userId);
//        try {
//            Object cacheObj = baseCache.getOneHourCache().get(wechatPromotion, () -> {
                BxUserPromotion bxUserPromotion = bxUserPromotionDao.findByUserId(userId);
//                System.out.println("BxPromotion 使用数据库");
                if (bxUserPromotion == null) {
                    return null;
                }

                Optional<BxPromotion> optional =  bxPromotionDao.findById(bxUserPromotion.getPromotionId());
                if ( optional != null && optional.isPresent()) {
                	return optional.get();
                }
               return null;
//            });
//            if (cacheObj instanceof BxPromotion) {
//                BxPromotion bxPromotion = (BxPromotion) cacheObj;
//                return bxPromotion;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public BxPromotion createByUserId(Integer userId) {
//    	 User user = userDao.getOne(userId);
        User user = null;
    	Optional<User> optional =  userDao.findById(userId);
        if ( optional != null && optional.isPresent()) {
        	user = optional.get();
        }    
        if (user == null || user.getEnabled() == null || user.getEnabled() == 0) {
            throw new CustomException(0, "用户认证错误:无此用户或用户状态无效");
        }
        BxUserPromotion bxUserPromotion = bxUserPromotionDao.findByUserId(userId);
        BxPromotion bxPromotion = new BxPromotion();
        if (bxUserPromotion != null) {
//            bxPromotion = bxPromotionDao.getOne(bxUserPromotion.getPromotionId());
            Optional<BxPromotion> optionalBp =  bxPromotionDao.findById(bxUserPromotion.getPromotionId());
            if ( optionalBp != null && optionalBp.isPresent()) {
            	bxPromotion= optionalBp.get();
            }
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
            bxUserPromotionEntity.setUserId(userId);
            bxUserPromotionEntity.setPromotionId(bxPromotion.getId());
            bxUserPromotionEntity.setCreateTime(new Date());
            bxUserPromotionDao.save(bxUserPromotionEntity);
        }

        return bxPromotion;
    }
    
    @Override
    public BxPromotion getOneByPromotionId(Integer promotionId) {
        Optional<BxPromotion> optional =  bxPromotionDao.findById(promotionId);
        if ( optional != null && optional.isPresent()) {
            return optional.get();
        }
       return null;
    }
    
    @Override
    public List<BxPromotion> findByStatus(Integer status) {
        return bxPromotionDao.findByStatus(status);
    }
}