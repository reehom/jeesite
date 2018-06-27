/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.xq.common.Const;
import com.thinkgem.jeesite.modules.xq.entity.XqFjcl;
import com.thinkgem.jeesite.modules.xq.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.xq.entity.XqYw;
import com.thinkgem.jeesite.modules.xq.dao.XqYwDao;
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
	private XqFjclService service;

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

	@Transactional(readOnly = false)
	public String saveFjcl(String xqId, MultipartFile pdfFile, String suffix){
		Date date = new Date();

		String name = IdGen.uuid();
		//客户端访问路径
		String filePath = Servlets.getRequest().getContextPath() +
				Global.USERFILES_BASE_URL + "docFile" + "/" + name +"." + suffix;
		//本地保存路径
		String fileLocalPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + "docFile" + "/" + name +"."+suffix;
		//创建目录
		File saveDirFile = new File(fileLocalPath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		try {
			pdfFile.transferTo(saveDirFile);
		} catch (Exception e) {
			e.printStackTrace();
			return "上传失败！！！";
		}

		XqFjcl fjcl = new XqFjcl();
		fjcl.setFjclId("WJ"+DateTimeUtil.IdGenStr(date)+Math.round(Math.random()*100));
		fjcl.setXqId(xqId);
		fjcl.setFjclCode(xqId);
		fjcl.setFjclUrl(filePath);
		fjcl.setFjclSize(String.valueOf(pdfFile.getSize()));
		fjcl.setFjclName(pdfFile.getOriginalFilename());
		fjcl.setDelFlag("0");
		service.saveData(fjcl);

		return Const.SUCCESS;
	}

}