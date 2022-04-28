/**
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.com).
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

 import { useAuthContext } from "@asgardeo/auth-react-native";
 import React from "react";
 import { ActivityIndicator, Text, View } from "react-native";
 import { styles } from "../components/stylesheet";
 import { useLoginContext } from "../../context/LoginContext";
 import { Button, ButtonContainer } from "../components";

 // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types
 const HomeScreen = () => {
 
     const { loginState, setLoginState, loading, setLoading } = useLoginContext();
     const { state, signOut, refreshAccessToken } = useAuthContext();
 console.log(loginState.idToken);
     /**
      * This function will handle the refresh button click.
      */
     const handleRefreshtoken = async () => {
 
         setLoading(true);
         refreshAccessToken()
             .catch((error) => {
                 setLoading(false);
                 // eslint-disable-next-line no-console
                 console.log(error);
             });
     };
 
     /**
      * This function will handle the sign out button click.
      */
     const handleSignOut = async () => {
 
         setLoginState({
             ...loginState, ...state, hasLogoutInitiated: true
         });
 
         signOut()
             .catch((error) => {
                 setLoading(false);
                 // eslint-disable-next-line no-console
                 console.log(error);
             });
     };
 
     return (
         <View style = { styles.flexContainer }>
             <View style = { styles.flex }>
                 <View>
                     <Text style = { styles.flexHeading }>
                       Hi { loginState.username } !
                     </Text>
                     <Text style = { styles.flexBody }>
                       AllowedScopes : { loginState.allowedScopes }
                     </Text>
                     <Text style = { styles.flexBody }>SessionState : </Text>
                     <Text style = { styles.flexDetails }>
                         { loginState.sessionState }
                     </Text>
                 </View>
             </View>
 
             <View style = { styles.flex }>
                 <View>
                     <Text style = { styles.flexHeading }>Refresh token</Text>
                     <Text style = { styles.refToken }>{ loginState.refreshToken }</Text>
                 </View>
             </View>
 
             <View style = { styles.flex }>
                 <View>
                     <Text style = { styles.flexHeading }>Decoded ID token</Text>
                     <Text style = { styles.body }>amr : { loginState.amr }, { "\n" }at_hash : 
                         { loginState.at_hash }, { "\n" }aud: { loginState.aud }, { "\n" }azp : 
                         { loginState.azp }, { "\n" }c_hash : { loginState.c_hash }, { "\n" }exp : 
                         { loginState.exp }, { "\n" }iat : { loginState.iat }, { "\n" }iss : 
                         { loginState.iss }, { "\n" }nbf :  { loginState.nbf }, { "\n" }sub : 
                         { loginState.sub }</Text>
                 </View>
             </View>
 
             {
                 loading ? 
                     (<View style={ styles.loading } pointerEvents="none">
                         <ActivityIndicator size="large" color="#FF8000" />
                     </View>) : null
             }

            <ButtonContainer>
                <Button onPress = { handleSignOut } text = "SignOut" color = "#FF8000" />
                <Button onPress = { handleRefreshtoken } text = "Refresh" color = "#FF3333" />
            </ButtonContainer>

         </View>
     );
 };
 
 export default HomeScreen;
 