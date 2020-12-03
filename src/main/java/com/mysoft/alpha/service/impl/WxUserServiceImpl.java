package com.mysoft.alpha.service.impl;

import com.mysoft.alpha.cache.CacheKeyManager;
import com.mysoft.alpha.dao.WxUserDao;
import com.mysoft.alpha.entity.WxUser;
import com.mysoft.alpha.service.WxUserService;
import com.mysoft.alpha.util.BaseCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * (WxUser)表服务实现类
 *
 * @author makejava
 * @since 2020-11-29 14:12:52
 */
@Service
public class WxUserServiceImpl implements WxUserService {
    /**
     * 服务对象
     */
    @Autowired
    private WxUserDao wxUserDao;

    /**
     * 缓存
     */
    @Autowired
    private BaseCache baseCache;

    @Override
    public WxUser findByOpenid(String openid) {
        String wechatWxuser = String.format(CacheKeyManager.WECHAT_WXUSER,openid);
        try {
            Object cacheObj = baseCache.getOneHourCache().get(wechatWxuser, () -> {
                WxUser wxUser = wxUserDao.findByOpenid(openid);
                return wxUser;
            });
            if (cacheObj instanceof WxUser) {
                WxUser wxUser = (WxUser) cacheObj;
                return wxUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public WxUser createWxUser(WxUser wxUser) {
        return wxUserDao.save(wxUser);
    }

    @Override
    public WxUser updateWxUser(WxUser wxUser) {
        return wxUserDao.saveAndFlush(wxUser);
    }

}