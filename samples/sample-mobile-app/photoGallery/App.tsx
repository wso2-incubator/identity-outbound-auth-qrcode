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

import React from 'react';
import { AuthProvider } from "@asgardeo/auth-react-native";
import { LoginContextProvider } from "./context/LoginContext";
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import LoginScreen from './src/screens/LoginScreen';
import GalleryScreen from './src/screens/GalleryScreen';
import QRScannerScreen from './src/screens/QRScannerScreen';
import HomeScreen from './src/screens/HomeScreen';
import Icon from "react-native-vector-icons/FontAwesome";
import {StyleSheet, StatusBar, Button, View, Text} from 'react-native';
import MainScreen from './src/screens/MainScreen';
import {DeviceInfoUtil} from '@wso2/auth-qr-react-native';

const Stack = createNativeStackNavigator();

// Initialize device information
new DeviceInfoUtil();

const App = () => {
  return (
  <>
  <AuthProvider>
    <LoginContextProvider>
      <NavigationContainer>
        <Stack.Navigator initialRouteName = "Login" headerMode = "none">
          <Stack.Screen name = "Login" component = {LoginScreen} options={ { headerShown: false } } />
           <Stack.Screen name="MainScreen" component={ MainScreen } options={ { headerShown: false } }
           />
        </Stack.Navigator>
      </NavigationContainer>
    </LoginContextProvider>
  </AuthProvider>
  </>
  );
};

export default App;
