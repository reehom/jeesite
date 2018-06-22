<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>需求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
             $("#resetSubmit").click(function () {
				 $("#xqId").val("");
				 $("#xqTitle").val("");
				 $("#createDate").val("");
				 $("#cDate").val("");
				 $("#btnSubmit").click();
             });
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/xq/xqYw/">需求列表</a></li>
	<shiro:hasPermission name="xq:xqYw:edit"><li><a href="${ctx}/xq/xqYw/add">需求添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="xqYw" action="${ctx}/xq/xqYw/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>需求编号：</label>
			<form:input id="xqId" path="xqId" htmlEscape="false" maxlength="64" class="input-medium"/>
		</li>
		<li><label>需求名称：</label>
			<form:input id="xqTitle" path="xqTitle" htmlEscape="false" maxlength="64" class="input-medium"/>
		</li>
		<li><label>提出时间：</label>
			<input id="createDate"  name="createDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				   value="<fmt:formatDate value="${xqYw.createDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			　--　
			<input id="cDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				   value="<fmt:formatDate value="${xqYw.createDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="clearfix"><input id="resetSubmit" class="btn btn-white" type="reset" value="重置"/></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>需求编号</th>
		<th>所属系统</th>
		<th>需求名称</th>
		<th>提出人</th>
		<th>提出时间</th>
		<th>来源</th>
		<th>状态</th>
		<shiro:hasPermission name="xq:xqYw:edit"><th>操作</th></shiro:hasPermission>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="xqYw">
		<tr>
			<td>
					${xqYw.xqId}
			</td>
			<td>
					${xqYw.xqSsxt}
			</td>
			<td>
				<a href="${ctx}/xq/xqYw/form?id=${xqYw.xqId}">
						${xqYw.xqTitle}
				</a>
			</td>
			<td>
					${xqYw.createBy.name}
			</td>
			<td>
				<fmt:formatDate value="${xqYw.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td>
					${xqYw.xqXqly}
			</td>
			<td>
					${xqYw.delFlag}
			</td>
			<shiro:hasPermission name="xq:xqYw:edit"><td>
				<a href="${ctx}/xq/xqYw/form?id=${xqYw.xqId}">修改</a>
				<a href="${ctx}/xq/xqYw/delete?id=${xqYw.xqId}" onclick="return confirmx('确认要删除该需求吗？', this.href)">删除</a>
			</td></shiro:hasPermission>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>