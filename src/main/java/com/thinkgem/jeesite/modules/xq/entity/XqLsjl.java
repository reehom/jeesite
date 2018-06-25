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
public class XqLsjl extends DataEntity<XqLsjl> {
	
	private static final long serialVersionUID = 1L;
	private String lsjlId;		// lsjl_id
	private String lsjlJlzt;		// 记录状态
	private String xqCznr;		// 操作内容
	private String xqId;		// xq_id
	
	public XqLsjl() {
		super();
	}

	public XqLsjl(String id){
		super(id);
	}

	@Length(min=1, max=64, message="lsjl_id长度必须介于 1 和 64 之间")
	public String getLsjlId() {
		return lsjlId;
	}

	public void setLsjlId(String lsjlId) {
		this.lsjlId = lsjlId;
	}
	
	@Length(min=1, max=255, message="记录状态长度必须介于 1 和 255 之间")
	public String getLsjlJlzt() {
		return lsjlJlzt;
	}

	public void setLsjlJlzt(String lsjlJlzt) {
		this.lsjlJlzt = lsjlJlzt;
	}
	
	@Length(min=0, max=2047, message="操作内容长度必须介于 0 和 2047 之间")
	public String getXqCznr() {
		return xqCznr;
	}

	public void setXqCznr(String xqCznr) {
		this.xqCznr = xqCznr;
	}
	
	@Length(min=1, max=64, message="xq_id长度必须介于 1 和 64 之间")
	public String getXqId() {
		return xqId;
	}

	public void setXqId(String xqId) {
		this.xqId = xqId;
	}
	
}