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


import {KJUR} from "jsrsasign";
import uuid from "uuid-random";
import {AuthRequestInterface} from "../models";
import {DateTimeUtil, RequestSenderUtil} from "../utils";

export class AuthorizationService {

    /**
     * Process the request as an organized object.
     *
     * @param request JSON object of the request
     */
    public static processAuthRequest(
        request: any
    ): AuthRequestInterface {

        let authRequest: AuthRequestInterface;

   
            if (request.sessionDataKey) {
                authRequest = {   
                    sessionDataKey: request.sessionDataKey,
                    tenantDomain: request.tenantDomain,
                };
            } else {
    
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
    public static async sendAuthRequest(
        authRequest: AuthRequestInterface,
        token: any,
        response: string
    ): Promise<any> {

        const timestamp = new DateTimeUtil();

        const authResponse = {
            sessionDataKey: authRequest.sessionDataKey,
            tenantDomain: authRequest.tenantDomain,
            clientID: "A9qHTjNwUOAifHqCdfcvwLYfslYa"
        };

        const headers = {
            Accept: "application/json",
            "Content-Type": "application/json",
            "Authorization" : "Bearer " + token
        };

        const authRequestBody: any = {
            authResponse: authResponse
        };


        const authUrl = authRequest.host + "/qr-auth/authenticate";

        const request = new RequestSenderUtil();
        const result: Promise<any> = request.sendRequest(
            authUrl, "POST", headers, JSON.stringify(authRequestBody));

        authRequest.requestTime = timestamp.getDateTime();

        return result.then((res) => {
            let result;
            if (res.status === 202 && response == "SUCCESSFUL") {
                authRequest.authenticationStatus = "Accepted";
                result = "OK";
            } else if (res.status === 202 && response == "DENIED") {
                authRequest.authenticationStatus = "Denied";
                result = "OK";
            } else {
                console.error("Auth response has a problem. Check! " + String(res));
            }

            return JSON.stringify({res: result, data: authRequest});
        });
    }

}
