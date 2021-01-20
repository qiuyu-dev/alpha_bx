package com.mysoft.alpha.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.mysoft.alpha.service.MailService;
import com.mysoft.alpha.util.DateUtil;

@Configuration
@EnableScheduling
@EnableAsync     
public class ScheduledTask {
	 private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
	
	 @Autowired
	 MailService mailService;
	 
	@Async
    @Scheduled(cron = "${alpha.scheduledtask.cron:0 15 10 * * ?}")
    public void scheduled(){
		long begin = System.currentTimeMillis();
		log.info("开始处理邮件：" + DateUtil.getCurrentTime());
        mailService.receiveMail();	
        mailService.sendStatMail();
        log.info("结束处理邮件：" + DateUtil.getCurrentTime() + " 耗时：" + (System.currentTimeMillis() - begin)/1000 + "s");
         
    }    


}
