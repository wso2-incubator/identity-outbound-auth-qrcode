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

import { AuthProvider } from "@asgardeo/auth-react";
import React, { FunctionComponent, ReactElement } from "react";
import { render } from "react-dom";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./app.css";
import { default as authConfig } from "./config.json";
import { HomePage, NotFoundPage } from "./pages";
import { LoginInfo } from "./pages/LoginInfo";

const App: FunctionComponent<{}> = (): ReactElement => (
    <AuthProvider config={authConfig}>
        <Router>
            <Switch>
                <Route exact path="/" component={HomePage} />
                <Route exact path="/Home" component={HomePage} />
                <Route exact path="/LoginInfo" component={LoginInfo} />
                <Route component={NotFoundPage} />
            </Switch>
        </Router>
    </AuthProvider>
);

render((<App />), document.getElementById("root"));
