package com.mysoft.alpha.service;

import com.mysoft.alpha.vo.MailVO;

public interface MailService {
	public void receiveMail();
	public MailVO sendMail(MailVO mailVO);
	public MailVO sendStatMail();
}
