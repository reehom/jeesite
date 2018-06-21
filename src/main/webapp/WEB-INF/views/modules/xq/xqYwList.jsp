<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>需求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {

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
	<li><a href="${ctx}/xq/xqYw/form">需求添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="xqYw" action="${ctx}/xq/xqYw/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>需求编号：</label>
			<form:input path="xqId" htmlEscape="false" maxlength="64" class="input-medium"/>
		</li>
		<li><label>需求名称：</label>
			<form:input path="xqTitle" htmlEscape="false" maxlength="64" class="input-medium"/>
		</li>
		<li><label>提出时间：</label>
			<input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${xqYw.createDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="clearfix"><input id="btnReset" class="btn btn-white" type="reset" value="重置"/></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>编号</th>
		<th>系统</th>
		<th>需求名称</th>
		<th>提出人</th>
		<th>提出时间</th>
		<th>来源</th>
		<th>状态</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="xqYw">
		<tr>
			<td>
				<a href="${ctx}/xq/xqYw/form?id=${xqYw.xqId}">${xqYw.xqId}</a>
			</td>
			<td>${xqYw.xqSsxt}</td>
			<td>${xqYw.xqTitle}</td>
			<td>${xqYw.createBy.name}</td>
			<td>
				<fmt:formatDate value="${xqYw.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td>${xqYw.xqXqly}</td>
			<td>待审核</td>
			<td>
				<a href="${ctx}/xq/xqYw/form?id=${xqYw.id}">修改</a>
				<a href="${ctx}/xq/xqYw/delete?id=${xqYw.id}" onclick="return confirmx('确认要删除该需求吗？', this.href)">删除</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>