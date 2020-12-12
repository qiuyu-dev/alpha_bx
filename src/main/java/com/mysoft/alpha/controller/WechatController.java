package com.mysoft.alpha.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysoft.alpha.config.AlphaConfig;
import com.mysoft.alpha.config.WeChatConfig;
import com.mysoft.alpha.dto.DisplayDTO;
import com.mysoft.alpha.entity.BxPromotion;
import com.mysoft.alpha.entity.User;
import com.mysoft.alpha.entity.WxUser;
import com.mysoft.alpha.result.Result;
import com.mysoft.alpha.result.ResultFactory;
import com.mysoft.alpha.service.BxAchievementService;
import com.mysoft.alpha.service.BxPromotionService;
import com.mysoft.alpha.service.BxTaskService;
import com.mysoft.alpha.service.UserService;
import com.mysoft.alpha.service.WxUserService;
import com.mysoft.alpha.util.HttpUtils;
import com.mysoft.alpha.util.QRCodeUtil;

@RestController
@RequestMapping("/api/v1/wechat")
public class WechatController {
	 private static final Logger log = LoggerFactory.getLogger(WechatController.class);
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private AlphaConfig alphaConfig;

    @Autowired
    private UserService userService;
    
    @Autowired
    private WxUserService wxUserService;
    
    /**
     * 保险推广服务对象
     */
    @Autowired
    private BxPromotionService bxPromotionService;


    /**
     * 保险业绩服务对象
     */
    @Autowired
    private BxAchievementService bxAchievementService;

    /**
     * 保险任务服务对象
     */
    @Autowired
    private BxTaskService bxTaskService;
    
	@GetMapping("/checkUser")
	public Result checkUser(@RequestParam Map<String, String> map, HttpServletRequest request) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String name = map.get("name");
		String phone = map.get("phone");//手机号为系统账号
		String openid = map.get("openid");
		String nickName = map.get("nickName");
		String avatarUrl = map.get("avatarUrl");
		String gender = map.get("gender");
		String country = map.get("country");
		String province = map.get("province");
		String city = map.get("city");
		String language = map.get("language");		
		User user = userService.findByUserNameAndEnabled(phone,1);
		if(log.isInfoEnabled()) {
			log.info(String.format("核验用户%s,%s",phone,user));
		}
		if(user == null) {
			return ResultFactory.buildFailResult("请联系您的主管提交您的销售信息给我们");
		} else {
			if (StringUtils.isBlank(openid)) {
				return ResultFactory.buildFailResult("参数openid为空");
			}
			WxUser wxUser = wxUserService.findByOpenid(openid);
			if (wxUser == null) {
				return ResultFactory.buildFailResult("微信用户不存在或参数openid错误");
				// 我方微信用户信息未知则保存
//                WxUser wxUserEntity = new WxUser();
//                wxUserEntity.setOpenid(openid);
//                wxUserEntity.setPhone(phone);
//                wxUserEntity.setName(name);
//                wxUserEntity.setNickName(nickName);
//                wxUserEntity.setAvatarUrl(avatarUrl);
//                wxUserEntity.setGender(gender != null?Integer.parseInt(gender):0);                       
//                wxUserEntity.setCountry(country);
//                wxUserEntity.setProvince(province);
//                wxUserEntity.setCity(city);
//                wxUserEntity.setLanguage(language);
//    			   wxUserEntity.setCreateTime(new Date());
//                wxUserService.createWxUser(wxUserEntity);

			} else if (user != null && wxUser != null) {
				wxUser.setUserid(user.getId());// 存入userid
				wxUser.setPhone(phone);
				wxUser.setName(name);
				wxUser.setNickName(nickName);
				wxUser.setAvatarUrl(avatarUrl);
				wxUser.setGender(gender != null ? Integer.parseInt(gender) : 0);
				wxUser.setCountry(country);
				wxUser.setProvince(province);
				wxUser.setCity(city);
				wxUser.setLanguage(language);
				wxUser.setUser(user);
				wxUser = wxUserService.updateWxUser(wxUser);
				// 给用户分配推广URL
				BxPromotion bxPromotion = bxPromotionService.findByUserId(user.getId());
				// 确认我方用户是否已认证
				if (bxPromotion == null) {
					bxPromotion = bxPromotionService.createByUserId(user.getId());
				}
				user.setBxPromotion(bxPromotion);
				Long amount;
				if (bxAchievementService.findAmountByUserId(user.getId()) != null) {
					amount = bxAchievementService.findAmountByUserId(user.getId());
					retMap.put("amount", amount);
				} else {
					retMap.put("amount", Long.valueOf(0));
				}
				retMap.put("name", user.getName());
				retMap.put("phone", user.getPhone());
				retMap.put("team", user.getTeam());
			} else {
				return ResultFactory.buildFailResult("用户不存在");
			}
		}
		return ResultFactory.buildSuccessResult(retMap);
	}    
	
	@GetMapping("/getUser")
    public Result getUser(@RequestParam(value = "openid", required = true) String openid, HttpServletResponse response)
            throws Exception {
        WxUser wxUser = wxUserService.findByOpenid(openid);
        if (wxUser == null) {
            return ResultFactory.buildFailResult("微信用户不存在或参数openid错误");
        } else {
            if (wxUser.getUserid() != null) {
                User user = userService.findById(wxUser.getUserid());
                //确认是否有我方用户
                if (user != null) {
                    BxPromotion bxPromotion = bxPromotionService.findByUserId(user.getId());
                    //确认我方用户是否已认证
                    if (bxPromotion != null && user.getEnabled().intValue() == 1) {
                        user.setBxPromotion(bxPromotion);
                    }
                    wxUser.setUser(user);
                }
            }
        }
        if(log.isInfoEnabled()) {
        	log.info(String.format("GetMapping(\"/getUser\"),openid:%s, wxUser:%s",openid, wxUser.toString()));
        }

        return ResultFactory.buildSuccessResult(wxUser);
    }	
	
    @GetMapping("/wxlogin")
    public Result wxlogin(@RequestParam(value = "code",required = true)String code, HttpServletResponse response) throws Exception {
    	String accessTokenUrl = String.format(WeChatConfig.getAuthJscode2session(),weChatConfig.getAppId(),weChatConfig.getAppsecret(),code);
        //获取access_token
        Map<String ,Object> baseMap =  HttpUtils.doGet(accessTokenUrl);
		if(log.isInfoEnabled()) {
			log.info(String.format("%s",baseMap));
		};
        String openid = (String)baseMap.get("openid");
		if(StringUtils.isBlank(openid)) {
			return ResultFactory.buildFailResult("参数openid为空");
		}
        WxUser wxUser = wxUserService.findByOpenid(openid);
        if(wxUser == null) {
        	WxUser wxUserEntity = new WxUser();
        	if(openid != null) {
        		wxUserEntity.setOpenid(openid);
        	}        	
            String unionid = (String) baseMap.get("unionid");
            if (unionid != null) {
            	wxUserEntity.setUnionid(unionid);
            }
        	wxUserEntity.setCreateTime(new Date());
        	wxUserEntity = wxUserService.createWxUser(wxUserEntity);
        	return ResultFactory.buildSuccessResult(wxUserEntity);
        }else {
        	if(wxUser.getUserid() != null) {
                User user = userService.findById(wxUser.getUserid());
                //确认是否有我方用户
                if (user != null) {            	
                    BxPromotion bxPromotion = bxPromotionService.findByUserId(user.getId());
                    //确认我方用户是否已认证
                    if (bxPromotion != null && user.getEnabled().intValue() == 1) {
                        user.setBxPromotion(bxPromotion);
                    }
                    wxUser.setUser(user);
                }
        	}
        }
    	return ResultFactory.buildSuccessResult(wxUser);
    }
    
    @GetMapping("/getAmount")
    public Result getAmount(@RequestParam(value = "openid",required = true)String openid, HttpServletResponse response) throws Exception {
    	Map<String, Object> retMap = new HashMap<String,Object>();    	
		if(StringUtils.isBlank(openid)) {
			return ResultFactory.buildFailResult("参数openid为空");
		}
    	WxUser wxUser = wxUserService.findByOpenid(openid);
    	if(wxUser !=null) {    		
    		User user = userService.findById(wxUser.getUserid());
    		if(user != null) {
        		Long amount = bxAchievementService.findAmountByUserId(user.getId());
        		retMap.put("amount", amount);
    		}else {
    			retMap.put("amount", 0);
    		}
    		return ResultFactory.buildSuccessResult(retMap);
    	}else {
    		return ResultFactory.buildFailResult("用户不存在");
    	}
    }
    
    @GetMapping("/updateWxUser")
    public Result updateWxUser(@RequestParam Map<String, String> map, HttpServletRequest request) throws Exception {
		String openid = map.get("openid");
		String nickName = map.get("nickName") ;
		String avatarUrl = map.get("avatarUrl");
		String gender = map.get("gender");
		String country = map.get("country");
		String province = map.get("province");
		String city = map.get("city");
		String language = map.get("language");
		WxUser wxUser = wxUserService.findByOpenid(openid);
		if(StringUtils.isBlank(openid)) {
			return ResultFactory.buildFailResult("参数openid为空");
		}
		if(wxUser != null) {
			wxUser.setNickName(nickName);
			wxUser.setAvatarUrl(avatarUrl);
			wxUser.setGender(gender != null?Integer.parseInt(gender):0);
			wxUser.setCountry(country);
			wxUser.setProvince(province);
			wxUser.setCity(city);
			wxUser.setLanguage(language);			
			wxUserService.updateWxUser(wxUser);
		}
    	return ResultFactory.buildSuccessResult("success");
    }
    
    //查询展示
    @GetMapping("/display")
    public Result display(@RequestParam(value = "openid", required = true) String openid,
                          @RequestParam(value = "findsub", required = true) Boolean findsub, HttpServletRequest request)
            throws Exception {
		if(StringUtils.isBlank(openid)) {
			return ResultFactory.buildFailResult("参数openid为空");
		}
        WxUser wxUser = wxUserService.findByOpenid(openid);
        User user = new User();
        if (wxUser != null) {
            user = userService.findById(wxUser.getUserid());
            if (user == null) {
                return ResultFactory.buildFailResult("用户不存在");
            }
        } else {
            return ResultFactory.buildFailResult("用户不存在");
        }
        List<DisplayDTO> list = listAllUser(user.getId(), 1, findsub);
        if(list != null) {
        	if(log.isInfoEnabled()) {
               log.info(String.format("GetMapping(\"/display\"),openid:%s,findsub:%s,list:%s", openid, findsub, list.toString()));
        	}
            return ResultFactory.buildSuccessResult(list);
        }
        return ResultFactory.buildFailResult("用户不存在");
    }

    List<DisplayDTO> listAllUser(Integer userId, int level, boolean findsub) {
        User user = userService.findById(userId);
        if (user == null) {
            return null;
        } else {
            DisplayDTO displayDTO = new DisplayDTO();
            displayDTO.setUserId(user.getId());
            displayDTO.setName(user.getName());
            displayDTO.setLevel(level);
            displayDTO.setAchievementAmount(bxAchievementService.findAmountByUserId(user.getId()));
            displayDTO.setTaskAmount(bxTaskService.findAmountByUserId(user.getId()));
            displayDTO.setdValue((displayDTO.getTaskAmount() != null ? displayDTO.getTaskAmount() : 0) -
                    (displayDTO.getAchievementAmount() != null ? displayDTO.getAchievementAmount() : 0));
            if(displayDTO.getdValue()<0) {//负数设置成0
            	displayDTO.setdValue(Long.valueOf(0));
            }
            List<DisplayDTO> list = new LinkedList<>();

            list.add(displayDTO);
            List<User> subUserList = userService.findSubUsers(user.getId());
            if (findsub) {
                for (User subUser : subUserList) {
                    list.addAll(listAllUser(subUser.getId(), level + 1, true));
                }
            }
            return list;
        }
    }
    
    
  //生成二维码
    @GetMapping("/qrimage")
    public Result qrimage(@RequestParam(value = "promotionId", required = true) Integer promotionId,
                          HttpServletRequest request) throws Exception {
        List<BxPromotion> promotionList = new ArrayList<BxPromotion>();
        if (promotionId.intValue() == 0) {
            promotionList = bxPromotionService.findByStatus(1);
        } else {
            if (bxPromotionService.getOneByPromotionId(promotionId) != null) {
                promotionList.add(bxPromotionService.getOneByPromotionId(promotionId));
            }
        }
        if (promotionList.size() < 1) {
            return ResultFactory.buildFailResult("保险推广URL不存在");
        } else {
            String destPath = alphaConfig.getUploadFolder();

            for (BxPromotion bxPromotion : promotionList) {
            	
                QRCodeUtil.encode(bxPromotion.getUrl(), null,
                        destPath + "qrcode" + bxPromotion.getId() ,QRCodeUtil.QRCODE_FILENAME, true);
            }

        }
        if(log.isInfoEnabled()) {
        	log.info(String.format(request.getRequestURI() +",promotionId:%s,共计:%s", promotionId,promotionList.size()));
        }
        return ResultFactory.buildSuccessResult("生成保险推广URL二维码成功,共计：" + promotionList.size() + "个。");
    }
    
    //通过二维码图片链接生成
    @GetMapping("/genQRCodeUrl")
    public Result genQRCodeUrl(@RequestParam(value = "promotionId", required = true) Integer promotionId,
                          HttpServletRequest request) throws Exception {
        List<BxPromotion> promotionList = new ArrayList<BxPromotion>();
        if (promotionId.intValue() == 0) {
            promotionList = bxPromotionService.findByStatus(1);
        } else {
            if (bxPromotionService.getOneByPromotionId(promotionId) != null) {
                promotionList.add(bxPromotionService.getOneByPromotionId(promotionId));
            }
        }
        if (promotionList.size() < 1) {
            return ResultFactory.buildFailResult("二维码图片链接URL不存在");
        } else {
            String destPath = alphaConfig.getUploadFolder();

            for (BxPromotion bxPromotion : promotionList) {
            	
                QRCodeUtil.genQRCode(bxPromotion.getUrl(), 
                        destPath + "qrcode" + bxPromotion.getId() ,QRCodeUtil.QRCODE_FILENAME);
            }

        }
        if(log.isInfoEnabled()) {
        	log.info(String.format(request.getRequestURI() +",promotionId:%s,共计:%s", promotionId,promotionList.size()));
        }
        return ResultFactory.buildSuccessResult("生成图片链接二维码成功,共计：" + promotionList.size() + "个。");
    }    
}
