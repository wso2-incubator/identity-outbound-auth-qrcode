/*
 *  Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */


package org.wso2.carbon.identity.application.authenticator.qrcode.servlet.servlet;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static junit.framework.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.testng.Assert.assertThrows;

import junit.framework.TestCase;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authenticator.qrcode.QRAuthenticator;
import org.wso2.carbon.identity.application.authenticator.qrcode.dto.AuthDataDTO;
import org.wso2.carbon.identity.application.authenticator.qrcode.servlet.QRServletConstants;
import org.wso2.carbon.user.core.util.UserCoreUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@PrepareForTest({UserCoreUtil.class})
public class QRServletTest{

    @Mock
    private QRServlet mockedQRServlet;

    private QRServlet qrServlet;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Spy
    private AuthenticationContext context;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private  UserCoreUtil userCoreUtil;

    @BeforeMethod
    public void setUp() {

        qrServlet = new QRServlet();
        initMocks(this);
    }

//    @Test
//    public void testHandleMobileResponse() throws Exception {
//
//        JsonObject authResponse = new JsonObject();
//        authResponse.addProperty(QRServletConstants.SESSION_DATA_KEY, "234567890");
//        authResponse.addProperty(QRServletConstants.TENANT_DOMAIN, "carbon.super");
//        authResponse.addProperty(QRServletConstants.CLIENT_ID, "abcdefghijklmnopqrs");
//        httpServletRequest.setAttribute(QRServletConstants.AUTH_RESPONSE,authResponse);
//        when("getTenantQualifiedUsername").thenReturn("admin@carbon.super");
//        Whitebox.invokeMethod(qrServlet, "handleMobileResponse", httpServletRequest, httpServletResponse);
//    }

//    @Test
//    public void testGetTenantQualifiedUsername() throws Exception {
//
//        when(userCoreUtil.addTenantDomainToEntry(Mockito.anyString(), Mockito.anyString()))
//                .thenReturn("admin@carbon.super");
//        Assert.assertEquals(Whitebox.invokeMethod(qrServlet, "getTenantQualifiedUsername"),
//                "admin@carbon.super");
//    }
}