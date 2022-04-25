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

package org.wso2.carbon.identity.application.authenticator.qrcode;

import org.mockito.ArgumentCaptor;
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
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.AuthenticationFailedException;
import org.wso2.carbon.identity.application.authentication.framework.inbound.InboundConstants;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.QRAuthContextManager;
import org.wso2.carbon.identity.application.authenticator.qrcode.common.impl.QRAuthContextManagerImpl;
import org.wso2.carbon.identity.application.authenticator.qrcode.dto.AuthDataDTO;
import org.wso2.carbon.identity.core.ServiceURL;
import org.wso2.carbon.identity.core.ServiceURLBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

import static junit.framework.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.testng.Assert.assertThrows;

@PrepareForTest({QRAuthContextManagerImpl.class})
public class QRAuthenticatorTest {

    @Mock
    private QRAuthenticator mockedQRAuthenticator;

    @Spy
    private QRAuthenticator spy;

    private QRAuthenticator qrAuthenticator;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Spy
    private AuthenticationContext context;

    @Spy
    private AuthenticationContext mockedContext;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private AuthDataDTO authDataDTO;

    @Mock
    private QRAuthContextManagerImpl qrAuthContextManagerImpl;

    @Mock
    private ServiceURLBuilder serviceURLBuilder;

    @Mock
    private ServiceURL serviceURL;

    private AuthDataDTO authData;

    @BeforeMethod
    public void setUp() {
        qrAuthenticator = new QRAuthenticator();
        authData = new AuthDataDTO();
        initMocks(this);
    }

//    @Test(description = "Test case for canHandle() method true case.")
//    public void testCanHandle() throws Exception {
//
//        when(httpServletRequest.getParameter(QRAuthenticatorConstants.PROCEED_AUTH)).thenReturn("213432");
//        Assert.assertEquals(qrAuthenticator.canHandle(httpServletRequest), true);
//    }
//
//    @Test(description = "Test case for canHandle() method false case.")
//    public void testCanHandleFalse() throws Exception {
//
//        when(httpServletRequest.getParameter(QRAuthenticatorConstants.PROCEED_AUTH)).thenReturn(null);
//        Assert.assertEquals(qrAuthenticator.canHandle(httpServletRequest), false);
//    }
//
//    @Test(description = "Test case for getContextIdentifier() method.")
//    public void testGetContextIdentifier() {
//
//        when(httpServletRequest.getRequestedSessionId()).thenReturn("234567890");
//        when(httpServletRequest.getParameter("sessionDataKey")).thenReturn("234567890");
//        Assert.assertEquals(qrAuthenticator.getContextIdentifier(httpServletRequest), "234567890");
//
//        when(httpServletRequest.getRequestedSessionId()).thenReturn("234567890");
//        when(httpServletRequest.getParameter("sessionDataKey")).thenReturn(null);
//        Assert.assertNull(qrAuthenticator.getContextIdentifier(httpServletRequest));
//    }

//    @Test(description = "Test case for initiateAuthenticationRequest()")
//    public void testInitiateAuthenticationRequest() throws Exception {
//
//        when(httpServletRequest.getParameter(InboundConstants.RequestProcessor.CONTEXT_KEY)).thenReturn(null);
//        Whitebox.invokeMethod(mockedQRAuthenticator, "initiateAuthenticationRequest",
//                httpServletRequest, httpServletResponse, context);
//    }

//    @Test(description = "Test case for processAuthenticationResponse() method with username and tenant domain.")
//    public void testProcessAuthenticationResponse() throws Exception {
//        AuthenticationContext authContext = new AuthenticationContext();
//        QRAuthContextManager contextManager = spy(new QRAuthContextManagerImpl());
//       // whenNew(QRAuthContextManagerImpl.class).withNoArguments().thenReturn(qrAuthContextManagerImpl);
//
//        when(httpServletRequest.getParameter(Mockito.anyString())).thenReturn("234567890");
//        when( contextManager.getContext(Mockito.anyString())).thenReturn(mockedContext);
//
//        authData.setUsername("admin");
//        authData.setTenantDomain(QRAuthenticatorConstants.SUPER_TENANT_DOMAIN);
//        when(mockedContext.getProperty(QRAuthenticatorConstants.CONTEXT_AUTH_DATA)).thenReturn(authData);
//        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
//        authenticatedUser.setUserName("admin");
//        authenticatedUser.setTenantDomain(QRAuthenticatorConstants.SUPER_TENANT_DOMAIN);
//        Whitebox.invokeMethod(qrAuthenticator, "processAuthenticationResponse", httpServletRequest, httpServletResponse, context);
//        Assert.assertEquals(context.getSubject(), authenticatedUser);




//            whenNew(QRAuthContextManagerImpl.class).withNoArguments().thenReturn(qrAuthContextManagerImpl);
//            when(httpServletRequest.getParameter(QRAuthenticatorConstants.SESSION_DATA_KEY)).thenReturn("234567890");
//            when(qrAuthContextManagerImpl.getContext("234567890")).thenReturn(mockedContext);
//            AuthenticationContext sessionContext = qrAuthContextManagerImpl.getContext("234567890");
//            when(sessionContext.getProperty(QRAuthenticatorConstants.CONTEXT_AUTH_DATA)).thenReturn(authData);
//
//            authData.setUsername("admin");
//            authData.setTenantDomain(QRAuthenticatorConstants.SUPER_TENANT_DOMAIN);
 //           qrAuthenticator.processAuthenticationResponse(httpServletRequest, httpServletResponse, context);
//    }

//    @Test(description = "Test case for processAuthenticationResponse() method with username and tenant domain set to null.")
//    public void testProcessAuthenticationResponseNullUser() throws Exception {
//
//        try{
//            authData.setUsername(null);
//            authData.setTenantDomain(null);
//
//            whenNew(QRAuthContextManagerImpl.class).withNoArguments().thenReturn(qrAuthContextManagerImpl);
//
//            mockedContext.setProperty(QRAuthenticatorConstants.CONTEXT_AUTH_DATA, authData);
//            qrAuthContextManagerImpl.storeContext("234567890", mockedContext);
//
//            when(httpServletRequest.getParameter(QRAuthenticatorConstants.SESSION_DATA_KEY)).thenReturn("234567890");
//
//            when(qrAuthContextManagerImpl.getContext(Mockito.anyString())).thenReturn(mockedContext);
//            AuthenticationContext sessionContext = qrAuthContextManagerImpl.getContext("234567890");
//            when(sessionContext.getProperty(QRAuthenticatorConstants.CONTEXT_AUTH_DATA)).thenReturn(authData);
//
//            qrAuthenticator.processAuthenticationResponse(httpServletRequest, httpServletResponse, context);
//            fail( "Missing exception" );
//        } catch( AuthenticationFailedException e ) {
//            Assert.assertEquals("Error occurred while retrieving user data.", e.getMessage());
//        }
//    }
//
//    @Test(description = "Test case for retryAuthenticationEnabled() method.")
//    public void testRetryAuthenticationEnabled() {
//
//        Assert.assertEquals(qrAuthenticator.retryAuthenticationEnabled(), true);
//    }
//
//    @Test(description = "Test case for getFriendlyName() method.")
//    public void testGetFriendlyName() {
//
//        Assert.assertEquals(qrAuthenticator.getFriendlyName(),
//                QRAuthenticatorConstants.AUTHENTICATOR_FRIENDLY_NAME);
//    }
//
//    @Test(description = "Test case for getName() method.")
//    public void testGetName() {
//
//        Assert.assertEquals(qrAuthenticator.getName(), QRAuthenticatorConstants.AUTHENTICATOR_NAME);
//    }
}