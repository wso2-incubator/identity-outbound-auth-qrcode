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

import org.wso2.carbon.identity.application.common.cache.CacheKey;

/**
 * Class handling QR authentication context cache key objects.
 */
public class QRAuthContextCacheKey extends CacheKey {

    private static final long serialVersionUID = -2846349295093760499L;
    private final String requestId;

    public QRAuthContextCacheKey(String requestId) {

        this.requestId = requestId;
    }

    public String getRequestId() {

        return requestId;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof QRAuthContextCacheKey)) {
            return false;
        }
        return this.requestId.equals(((QRAuthContextCacheKey) o).getRequestId());
    }

    @Override
    public int hashCode() {

        return requestId.hashCode();
    }
}
