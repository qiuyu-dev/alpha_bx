package com.mysoft.alpha.service.impl;

import com.mysoft.alpha.dao.BxUserPromotionDao;
import com.mysoft.alpha.service.BxUserPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * (BxUserPromotion)表服务实现类
 *
 * @author makejava
 * @since 2020-11-29 14:12:58
 */
@Service
public class BxUserPromotionServiceImpl implements BxUserPromotionService {
    /**
     * 服务对象
     */
    @Autowired
    private BxUserPromotionDao bxUserPromotionDao;

}