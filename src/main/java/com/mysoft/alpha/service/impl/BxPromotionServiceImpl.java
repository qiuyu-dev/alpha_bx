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
    @Autowired
    private BaseCache baseCache;

    @Override
    public BxPromotion findByUserid(Integer userid) {
        String wechatPromotion = String.format(CacheKeyManager.WECHAT_PROMOTION, userid);
        try {
            Object cacheObj = baseCache.getOneHourCache().get(wechatPromotion, () -> {
                BxUserPromotion bxUserPromotion = bxUserPromotionDao.findByUserId(userid);
                System.out.println("BxPromotion 使用数据库");
                if (bxUserPromotion == null) {
                    return null;
                }
                return bxPromotionDao.getOne(bxUserPromotion.getPromotionId());
            });
            if (cacheObj instanceof BxPromotion) {
                BxPromotion bxPromotion = (BxPromotion) cacheObj;
                return bxPromotion;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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