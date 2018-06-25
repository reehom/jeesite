/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.xq.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.xq.entity.XqYw;
import com.thinkgem.jeesite.modules.xq.dao.XqYwDao;

/**
 * 需求业务表Service
 * @author ThinkGem
 * @version 2018-06-21
 */
@Service
@Transactional(readOnly = true)
public class XqYwService extends CrudService<XqYwDao, XqYw> {

	@Autowired
	private XqYwDao xqYwDao;

	public XqYw get(String id) {
		return super.get(id);
	}
	
	public List<XqYw> findList(XqYw xqYw) {
		return super.findList(xqYw);
	}
	
	public Page<XqYw> findPage(Page<XqYw> page, XqYw xqYw,String status) {

        if (StringUtils.isNotBlank(status)){
            xqYw.setDelFlag(status);
        }else{
            xqYw.setDelFlag(null);
        }
		return super.findPage(page, xqYw);
}
	
	@Transactional(readOnly = false)
	public void save(XqYw xqYw) {
		Date date = new Date();
		if(StringUtils.isBlank(xqYw.getXqId())){
			String XqId = "XQ"+DateTimeUtil.IdGenStr(date)+Math.round(Math.random()*100);
			xqYw.setXqId(XqId);
		}
		super.save(xqYw);
	}
	
	@Transactional(readOnly = false)
	public void delete(XqYw xqYw) {
		super.delete(xqYw);
	}
	
}