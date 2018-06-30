<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>需求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#add").click(function () {
                window.location.href="${ctx}/xq/xqYw/addPage";
            });

            $("#resetSubmit").click(function () {
                $("#xqId").val("");
                $("#xqTitle").val("");
                $("#strDate").val("");
                $("#btnSubmit").click();
            });

            laydate.render({
                elem: '#strDate'
                ,range: "~"
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
	<c:if test="${empty audit}">
		<li class="active"><a>需求列表</a></li>
		<shiro:hasPermission name="xq:xqYw:edit">
			<li><a href="${ctx}/xq/xqYw/addPage">需求添加</a></li>
		</shiro:hasPermission>
	</c:if>
	<c:if test="${not empty audit}">
		<li class="active"><a>需求审核列表</a></li>
	</c:if>
</ul>
<c:if test="${empty audit}">
	<div class="breadcrumb form-search" style="float: right;">
		<button id="add" class="btn btn-info" type="button"><i class="icon-plus"></i> 新增</button>
	</div>
</c:if>
<form:form id="searchForm" modelAttribute="xqYw" action="${ctx}/xq/xqYw/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>需求编号：</label>
			<form:input id="xqId" path="xqId" htmlEscape="false" maxlength="64" class="input-medium"/>
		</li>
		<li>
			<label>需求名称：</label>
			<form:input id="xqTitle" path="xqTitle" htmlEscape="false" maxlength="64" class="input-medium"/>
		</li>
		<li>
			<label>提出时间：</label>
			<input type="text" class="input-medium" value="${strDate}" name="strDate" id="strDate" placeholder="请选择" readonly="readonly"/>
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
		<th>当前状态</th>
		<th>下一步状态</th>
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
				<a href="${ctx}/xq/xqYw/infoPage?id=${xqYw.xqId}">${xqYw.xqTitle}</a>
			</td>
			<td>
					${xqYw.createBy.name}
			</td>
			<td>
				<fmt:formatDate value="${xqYw.createDate}" pattern="yyyy-MM-dd HH:mm"/>
			</td>
			<td>
					${xqYw.xqXqly}
			</td>
			<td>
				<c:if test="${xqYw.delFlag=='0'}">提交成功</c:if>
				<c:if test="${xqYw.delFlag=='2'}">审核通过</c:if>
				<c:if test="${xqYw.delFlag=='3'}">审核不通过</c:if>
				<c:if test="${xqYw.delFlag=='4'}">开发中</c:if>
				<c:if test="${xqYw.delFlag=='5'}">已完成</c:if>
				<c:if test="${xqYw.delFlag=='9'}">已撤销</c:if>
			</td>
			<td>
				<c:if test="${xqYw.delFlag=='0'}">待审核</c:if>
				<c:if test="${xqYw.delFlag=='2'}">待开发</c:if>
				<c:if test="${xqYw.delFlag=='3'}">结束</c:if>
				<c:if test="${xqYw.delFlag=='4'}">待开发完成</c:if>
				<c:if test="${xqYw.delFlag=='5'}">结束</c:if>
				<c:if test="${xqYw.delFlag=='9'}">结束</c:if>
			</td>
			<shiro:hasPermission name="xq:xqYw:edit">
				<td>
					<a href="${ctx}/xq/xqYw/infoPage?id=${xqYw.xqId}">查看</a>&nbsp;

					<c:if test="${xqYw.delFlag=='0'}">
						<c:if test="${xqYw.createBy == fns:getUser()}">
							<a href="${ctx}/xq/xqYw/updatePage?id=${xqYw.xqId}">修改</a>&nbsp;
						</c:if>
					</c:if>

					<c:if test="${xqYw.delFlag=='0'}">
						<c:if test="${'1'==fns:getUser()}">
							<a href="${ctx}/xq/xqYw/auditPage?id=${xqYw.xqId}">审核</a>&nbsp;
						</c:if>
					</c:if>

					<c:if test="${xqYw.delFlag=='2'}">
						<c:if test="${'1'==fns:getUser()}">
							<a href="${ctx}/xq/xqYw/deal?id=${xqYw.xqId}"  onclick="return confirmx('确认要开始开发该需求吗？', this.href)">开始开发</a>&nbsp;
						</c:if>
					</c:if>

					<c:if test="${xqYw.delFlag=='4'}">
						<c:if test="${'1'==fns:getUser()}">
							<a href="${ctx}/xq/xqYw/deal?id=${xqYw.xqId}"  onclick="return confirmx('确认该需求开发完成？', this.href)">开发完成</a>&nbsp;
						</c:if>
					</c:if>

					<c:if test="${xqYw.delFlag=='0'}">
						<c:if test="${xqYw.createBy==fns:getUser()}">
							<a href="${ctx}/xq/xqYw/delete?id=${xqYw.xqId}" onclick="return confirmx('确认要撤销该需求吗？', this.href)">撤销</a>
						</c:if>
					</c:if>
				</td>
			</shiro:hasPermission>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>