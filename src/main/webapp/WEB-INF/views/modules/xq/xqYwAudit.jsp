<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>需求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

            $(document).ready(function() {
                //$("#name").focus();
				$("#xqSsxt").attr("readOnly",true);
				$("#xqXqly").attr("readOnly",true);
				$("#xqShr").attr("readOnly",true);
                $("#xqTitle").attr("readOnly",true);
                $("#xqXqms").attr("readOnly",true);


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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xq/xqYw/">需求列表</a></li>
		<li class="active"><a href="${ctx}/xq/xqYw/form?id=${xqYw.id}">需求<shiro:hasPermission name="xq:xqYw:edit">${not empty xqYw.xqId?'审核':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xq:xqYw:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<div ></div>
	<form:form id="inputForm" modelAttribute="xqYw"  action="#" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<form:hidden path="xqId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

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
				<form:input path="xqSsxt" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">需求来源：</label>
			<div class="controls">
				<form:input path="xqXqly" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">需求描述：</label>
			<div class="controls">
				<form:textarea path="xqXqms" htmlEscape="false" rows="4" maxlength="2047" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">需求细化：</label>
			<div class="controls">
				<form:textarea path="xqXqxh" htmlEscape="false" rows="4" maxlength="2047" class="input-xxlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
				<form:input path="xqShr" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">remarks：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="xq:xqYw:edit"><input id="passBtn" class="btn" type="button" value="同意" onclick="pass()"/></shiro:hasPermission>
			<shiro:hasPermission name="xq:xqYw:edit"><input id="noPassBtn" class="btn" type="button" value="不同意" onclick="noPass()"/></shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>


	<script>
        function pass() {
			document.getElementById("inputForm").action="${ctx}/xq/xqYw/save?action=access";
            $("#inputForm").submit();
        }
        function noPass(){
            document.getElementById("inputForm").action="${ctx}/xq/xqYw/save?action=deny";
            $("#inputForm").submit();
        }
	</script>
</body>
</html>

