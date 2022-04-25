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

import React, { ReactElement, useState, useCallback } from "react";
import APP_LOGO from "../images/login.jpg";
import Gallery from "react-photo-gallery";
import Carousel, { Modal, ModalGateway } from "react-images";
import { photos } from "../images/photos";
import Navbar from "../components/Navbar";

 export const PhotoGallery = (props): ReactElement => {

    const [currentImage, setCurrentImage] = useState(0);
    const [viewerIsOpen, setViewerIsOpen] = useState(false);

    const openLightbox = useCallback((event, { photo, index }) => {
        setCurrentImage(index);
        setViewerIsOpen(true);
    }, []);

    const closeLightbox = () => {
        setCurrentImage(0);
        setViewerIsOpen(false);
    };


     return (
         <>
            <Navbar name = {props.name} />
            <div className = "heading">
                <img src = { APP_LOGO } style = {{paddingRight: "10px", height: "83px"}}/>
                <p className = "app-name">photoGallery</p>
            </div>
            <div className = "content" style = {{backgroundColor: "#ccc"}}>
                <Gallery photos = {photos} onClick = {openLightbox} />
                <ModalGateway>
                    {viewerIsOpen ? (
                    <Modal onClose = {closeLightbox}>
                        <Carousel
                        currentIndex = {currentImage}
                        views = {photos.map(x => ({
                            ...x,
                            srcset: x.srcSet,
                            caption: x.title
                        }))}
                        />
                    </Modal>
                    ) : null}
                </ModalGateway>
            </div>
         </>
     );
 };
 