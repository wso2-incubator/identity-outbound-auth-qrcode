/**
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import { useAuthContext } from "@asgardeo/auth-react";
import React, { FunctionComponent, ReactElement, useEffect, useState } from "react";
import { DefaultLayout } from "../layouts/default";
import { AuthenticationResponse } from "../components";
import Navbar from "../components/Navbar";
 
 /**
  * Decoded ID Token Response component Prop types interface.
  */
 type HomePagePropsInterface = {};
 
 /**
  * LoginInfo page for the Sample.
  *
  * @param {HomePagePropsInterface} props - Props injected to the component.
  *
  * @return {React.ReactElement}
  */
 export const LoginInfo: FunctionComponent<HomePagePropsInterface> = (
     props: HomePagePropsInterface
 ): ReactElement => {
 
     const {
         state,
         signIn,
         signOut,
         getBasicUserInfo,
         getIDToken,
         getDecodedIDToken
     } = useAuthContext();
 
     const [ derivedAuthenticationState, setDerivedAuthenticationState ] = useState<any>(null);
     const [ hasAuthenticationErrors, setHasAuthenticationErrors ] = useState<boolean>(false);
     const [ userInfo, setUserInfo ] = useState<any>(null);
 
     useEffect(() => {
 
         if (!state?.isAuthenticated) {
             return;
         }
 
         (async (): Promise<void> => {
             const basicUserInfo = await getBasicUserInfo();
             const idToken = await getIDToken();
             const decodedIDToken = await getDecodedIDToken();
 
             const derivedState = {
                 authenticateResponse: basicUserInfo,
                 idToken: idToken.split("."),
                 decodedIdTokenHeader: JSON.parse(atob(idToken.split(".")[0])),
                 decodedIDTokenPayload: decodedIDToken
             };
 
             setDerivedAuthenticationState(derivedState);
             setUserInfo(basicUserInfo);
         })();
     }, [ state.isAuthenticated ]);
 
     const handleLogout = () => {
         signOut();
     };

     function getName() {

        var userData = JSON.parse(JSON.stringify(userInfo));
        for(var data in userData) {
            if(data === "sub"){
                return (userData[data].charAt(0).toUpperCase() + userData[data].slice(1).toLowerCase());
            }
        }
    };
 
     return (
        <>
            <Navbar name = {getName()} />

            <DefaultLayout
                isLoading = { state.isLoading }
                hasErrors = { hasAuthenticationErrors }
            >
            <div className = "content">
                <AuthenticationResponse
                    derivedResponse = { derivedAuthenticationState }
                />
                <button
                    className = "btn primary mt-4"
                    onClick = { () => {
                        handleLogout();
                    } }
                >
                    Logout
                </button>
            </div>
            </DefaultLayout>
        </>   
     );
 };
 