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
<%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.util.AuthContextAPIClient" %>
<%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.util.Constants" %>
<%@ page import="org.wso2.carbon.identity.core.util.IdentityCoreConstants" %>
<%@ page import="org.wso2.carbon.identity.core.util.IdentityUtil" %>
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
    <!-- header -->
    <%
        File headerFile = new File(getServletContext().getRealPath("extensions/header.jsp"));
        if (headerFile.exists()) {
    %>
        <jsp:include page="extensions/header.jsp"/>
    <% } else { %>
        <jsp:include page="includes/header.jsp"/>
    <% } %>
    <script src="js/gadget.js"></script>
    <script src="js/qrCodeGenerator.js"></script>
    <script src="https://cdn.rawgit.com/davidshimjs/qrcodejs/gh-pages/qrcode.min.js"></script>

</head>

<body class="login-portal layout authentication-portal-layout">
    <main class="center-segment">
        <div class="ui container medium center aligned middle aligned">

            <!-- product-title -->
            <%
                File productTitleFile = new File(getServletContext().getRealPath("extensions/product-title.jsp"));
                if (productTitleFile.exists()) {
            %>
                <jsp:include page="extensions/product-title.jsp"/>
            <% } else { %>
                <jsp:include page="includes/product-title.jsp"/>
            <% } %>

            <div class="ui segment">
                <h3 class="ui header">
                    Authenticating with QR Code
                </h3>
                <h4 class="ui header">
                    Scan this QR code using an authenticator app
                </h4>
                    <div style="display:flex; justify-content:center">
                        <div class="ui center aligned basic segment" id="qrcode"></div>
                    </div>
                
                
                <script type="text/javascript">
                    var qrcode = new QRCode(document.getElementById("qrcode"), {
                    text: 'sessionDataKey=<%=Encode.forHtmlAttribute(request.getParameter("sessionDataKey"))%>',
                    width: 180,
                    height: 180,
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

    <script type="text/javascript">

        $(document).ready(function() {
            var key =  document.getElementById("ske").value;
            if(key != null) {
                loadQRCode(key);
            }
        });

        let i = 0;
        let sessionDataKey;
        const refreshInterval = 1000;
        const timeout = 900000;
        let qrEndpointWithQueryParams = "<%=QRAuthenticatorConstants.QR_ENDPOINT +
         QRAuthenticatorConstants.POLLING_QUERY_PARAMS%>";
        const GET = 'GET';

        $(document).ready(function () {
            var startTime = new Date().getTime();
            console.log("Start time: "+ startTime);

            const intervalListener = window.setInterval(function () {
                checkWaitStatus();
                i++;
                console.log("Polled ${i} times")
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
                    async: false,
                    cache : false,
                    method: GET,
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

                if ((res.status) === "<%=QRAuthenticatorConstants.COMPLETED%>") {
                    document.getElementById("proceedAuthorization").value = "proceed";
                    document.getElementById("sessionDataKey").value = sessionDataKey;
                    continueAuthentication(res);
                } else {
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
