/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.service;

import java.util.List;

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
		super.save(xqLsjl);
	}
	
	@Transactional(readOnly = false)
	public void delete(XqLsjl xqLsjl) {
		super.delete(xqLsjl);
	}
	
}