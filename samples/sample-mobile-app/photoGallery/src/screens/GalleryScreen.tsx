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
import {View, StyleSheet, StatusBar} from 'react-native';
import GridImageView from 'react-native-grid-image-viewer';

const GalleryScreen = ({ navigation }) => {

    return (
    <>
    <StatusBar barStyle = {'dark-content'} />
      <View style = {styles.background}>
      <GridImageView data = {[
       'https://source.unsplash.com/2ShvY8Lf6l0/800x599',
       'https://source.unsplash.com/Dm-qxdynoEc/800x799',
       'https://source.unsplash.com/qDkso9nvCg0/600x799',
       'https://source.unsplash.com/iecJiKe_RNg/600x799',
       'https://source.unsplash.com/epcsn8Ed8kY/600x799',
       'https://source.unsplash.com/NQSWvyVRIJk/800x599',
       'https://source.unsplash.com/zh7GEuORbUw/600x799',
       'https://source.unsplash.com/PpOHJezOalU/800x599',
       'https://source.unsplash.com/I1ASdgphUH4/800x599']} />
      </View>
      </>
    );
};

const styles = StyleSheet.create({
  background: {
    backgroundColor: 'black',
    flex: 1
  },
});

export default GalleryScreen;
