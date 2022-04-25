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

/**
 * Constants used by the QRAuthenticator.
 */
public abstract class QRAuthenticatorConstants {

    public static final String AUTHENTICATOR_NAME = "qrcode";
    public static final String AUTHENTICATOR_FRIENDLY_NAME = "QRCodeBased";
    public static final String QR_PAGE = "/authenticationendpoint/qrpage.jsp";
    public static final String SUPER_TENANT_DOMAIN = "carbon.super";
    public static final String PROCEED_AUTH = "proceedAuthorization";
    public static final String SESSION_DATA_KEY = "sessionDataKey";
    public static final String TENANT_DOMAIN = "tenantDomain";
    public static final String CONTEXT_AUTH_DATA = "authData";
    public static final String AUTH_STATUS = "authStatus";
    public static final String AUTHENTICATED_USER = "authUser";
    public static final String QR_ENDPOINT = "/qr-auth/check-status";
    public static final String POLLING_QUERY_PARAMS = "?sessionDataKey=";
    public static final String AUTH_REQUEST_STATUS_SUCCESS = "SUCCESSFUL";

    /**
     * Object holding authentication mobile response status.
     */
    public enum Status {
        COMPLETED, PENDING
    }
}
