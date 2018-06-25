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
                    loading('正在提交，请稍等...');
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

        });
        function deleteFile() {
            $("#uploadFile").val("");
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/xq/xqYw/">需求列表</a></li>
	<li class="active"><a href="${ctx}/xq/xqYw/add">需求<shiro:hasPermission name="xq:xqYw:edit">${not empty xqYw.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xq:xqYw:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="xqYw" action="${ctx}/xq/xqYw/save" method="post" class="form-horizontal" enctype="multipart/form-data">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">需求名称：</label>
		<div class="controls">
			<form:input path="xqTitle" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">所属系统：</label>
		<div class="controls">
			<form:select path="xqSsxt" htmlEscape="false"
						 maxlength="64" class="input-xlarge required" items="${systemLists}" style = "width:285px;"
			/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">需求来源：</label>
		<div class="controls">
            <form:select path="xqXqly" htmlEscape="false"
                         maxlength="64" class="input-xlarge required" items="${resourcesLists}" style = "width:285px;"
            />

			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">需求描述：</label>
		<div class="controls">
			<form:textarea path="xqXqms" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">附件材料：</label>
		<div class="controls">
			<input type="file" name="files" htmlEscape="false" maxlength="100" class="input-xxlarge" id="uploadFile"/>
			<a href="javascript:void(0);" onclick="deleteFile();">清空</a>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="xq:xqYw:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>