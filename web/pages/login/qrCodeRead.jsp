<%-- 
    Document   : qrCodeRead
    Created on : Dec 6, 2017, 1:48:32 PM
    Author     : kreshan88
--%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.epic.login.bean.SessionUserBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>   
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />

        <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/resources/images/favicon.ico" rel="shortcut icon" type="image/ico" />
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.js" type="text/javascript"></script>

        <script type="text/javascript">
            function ResetForm() {
//                $('#userName').val("");
//                $('.message').empty();
            }
        
            function staCheck() {
                
                $('#divmsg').empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/statusCgecking',
                    
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.success) {
                            alert("ddddd1")
                            window.location = "${pageContext.request.contextPath}/goHomeByValQRloginCall";
                        } else {
                           alert("ddddd2")
                        }

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }

            function doSomething() { 
                alert('start');
                staCheck();
            } 

            $(document).ready(function () {
                setInterval(doSomething, 2000);
             });
        </script>

        <title>QR code read Page</title>
    </head>
    <body>
        <div class="login_body">

            <div class="header">
                <div class="leftimg"></div>
                <!--<div class="rightimg"></div>-->
                <div class="login_ticker">
                    <div class="login_content"></div>
                </div>

            </div>
            <!--login form-->

            <div class="watermark"></div>
            <div class="content">

                <div id="dialog">
                 

                    
                    <s:form id="qrcode" action="goHomeByValQRloginCall" theme="simple" >

                        <table>
                            <s:hidden name="userName" id="userName"  value="admin"/>
                            <tr>
                                <td class="lable">User name</td>
                                <td class="lable">:</td>
                                <td colspan="2">${SessionObject.username}</td>
                            </tr>
                            <tr>
                                <td class="lable">QR-code</td>
                                <td class="lable">:</td>
                                <td class="lable"><img src="${pageContext.request.contextPath}/resources/images/863163033112758.png"/></td>
                            </tr>

                            <tr>  
                                <td colspan="2"></td>
                                <td align="right"><s:submit label="Login" cssClass="login_button" value="Login" /></td>
                            </tr>



                        </table>
                        <div class="message">         
                            <s:div id="divmsg">
                                <i style="color: red">  <s:actionerror theme="jquery"/></i>
                                <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                            </s:div>         
                        </div>
                    </s:form>
                    
                     
                </div>
            </div>
            <!--end of login form-->


            <div class="footer"><jsp:include page="../../footer.jsp" /></div>
        </div>
    </body>
</html>
