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

import React from 'react';
import QRCodeScanner from 'react-native-qrcode-scanner';
import { RNCamera } from 'react-native-camera';
import { useLoginContext } from "../../context/LoginContext";
import {AuthorizationService} from '@wso2/auth-qr-react-native';
import ReactNativeBiometrics from 'react-native-biometrics'

const QRScannerScreen = ({ navigation }) => {

    const { loginState, setLoginState, loading, setLoading } = useLoginContext();

    let onSuccess = (qrData) => {

        ReactNativeBiometrics.isSensorAvailable().then(resultObject => {
            const {available, biometryType} = resultObject;
          
            if (available && biometryType === ReactNativeBiometrics.TouchID) {
              console.log('TouchID is supported');
          
            } else if (available && biometryType === ReactNativeBiometrics.FaceID) {
              console.log('FaceID is supported');
          
            } else if (available && biometryType === ReactNativeBiometrics.Biometrics) {
              console.log('Biometrics is supported');
              try{
                ReactNativeBiometrics.simplePrompt({
                    promptMessage: 'Confirm fingerprint',
                })
                    .then(resultObject => {
                    const {success} = resultObject;
                
                    if (success) {
                        console.log('successful biometrics provided');
                        sendRequestToSDK(qrData);
                
                    } else {
                        console.log('user cancelled biometric prompt');
                    }
                    })
                    .catch(() => {
                    console.log('biometrics failed');
                    });
                }
                catch(e){
                    console.log("Device not Support Fingerprint")
                }
            } else {
                console.log('Biometrics not supported');
            }
        });
    };

    let sendRequestToSDK = (qrData) => {
        let authData = AuthorizationService.processAuthRequest(qrData.data);
        AuthorizationService.sendAuthRequest(authData, loginState.accessToken, 'SUCCESSFUL')
            .then((res) => {
                let response = JSON.parse(res);
                console.log(
                    'Authorization response: ' +
                    response.data.authenticationStatus,
                );

                if (response.res == 'OK') {
                    console.log(
                        'Activity data at success: ' + JSON.stringify(authData),
                    );
                }

                // navigation.navigate(
                //     response.res == 'OK' ? 'Main' : 'Authorization Failed',
                // );
            })
            .catch((err) => {
                console.log('Send auth error: ' + err);
            });
    };

    return (
        <QRCodeScanner
            onRead={onSuccess}
            showMarker={true}
            flashMode={RNCamera.Constants.FlashMode.off}
            cameraStyle={{
                marginTop: 10,
                height: 200,
                alignSelf: 'flex-end',
            }}
        />
    );
};

export default QRScannerScreen;
