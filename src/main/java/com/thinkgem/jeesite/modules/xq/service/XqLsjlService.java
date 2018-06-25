/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.xq.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.xq.entity.XqLsjl;
import com.thinkgem.jeesite.modules.xq.dao.XqLsjlDao;

/**
 * 需求业务表Service
 * @author ThinkGem
 * @version 2018-06-21
 */
@Service
@Transactional(readOnly = true)
public class XqLsjlService extends CrudService<XqLsjlDao, XqLsjl> {

	@Autowired
	private XqLsjlDao xqLsjlDao;

	public XqLsjl get(String id) {
		return super.get(id);
	}
	
	public List<XqLsjl> findList(XqLsjl xqLsjl) {
		return super.findList(xqLsjl);
	}
	
	public Page<XqLsjl> findPage(Page<XqLsjl> page, XqLsjl xqLsjl) {
		return super.findPage(page, xqLsjl);
	}
	
	@Transactional(readOnly = false)
	public void save(XqLsjl xqLsjl) {
		Date date = new Date();
		String id = "JL"+DateTimeUtil.IdGenStr(date)+Math.round(Math.random()*100);
		xqLsjl.setLsjlId(id);
		super.save(xqLsjl);
	}
	
	@Transactional(readOnly = false)
	public void delete(XqLsjl xqLsjl) {
		super.delete(xqLsjl);
	}

	public List<XqLsjl> findRecordList(String xqId){
		return xqLsjlDao.findRecordList(xqId);
	}
	
}