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
	
	public Page<XqYw> findPage(Page<XqYw> page, XqYw xqYw,String status, String startDate, String endDate) {
        if (StringUtils.isNotBlank(status)){
            xqYw.setDelFlag(status);
        }else{
            xqYw.setDelFlag(null);
        }
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            xqYw.setStartDate(startDate);
            xqYw.setEndDate(endDate);
        }
		return super.findPage(page, xqYw);
}
	
	@Transactional(readOnly = false)
	public void save(XqYw xqYw) {
		Date date = new Date();
		if(StringUtils.isBlank(xqYw.getXqId())){
			String dateStr = String.valueOf(date.getTime());
			String XqId = "XQ" + dateStr.substring(0, 11) + Math.round(Math.random()*10);
			xqYw.setXqId(XqId);
		}
		super.save(xqYw);
	}

	/*
	* 需求撤销，状态修改为撤销状态
	* */
	@Transactional(readOnly = false)
	public void delete(XqYw xqYw) {
		xqYw.preUpdate();
		xqYw.setDelFlag(Const.XQStatus.CANCEL);
		dao.update(xqYw);
	}

	/*
	* @param xqId
	* @param pdfFile  上传文件数据
	* @param suffix  上传文件后缀名
	* */
	@Transactional(readOnly = false)
	public String saveFjcl(String xqId, MultipartFile pdfFile){
		Date date = new Date();

		String name = IdGen.uuid();
		String imgName = pdfFile.getOriginalFilename();
		String suffix = imgName.substring(imgName.lastIndexOf(".") + 1,imgName.length());
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
		String dateStr = String.valueOf(date.getTime());
		String XqId = "WJ" + dateStr.substring(0, 11) + Math.round(Math.random() * 10);
		fjcl.setFjclId(XqId);
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