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
 */

package org.wso2.carbon.identity.application.authenticator.qrcode.common.cache;

import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.common.cache.CacheEntry;

/**
 * Model class for QR authentication context cache entries.
 */
public class QRAuthContextCacheEntry extends CacheEntry {

    private static final long serialVersionUID = -2846349295093760599L;

    private final AuthenticationContext authenticationContext;

    public QRAuthContextCacheEntry(AuthenticationContext authenticationContext) {

        this.authenticationContext = authenticationContext;
    }

    public AuthenticationContext getAuthenticationContext() {

        return authenticationContext;
    }
}
