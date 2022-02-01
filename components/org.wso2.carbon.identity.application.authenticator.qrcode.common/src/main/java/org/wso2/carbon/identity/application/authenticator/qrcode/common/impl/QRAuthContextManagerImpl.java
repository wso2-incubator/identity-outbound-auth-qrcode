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

package org.wso2.carbon.identity.application.authenticator.qrcode.common.impl;

import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.QRAuthContextManager;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.cache.QRAuthContextCache;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.cache.QRAuthContextCacheEntry;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.cache.QRAuthContextCacheKey;

/**
 * Implements QRAuthContextManager interface.
 */
public class QRAuthContextManagerImpl implements QRAuthContextManager {

    @Override
    public void storeContext(String key, AuthenticationContext context) {

        QRAuthContextCache.getInstance().addToCacheByRequestId(
                new QRAuthContextCacheKey(key), new QRAuthContextCacheEntry(context));
    }

    @Override
    public AuthenticationContext getContext(String key) {

        QRAuthContextCacheKey cacheKey = new QRAuthContextCacheKey(key);
        return QRAuthContextCache.getInstance().getValueFromCacheByRequestId(cacheKey).getAuthenticationContext();
    }

    @Override
    public void clearContext(String key) {

        QRAuthContextCache.getInstance().clearCacheEntryByRequestId(new QRAuthContextCacheKey(key));
    }
}
