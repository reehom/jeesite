<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>提交成功</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });
    </script>
</head>
<body>
<div style="width:90%; height: 150px;">
</div>
<div style="width:90%; height: 150px;" align="center">
    <img src="${ctxStatic}/images/comSuccess.png" width="150" height="150">
</div>
<div style="width:90%;height: 50px;" align="center">
    <h2>需求登记提交成功，等待客服审查</h2>
</div>
<div style="width:90%;height: 50px;" align="center">
    <h3>你的需求编号为：20180410003</h3>
</div>
<div style="width:90%;height: 50px;" align="center">
    <p>审查通过后，系统将以发送短信和站内消息进行通知，请留意通知并完成相关操作。</p>
</div>
<div style="width:90%;height: 50px;" align="center">
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="我的需求"/>&nbsp;&nbsp;&nbsp;&nbsp;
    <input id="resetSubmit" class="btn btn-white" type="reset" value="继续申请"/>
</div>
</body>
</html>
