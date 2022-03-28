# identity-outbound-auth-qrcode

Documentation explaining the architecture of the QR code based authenticator.

[![Stackoverflow](https://img.shields.io/badge/Ask%20for%20help%20on-Stackoverflow-orange)](https://stackoverflow.com/questions/tagged/wso2is)
[![Join the chat at https://join.slack.com/t/wso2is/shared_invite/enQtNzk0MTI1OTg5NjM1LTllODZiMTYzMmY0YzljYjdhZGExZWVkZDUxOWVjZDJkZGIzNTE1NDllYWFhM2MyOGFjMDlkYzJjODJhOWQ4YjE](https://img.shields.io/badge/Join%20us%20on-Slack-%23e01563.svg)](https://join.slack.com/t/wso2is/shared_invite/enQtNzk0MTI1OTg5NjM1LTllODZiMTYzMmY0YzljYjdhZGExZWVkZDUxOWVjZDJkZGIzNTE1NDllYWFhM2MyOGFjMDlkYzJjODJhOWQ4YjE)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/wso2-incubator/identity-outbound-auth-qrcode/blob/main/LICENSE)
[![Twitter](https://img.shields.io/twitter/follow/wso2.svg?style=social&label=Follow)](https://twitter.com/intent/follow?screen_name=wso2)

---


## Contents
- [Introduction](#introduction)
- [Components](#components)
    - [Authenticator Component](#authenticator-component)
    - [Servlet Component](#servlet-component)
    - [Common Component](#common-component)
- [Architecture](#architecture)
- [Sample Requests](#sample-requests)


## Introduction

With the advancement of technology and the increasing focus on the quality of user experience, some businesses tend to
make a web version for their application, along with the mobile app. In these kinds of applications, a user’s mobile
phone (with an active data connection) can act as a helper device to authenticate the user to access the web
application by scanning the QR code displayed in the web login screen by using the scanner in the mobile app.
Therefore, this QR code authentication will balance the usability and security without affecting user privacy.



## Components

### Authenticator Component
The QR authenticator component is responsible for handling a user’s authentication requests. Once the user selects the  
"Login with QR" option, the QR authenticator gets triggered as the initial component to start a request.

Once the QR code is generated and displayed in the login page, the user scans it using the mobile app in his/her
mobile device and provide biometrics. Then the user authorizes or denies the request it’s received, and sends the response to
the authentication endpoint. A polling mechanism checks regularly to validate if the user has responded to the request. 
If a record persists, the authentication flow is continued and the user is authenticated to access the required service.

### Servlet Component
The servlet component handles the API requests related to authentication. All the endpoints called for authentication
are registered at this component and all related operations are completed by it. The operations  covered are;
- Receiving an authentication response from a device and storing the data
- Polling requests for validating if the authentication response has been received

### Common Component
The common component handles functionalities that are required by the authenticator and the servlet components. The
functionalities handled are;
- Storing authentication context information in cache for the authentication flow

## Architecture

Given below is a high level architecture diagram of all the functionalities handled by the QR authenticator components
at different levels.

<img src="C:\Users\isurika\Downloads\sequence diagram.drawio (6).png"/>

## Sample Requests
Requests used for authentication are as follows

#### Authentication Request
| POST | https://{host}/qr-auth/authenticate |
|------|---------------------------------------|
| Purpose | Authentication request sent from the mobile device |
| Content Type | application/json |
| Request Body |
```javascript
const authResponse = {
    sessionDataKey: "b03f90c9-6723-48f6-863b-a35f1ac77f57",
    tenantDomain: "carbon.super",
    clientID: "zCoVxvKyxRtKTytoszh7kffTKV8a"
}
```
| Responses | - |
|-----------|---|
| 201 | Accepted|
| 400 | Bad Request |
| 401 | Unauthorized |
| 500 | Internal Server Error |

#### Check status request
| GET | https://{host}/qr-auth/check-status?sessionDataKey={sessionDataKey} |
|-----|----|
| Purpose | Check if the authentication request from the mobile device was received|
|Responses|-|
|200|OK <br/><br/>{<br/>"status": "COMPLETED"<br/>}
|400|Bad Request|
|401|Unauthorized|
|500|Internal Server Error|
