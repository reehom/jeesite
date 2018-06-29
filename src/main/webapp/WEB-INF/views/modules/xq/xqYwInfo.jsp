<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>需求详情</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .div-left{
            width: 64%;
            float: left;
            padding-left: 3%
        }
        .div-right{
            width: 30%;
            float: right;
        }
        .div-padding-bottom{
            padding-bottom: 10px;
        }
        .div-button{
            text-align: center;
            padding-top: 30px;
        }

        .legend{
            font-size: 16px;
            font-weight: normal;
            color: black;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

        });
    </script>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a>需求详情</a></li>
</ul>
<div>
    <div class="div-padding-bottom">
        <ul class="breadcrumb">
            <li class="active">
                <span class="badge badge-info" style="font-size: 14px;padding-top: 4px;">${xqYw.xqId}</span>
            </li>
            <li class="active" style="color: black"><h4>${xqYw.xqTitle}</h4></li>
        </ul>
    </div>
    <div class="div-left">
        <div class="div-padding-bottom">
            <fieldset>
                <legend class="legend">需求描述</legend>
                <div class="article-content">${xqYw.xqXqms}</div>
            </fieldset>
        </div>
        <c:if test="${not empty xqYw.xqXqxh}">
        <div class="div-padding-bottom">
            <fieldset>
                <legend class="legend">需求细化</legend>
                <div class="article-content">${xqYw.xqXqxh}</div>
            </fieldset>
        </div>
        </c:if>
        <div class="div-padding-bottom">
            <fieldset>
                <legend class="legend">附件材料</legend>
                <div class="article-content">
                    <c:if test="${empty fjcl}">无</c:if>
                    <c:if test="${not empty fjcl}">
                        <c:forEach items="${fjcl}" var="fjcl">
                            <div style="height: 25px">${fjcl.fjclName}&nbsp;&nbsp;&nbsp;<a href="${ctx}/xq/xqYw/fileDown?id=${fjcl.fjclId}">下载</a></div>
                        </c:forEach>
                    </c:if>
                </div>
            </fieldset>
        </div>
        <div class="div-padding-bottom">
            <fieldset>
                <div class="legend" style="margin: 10px 0px;">历史记录</div>
                    <table class="table table-hover">
                        <tbody>
                        <c:forEach items="${recordLists}" var="record">
                            <tr>
                                <td style="width: 30%;">
                                    <fmt:formatDate value="${record.createDate}" pattern="yyyy-MM-dd HH:mm"/>
                                </td>
                                <td style="width: 40%;">
                                        ${record.createBy.name}
                                </td>
                                <td style="width: 30%;">
                                    <c:if test="${record.lsjlJlzt=='0'}">建立</c:if>
                                    <c:if test="${record.lsjlJlzt=='1'}">审核通过</c:if>
                                    <c:if test="${record.lsjlJlzt=='2'}">审核不通过</c:if>
                                    <c:if test="${record.lsjlJlzt=='3'}">修改</c:if>
                                    <c:if test="${record.lsjlJlzt=='4'}">开发中</c:if>
                                    <c:if test="${record.lsjlJlzt=='5'}">开发完成</c:if>
                                    <c:if test="${record.lsjlJlzt=='9'}">撤销</c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
            </fieldset>
        </div>
        <div class="div-button">
            <button class="btn" type="button" onclick="history.go(-1)"><i class="icon-chevron-left"></i> 返 回</button>
        </div>
    </div>
    <div class="div-right">
        <ul class="nav nav-tabs">
            <li class="active"><a>基本信息</a></li>
        </ul>
            <form:form id="inputForm" class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" style="width: 25%">提出人：</label>
                    <div class="controls" style="margin-left: 30%;">
                            ${xqYw.createBy.name}
                    </div>
                </div><div class="control-group">
                    <label class="control-label" style="width: 25%">提出时间：</label>
                    <div class="controls" style="margin-left: 30%;">
                        <fmt:formatDate value="${xqYw.createDate}" pattern="yyyy-MM-dd HH:mm"/>
                    </div>
                </div><div class="control-group">
                    <label class="control-label" style="width: 25%">所属系统：</label>
                    <div class="controls" style="margin-left: 30%;">
                            ${xqYw.xqSsxt}
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" style="width: 25%">需求来源：</label>
                    <div class="controls" style="margin-left: 30%;">
                            ${xqYw.xqXqly}
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" style="width: 25%">备注：</label>
                    <div class="controls" style="margin-left: 30%;">
                            ${xqYw.remarks}
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" style="width: 25%">状态：</label>
                    <div class="controls" style="margin-left: 30%;">
                        <c:if test="${xqYw.delFlag=='0'}">待审核</c:if>
                        <c:if test="${xqYw.delFlag=='1'}">删除</c:if>
                        <c:if test="${xqYw.delFlag=='2'}">审核通过-待开发</c:if>
                        <c:if test="${xqYw.delFlag=='3'}">审核不通过</c:if>
                        <c:if test="${xqYw.delFlag=='4'}">开发中</c:if>
                        <c:if test="${xqYw.delFlag=='5'}">已完成</c:if>
                        <c:if test="${xqYw.delFlag=='9'}">已撤销</c:if>
                    </div>
                </div>
            </form:form>
        </div>
</div>
</body>
</html>