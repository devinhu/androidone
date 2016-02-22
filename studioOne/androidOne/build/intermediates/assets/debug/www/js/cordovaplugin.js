/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var phone_gap = {

    // Application Constructor
    initialize: function () {
        phone_gap.bindEvents();
    },

    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function () {
        document.addEventListener("deviceready", phone_gap.onDeviceReady, false);
    },

    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicity call 'app.receivedEvent(...);'
    onDeviceReady: function () {
        document.addEventListener("backbutton", phone_gap.onBackKeyDown, false);
    },

    //手机物理键返回按钮处理
    onBackKeyDown: function () {
        phone_gap.back();
    },

    /**
     * [检查网络]
     * sucCallback: 成功回调，返回true
     * failCallback：失败回调
     **/
    checkNetWork: function (sucCallback, failCallback) {
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        cordova.exec(function () {
        }, function () {
        }, "DemoPlugin", "checkNetWork", []);
    },

    /**
     * [判断是否登陆]
     * sucCallback: 成功回调，返回true
     * failCallback：失败回调
     **/
    isLogin: function (sucCallback, failCallback) {
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "isLogin", []);
    },

    /**
     * [获取登陆信息]
     * sucCallback: 成功回调，返回结果
     * failCallback：失败回调
     **/
    getLoginInfo: function (sucCallback, failCallback) {
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "getLoginInfo", []);
    },

    /**
     * [web页面跳转]
     * url:跳转url
     * params: 参数
     * sucCallback: 成功回调
     * failCallback：失败回调
     **/
    showpage: function (url, params, sucCallback, failCallback) {
        var url = url || "";
        var params = params || "";
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "showpage", [url, params]);
    },


    /**
     * [Native页面跳转]
     * activity: 页面名称，如LoginActivity
     * params: 参数
     * sucCallback: 成功回调
     * failCallback：失败回调
     **/
    startActivity: function (activity, params, sucCallback, failCallback) {
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        var activity = activity || "";
        var params = params || "";
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "startActivity", [activity, params]);
    },


    /**
     * [显示头部]
     * title: 页面标题
     * btnRight: 右边按钮文字
     * btnRightImg: 右边按钮背景图片
     * sucCallback: 成功回调
     * failCallback：失败回调
     **/
    showhead: function (title, btnRight, btnRightImg, sucCallback, failCallback) {
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        var title = title || "";
        var btnRight = btnRight || "";
        var btnRightImg = btnRightImg || "";
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "showhead", [title, btnRight, btnRightImg]);
    },

    /**
     * [提示消息]
     * message: 消息
     * sucCallback: 成功回调
     * failCallback：失败回调
     **/
    toast: function (message) {
        cordova.exec(function () {
        }, function () {
        }, "DemoPlugin", "toast", [message]);
    },

    /**
     * [弹出框]
     * message: 消息
     * sucCallback: 成功回调
     * failCallback：失败回调
     **/
    alertDialog: function (message, sucCallback, failCallback) {
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "alertDialog", [message]);
    },


    /**
     * [隐藏键盘]
     * sucCallback: 成功回调
     * failCallback：失败回调
     **/
    hideSoftInput: function (sucCallback, failCallback) {
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "hideSoftInput", []);
    },


    /**
     * [返回]
     * url: 指定返回页面url，web页面一般为锚点
     * sucCallback: 成功回调
     * failCallback：失败回调
     **/
    back: function (url, sucCallback, failCallback) {
        if (/order/.test(location.href)) {
            var url = "order";
        } else if (/shoppingcart/.test(location.href)) {
            var url = "shoppingcart";
        } else if (/fanvirite/.test(location.href)) {
            var url = "fanvirite";
        } else {
            var url = ""
        }
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "back", [url]);
    },

    /**
     * [退出系统]
     * sucCallback: 成功回调
     * failCallback：失败回调
     **/
    exit: function (sucCallback, failCallback) {
        var sucCallback = sucCallback || function () {
            };
        var failCallback = failCallback || function () {
            };
        cordova.exec(sucCallback, failCallback, "DemoPlugin", "exit", []);
    }

};

window.phoneGap = phone_gap;
