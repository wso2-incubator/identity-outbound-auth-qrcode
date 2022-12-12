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

package org.wso2.carbon.identity.application.authenticator.qrcode.common;

import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;

/**
 * This interface manages authentication context operations for QR based authentication.
 */
public interface QRAuthContextManager {

    /**
     * Store authentication context.
     *
     * @param key     Unique key for identifying the authentication context for the session.
     * @param context Authentication context.
     */
    void storeContext(String key, AuthenticationContext context);

    /**
     * Get stored authentication context.
     *
     * @param key Unique key for identifying the authentication context for the session.
     * @return Authentication context stored under unique key.
     */
    AuthenticationContext getContext(String key);

    /**
     * Remove the authentication context from storage to end its usage.
     *
     * @param key Unique key for identifying the authentication context for the session.
     */
    void clearContext(String key);
}
