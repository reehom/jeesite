/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 需求业务表Entity
 * @author ThinkGem
 * @version 2018-06-21
 */
public class XqYw extends DataEntity<XqYw> {
	
	private static final long serialVersionUID = 1L;
	private String xqId;		// xq_id
	private String xqTitle;		// xq_title
	private String xqSsxt;		// 所属系统
	private String xqXqly;		// 需求来源
	private String xqXqms;		// 需求描述
	private String xqXqxh;		// 需求细化
	private String xqShr;		// 审核人
	private String startDate;		// 开始时间
	private String endDate;		// 结束时间
	
	public XqYw() {
		super();
	}

	public XqYw(String id){
		super(id);
	}

	@Length(min=1, max=64, message="xq_id长度必须介于 1 和 64 之间")
	public String getXqId() {
		return xqId;
	}

	public void setXqId(String xqId) {
		this.xqId = xqId;
	}
	
	@Length(min=1, max=255, message="xq_title长度必须介于 1 和 255 之间")
	public String getXqTitle() {
		return xqTitle;
	}

	public void setXqTitle(String xqTitle) {
		this.xqTitle = xqTitle;
	}
	
	@Length(min=1, max=64, message="所属系统长度必须介于 1 和 64 之间")
	public String getXqSsxt() {
		return xqSsxt;
	}

	public void setXqSsxt(String xqSsxt) {
		this.xqSsxt = xqSsxt;
	}
	
	@Length(min=1, max=255, message="需求来源长度必须介于 1 和 255 之间")
	public String getXqXqly() {
		return xqXqly;
	}

	public void setXqXqly(String xqXqly) {
		this.xqXqly = xqXqly;
	}
	
	@Length(min=0, max=2047, message="需求描述长度必须介于 0 和 2047 之间")
	public String getXqXqms() {
		return xqXqms;
	}

	public void setXqXqms(String xqXqms) {
		this.xqXqms = xqXqms;
	}
	
	@Length(min=0, max=2047, message="需求细255化长度必须介于 0 和 255 之间")
	public String getXqXqxh() {
		return xqXqxh;
	}

	public void setXqXqxh(String xqXqxh) {
		this.xqXqxh = xqXqxh;
	}
	
	@Length(min=0, max=64, message="审核人长度必须介于 0 和 64 之间")
	public String getXqShr() {
		return xqShr;
	}

	public void setXqShr(String xqShr) {
		this.xqShr = xqShr;
	}


	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}