package com.mysoft.alpha.service.impl;

import com.mysoft.alpha.dao.BxTaskDao;
import com.mysoft.alpha.service.BxTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (BxTask)表服务实现类
 *
 * @author makejava
 * @since 2020-11-29 14:13:04
 */
@Service
public class BxTaskServiceImpl implements BxTaskService {
    /**
     * 服务对象
     */
    @Autowired
    private BxTaskDao bxTaskDao;

    @Override
    public Long findAmountByUserId(Integer userId) {
        return bxTaskDao.findAmountByParamsAndSort(userId);
    }
}