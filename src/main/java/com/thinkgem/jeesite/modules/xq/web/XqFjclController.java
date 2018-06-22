/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;

import com.thinkgem.jeesite.modules.xq.entity.XqFjcl;
import com.thinkgem.jeesite.modules.xq.service.XqFjclService;

/**
 * 附件材料表Controller
 * @author ThinkGem
 * @version 2018-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/xq/xqFjcl")
public class XqFjclController extends BaseController {

	@Autowired
	private XqFjclService xqFjclService;
	
	@ModelAttribute
	public XqFjcl get(@RequestParam(required=false) String id) {
		XqFjcl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = xqFjclService.get(id);
		}
		if (entity == null){
			entity = new XqFjcl();
		}
		return entity;
	}
	
	@RequiresPermissions("xq:xqFjcl:view")
	@RequestMapping(value = {"list", ""})
	public String list(XqFjcl xqFjcl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<XqFjcl> page = xqFjclService.findPage(new Page<XqFjcl>(request, response), xqFjcl); 
		model.addAttribute("page", page);
		return "modules/xq/xqFjclList";
	}

	@RequiresPermissions("xq:xqFjcl:view")
	@RequestMapping(value = "form")
	public String form(XqFjcl xqFjcl, Model model) {
		model.addAttribute("xqFjcl", xqFjcl);
		return "modules/xq/xqFjclForm";
	}

	@RequiresPermissions("xq:xqFjcl:edit")
	@RequestMapping(value = "save")
	public String save(XqFjcl xqFjcl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, xqFjcl)){
			return form(xqFjcl, model);
		}
		xqFjclService.save(xqFjcl);
		addMessage(redirectAttributes, "保存附件成功");
		return "redirect:"+Global.getAdminPath()+"/xq/xqFjcl/?repage";
	}
	
	@RequiresPermissions("xq:xqFjcl:edit")
	@RequestMapping(value = "delete")
	public String delete(XqFjcl xqFjcl, RedirectAttributes redirectAttributes) {
		xqFjclService.delete(xqFjcl);
		addMessage(redirectAttributes, "删除附件成功");
		return "redirect:"+Global.getAdminPath()+"/xq/xqFjcl/?repage";
	}

	@RequestMapping(value = "success")
	public String success(XqFjcl xqFjcl, Model model) {

		return "modules/xq/commitSuccess";
	}

}