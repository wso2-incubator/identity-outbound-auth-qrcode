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

package org.wso2.carbon.identity.application.authenticator.qrcode.servlet;

/**
 * This class contains constants used for qr endpoint component.
 */
public class QRServletConstants {

    public static final String QR_AUTH_ENDPOINT = "/qr-auth/authenticate";
    public static final String QR_AUTH_STATUS_ENDPOINT = "/qr-auth/check-status";
    public static final String SESSION_DATA_KEY = "sessionDataKey";
    public static final String TENANT_DOMAIN = "tenantDomain";
    public static final String AUTHENTICATED_USER = "authUser";
    public static final String AUTH_DATA = "authData";
    public static final String AUTH_STATUS = "authStatus";
    public static final String AUTH_RESPONSE = "authResponse";
    public static final String MEDIA_TYPE_JSON = "application/json";

    /**
     * Object holding authentication mobile response status.
     */
    public enum Status {

        COMPLETED, PENDING
    }

    /**
     * Enum which contains error codes and corresponding error messages.
     */
    public enum ErrorMessages {

        ERROR_CODE_AUTH_RESPONSE_TOKEN_NOT_FOUND(
                "QBA-15001",
                "The request did not contain an authentication response token"
        ),
        ERROR_CODE_SESSION_DATA_KEY_NOT_FOUND(
                "QBA-15002",
                "Authentication response token received from device: %s does not contain a session data key."
        ),
        ERROR_CODE_WEB_SESSION_DATA_KEY_NOT_FOUND(
                "QBA-15003",
                "Error occurred when checking authentication status. The session data key was null or "
                        + "the HTTP request was not supported."
        );

        private final String code;
        private final String message;

        ErrorMessages(String code, String message) {

            this.code = code;
            this.message = message;
        }

        public String getCode() {

            return code;
        }

        public String getMessage() {

            return message;
        }

        @Override
        public String toString() {

            return code + " - " + message;
        }
    }
}
