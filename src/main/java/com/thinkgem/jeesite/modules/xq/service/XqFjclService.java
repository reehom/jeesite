/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.xq.entity.XqFjcl;
import com.thinkgem.jeesite.modules.xq.dao.XqFjclDao;

/**
 * 附件材料表Service
 * @author ThinkGem
 * @version 2018-06-21
 */
@Service
@Transactional(readOnly = true)
public class XqFjclService extends CrudService<XqFjclDao, XqFjcl> {

	public XqFjcl get(String id) {
		return super.get(id);
	}
	
	public List<XqFjcl> findList(XqFjcl xqFjcl) {
		return super.findList(xqFjcl);
	}
	
	public Page<XqFjcl> findPage(Page<XqFjcl> page, XqFjcl xqFjcl) {
		return super.findPage(page, xqFjcl);
	}
	
	@Transactional(readOnly = false)
	public void save(XqFjcl xqFjcl) {
		super.save(xqFjcl);
	}
	
	@Transactional(readOnly = false)
	public void delete(XqFjcl xqFjcl) {
		super.delete(xqFjcl);
	}
	
}