/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.QRAuthContextManager;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.impl.QRAuthContextManagerImpl;
import org.wso2.carbon.identity.application.authenticator.qrcode.dto.AuthDataDTO;
import org.wso2.carbon.identity.application.authenticator.qrcode.servlet.QRServletConstants;
import org.wso2.carbon.identity.application.authenticator.qrcode.servlet.store.QRDataStore;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for handling authentication requests sent from mobile device.
 */
public class QRServlet extends HttpServlet {

    private static final long serialVersionUID = -2050679246736808648L;
    private static final Log log = LogFactory.getLog(QRServlet.class);
    private final QRDataStore qrDataStoreInstance = QRDataStore.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        handleMobileResponse(request, response);
    }

    /**
     * Handles authentication request received from mobile app.
     *
     * @param request  HTTP request
     * @param response HTTP response
     * @throws IOException
     */
    private void handleMobileResponse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        JsonObject json = new JsonParser().parse(request.getReader().readLine()).getAsJsonObject();
        JsonObject responseData = json.get(QRServletConstants.AUTH_RESPONSE).getAsJsonObject();

        if (responseData.get(QRServletConstants.SESSION_DATA_KEY).isJsonNull()) {
            if (log.isDebugEnabled()) {
                log.debug(QRServletConstants.ErrorMessages.ERROR_CODE_AUTH_RESPONSE_TOKEN_NOT_FOUND.toString());
            }

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    QRServletConstants.ErrorMessages.ERROR_CODE_AUTH_RESPONSE_TOKEN_NOT_FOUND.toString());
        } else {
            String sessionDataKey = responseData.get(QRServletConstants.SESSION_DATA_KEY).getAsString();
            String tenantDomain = responseData.get(QRServletConstants.TENANT_DOMAIN).getAsString();
            String clientID = responseData.get(QRServletConstants.CLIENT_ID).getAsString();

            if (StringUtils.isEmpty(sessionDataKey)) {
                String errorMessage = String.format(
                        QRServletConstants.ErrorMessages.ERROR_CODE_SESSION_DATA_KEY_NOT_FOUND.toString());

                if (log.isDebugEnabled()) {
                    log.debug(errorMessage);
                }
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
            } else {
                addToContext(sessionDataKey, tenantDomain, clientID);
                String status = QRServletConstants.Status.COMPLETED.name();
                qrDataStoreInstance.updateAuthStatus(sessionDataKey, status);

                response.setStatus(HttpServletResponse.SC_ACCEPTED);

                if (log.isDebugEnabled()) {
                    log.debug("Completed processing auth response from mobile app.");
                }
            }
        }
    }

    /**
     * Add the received auth response details to the authentication context.
     *
     * @param sessionDataKey Unique key to identify the session
     * @param tenantDomain   tenant domain of the mobile application
     * @param clientID client ID of the mobile application
     */
    private void addToContext(String sessionDataKey, String tenantDomain, String clientID) {

        QRAuthContextManager contextManager = new QRAuthContextManagerImpl();
        AuthenticationContext context = contextManager.getContext(sessionDataKey);

        AuthDataDTO authDataDTO = (AuthDataDTO) context.getProperty(QRServletConstants.AUTH_DATA);
        authDataDTO.setTenantDomain(tenantDomain);
        authDataDTO.setClientID(clientID);
        context.setProperty(QRServletConstants.AUTH_DATA, authDataDTO);
        contextManager.storeContext(sessionDataKey, context);
    }
}
