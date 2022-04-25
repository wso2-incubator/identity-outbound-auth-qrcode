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
import { default as authConfig } from "../config.json";
import APP_LOGO from "../images/login.jpg";
import { DefaultLayout } from "../layouts/default";
import { PhotoGallery } from "./PhotoGallery";

/**
 * Decoded ID Token Response component Prop types interface.
 */
type HomePagePropsInterface = {};

/**
 * Home page for the Sample.
 *
 * @param {HomePagePropsInterface} props - Props injected to the component.
 *
 * @return {React.ReactElement}
 */
export const HomePage: FunctionComponent<HomePagePropsInterface> = (
    props: HomePagePropsInterface
): ReactElement => {

    const {
        state,
        signIn,
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

    const handleLogin = () => {
        signIn()
            .catch(() => setHasAuthenticationErrors(true));
    };

    // If `clientID` is not defined in `config.json`, show a UI warning. 
    if (!authConfig?.clientID) {

        return (
            <div className="content">
                <h2>You need to update the Client ID to proceed.</h2>
                <p>Please open "src/config.json" file using an editor, and update
                    the <code>clientID</code> value with the registered application's client ID.</p>
                <p>Visit repo <a
                    href="https://github.com/asgardeo/asgardeo-auth-react-sdk/tree/master/samples/asgardeo-react-app">README</a> for
                    more details.</p>
            </div>
        );
    }

    function getName() {

        var userData = JSON.parse(JSON.stringify(userInfo));
        for(var data in userData) {
            if(data === "sub"){
                return (userData[data].charAt(0).toUpperCase() + userData[data].slice(1).toLowerCase());
            }
        }
    };

    return (
        state.isAuthenticated
            ? (
                <PhotoGallery name = {getName()} />
            )
            : (
                <DefaultLayout
                    isLoading = { state.isLoading }
                    hasErrors = { hasAuthenticationErrors }
                >
                    <div className = "content"  style = {{backgroundColor: "#eee"}}>
                        <div className = "home-image" >
                            <img src = { APP_LOGO } className = "react-logo-image logo" style = {{borderRadius: "30px"}}/>
                        </div>
                        <h1>
                            <b>photoGallery</b>
                        </h1>
                        <button
                            className = "loginButton"
                            onClick = {() => {
                                handleLogin();
                            }}
                        >
                            Login
                        </button>
                    </div>
                </DefaultLayout>
            )        
    );
};
