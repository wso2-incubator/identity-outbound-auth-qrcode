# identity-outbound-auth-qrcode

Documentation for building and installing the qr-auth components in the IS.

[![Stackoverflow](https://img.shields.io/badge/Ask%20for%20help%20on-Stackoverflow-orange)](https://stackoverflow.com/questions/tagged/wso2is)
[![Join the chat at https://join.slack.com/t/wso2is/shared_invite/enQtNzk0MTI1OTg5NjM1LTllODZiMTYzMmY0YzljYjdhZGExZWVkZDUxOWVjZDJkZGIzNTE1NDllYWFhM2MyOGFjMDlkYzJjODJhOWQ4YjE](https://img.shields.io/badge/Join%20us%20on-Slack-%23e01563.svg)](https://join.slack.com/t/wso2is/shared_invite/enQtNzk0MTI1OTg5NjM1LTllODZiMTYzMmY0YzljYjdhZGExZWVkZDUxOWVjZDJkZGIzNTE1NDllYWFhM2MyOGFjMDlkYzJjODJhOWQ4YjE)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/wso2-extensions/identity-outbound-auth-push/blob/master/LICENSE)
[![Twitter](https://img.shields.io/twitter/follow/wso2.svg?style=social&label=Follow)](https://twitter.com/intent/follow?screen_name=wso2)

---

## Setup and Installing QR Authenticator

**Step 1:** Cloning the project

Clone the `identity-outbound-auth-qrcode` repository

**Step 2:** Building the project

Build the project by running `mvn clean install` at the root directory

**Step 3:** Deploying server components

- Go to `identity-outbound-auth-qrcode/components` →
    - `org.wso2.carbon.identity.application.authenticator.qrcode` → `target`
- Copy the `.jar` file
- Go to `<IS_HOME>/repository/components/dropins`
- Paste the `.jar` file into the dropins directory
- Alternatively it's possible to drag and drop the `.jar` file to the dropins directory
- Similarly, repeat the above steps for the components;
    - `org.wso2.carbon.identity.application.authenticator.qrcode.servlet`
    - `org.wso2.carbon.identity.application.authenticator.qrcode.common`
    
**Step 4:** Deploying QR Authentication Pages
- Go to `identity-outbound-auth-qrcode/components` →
    - `org.wso2.carbon.identity.application.authenticator.qrcode` → `src` → `main` → `resources` → `artifacts`
- Copy `qrpage.jsp`
- Go to `<IS_HOME>/repository/deployment/server/webapps` → `authenticationendpoint`
- Paste or drop the `JSP` files in the `authenticationendpoint` directory

**Step 5:**
- Go to `<IS_HOME>/repository/resources/conf/templates/repository/conf/identity`
- Open `identity.xml.j2`
- Scroll down to the “ResourceAccessControl” section

The following lines should be added for setting access control for qr-auth endpoints
```xml
<ResourceAccessControl>
    <Resource context="(.*)/qr-auth/authenticate" secured="true" http-method="POST" />
    <Resource context="(.*)/qr-auth/check-status" secured="true" http-method="GET" />
</ResourceAccessControl>
```

Add the following to allow multi-tenant support for endpoints

```xml
<TenantContextsToRewrite>
       <Servlet>
           {% for servlet in tenant_context.rewrite.servlets %}
           <Context>{{servlet}}</Context>
           {% endfor %}
           <Context>/qr-auth/(.*)</Context>
       </Servlet>
</TenantContextsToRewrite>
```


**NOTE: In order to communicate with WSO2 IS using a physical device (which will be required for developing an app
using the qrcode based authentication SDK), the hostname of the IS should be changed to the IP address of the machine
running the server.**

**Additionally, the keystore of the IS should be updated for the Android device to allow communication between the
physical device and WSO2 IS running locally.**
