/*
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.wso2.carbon.identity.application.authenticator.qrcode.servlet.servlet;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authenticator.qrcode.QRAuthenticatorConstants;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.QRAuthContextManager;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.impl.QRAuthContextManagerImpl;
import org.wso2.carbon.identity.application.authenticator.qrcode.servlet.QRServletConstants;
import org.wso2.carbon.identity.application.authenticator.qrcode.servlet.model.WaitStatus;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for handling the status checks for authentication requests from the qr authenticator wait page.
 */
public class QRAuthCheckServlet extends HttpServlet {

    private static final Log log = LogFactory.getLog(QRAuthCheckServlet.class);
    private static final long serialVersionUID = -913670970043040923L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!(request.getParameterMap().containsKey(QRServletConstants.SESSION_DATA_KEY))) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            if (log.isDebugEnabled()) {
                log.debug(QRServletConstants.ErrorMessages.ERROR_CODE_WEB_SESSION_DATA_KEY_NOT_FOUND.toString());
            }

        } else {
            handleWebResponse(request, response);
        }
    }

    /**
     * Handles requests received from the wait page to check the authentication status.
     *
     * @param request  HTTP request
     * @param response HTTP response
     * @throws IOException When accessing writer.
     */
    private void handleWebResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {

        WaitStatus waitStatus = new WaitStatus();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(QRServletConstants.MEDIA_TYPE_JSON);

        QRAuthContextManager contextManager = new QRAuthContextManagerImpl();
        AuthenticationContext sessionContext = contextManager.getContext(request
                .getParameter(QRAuthenticatorConstants.SESSION_DATA_KEY));
        String status = String.valueOf(sessionContext.getProperty(QRServletConstants.AUTH_STATUS));

        if (status.equals(QRServletConstants.Status.PENDING.name())) {
            waitStatus.setStatus(QRServletConstants.Status.PENDING.name());

            if (log.isDebugEnabled()) {
                log.debug("Mobile authentication response has not been received yet.");
            }
        } else if (status.equals(QRServletConstants.Status.COMPLETED.name())) {
            waitStatus.setStatus(QRServletConstants.Status.COMPLETED.name());

            if (log.isDebugEnabled()) {
                log.debug("Mobile authentication has been received. Proceeding to authenticate.");
            }
        }

        String waitResponse = new Gson().toJson(waitStatus);
        PrintWriter out = response.getWriter();
        out.print(waitResponse);
        out.flush();
        out.close();
    }
}
