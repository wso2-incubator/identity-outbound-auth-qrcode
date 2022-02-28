/**
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { DateTimeUtil, RequestSenderUtil } from "../utils";
export class AuthorizationService {
    /**
     * Process the request as an organized object.
     *
     * @param request JSON object of the request
     */
    static processAuthRequest(request) {
        let authRequest;
        if (request.sessionDataKey) {
            authRequest = {
                sessionDataKey: request.sessionDataKey,
                tenantDomain: request.tenantDomain,
            };
        }
        else {
            throw new Error("One or more required parameters (tenantDomain, sessionDataKey) was not found.");
        }
        return authRequest;
    }
    /**
     * Send the request to the IS to allow or deny authorization.
     *
     * @param authRequest Object for the authentication request
     * @param response Authorisation response given by the user
     * @param account Registered account requesting to authenticate
     */
    static sendAuthRequest(authRequest, token, response) {
        return __awaiter(this, void 0, void 0, function* () {
            const timestamp = new DateTimeUtil();
            const authResponse = {
                sessionDataKey: authRequest.sessionDataKey,
                tenantDomain: authRequest.tenantDomain,
                clientID: "LfQXHek26Vp1tbRwesJ0HJu4aMMa"
            };
            const headers = {
                Accept: "application/json",
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            };
            const authRequestBody = {
                authResponse: authResponse
            };
            const authUrl = authRequest.host + "/qr-auth/authenticate";
            const request = new RequestSenderUtil();
            const result = request.sendRequest(authUrl, "POST", headers, JSON.stringify(authRequestBody));
            authRequest.requestTime = timestamp.getDateTime();
            return result.then((res) => {
                let result;
                if (res.status === 202 && response == "SUCCESSFUL") {
                    authRequest.authenticationStatus = "Accepted";
                    result = "OK";
                }
                else if (res.status === 202 && response == "DENIED") {
                    authRequest.authenticationStatus = "Denied";
                    result = "OK";
                }
                else {
                    console.error("Auth response has a problem. Check! " + String(res));
                }
                return JSON.stringify({ res: result, data: authRequest });
            });
        });
    }
}
