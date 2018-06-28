<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>需求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $("#addFiles").click(function () {
                var html = "<div class=\"control-group\">\n" +
                    "<label class=\"control-label\"></label>\n" +
                    "<div class=\"controls\">\n" +
                    "<input type=\"file\" name=\"files\"  htmlEscape=\"false\" maxlength=\"100\" class=\"upload input-xxlarge\" id=\"uploadFile\"/>\n" +
                    "</div>\n" +
                    "</div>";

                $("#div").after(html);
            });

            $("#resetFiles").click(function () {
                $(".upload").val("");
            });
        });

	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/xq/xqYw/list?only=true">需求列表</a></li>
	<li class="active"><a href="${ctx}/xq/xqYw/add">需求<shiro:hasPermission name="xq:xqYw:edit">${not empty xqYw.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xq:xqYw:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="xqYw" action="${ctx}/xq/xqYw/save?action=add" method="post" class="form-horizontal" enctype="multipart/form-data">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">需求名称：</label>
		<div class="controls">
			<form:input path="xqTitle" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			<span class="help-inline" style="color: red">*</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">所属系统：</label>
		<div class="controls">
			<form:select path="xqSsxt" htmlEscape="false" maxlength="64" class="input-xlarge required" items="${systemLists}" style = "width:285px;"/>
			<span class="help-inline" style="color: red">*</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">需求来源：</label>
		<div class="controls">
            <form:select path="xqXqly" htmlEscape="false"
                         maxlength="64" class="input-xlarge required" items="${resourcesLists}" style = "width:285px;"/>
			<span class="help-inline" style="color: red">*</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">需求描述：</label>
		<div class="controls">
			<form:textarea path="xqXqms" htmlEscape="false" rows="5" style="width:500px;" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">备注：</label>
		<div class="controls">
			<form:textarea path="remarks" htmlEscape="false" rows="3" style="width:500px;" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group" id="div">
		<label class="control-label">附件材料：</label>
		<div class="controls">
			<button id="addFiles" type="button"  class="btn btn-white">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="resetFiles" type="button"  class="btn btn-white">全部清空</button>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="xq:xqYw:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>