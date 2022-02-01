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

package org.wso2.carbon.identity.application.authenticator.qrcode.servlet.store;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Updates a hash-map which stores the status of the authentication request.
 */
public class QRDataStore implements Serializable {

    private static final long serialVersionUID = 8385881451715660472L;
    private static final QRDataStore qrDataStoreInstance = new QRDataStore();
    private final Map<String, String> qrDataStore = new HashMap<>();

    private QRDataStore() {

    }

    public static QRDataStore getInstance() {

        return qrDataStoreInstance;
    }

    /**
     * Returns the authentication status stored against the session data key in qr data store.
     *
     * @param sessionDataKey Unique ID for the session
     * @return Authentication status
     */
    public String getAuthStatus(String sessionDataKey) {

        return qrDataStore.get(sessionDataKey);
    }

    /**
     * Adds a new record of session data key against auth status to the qr data store.
     *
     * @param sessionDataKey Unique ID for the session
     * @param authStatus     Authentication status
     */
    public void updateAuthStatus(String sessionDataKey, String authStatus) {

        qrDataStore.put(sessionDataKey, authStatus);
    }

    /**
     * Removes the record with the given session data key in qr data store.
     *
     * @param sessionDataKey Unique ID for the session
     */
    public void removeQRData(String sessionDataKey) {

        qrDataStore.remove(sessionDataKey);
    }
}
