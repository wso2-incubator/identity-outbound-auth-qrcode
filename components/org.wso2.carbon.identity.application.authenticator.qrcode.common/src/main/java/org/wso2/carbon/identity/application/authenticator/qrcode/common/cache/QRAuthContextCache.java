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
 */

package org.wso2.carbon.identity.application.authenticator.qrcode.common.cache;

import org.wso2.carbon.identity.application.authentication.framework.store.SessionDataStore;
import org.wso2.carbon.identity.application.common.cache.BaseCache;

/**
 * Class handling QR authentication cache.
 */
public class QRAuthContextCache extends
        BaseCache<QRAuthContextCacheKey, QRAuthContextCacheEntry> {

    private static final String QR_AUTH_CONTEXT_CACHE = "QRAuthContextCache";
    private static volatile QRAuthContextCache cache;

    private QRAuthContextCache() {

        super(QR_AUTH_CONTEXT_CACHE, true);
    }

    public static QRAuthContextCache getInstance() {

        if (cache == null) {
            synchronized (QRAuthContextCache.class) {
                cache = new QRAuthContextCache();
            }
        }
        return cache;
    }

    /**
     * Store authentication context to the session data store.
     *
     * @param id SessionDataKey for the session.
     * @param entry QRAuthContextCacheEntry containing QR authentication context.
     */
    private void storeToSessionStore(String id, QRAuthContextCacheEntry entry) {

        SessionDataStore.getInstance().storeSessionData(id, QR_AUTH_CONTEXT_CACHE, entry);
    }

    /**
     * Gets the QR authentication context from SessionDataStore by the SessionDataKey.
     *
     * @param id SessionDataKey used as the ID.
     * @return QR authentication context.
     */
    private QRAuthContextCacheEntry getFromSessionStore(String id) {

        return (QRAuthContextCacheEntry) SessionDataStore.getInstance().getSessionData(id, QR_AUTH_CONTEXT_CACHE);
    }

    /**
     * Remove cached authentication context by SessionDataKey.
     *
     * @param id SessionDataKey for the session.
     */
    private void clearFromSessionStore(String id) {

        SessionDataStore.getInstance().clearSessionData(id, QR_AUTH_CONTEXT_CACHE);
    }

    /**
     * Clear stored cache under the SessionDataKey.
     *
     * @param key QRAuthenticationContextKey with SessionDataKey.
     */
    public void clearCacheEntryByRequestId(QRAuthContextCacheKey key) {

        super.clearCacheEntry(key);
        clearFromSessionStore(key.getRequestId());
    }

    /**
     * Add the authentication context to cache by the SessionDataKey.
     *
     * @param key QRAuthenticationContextKey with SessionDataKey.
     * @param entry QRAuthenticationCacheEntry containing authentication context.
     */
    public void addToCacheByRequestId(QRAuthContextCacheKey key,
                                      QRAuthContextCacheEntry entry) {

        super.addToCache(key, entry);
        storeToSessionStore(key.getRequestId(), entry);
    }

    /**
     * Gets the QR authentication context from cache by the SessionDataKey.
     *
     * @param key QRAuthenticationContextKey with SessionDataKey.
     * @return QRAuthenticationCacheEntry containing authentication context.
     */
    public QRAuthContextCacheEntry getValueFromCacheByRequestId(QRAuthContextCacheKey key) {

        QRAuthContextCacheEntry cacheEntry = super.getValueFromCache(key);
        if (cacheEntry == null) {
            cacheEntry = getFromSessionStore(key.getRequestId());
        }
        return cacheEntry;
    }
}
