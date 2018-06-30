/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.xq.common.Const;
import com.thinkgem.jeesite.modules.xq.entity.XqFjcl;
import com.thinkgem.jeesite.modules.xq.entity.XqLsjl;
import com.thinkgem.jeesite.modules.xq.service.XqFjclService;
import com.thinkgem.jeesite.modules.xq.service.XqLsjlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.xq.entity.XqYw;
import com.thinkgem.jeesite.modules.xq.service.XqYwService;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * 需求业务表Controller
 * @version 2018-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/xq/xqYw")
public class XqYwController extends BaseController {

	@Autowired
	private XqYwService xqYwService;

	@Autowired
	private XqLsjlService xqLsjlService;

	@Autowired
	private XqFjclService xqFjclService;

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
	 */
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = {"list", ""})
	public String list(XqYw xqYw, HttpServletRequest request, HttpServletResponse response, Model model,
					   @RequestParam(value = "status",required = false)String status) {

		//判断当前用户，如是管理员则显示全部
		if(Const.ADMINID.equals(UserUtils.getUser().getId())){
			//判断status，是否为我的需求菜单，是则显示我的需求列表
			if(Const.Status.NO_FINISH.equals(status) || Const.Status.FINISH.equals(status) || Const.Status.MYALL.equals(status)){
				xqYw.setCreateBy(UserUtils.getUser());
			}
		}else{
			xqYw.setCreateBy(UserUtils.getUser());
		}

		//判断status，是否为需求审核菜单，是则返回audit到前端
		if(Const.Status.NO_DONE.equals(status) || Const.Status.DONE.equals(status) || Const.Status.ALL.equals(status)){
			model.addAttribute("audit", Const.SUCCESS);
		}

		xqYw.setDelFlag(status);

		//日期范围选择
		String strDate = request.getParameter("strDate");
		String startDate = "";
		String endDate = "";
		if(StringUtils.isNotBlank(strDate)){
			String[] str = strDate.split("~");
			startDate = str[0].trim();
			endDate = str[1].trim();
		}

		Page<XqYw> page = xqYwService.findPage(new Page<XqYw>(request, response), xqYw, status, startDate, endDate);
		model.addAttribute("page", page);
		model.addAttribute("strDate", strDate);
		return "modules/xq/xqYwList";
	}

	/*
	 *需求添加页面
	 */
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "addPage")
	public String addPage(Model model){
		model.addAttribute("systemLists",Const.SystemLists.systemLists);
		model.addAttribute("resourcesLists",Const.XQResource.resourcesLists);
		return "modules/xq/xqYwAdd";
	}

	/*
	 *保存需求
	 */
	@RequiresPermissions("xq:xqYw:edit")
	@RequestMapping(value = "save")
	public String save(XqYw xqYw, Model  model,  @RequestParam(value="action",required = false)String action,
					   @RequestParam(value="files",required = false) MultipartFile multipartFiles[]) {

		//判断上传文件个数是否大于0
		Integer fileLen = multipartFiles.length;
		if(fileLen > 0){
			for(MultipartFile mul : multipartFiles){
				if(mul.getOriginalFilename() !=null && !"".equals(mul.getOriginalFilename())){
					if(mul.getSize() > 5242880){
						model.addAttribute("systemLists",Const.SystemLists.systemLists);
						model.addAttribute("resourcesLists",Const.XQResource.resourcesLists);
						model.addAttribute("message","上传文件不可大于5m");
						return "modules/xq/xqYwAdd";
					}
				}
			}
		}

		saveYw(xqYw, model, action);

		//添加附件材料数据到数据库
		if(fileLen > 0){
			for(MultipartFile mul : multipartFiles) {
				if(mul.getOriginalFilename() !=null && !"".equals(mul.getOriginalFilename())){
					xqYwService.saveFjcl(xqYw.getXqId(), mul);
				}
			}
		}
		/*if(!Const.SUCCESS.equals(saveYw(xqYw, model, action))){
			flag = false;
		}

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
		}*/

		model.addAttribute("xqId",xqYw.getXqId());
		return "modules/xq/commitSuccess";
	}


	@RequiresPermissions("xq:xqYw:edit")
	public String saveYw(XqYw xqYw, Model  model, String action){

		//TODO: ？？
		if (!beanValidator(model, xqYw)){
			return updatePage(xqYw, model);
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
					xqLsjl.setXqCznr(xqYw.getXqXqms());
				}
			}
		}
		xqYwService.save(xqYw);
		xqLsjl.setXqId(xqYw.getXqId());
		xqLsjlService.save(xqLsjl);
		return Const.SUCCESS;
	}

	/*
	 *需求撤销
	 * */
	@RequiresPermissions("xq:xqYw:edit")
	@RequestMapping(value = "delete")
	public String delete(XqYw xqYw, RedirectAttributes redirectAttributes) {

		//判断该流程是否为待审核
		if(Const.XQStatus.TO_BE_AUDITED.equals(xqYw.getDelFlag())){
			xqYwService.delete(xqYw);
			XqLsjl xqLsjl = new XqLsjl();
			xqLsjl.setLsjlJlzt(Const.LsjlZt.DELETE);
			xqLsjl.setXqCznr("撤销需求");
			xqLsjl.setXqId(xqYw.getXqId());
			xqLsjlService.save(xqLsjl);
			addMessage(redirectAttributes, "撤销成功");
		}else{
			addMessage(redirectAttributes, "撤销失败");
		}
		return "redirect:"+Global.getAdminPath()+"/xq/xqYw/?repage";
	}

	/*
	 * 跳转审核页面
	 * */
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "auditPage")
	public String auditPage(XqYw xqYw, Model model) {
		model.addAttribute("xqYw", xqYw);
		List<XqLsjl> recordLists= xqLsjlService.findRecordList(xqYw.getXqId());
		model.addAttribute("recordLists",recordLists);
		List<XqFjcl> fjcl= xqFjclService.findFjclbyXqywId(xqYw.getXqId());
		model.addAttribute("fjcl", fjcl);
		return "modules/xq/xqYwAudit";
	}

	/*
	 * 需求审核保存
	 * */
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "auditSave")
	public String auditSave(XqYw xqYw, Model  model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		//从前端获取action的值，判断该动作为审核通过或不通过
		String action = "";
		String passBtn = request.getParameter("passBtn");
		String noPassBtn = request.getParameter("noPassBtn");
		if(StringUtils.isNotBlank(passBtn)) {
			action = passBtn;
		}
		if(StringUtils.isNotBlank(noPassBtn)) {
			action = noPassBtn;
		}

		if(StringUtils.isNotBlank(action)) {
			if (StringUtils.equals(action, Const.SaveAction.ACCESS)) {
				addMessage(redirectAttributes, "审核通过");
				xqYw.setDelFlag(Const.XQStatus.PASS);
			} else if (StringUtils.equals(action, Const.SaveAction.DENY)) {
				xqYw.setDelFlag(Const.XQStatus.NO_PASS);
				addMessage(redirectAttributes, "审核不通过");
			}
		}
		saveYw(xqYw, model, action);
		return "redirect:"+Global.getAdminPath()+"/xq/xqYw";
	}



	/*
	 * 文件下载
	 * */
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "fileDown")
	public void fileDown(@RequestParam(required=false) String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//得到要下载的文件名
		XqFjcl fjcl = xqFjclService.get(id);

		String fileName = fjcl.getFjclName();
		String fileUrl = fjcl.getFjclUrl();
		String fileLocalPath = Global.getUserfilesBaseDir() + fileUrl;

		//得到要下载的文件
		File file = new File(fileLocalPath);
		//如果文件不存在
		if(!file.exists()){
			request.setAttribute("message", "您要下载的资源不存在！！");
			return;
		}
		//处理文件名
		String realname = fileName.substring(fileName.indexOf("_")+1);
		//设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(fileLocalPath);
		//创建输出流
		OutputStream out = response.getOutputStream();
		//创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		//循环将输入流中的内容读取到缓冲区当中
		while((len=in.read(buffer))>0){
			//输出缓冲区的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
		}
		//关闭文件输入流
		in.close();
		//关闭输出流
		out.close();
	}

	/*
	* 跳转详情页
	* */
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "infoPage")
	public String infoPage(XqYw xqYw, Model model) {
		model.addAttribute("xqYw", xqYw);
		List<XqLsjl> recordLists= xqLsjlService.findRecordList(xqYw.getXqId());
		model.addAttribute("recordLists",recordLists);
		List<XqFjcl> fjcl= xqFjclService.findFjclbyXqywId(xqYw.getXqId());
		model.addAttribute("fjcl", fjcl);
		return "modules/xq/xqYwInfo";
	}


	/*
	 *需求修改页面
	 */
	@RequiresPermissions("xq:xqYw:view")
	@RequestMapping(value = "updatePage")
	public String updatePage(XqYw xqYw, Model model) {
		model.addAttribute("xqYw", xqYw);
		List<XqLsjl> recordLists= xqLsjlService.findRecordList(xqYw.getXqId());
		model.addAttribute("recordLists",recordLists);
		List<XqFjcl> fjcl= xqFjclService.findFjclbyXqywId(xqYw.getXqId());
		model.addAttribute("fjcl", fjcl);
		return "modules/xq/xqYwUpdate";
	}


	/*
	* 需求修改
	* */
	@RequiresPermissions("xq:xqYw:edit")
	@RequestMapping(value = "update")
	public String update(XqYw xqYw, Model  model, HttpServletRequest request, RedirectAttributes redirectAttributes,
						 @RequestParam(value="files",required = false) MultipartFile multipartFiles[],@RequestParam(value="action",required = false)String action) {

		//判断该流程是否为待审核
		if(!Const.XQStatus.TO_BE_AUDITED.equals(xqYw.getDelFlag())){
			addMessage(redirectAttributes, "修改失败");
			return "redirect:"+Global.getAdminPath()+"/xq/xqYw/?repage";
		}

		//判断上传文件个数是否大于0
		Integer fileLen = multipartFiles.length;
		if(fileLen > 0){
			for(MultipartFile mul : multipartFiles){
				if(mul.getOriginalFilename() !=null && !"".equals(mul.getOriginalFilename())){
					if(mul.getSize() > 5242880){
						model.addAttribute("xqYw", xqYw);
						List<XqLsjl> recordLists= xqLsjlService.findRecordList(xqYw.getXqId());
						model.addAttribute("recordLists",recordLists);
						List<XqFjcl> fjcl= xqFjclService.findFjclbyXqywId(xqYw.getXqId());
						model.addAttribute("fjcl", fjcl);
						model.addAttribute("message", "上传文件不可大于5m");
						return "modules/xq/xqYwUpdate";
					}
				}
			}
		}

		saveYw(xqYw, model, action);

		if(fileLen > 0){
			for(MultipartFile mul : multipartFiles) {
				if( !"".equals(mul.getOriginalFilename()) && mul.getOriginalFilename() !=null ){
					xqYwService.saveFjcl(xqYw.getXqId(), mul);
				}
			}
		}

		addMessage(redirectAttributes, "修改成功");
		return "redirect:"+Global.getAdminPath()+"/xq/xqYw";
	}

	/*
	* 需求开始开发与开发完成流程动作
	* */
	@RequiresPermissions("xq:xqYw:edit")
	@RequestMapping(value = "deal")
	public String deal(RedirectAttributes redirectAttributes, @RequestParam(value="id",required = false)String id) {
		XqYw xqYw = xqYwService.get(id);
		xqYw.setId(id);

		//判断当前流程状态，如果为审核通过，则进入开始开发
		if(Const.XQStatus.PASS.equals(xqYw.getDelFlag())){
			xqYw.setDelFlag(Const.XQStatus.CODING);
			xqYwService.save(xqYw);

			XqLsjl xqLsjl = new XqLsjl();
			xqLsjl.setLsjlJlzt(Const.LsjlZt.DEALING);
			xqLsjl.setXqCznr("正在开发");
			xqLsjl.setXqId(xqYw.getXqId());
			xqLsjlService.save(xqLsjl);
			addMessage(redirectAttributes, "操作成功");

			//判断当前流程状态，如果为开发中，则进入开发完成。
		}else if(Const.XQStatus.CODING.equals(xqYw.getDelFlag())){
			xqYw.setDelFlag(Const.XQStatus.FINISH);
			xqYwService.save(xqYw);

			XqLsjl xqLsjl = new XqLsjl();
			xqLsjl.setLsjlJlzt(Const.LsjlZt.DEALT);
			xqLsjl.setXqCznr("开发完成");
			xqLsjl.setXqId(xqYw.getXqId());
			xqLsjlService.save(xqLsjl);
			addMessage(redirectAttributes, "操作成功");

		}else{
			addMessage(redirectAttributes, "操作失败");
		}
		return "redirect:"+Global.getAdminPath()+"/xq/xqYw/?repage";
	}


}