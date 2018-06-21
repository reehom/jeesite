/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 附件材料表Entity
 * @author ThinkGem
 * @version 2018-06-21
 */
public class XqFjcl extends DataEntity<XqFjcl> {
	
	private static final long serialVersionUID = 1L;
	private String fjclId;		// fjcl_id
	private String xqId;		// xq_id
	private String fjclCode;		// 编号
	private String fjclName;		// 名称
	private String fjclExt;		// 拓展名
	private String fjclUrl;		// fjcl_url
	private String fjclSize;		// fjcl_size
	
	public XqFjcl() {
		super();
	}

	public XqFjcl(String id){
		super(id);
	}

	@Length(min=1, max=11, message="fjcl_id长度必须介于 1 和 11 之间")
	public String getFjclId() {
		return fjclId;
	}

	public void setFjclId(String fjclId) {
		this.fjclId = fjclId;
	}
	
	@Length(min=1, max=64, message="xq_id长度必须介于 1 和 64 之间")
	public String getXqId() {
		return xqId;
	}

	public void setXqId(String xqId) {
		this.xqId = xqId;
	}
	
	@Length(min=1, max=255, message="编号长度必须介于 1 和 255 之间")
	public String getFjclCode() {
		return fjclCode;
	}

	public void setFjclCode(String fjclCode) {
		this.fjclCode = fjclCode;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getFjclName() {
		return fjclName;
	}

	public void setFjclName(String fjclName) {
		this.fjclName = fjclName;
	}
	
	@Length(min=0, max=127, message="拓展名长度必须介于 0 和 127 之间")
	public String getFjclExt() {
		return fjclExt;
	}

	public void setFjclExt(String fjclExt) {
		this.fjclExt = fjclExt;
	}
	
	@Length(min=0, max=255, message="fjcl_url长度必须介于 0 和 255 之间")
	public String getFjclUrl() {
		return fjclUrl;
	}

	public void setFjclUrl(String fjclUrl) {
		this.fjclUrl = fjclUrl;
	}
	
	@Length(min=0, max=11, message="fjcl_size长度必须介于 0 和 11 之间")
	public String getFjclSize() {
		return fjclSize;
	}

	public void setFjclSize(String fjclSize) {
		this.fjclSize = fjclSize;
	}
	
}