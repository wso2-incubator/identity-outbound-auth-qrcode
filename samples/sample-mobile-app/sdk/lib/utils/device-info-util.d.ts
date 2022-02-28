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
/**
 * Util class for handling device information.
 */
export declare class DeviceInfoUtil {
    private static deviceName;
    private static deviceBrand;
    private static deviceModel;
    constructor();
    /**
     * Returns the name of the device.
     *
     * @returns deviceName - Name of the device
     */
    static getDeviceName(): string;
    /**
     * Returns the model name of the device.
     *
     * @returns deviceModel - Model of the device
     */
    static getDeviceModel(): string;
    /**
     * Returns the brand name of the device.
     *
     * @returns deviceBrand - Brand name of the device
     */
    static getDeviceBrand(): string;
}
