package com.mysoft.alpha.common;

import java.io.Serializable;

public enum CustomStatus implements Serializable {
	STATUS_1(-1,"未知"),//客户-采购方
	STATUS1(1,"已触发待申请"),//客户-采购方
	STATUS2(2,"重新触发待申请"),//客户-采购方
	STATUS3(3,"待审核"),//客户或采购方-服务方
	STATUS_3(-3,"申请驳回"),//客户或采购方-服务方
	STATUS4(4,"修改待审核"),//客户或采购方-服务方
	STATUS5(5,"审核通过可付费"),//服务方-客户或采购方
	STATUS_5(-5,"审核驳回"),//服务方-客户或采购方
	STATUS6(6,"付费完成待收款"),//客户或采购方-服务方
	STATUS7(7,"确认收款服务中"),//服务方-客户或采购方
	STATUS_7(-7,"付款驳回"),//服务方-客户或采购方
	STATUS8(8,"服务完成"),//服务方-客户或采购方
	STATUS9(9,"服务完成且评价");//服务方-客户或采购方
	
	private final int value;

	private final String reasonPhrase;

	CustomStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}
	
	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}

	public int getValue() {
		return value;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}
}
