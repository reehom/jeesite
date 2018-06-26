/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.xq.common.Const;
import com.thinkgem.jeesite.modules.xq.entity.XqLsjl;
import com.thinkgem.jeesite.modules.xq.service.XqLsjlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.xq.entity.XqYw;
import com.thinkgem.jeesite.modules.xq.service.XqYwService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需求业务表Controller
 * @author ThinkGem
 * @version 2018-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/xq/xqYw")
public class XqYwController extends BaseController {

	@Autowired
	private XqYwService xqYwService;

	@Autowired
	private XqLsjlService xqLsjlService;

	@ModelAttribute
	public XqYw get(@RequestParam(required=false) String id) {
		XqYw entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = xqYwService.get(id);
		}
		if (entity == null){
			entity = new XqYw();
		}
		return entity;
	}
	
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = {"list", ""})
	public String list(XqYw xqYw, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(value = "status",required = false)String status) {
		Page<XqYw> page = xqYwService.findPage(new Page<XqYw>(request, response), xqYw,status);
		String userType = UserUtils.getUser().getUserType();
		model.addAttribute("userType",userType);
		model.addAttribute("page", page);
		return "modules/xq/xqYwList";
	}

	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "form")
	public String form(XqYw xqYw, Model model) {
		model.addAttribute("systemLists",Const.SystemLists.systemLists);
		model.addAttribute("resourcesLists",Const.XQResource.resourcesLists);
		model.addAttribute("xqYw", xqYw);
		List<XqLsjl> recordLists= xqLsjlService.findRecordList(xqYw.getXqId());
		model.addAttribute("recordLists",recordLists);
		return "modules/xq/xqYwForm";
	}

	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "add")
	public String form(Model model) {
		model.addAttribute("systemLists",Const.SystemLists.systemLists);
		model.addAttribute("resourcesLists",Const.XQResource.resourcesLists);
		return "modules/xq/xqYwAdd";
	}

	@RequiresPermissions("xq:xqYw:edit")
	@RequestMapping(value = "save")
	public String save(XqYw xqYw, Model  model, HttpServletRequest request, RedirectAttributes redirectAttributes,@RequestParam(value="files",required = false) MultipartFile multipartFiles[],@RequestParam(value="action",required = false)String action) {
		model.addAttribute("systemLists",Const.SystemLists.systemLists);
		model.addAttribute("resourcesLists",Const.XQResource.resourcesLists);

		//转型为MultipartHttpRequest
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
		//获得文件
		MultipartFile pdfFile  =  multipartRequest.getFile("files");
		if(pdfFile.getOriginalFilename() ==null || "".equals(pdfFile.getOriginalFilename())){
			model.addAttribute("message","上传文件不存在");
			return "modules/xq/xqYwAdd";
		}

		String imgName = pdfFile.getOriginalFilename();
		String suffix = imgName.substring(imgName.lastIndexOf(".")+1,imgName.length());
		if(!"pdf".equals(suffix)){
			model.addAttribute("message","上传格式不正确,仅限pdf文件");
			return "modules/xq/xqYwAdd";
		}

		if(pdfFile.getSize() > 5242880){
			model.addAttribute("message","上传文件不可大于5m");
			return "modules/xq/xqYwAdd";
		}



		if (!beanValidator(model, xqYw)){
			return form(xqYw, model);
		}
		if(StringUtils.isNotBlank(action)){
			if(StringUtils.equals(action,Const.XQStatus.ACCESS)){
					xqYw.setDelFlag(Const.XQStatus.PASS);
					xqYw.setXqShr(UserUtils.getUser().getName());
			}else if(StringUtils.equals(action,Const.XQStatus.DENY)){
				   xqYw.setDelFlag(Const.XQStatus.NO_PASS);
					xqYw.setXqShr(UserUtils.getUser().getName());
			}else if(StringUtils.equals(action,Const.XQStatus.EDIT)){
					xqYw.setDelFlag(Const.XQStatus.TO_BE_AUDITED);
			}

		}

		/*
		* 生成操作历史记录
		* xqYw  id 为空时为建立操作
		*  action = acess  为审核通过
		*  action = deny 为 审核不通过
		*  action = edit 为修改操作
		* */
		XqLsjl xqLsjl = new XqLsjl();

		if(StringUtils.isBlank(xqYw.getXqId())){

			xqLsjl.setXqCznr(xqYw.getXqXqms());
			xqLsjl.setLsjlJlzt("0");
		}else{
			if(StringUtils.isNotBlank(action)){
				//审核通过
				if(StringUtils.equals(action,Const.XQStatus.ACCESS)){
					xqLsjl.setLsjlJlzt("1");
				//审核不通过
				}else if(StringUtils.equals(action,Const.XQStatus.DENY)){
					xqLsjl.setLsjlJlzt("2");
				}
				//审核时 需求细化为改动内容
				xqLsjl.setXqCznr(xqYw.getXqXqxh());
			}else if(StringUtils.equals(action,Const.XQStatus.EDIT)){
				xqLsjl.setLsjlJlzt("3");
				//修改操作   需求描述为改动内容
				xqLsjl.setXqCznr(xqYw.getXqXqms());
			}
		}
		xqYwService.save(xqYw);
		xqLsjl.setXqId(xqYw.getXqId());
		xqLsjlService.save(xqLsjl);
		addMessage(redirectAttributes, "保存需求成功");

		String msg = xqYwService.saveFjcl(xqYw.getXqId(), pdfFile, suffix);
		model.addAttribute("message",msg);

		return "redirect:"+Global.getAdminPath()+"/xq/xqYw/?repage";
	}
	
	@RequiresPermissions("xq:xqYw:edit")
	@RequestMapping(value = "delete")
	public String delete(XqYw xqYw, RedirectAttributes redirectAttributes) {
		xqYwService.delete(xqYw);
		addMessage(redirectAttributes, "删除需求成功");
		return "redirect:"+Global.getAdminPath()+"/xq/xqYw/?repage";
	}

	//跳转审核页面
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "audit")
	public String audit(XqYw xqYw,Model model) {
		model.addAttribute("xqYw", xqYw);
		model.addAttribute("xqYw", xqYw);
		return "modules/xq/xqYwAudit";
	}

}