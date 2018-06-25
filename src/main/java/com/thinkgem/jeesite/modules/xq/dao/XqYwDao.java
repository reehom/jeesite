/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.xq.entity.XqYw;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * 需求业务表DAO接口
 * @author ThinkGem
 * @version 2018-06-21
 */
@MyBatisDao
public interface XqYwDao extends CrudDao<XqYw> {



//    public List<XqYw> findListByStatus(@Param("XqYw") XqYw entity, @Param("status") String status, @Param("page")Page<XqYw> page);
}