package com.mysoft.alpha.service.impl;

import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysoft.alpha.dao.BxAchievementDao;
import com.mysoft.alpha.entity.BxAchievement;
import com.mysoft.alpha.service.MailService;
import com.mysoft.alpha.util.DateUtil;
import com.mysoft.alpha.util.ExcelExportUtil;
import com.mysoft.alpha.vo.MailVO;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.IMAPProtocol;

@Service
public class MailServiceImpl  implements MailService {
	private static final String FROM_360 = "monitor@360loan.cn";

	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSenderImpl mailSender;//注入邮件工具类
    
    @Autowired
    private BxAchievementDao bxAchievementDao;
    
    @Async
    @Override
    public MailVO sendMail(MailVO mailVO) {
        try {
            checkMail(mailVO); //1.检测邮件
            sendMimeMail(mailVO); //2.发送邮件
            return saveMail(mailVO); //3.保存邮件
        } catch (Exception e) {
        	mailVO.setStatus("fail");
        	mailVO.setError(e.getMessage());
            return mailVO;
        }
    }
 
    //检测邮件信息类
    private void checkMail(MailVO MailVO) {
        if (StringUtils.isEmpty(MailVO.getTo())) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(MailVO.getSubject())) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(MailVO.getText())) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }
 
    //构建复杂邮件信息类
    private void sendMimeMail(MailVO mailVO) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
            if(mailVO.getFrom() == null) {
            	mailVO.setFrom(getMailSendFrom());//邮件发信人从配置项读取
            }            
            messageHelper.setFrom(mailVO.getFrom());//邮件发信人
            messageHelper.setTo(mailVO.getTo().split(","));//邮件收信人
            messageHelper.setSubject(mailVO.getSubject());//邮件主题
            messageHelper.setText(mailVO.getText());//邮件内容
            if (!StringUtils.isEmpty(mailVO.getCc())) {//抄送
                messageHelper.setCc(mailVO.getCc().split(","));
            }
            if (!StringUtils.isEmpty(mailVO.getBcc())) {//密送
                messageHelper.setCc(mailVO.getBcc().split(","));
            }
            if (mailVO.getMultipartFiles() != null) {//添加邮件附件
                for (MultipartFile multipartFile : mailVO.getMultipartFiles()) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }
            if (mailVO.getSentDate() == null) {//发送时间
            	mailVO.setSentDate(new Date());
                messageHelper.setSentDate(mailVO.getSentDate());
            }
            mailSender.send(messageHelper.getMimeMessage());//正式发送邮件
            mailVO.setStatus("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);//发送失败
        }
    }
 
    //保存邮件
    private MailVO saveMail(MailVO MailVO) {
 
        //将邮件保存到数据库..
 
        return MailVO;
    }
 
    //获取邮件发信人
    private String getMailSendFrom() {
        return mailSender.getJavaMailProperties().getProperty("from");
    }
    
    //获取邮件收信人
    private String getMailSendTo() {
        return mailSender.getJavaMailProperties().getProperty("to");
    }
    
    //获取邮件抄送人
    private String getMailSendCc() {
        return mailSender.getJavaMailProperties().getProperty("cc");
    }

	@Override
	public void receiveMail() {		
		// 设置SSL连接、邮件环境
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = System.getProperties();
		props.setProperty("mail.imap.host", "imap.163.com");
		props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		props.setProperty("mail.imap.port", "993");
		props.setProperty("mail.imap.socketFactory.port", "993");
		props.setProperty("mail.imap.auth", "true");
		// 建立邮件会话
		Session session = Session.getInstance(props, null);
        String username = "onetreehill2020@163.com";
        String password = "YJGCKUAWCGGBBUWF";
		// 设置连接邮件仓库的环境
		URLName url = new URLName("imap", "imap.163.com", 993, null, username, password);
		Store store = null;
		Folder inbox = null;
		try {
			// 得到邮件仓库并连接
			store = session.getStore(url);
			store.connect();
	        final Map<String, String> clientParams = new HashMap<String, String>();
	        clientParams.put("name", "my-imap");
	        clientParams.put("version", "1.0");
			// 得到收件箱并抓取邮件
			inbox = store.getFolder("INBOX");
			((IMAPFolder) inbox).doOptionalCommand("ID not supported", new IMAPFolder.ProtocolCommand() {
				@Override
				public Object doCommand(IMAPProtocol p) throws ProtocolException {
					return p.id(clientParams);
				}
			});
			inbox.open(Folder.READ_WRITE);
			
			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);
			// false代表未读，true代表已读
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			// 根据设置好的条件获取message
			Message[] messages = inbox.search(ft);
			inbox.fetch(messages, profile);
			int length = messages.length;
			log.info("收件箱的邮件数：" + length);

			for (int i = 0; i < length; i++) {
				String from = MimeUtility.decodeText(messages[i].getFrom()[0].toString());
				if(from.equalsIgnoreCase(FROM_360)) {
					InternetAddress ia = new InternetAddress(from);
					String subject = messages[i].getSubject();
					String sendTime = DateUtil.dateToStrLong(messages[i].getSentDate());
//					System.out.println("发件人：" + ia.getPersonal() + '(' + ia.getAddress() + ')');
//					System.out.println("主题：" + subject);
//					System.out.println("邮件发送时间：" + sendTime);
//					System.out.println("邮件内容：" + messages[i].getContent());
					Object obj = messages[i].getContent();
					if(obj instanceof Multipart) {
						MimeMultipart mimeMultipart =  (MimeMultipart)messages[i].getContent();
						BodyPart bodyPart = mimeMultipart.getBodyPart(0);
						Object  content = bodyPart.getContent();
	                    List<BxAchievement> list = parseHtml(content.toString(), subject);
	                    if(list != null && list.size() > 0) {
		                    int row = saveAchievement(list, subject);
		                    log.info("更新条数：" + row);
	                    }                    
	                    
					}else {
						System.out.println(obj);
					}
					messages[i].setFlag(Flags.Flag.SEEN, true);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inbox != null) {
					inbox.close(false);
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			try {
				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}		

	}

	private List<BxAchievement> parseHtml(String html, String subject) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		List<BxAchievement> bxAchievementList =  new ArrayList<BxAchievement>();
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByTag("tr");
		boolean isGzh = false;//是否公众号数据
		for (int i = 0; i < elements.size(); i++) {
		  String td = elements.get(i).text();
//		  System.out.println(td);
		  String[] tds =td.split(" ");
		  if(td.indexOf("关注") > 0 || subject.indexOf("关注") > 0) {
			  isGzh = true;
		  }
		  if(tds[0].equals("日期") || tds[0].equals("总计")) {
			  continue;
		  }
//		  for(int t=0;t<tds.length;t++) {
//			  System.out.print("td["+t+"]="+tds[t] + " ");
//		  }
//		  System.out.println();
//		  if(tds[0].equals("2021-01-17")) {
//			  list.add(tds);
//		  }
		  list.add(tds);
		}
		
		if(isGzh) {
			  for(int i = 0 ; i < list.size(); i++) {
				  
				  StringBuffer sql = new StringBuffer("insert into bx_achievement (flag,create_time,remark,url,amount,premium,follow_num) VALUES  (2,");
				  String[] tds = list.get(i);
				  if(tds[4].equals("None")) {
					  tds[4]="0";
				  }
				  if(tds[4].indexOf(".")>0) {
					  tds[4]=tds[4].substring(0,tds[4].indexOf("."));
				  }
				  if(tds[5].equals("None")) {
					  tds[5]="0";
				  }	
				  if(tds[5].indexOf(".")>0) {
					  tds[5]=tds[5].substring(0,tds[5].indexOf("."));
				  }
				  if(tds[6].equals("None")) {
					  tds[6]="0";
				  }
				  if(tds[6].indexOf(".")>0) {
					  tds[6]=tds[6].substring(0,tds[6].indexOf("."));
				  }
				  sql.append("date_format(\'"+tds[0] + "\', \'%Y-%m-%d\'),").append("\'"+tds[0].replaceAll("-", "")+"邮件导入数据\',").append("\'"+tds[3]+"\',").append(tds[4]+",").append(tds[5]+",").append(tds[6]).append(");");
//				  System.out.println(sql.toString());
				  
				  BxAchievement bxAchievement = new BxAchievement();
				  bxAchievement.setFlag(2);
				  bxAchievement.setCreateTime(DateUtil.strToDate(tds[0]));			  
				  bxAchievement.setRemark(tds[0].replaceAll("-", "")+"邮件导入数据");//备注区分手工导入
				  bxAchievement.setUrl(tds[3]);
				  bxAchievement.setAmount(Integer.valueOf(tds[4]));
				  bxAchievement.setPremium(Integer.valueOf(tds[5]));				  
				  bxAchievement.setFollowNum(Integer.valueOf(tds[6]));
				  bxAchievementList.add(bxAchievement);
			  }
			
		}else {
			  for(int i = 0 ; i < list.size(); i++) {
				  
				  StringBuffer sql = new StringBuffer("insert into bx_achievement (flag,create_time,remark,url,exposure_num,amount,premium,customers) VALUES  (1,");
				  String[] tds = list.get(i);
				  if(tds.length < 8) {
					  continue;//避免有空的情况入库,planid可能为空
				  }
				  if(tds[4].equals("None")) {
					  tds[4]="0";
				  }
				  if(tds[4].indexOf(".")>0) {
					  tds[4]=tds[4].substring(0,tds[4].indexOf("."));
				  }
				  if(tds[5].equals("None")) {
					  tds[5]="0";
				  }	
				  if(tds[5].indexOf(".")>0) {
					  tds[5]=tds[5].substring(0,tds[5].indexOf("."));
				  }
				  if(tds[6].equals("None")) {
					  tds[6]="0";
				  }				  
				  if(tds[6].indexOf(".")>0) {
					  tds[6]=tds[6].substring(0,tds[6].indexOf("."));
				  }
				  if(tds[7].equals("None")) {
					  tds[7]="";
				  }			 
				  sql.append("date_format(\'"+tds[0] + "\', \'%Y-%m-%d\'),").append("\'"+tds[0].replaceAll("-", "")+"邮件导入数据\',").append("\'"+tds[3]+"\',").append(tds[4]+",").append(tds[5]+",").append(tds[6]+",").append("\'"+tds[7]+ "\'").append(");");
//				  System.out.println(sql.toString());
				  
				  BxAchievement bxAchievement = new BxAchievement();
				  bxAchievement.setFlag(1);
				  bxAchievement.setCreateTime(DateUtil.strToDate(tds[0]));			  
				  bxAchievement.setRemark(tds[0].replaceAll("-", "")+"邮件导入数据");//备注区分手工导入
				  bxAchievement.setUrl(tds[3]);
				  bxAchievement.setExposureNum(Integer.valueOf(tds[4]));
				  bxAchievement.setAmount(Integer.valueOf(tds[5]));
				  bxAchievement.setPremium(Integer.valueOf(tds[6]));
				  bxAchievement.setCustomers(tds[7]);
				  bxAchievementList.add(bxAchievement);
			  }
		}		

		return bxAchievementList;
	}
	
	
	private int saveAchievement(List<BxAchievement> list,String subject)  throws Exception {
		log.info("邮件导入开始");
		int ret = 0;
		int maxid = bxAchievementDao.findMaxId();
		bxAchievementDao.saveAll(list);

		if(subject.indexOf("关注")>0) {
			ret = bxAchievementDao.updateAchievementGzh(maxid);
		}else {
			ret = bxAchievementDao.updateAchievement(maxid);
		}
		log.info("邮件导入结束");
		return ret;
	}

	@Async
	@Override
	public MailVO sendStatMail() {
		List<Map<String, Object>> maps = bxAchievementDao.findAllAchievement();
        String yesterday = DateUtil.getYesterdayDate();
        String subject = yesterday + "累计销量统计";//邮件主题
        String contenText = "您好！附件是" +subject +"，请查收！";
		String sheetTitle = subject;
		String[] title = new String[] { "姓名", "手机号", "团队", "成单量", "曝光人数", "保费" }; // 设置表格表头字段
		String[] properties = new String[] { "姓名", "手机号", "团队", "成单量", "曝光人数", "保费" }; // 查询对应的字段
		ExcelExportUtil excelExport2 = new ExcelExportUtil();
		excelExport2.setData(maps);
		excelExport2.setHeadKey(properties);
		excelExport2.setFontSize(14);
		excelExport2.setSheetName(sheetTitle);
		excelExport2.setTitle(sheetTitle);
		excelExport2.setHeadList(title);

		MailVO mailVO = new MailVO();
        mailVO.setTo(getMailSendTo());
        mailVO.setCc(getMailSendCc());
        mailVO.setSubject(subject);
        mailVO.setText(contenText);
		try {
			byte[] content = excelExport2.exportData();
			Multipart multipart = new MimeMultipart("related");
			MimeBodyPart bodyPart = new MimeBodyPart();
			DataSource ds = new ByteArrayDataSource(content,"application/multipart");
			DataHandler handler = new DataHandler(ds);
			bodyPart.setDataHandler(handler);
			bodyPart.setContentID("attach"); 
			bodyPart.setFileName(DateUtil.getYesterdayDate() +"统计.xls");
			multipart.addBodyPart(bodyPart);
		
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
            if(mailVO.getFrom() == null) {
            	mailVO.setFrom(getMailSendFrom());//邮件发信人从配置项读取
            }            
            messageHelper.setFrom(mailVO.getFrom());//邮件发信人
            messageHelper.setTo(mailVO.getTo().split(","));//邮件收信人
            messageHelper.setSubject(mailVO.getSubject());//邮件主题
            messageHelper.setText(mailVO.getText());//邮件内容
            if (!StringUtils.isEmpty(mailVO.getCc())) {//抄送
                messageHelper.setCc(mailVO.getCc().split(","));
            }
            if (!StringUtils.isEmpty(mailVO.getBcc())) {//密送
                messageHelper.setCc(mailVO.getBcc().split(","));
            }
            if (mailVO.getMultipartFiles() != null) {//添加邮件附件
                for (MultipartFile multipartFile : mailVO.getMultipartFiles()) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }
            if (mailVO.getSentDate() == null) {//发送时间
            	mailVO.setSentDate(new Date());
                messageHelper.setSentDate(mailVO.getSentDate());
            }

			MimeMessage mimeMessage = messageHelper.getMimeMessage();
			 // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(contenText);
            multipart.addBodyPart(contentPart);

			mimeMessage.setContent(multipart);
            mailSender.send(mimeMessage);//正式发送邮件
            mailVO.setStatus("ok");
		} catch (Exception e) {
			e.printStackTrace();
        	mailVO.setStatus("fail");
        	mailVO.setError(e.getMessage());
            return mailVO;
		} 
		return mailVO;
	}

}
