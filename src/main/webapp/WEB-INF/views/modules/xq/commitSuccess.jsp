<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>提交成功</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .div1{
            width:90%;
            height: 150px;
        }
        .div2{
            width:90%;
            height: 50px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
             $("#btnSubmit").click(function () {
                 window.location.href="${ctx}/xq/xqYw/?status=10";
                 /*$.post("${ctx}/xq/xqYw/list?only=true",function(result){
                     alert("aa");
                 });*/
             });

             $("#resetSubmit").click(function () {
                 window.location.href="${ctx}/xq/xqYw/addPage";
                 /*$.post("${ctx}/xq/xqYw/add",function(result){
                     alert("ss");
                 });*/
             });
        });
    </script>
</head>
<body>
<div class="div1">
</div>
<div class="div1" align="center">
    <img src="${ctxStatic}/images/comSuccess.png" width="150" height="150">
</div>
<div class="div2" align="center">
    <h2>需求登记提交成功，等待客服审查</h2>
</div>
<div class="div2" align="center">
    <h3>你的需求编号为：${xqId}</h3>
</div>
<div class="div2" align="center">
    <p>审查通过后，系统将以发送短信和站内消息进行通知，请留意通知并完成相关操作。</p>
</div>
<div class="div2" align="center">
    <input id="btnSubmit"  class="btn btn-primary" type="submit" value="我的需求"/>&nbsp;&nbsp;&nbsp;&nbsp;
    <input id="resetSubmit"  class="btn btn-white" type="reset" value="继续申请"/>
</div>
</body>
</html>
