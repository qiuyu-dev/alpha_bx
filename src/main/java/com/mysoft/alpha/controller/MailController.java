package com.mysoft.alpha.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysoft.alpha.result.Result;
import com.mysoft.alpha.result.ResultFactory;
import com.mysoft.alpha.service.BxAchievementService;
import com.mysoft.alpha.service.MailService;
import com.mysoft.alpha.util.ExcelExportUtil;
import com.mysoft.alpha.vo.MailVO;


@RestController
@RequestMapping("/api/v1/mail")
public class MailController {
	private static final Logger log = LoggerFactory.getLogger(MailController.class);
    @Autowired
    private MailService mailService;
    
    @Autowired
    private BxAchievementService bxAchievementService;
   
    @GetMapping("/send")
	public void send() throws Exception{
		//发送邮件
        MailVO mailVO = new MailVO();
        mailVO.setTo("75387273@qq.com");
        mailVO.setSubject("这是一封测试");
        mailVO.setText("你好2021");
//        mailService.sendMail(mailVO);

        mailService.sendStatMail();

	}
    
    @GetMapping("/export")
    public Result export(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		List<Map<String, Object>> maps = bxAchievementService.findAllAchievement();
		String sheetTitle = "test";
		String[] title = new String[] { "姓名", "手机号", "团队", "成单量", "曝光人数" }; // 设置表格表头字段
		String[] properties = new String[] { "姓名", "手机号", "团队", "成单量", "曝光人数" }; // 查询对应的字段
		ExcelExportUtil excelExport2 = new ExcelExportUtil();
		excelExport2.setData(maps);
		excelExport2.setHeadKey(properties);
		excelExport2.setFontSize(14);
		excelExport2.setSheetName(sheetTitle);
		excelExport2.setTitle(sheetTitle);
		excelExport2.setHeadList(title);
		excelExport2.exportExport(request, response);
		
    	return ResultFactory.buildSuccessResult("ok");
    }

}
