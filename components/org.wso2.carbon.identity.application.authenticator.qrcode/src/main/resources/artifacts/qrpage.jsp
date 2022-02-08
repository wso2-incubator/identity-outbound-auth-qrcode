<%--
  ~ Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
  ~
  ~ WSO2 Inc. licenses this file to you under the Apache License,
  ~ Version 2.0 (the "License"); you may not use this file except
  ~ in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.nio.charset.Charset" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.util.AuthContextAPIClient" %>
<%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.util.Constants" %>
<%@ page import="org.wso2.carbon.identity.core.util.IdentityCoreConstants" %>
<%@ page import="org.wso2.carbon.identity.core.util.IdentityUtil" %>
<%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.util.EndpointConfigManager" %>
<%@ page import="static org.wso2.carbon.identity.application.authentication.endpoint.util.Constants.STATUS" %>
<%@ page import="static org.wso2.carbon.identity.application.authentication.endpoint.util.Constants.STATUS_MSG" %>
<%@ page import="static org.wso2.carbon.identity.application.authentication.endpoint.util.Constants.CONFIGURATION_ERROR" %>
<%@ page import="static org.wso2.carbon.identity.application.authentication.endpoint.util.Constants.AUTHENTICATION_MECHANISM_NOT_CONFIGURED" %>
<%@ page import="static org.wso2.carbon.identity.application.authentication.endpoint.util.Constants.ENABLE_AUTHENTICATION_WITH_REST_API" %>
<%@ page import="static org.wso2.carbon.identity.application.authentication.endpoint.util.Constants.ERROR_WHILE_BUILDING_THE_ACCOUNT_RECOVERY_ENDPOINT_URL" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.owasp.encoder.Encode" %>

<%@ include file="includes/localize.jsp" %>
<jsp:directive.include file="includes/init-url.jsp"/>


<!doctype html>
<html>
<head>

    <script language="JavaScript" type="text/javascript" src="libs/jquery_3.4.1/jquery-3.4.1.js"></script>
    <script language="JavaScript" type="text/javascript" src="libs/bootstrap_3.4.1/js/bootstrap.min.js"></script>


    <!-- header -->
    <%
        File headerFile = new File(getServletContext().getRealPath("extensions/header.jsp"));
        if (headerFile.exists()) {
    %>
        <jsp:include page="extensions/header.jsp"/>
    <% } else { %>
        <jsp:include page="includes/header.jsp"/>
    <% } %>

    <script src="https://cdn.rawgit.com/davidshimjs/qrcodejs/gh-pages/qrcode.min.js"></script>

</head>

<body class="login-portal layout authentication-portal-layout">
    <main class="center-segment">
        <div class="ui container large center aligned middle aligned">

            <!-- product-title -->
            <%
                File productTitleFile = new File(getServletContext().getRealPath("extensions/product-title.jsp"));
                if (productTitleFile.exists()) {
            %>
                <jsp:include page="extensions/product-title.jsp"/>
            <% } else { %>
                <jsp:include page="includes/product-title.jsp"/>
            <% } %>

            <div class="ui segment" style="display:flex">
				<div class="ui left aligned basic segment">
					<h3 class="ui header">
						Login Using QR Code
					</h3>
					<h5 class="ui header">
						1. Open the mobile app on your phone
					</h5>
					<h5 class="ui header">
						2. Point your phone to this screen to capture the QR code
					</h5>
				</div>
				<div>
					<div class="ui center aligned basic segment" id="qrcode"></div>
				</div>
				
				<form id="toCommonAuth" action="<%=commonauthURL%>" method="POST" style="display:none;">
					<input type="hidden" name="ACTION" value="WaitResponse"/>
					<input type="hidden" id="sessionDataKey" name="sessionDataKey">
					<input type="hidden" id="proceedAuthorization" name="proceedAuthorization">
				</form>
                
                
                <script type="text/javascript">
                    var qrcode = new QRCode(document.getElementById("qrcode"), {
                    text: 'sessionDataKey=<%=Encode.forHtmlAttribute(request.getParameter("sessionDataKey"))%>&tenantDomain=<%=Encode.forHtmlAttribute(request.getParameter("tenantDomain"))%>',
                    width: 200,
                    height: 200,
                    colorDark : "#000000",
                    colorLight : "#ffffff",
                    correctLevel : QRCode.CorrectLevel.H,
                });
                </script>          

            </div>
        </div>
    </main>
    

    <!-- product-footer -->
    <%
        File productFooterFile = new File(getServletContext().getRealPath("extensions/product-footer.jsp"));
        if (productFooterFile.exists()) {
    %>
        <jsp:include page="extensions/product-footer.jsp"/>
    <% } else { %>
        <jsp:include page="includes/product-footer.jsp"/>
    <% } %>

    <!-- footer -->
    <%
        File footerFile = new File(getServletContext().getRealPath("extensions/footer.jsp"));
        if (footerFile.exists()) {
    %>
        <jsp:include page="extensions/footer.jsp"/>
    <% } else { %>
        <jsp:include page="includes/footer.jsp"/>
    <% } %>
	
	<% 
		String toEncode = EndpointConfigManager.getAppName() + ":" + String.valueOf(EndpointConfigManager.getAppPassword());
		byte[] encoding = Base64.encodeBase64(toEncode.getBytes());
		String authHeader = new String(encoding, Charset.defaultCharset());
		String header = "Client " + authHeader;
		System.out.println(header);
	%>

    <script type="text/javascript">
		
		let i = 0;
		let sessionDataKey;
		const refreshInterval = 1000;
		const timeout = 900000;
		let qrEndpointWithQueryParams = "/qr-auth/check-status?sessionDataKey=";
		const GET = 'GET';
		
		$(document).ready(function () {
				
			var startTime = new Date().getTime();
			console.log("Start time: "+ startTime);

			const intervalListener = window.setInterval(function () {
				checkWaitStatus();
				i++;
				console.log("Polled times " + i)
			}, refreshInterval);

			function checkWaitStatus() {
				const now = new Date().getTime();
				if ((startTime + timeout) < now) {
					window.clearInterval(intervalListener);
					window.location.replace("retry.do?statusMsg=qr.auth.timed.out.message&status=qr.auth.timed.out");
				}

				const urlParams = new URLSearchParams(window.location.search);
				sessionDataKey = urlParams.get('sessionDataKey');
				$.ajax(qrEndpointWithQueryParams + sessionDataKey, {
					method: GET,
					headers: {
						"Authorization": "<%=header%>"
					},
					success: function (res) {
						handleStatusResponse(res);
					},
					error: function () {
						checkWaitStatus();
					},
					failure: function () {
						window.clearInterval(intervalListener);
						window.location.replace("/retry.do");
					}
				});
			}

			function handleStatusResponse(res) {

				if ((res.status) === "COMPLETED") {
					document.getElementById("proceedAuthorization").value = "proceed";
					document.getElementById("sessionDataKey").value = sessionDataKey;
					continueAuthentication(res);
				} else {
					console.log(res.status);
					checkWaitStatus();
				}
			}

			function continueAuthentication(res) {
				console.log("Continuing Auth request");

				window.clearInterval(intervalListener);
				document.getElementById("toCommonAuth").submit();
			}


		});

 
    </script>

</body>
</html>
