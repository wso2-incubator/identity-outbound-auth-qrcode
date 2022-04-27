/*
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.application.authenticator.qrcode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.AbstractApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.LocalApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.AuthenticationFailedException;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.QRAuthContextManager;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.impl.QRAuthContextManagerImpl;
import org.wso2.carbon.identity.core.ServiceURLBuilder;
import org.wso2.carbon.identity.core.URLBuilderException;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * QR code based custom authenticator.
 */
public class QRAuthenticator extends AbstractApplicationAuthenticator implements LocalApplicationAuthenticator {

    private static final long serialVersionUID = 4345354156955223654L;
    private static final Log log = LogFactory.getLog(QRAuthenticator.class);

    /**
     * @param request The http servlet request.
     * @return true, if PROCEED_AUTH is not null.
     */
    @Override
    public boolean canHandle(HttpServletRequest request) {

        return request.getParameter(QRAuthenticatorConstants.PROCEED_AUTH) != null;
    }

    /**
     * Initiate authentication request.
     *
     * @param request  The request.
     * @param response The response.
     * @param context  The authentication context.
     * @throws AuthenticationFailedException If unable to redirect user to login page.
     */
    @Override
    protected void initiateAuthenticationRequest(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationContext context) throws AuthenticationFailedException {

        String tenantDomain = context.getTenantDomain();
        String sessionDataKey = request.getParameter(QRAuthenticatorConstants.SESSION_DATA_KEY);

        String authStatus = QRAuthenticatorConstants.Status.PENDING.name();
        context.setProperty(QRAuthenticatorConstants.AUTH_STATUS, authStatus);

        QRAuthContextManager contextManager = new QRAuthContextManagerImpl();
        contextManager.storeContext(sessionDataKey, context);

        redirectQRPage(response, sessionDataKey, tenantDomain);
    }

    /**
     * Redirect user to QR code page for login.
     *
     * @param response The response.
     * @param sessionDataKey The session data key.
     * @throws AuthenticationFailedException  If unable to redirect user to login page.
     */
    protected void redirectQRPage(HttpServletResponse response, String sessionDataKey, String tenantDomain)
            throws AuthenticationFailedException {

        try {
            String qrPage = ServiceURLBuilder.create().addPath(QRAuthenticatorConstants.QR_PAGE)
                    .addParameter(QRAuthenticatorConstants.SESSION_DATA_KEY, sessionDataKey)
                    .addParameter(QRAuthenticatorConstants.TENANT_DOMAIN, tenantDomain)
                    .addParameter("AuthenticatorName", QRAuthenticatorConstants.AUTHENTICATOR_FRIENDLY_NAME)
                    .build().getAbsolutePublicURL();
            response.sendRedirect(qrPage);

        } catch (IOException e) {
            String errorMessage = "Error occurred when trying to to redirect user to the login page.";
            throw new AuthenticationFailedException(errorMessage, e);
        } catch (URLBuilderException e) {
            String errorMessage = "Error occurred when building the URL for the login page for user.";
            throw new AuthenticationFailedException(errorMessage, e);
        }
    }

    /**
     * This method is overridden to authenticate user.
     *
     * @param request  The http servlet request.
     * @param response The http servlet response.
     * @param context  AuthenticationContext.
     */
    @Override
    protected void processAuthenticationResponse(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationContext context) {

        QRAuthContextManager contextManager = new QRAuthContextManagerImpl();
        String sessionDataKey = request.getParameter(QRAuthenticatorConstants.SESSION_DATA_KEY);
        AuthenticationContext sessionContext = contextManager.getContext(sessionDataKey);
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) sessionContext
                .getProperty(QRAuthenticatorConstants.AUTHENTICATED_USER);

        context.setSubject(authenticatedUser);
    }

    /**
     * Check whether status of retrying authentication.
     *
     * @return true, if retry authentication is enabled.
     */
    @Override
    protected boolean retryAuthenticationEnabled() {

        return true;
    }

    /**
     * Get friendly name.
     *
     * @return Authenticator friendly name.
     */
    @Override
    public String getFriendlyName() {

        return QRAuthenticatorConstants.AUTHENTICATOR_FRIENDLY_NAME;
    }

    /**
     * Get requested session ID.
     *
     * @param request The http servlet request.
     * @return Requested session ID.
     */
    @Override
    public String getContextIdentifier(HttpServletRequest request) {

        return request.getParameter("sessionDataKey");
    }

    /**
     * Get authenticator name.
     *
     * @return Authenticator name.
     */
    @Override
    public String getName() {

        return QRAuthenticatorConstants.AUTHENTICATOR_NAME;
    }
}
