/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.application.authenticator.qrcode.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.wso2.carbon.identity.application.authentication.framework.ApplicationAuthenticator;
import org.wso2.carbon.identity.application.authenticator.qrcode.QRAuthenticator;

/**
 * Service component class for the QR Authenticator initialization.
 */
@Component(
        name = "identity.application.authenticator.qrcode.QRAuthenticatorServiceComponent",
        immediate = true)
public class QRAuthenticatorServiceComponent {

    private static final Log log = LogFactory.getLog(QRAuthenticatorServiceComponent.class);

    /**
     * This method is to register the QR authenticator service.
     *
     * @param ctxt The Component Context
     */
    @Activate
    protected void activate(ComponentContext ctxt) {

        try {
            QRAuthenticator qrAuth = new QRAuthenticator();
            ctxt.getBundleContext().registerService(ApplicationAuthenticator.class.getName(), qrAuth, null);
            if (log.isDebugEnabled()) {
                log.info("QRAuthenticator bundle is activated");
            }
        } catch (Throwable e) {
            log.error("QRAuthenticator bundle activation Failed", e);
        }
    }

    /**
     * This method is to deactivate the QR authenticator the service.
     *
     * @param ctxt The Component Context
     */
    @Deactivate
    protected void deactivate(ComponentContext ctxt) {

        if (log.isDebugEnabled()) {
            log.info("QRAuthenticator bundle is deactivated");
        }
    }

}
