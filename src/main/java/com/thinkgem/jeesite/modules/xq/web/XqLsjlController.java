/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.xq.common.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;

import com.thinkgem.jeesite.modules.xq.entity.XqLsjl;
import com.thinkgem.jeesite.modules.xq.service.XqLsjlService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 需求业务表Controller
 * @author ThinkGem
 * @version 2018-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/xq/xqLsjl")
public class XqLsjlController extends BaseController {

	@Autowired
	private XqLsjlService xqLsjlService;
	
	@ModelAttribute
	public XqLsjl get(@RequestParam(required=false) String id) {
		XqLsjl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = xqLsjlService.get(id);
		}
		if (entity == null){
			entity = new XqLsjl();
		}
		return entity;
	}
	
	@RequiresPermissions("xq:xqLsjl:view")
	@RequestMapping(value = {"list", ""})
	public String list(XqLsjl xqLsjl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<XqLsjl> page = xqLsjlService.findPage(new Page<XqLsjl>(request, response), xqLsjl); 
		model.addAttribute("page", page);
		return "modules/xq/xqLsjlList";
	}

	@RequiresPermissions("xq:xqLsjl:view")
	@RequestMapping(value = "form")
	public String form(XqLsjl xqLsjl, Model model) {
		model.addAttribute("xqLsjl", xqLsjl);
		return "modules/xq/xqLsjlForm";
	}

	@RequiresPermissions("xq:xqLsjl:edit")
	@RequestMapping(value = "save")
	public String save(XqLsjl xqLsjl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, xqLsjl)){
			return form(xqLsjl, model);
		}
		xqLsjlService.save(xqLsjl);
		addMessage(redirectAttributes, "保存需求成功");
		return "redirect:"+Global.getAdminPath()+"/xq/xqLsjl/?repage";
	}
	
	@RequiresPermissions("xq:xqLsjl:edit")
	@RequestMapping(value = "delete")
	public String delete(XqLsjl xqLsjl, RedirectAttributes redirectAttributes) {
		xqLsjlService.delete(xqLsjl);
		addMessage(redirectAttributes, "删除需求成功");
		return "redirect:"+Global.getAdminPath()+"/xq/xqLsjl/?repage";
	}

	@RequestMapping(value = "get")
	@ResponseBody
	public ServerResponse<XqLsjl> getLsjl(XqLsjl xqLsjl, String id){
		if(xqLsjl==null){
			return ServerResponse.createByErrorMessage("找不到该记录");
		}

		return ServerResponse.createBySuccess(xqLsjl);



	}


}