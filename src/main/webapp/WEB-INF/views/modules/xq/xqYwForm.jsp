<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>需求管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">

        $(document).ready(function () {
            //$("#name").focus();

            var status = ${xqYw.delFlag};
            if (status == "0") {
                $("#xqXqxh").attr("readOnly", true);
                $("#xqShr").attr("readOnly", true);
            }
            var xqSsxt = '${xqYw.xqSsxt}';
            $("#xqSsxt").select(xqSsxt);

            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
    <li><a href="${ctx}/xq/xqYw/list?only=true">需求列表</a></li>
    <li class="active"><a href="${ctx}/xq/xqYw/form?id=${xqYw.id}">需求<shiro:hasPermission
            name="xq:xqYw:edit">${not empty xqYw.xqId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xq:xqYw:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<div class="container">
    <div class="row">
        <div class="form-inline">
            <form:form id="inputForm" modelAttribute="xqYw" action="${ctx}/xq/xqYw/save?action=edit" method="post"
                       class="form-horizontal" enctype="multipart/form-data">
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
                        <form:select path="xqSsxt" htmlEscape="false"
                                     maxlength="64" class="input-xlarge required" style="width:285px;"
                                     items="${systemLists}"
                        >
                        </form:select>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">需求来源：</label>
                    <div class="controls">
                        <form:select path="xqXqly" htmlEscape="false"
                                     maxlength="64" class="input-xlarge required" style="width:285px;"
                                     items="${resourcesLists}"
                        >
                        </form:select>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">需求描述：</label>
                    <div class="controls">
                        <form:textarea path="xqXqms" htmlEscape="false" rows="4" maxlength="2047"
                                       class="input-xxlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">需求细化：</label>
                    <div class="controls">
                        <form:textarea path="xqXqxh" htmlEscape="false" rows="4" maxlength="2047" class="input-xxlarge "
                                       readonly="true"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">审核人：</label>
                    <div class="controls">
                        <form:input path="xqShr" htmlEscape="false" maxlength="64" class="input-xlarge "
                                    readonly="true"/>
                    </div>
                </div>
                <%--<div class="control-group">--%>
                <%--<label class="control-label">remarks：</label>--%>
                <%--<div class="controls">--%>
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="form-actions">
                    <shiro:hasPermission name="xq:xqYw:edit">
                     <c:if test="${xqYw.delFlag=='3'}"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                                    value="保 存"/>&nbsp;
                     </c:if>
                    </shiro:hasPermission>
                    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                </div>
            </form:form>
        </div>
        <div class="col-xs-4">
            <h5>历史记录</h5>
            <table class="table table-hover">
                <thead></thead>
                <tbody>
                <c:forEach items="${recordLists}" var="record">
                    <tr>
                        <td style="width: 1px;"></td>
                        <td style="width: 30px;">
                            <fmt:formatDate value="${record.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td style="width: 30px;">
                            ${record.createBy.name}
                        </td>
                        <td style="width: 30px;">
                            <c:if test="${record.lsjlJlzt=='0'}">建立</c:if>
                            <c:if test="${record.lsjlJlzt=='1'}">审核通过</c:if>
                            <c:if test="${record.lsjlJlzt=='2'}">审核不通过</c:if>
                            <c:if test="${record.lsjlJlzt=='3'}">修改</c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>