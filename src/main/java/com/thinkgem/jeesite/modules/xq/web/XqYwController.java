/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/*
	 *@Param  status  传进不同status  查看处于不同状态的需求   不传时返回所有状态需求
	 *
	 *@Param  only  only存在并为true时  返回本人的数据， 为空或false 时 返回所有用户的数据
	 */
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = {"list", ""})
	public String list(XqYw xqYw, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(value = "status",required = false)String status,@RequestParam(value = "only",required = false)Boolean only) {
		String userType = UserUtils.getUser().getUserType();
		if(only != null && only == true){
			xqYw.setCreateBy(UserUtils.getUser());
		}
		Page<XqYw> page = xqYwService.findPage(new Page<XqYw>(request, response), xqYw,status);
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
	public String save(XqYw xqYw, Model  model, RedirectAttributes redirectAttributes,@RequestParam(value="files",required = false) MultipartFile multipartFiles[],@RequestParam(value="action",required = false)String action) {
		if (!beanValidator(model, xqYw)){
			return form(xqYw, model);
		}
		if(StringUtils.isNotBlank(action)){
			if(StringUtils.equals(action,Const.SaveAction.ACCESS)){
					xqYw.setDelFlag(Const.XQStatus.PASS);
					xqYw.setXqShr(UserUtils.getUser().getName());
			}else if(StringUtils.equals(action,Const.SaveAction.DENY)){
				   xqYw.setDelFlag(Const.XQStatus.NO_PASS);
					xqYw.setXqShr(UserUtils.getUser().getName());
			}else if(StringUtils.equals(action,Const.SaveAction.EDIT)){
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
			xqLsjl.setLsjlJlzt(Const.LsjlZt.ADD);
		}else{
			if(StringUtils.isNotBlank(action)){
				//审核通过  需求细化为改动内容
				if(StringUtils.equals(action,Const.SaveAction.ACCESS)){
					xqLsjl.setLsjlJlzt(Const.LsjlZt.PASS);
					xqLsjl.setXqCznr(xqYw.getXqXqxh());
				//审核不通过
				}else if(StringUtils.equals(action,Const.SaveAction.DENY)){
					xqLsjl.setLsjlJlzt(Const.LsjlZt.NO_PASS);
					xqLsjl.setXqCznr(xqYw.getXqXqxh());
				}else if(StringUtils.equals(action,Const.SaveAction.EDIT)){
					xqLsjl.setLsjlJlzt(Const.LsjlZt.EDIT);
					//修改操作   需求描述为改动内容
					xqLsjl.setXqCznr(xqYw.getXqXqms());
				}

			}
		}
		xqYwService.save(xqYw);
		xqLsjl.setXqId(xqYw.getXqId());
		xqLsjlService.save(xqLsjl);
		addMessage(redirectAttributes, "保存需求成功");
		return "redirect:"+Global.getAdminPath()+"/xq/xqYw/?only=true&repage";
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
		return "modules/xq/xqYwAudit";
	}

}