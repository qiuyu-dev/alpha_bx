package com.mysoft.alpha.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.IMAPProtocol;

public class ReceiveIMAP {
	
	public static void receiveSSLMail() {
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
			/**
			* Flag 类型列举如下
			* Flags.Flag.ANSWERED 邮件回复标记，标识邮件是否已回复。
			* Flags.Flag.DELETED 邮件删除标记，标识邮件是否需要删除。
			* Flags.Flag.DRAFT 草稿邮件标记，标识邮件是否为草稿。
			* Flags.Flag.FLAGGED 表示邮件是否为回收站中的邮件。
			* Flags.Flag.RECENT 新邮件标记，表示邮件是否为新邮件。
			* Flags.Flag.SEEN 邮件阅读标记，标识邮件是否已被阅读。
			* Flags.Flag.USER 底层系统是否支持用户自定义标记，只读。
			*/
			// false代表未读，true代表已读
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), true);
			// 根据设置好的条件获取message
			Message[] messages = inbox.search(ft);
//            Message[] messages = inbox.getMessages();
			inbox.fetch(messages, profile);
			int length = messages.length;
			System.out.println("收件箱的邮件数：" + length);
			System.out.println("-------------------------------------------\n");
			// 遍历收件箱、垃圾箱等名称
			Folder defaultFolder = store.getDefaultFolder();
			Folder[] folders = defaultFolder.list();

			for (int i = 0; i < folders.length; i++) {
				System.out.println("名称：" + folders[i].getName());
			}
			System.out.println("-------------------------------------------\n");
			for (int i = 0; i < length; i++) {
				String from = MimeUtility.decodeText(messages[i].getFrom()[0].toString());
				InternetAddress ia = new InternetAddress(from);
				System.out.println("发件人：" + ia.getPersonal() + '(' + ia.getAddress() + ')');
				System.out.println("主题：" + messages[i].getSubject());
				System.out.println("邮件发送时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(messages[i].getSentDate()));
				System.out.println("邮件内容：" + messages[i].getContent());
				Object obj = messages[i].getContent();
				if(obj instanceof String) {
					System.out.println("string");
				}else {
					MimeMultipart mimeMultipart =  (MimeMultipart)messages[i].getContent();
					BodyPart bPart = mimeMultipart.getBodyPart(0);
					Object  contentString = bPart.getContent();
//					System.out.println("contentString="+contentString);
					
                    parse(contentString.toString());
				}

				System.out.println("-------------------------------------------\n");
				messages[i].setFlag(Flags.Flag.SEEN, true);
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
	
	public static void receiveMail() {
        // 准备连接服务器的会话信息 
        Properties props = new Properties(); 
        props.setProperty("mail.store.protocol", "imap"); 
        props.setProperty("mail.imap.host", "imap.163.com"); 
        props.setProperty("mail.imap.port", "143"); 
        try {
        // 创建Session实例对象 
        Session session = Session.getInstance(props); 
         
        // 创建IMAP协议的Store对象 
        Store store = session.getStore("imap"); 
        String username = "onetreehill2020@163.com";
        String password = "YJGCKUAWCGGBBUWF";
        // 连接邮件服务器 
       store.connect(username, password); 
        
        // 获得收件箱 
        Folder folder = store.getFolder("INBOX"); 
        final Map<String, String> clientParams = new HashMap<String, String>();
        clientParams.put("name", "my-imap");
        clientParams.put("version", "1.0");
		((IMAPFolder) folder).doOptionalCommand("ID not supported", new IMAPFolder.ProtocolCommand() {
			@Override
			public Object doCommand(IMAPProtocol p) throws ProtocolException {
				return p.id(clientParams);
			}
		});
//		folder.open(Folder.READ_ONLY);        

        // 以读写模式打开收件箱 
        folder.open(Folder.READ_WRITE); 
         
        // 获得收件箱的邮件列表 
        Message[] messages = folder.getMessages(); 
         
        // 打印不同状态的邮件数量 
        System.out.println("收件箱中共" + messages.length + "封邮件!"); 
        System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!"); 
        System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!"); 
        System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!"); 
         
        System.out.println("------------------------开始解析邮件----------------------------------"); 
         
        
        int total = folder.getMessageCount();
        System.out.println("-----------------您的邮箱共有邮件：" + total + " 封--------------");
        // 得到收件箱文件夹信息，获取邮件列表
        Message[] msgs = folder.getMessages();
        System.out.println("\t收件箱的总邮件数：" + msgs.length);
        for (int i = 0; i < total; i++) {
            Message a = msgs[i];
            //   获取邮箱邮件名字及时间

            System.out.println(a.getReplyTo());
            
            System.out.println("==============");
            System.out.println(a.getSubject() + "   接收时间：" + a.getReceivedDate().toLocaleString()+"  contentType()=" +a.getContentType());
        }
        System.out.println("\t未读邮件数：" + folder.getUnreadMessageCount());
        System.out.println("\t新邮件数：" + folder.getNewMessageCount());
        System.out.println("----------------End------------------");
        
        
         
        // 关闭资源 
        folder.close(false); 
        store.close(); 
        } catch (Exception e) {
			e.printStackTrace();
		} 
    } 

public static void jsoupDemo() throws Exception {
    Integer cot=1;
    File file = new File("e://豆瓣T250书单.txt");
    if(file.exists()==false) file.createNewFile();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file,true));


    for (int i = 0; i <=225; i+=25) {
        Document document = Jsoup.parse(new URL("https://book.douban.com/top250?start=" + i), 10000);
        //获取书名
        List<String> bNameList = document.select("div#content .item .pl2 [title]").eachText();
        //获取简介
        List<String> bConList = document.select("div#content .item p.pl").eachText();
        for (int j = 0; j < bNameList.size(); j++) {
            bufferedOutputStream.write(String.valueOf(cot).getBytes());
            bufferedOutputStream.write("\r\n".getBytes());
            bufferedOutputStream.write(bNameList.get(j).getBytes());
            bufferedOutputStream.write("\r\n".getBytes());
            bufferedOutputStream.write(bConList.get(j).getBytes());
            bufferedOutputStream.write("\r\n".getBytes());
            cot++;
        }

    }
    bufferedOutputStream.close();
    System.out.println("completed");
}
	public static void test() throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		File input = new File("C:\\project\\alpha_bx\\src\\main\\resources\\public\\20210116.html");
		Document document = Jsoup.parse(input,"UTF-8");
		Elements elements = document.getElementsByTag("tr");
		for (Element element : elements) {
		  String td = element.text();
		  System.out.println(""+td);
		  String[] tds =td.split(" ");
		  if(tds[0].equals("日期") || tds[0].equals("总计")) {
			  continue;
		  }
		  list.add(tds);
		  for(int t=0;t<tds.length;t++) {
			  System.out.print("td["+t+"]="+tds[t] + " ");				  
		  }
//		  System.out.println(sql.toString());
//		  list.add(sql.toString());
		  System.out.println();
		}
		  
		  for(int i = 0 ; i < list.size(); i++) {
			  StringBuffer sql = new StringBuffer("insert into bx_achievement (flag,create_time,remark,url,exposure_num,amount,premium,customers) VALUES  (1,");
			  String[] tds = list.get(i);
			  if(tds[7].equals("None")) {
				  tds[7]="";
			  }
			  sql.append("date_format(\'"+tds[0] + "\', \'%Y-%m-%d\'),").append("\'"+tds[0].replaceAll("-", "")+"数据\',").append("\'"+tds[3]+"\',").append(tds[4]+",").append(tds[5]+",").append(tds[6]+",").append("\'"+tds[7]+ "\'").append(");");
			  System.out.println(sql.toString());
			  
		  }

	}
	
	public static List<String[]> parse(String html) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
//        String[] content = new String[8];
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByTag("tr");
		for (int i = 0;i < elements.size(); i++) {
		  String td = elements.get(i).text();
		  System.out.println(""+td);
		  String[] tds =td.split(" ");
		  for(int t=0;t<tds.length;t++) {
			       System.out.print("td["+t+"]="+tds[t] + " ");
		  }
		  System.out.println();
		  list.add(tds);
		}
		return list;

	}

     public static void main(String[] args) throws Exception { 
//    	 receiveSSLMail();
//    	 jsoupDemo();
    	 test();
     }
}
