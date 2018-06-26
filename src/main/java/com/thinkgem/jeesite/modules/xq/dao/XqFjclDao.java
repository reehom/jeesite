/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.xq.entity.XqFjcl;

/**
 * 附件材料表DAO接口
 * @author ThinkGem
 * @version 2018-06-21
 */
@MyBatisDao
public interface XqFjclDao extends CrudDao<XqFjcl> {

    void saveData(XqFjcl xqFjcl);
	
}