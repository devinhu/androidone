/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.sd.one.common.okhttp;

import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class RequestParams {

    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    protected ConcurrentHashMap<String, String> urlParams;
    protected ConcurrentHashMap<String, FileWrapper> fileParams;
    protected JSONObject jsonParams;

    public RequestParams(Map<String, String> source) {
        init();
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }


	public RequestParams(final String key, final String value) {
        this(new HashMap<String, String>() {{
            put(key, value);
        }});
    }


    public RequestParams(Object... keysAndValues) {
        init();
        int len = keysAndValues.length;
        if (len % 2 != 0)
            throw new IllegalArgumentException("Supplied arguments must be even");
        for (int i = 0; i < len; i += 2) {
            String key = String.valueOf(keysAndValues[i]);
            String val = String.valueOf(keysAndValues[i + 1]);
            put(key, val);
        }
    }


    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

    public void put(String key, int value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, float value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, double value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, boolean value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(JSONObject value) {
        if (value != null) {
           jsonParams = value;
        }
    }

    public void put(String key, File file) throws FileNotFoundException {
        if (key != null && file != null) {
            fileParams.put(key, new FileWrapper(file, file.getName(), guessMimeType(file.getName())));
        }
    }

    private MediaType guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            return MEDIA_TYPE_STREAM;
        }
        return MediaType.parse(contentType);
    }

    public void remove(String key) {
        urlParams.remove(key);
        fileParams.remove(key);
        jsonParams = null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            if (result.length() > 0){
                result.append("&");
            }

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }

        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            if (result.length() > 0){
                result.append("&");
            }
            result.append(entry.getKey());
            result.append("=");
            result.append("FILE");
        }

        return result.toString();
    }


    public RequestBody getRequestBody() throws IOException {
        if (fileParams.isEmpty()) {
            return createStrBody();
        } else if(jsonParams != null) {
            return createJsonBody();
        }else {
            return createMultipartBody();
        }
    }

    public RequestBody createStrBody() {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            bodyBuilder.add(entry.getKey(), entry.getValue());
        }
        return bodyBuilder.build();
    }

    public RequestBody createJsonBody() {
        return RequestBody.create(MEDIA_TYPE_JSON, jsonParams.toString());
    }

    private RequestBody createMultipartBody() throws IOException {
        MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        // Add string params
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            multipartBodybuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        // Add file params
        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            RequestBody fileBody = RequestBody.create(entry.getValue().contentType, entry.getValue().file);
            multipartBodybuilder.addFormDataPart(entry.getKey(), entry.getValue().fileName, fileBody);
        }

        return multipartBodybuilder.build();
    }

    private void init() {
        urlParams = new ConcurrentHashMap<String, String>();
        fileParams = new ConcurrentHashMap<String, FileWrapper>();
        jsonParams = null;
    }


    /**
     * 文件类型的包装类
     */
    public static class FileWrapper {
        public File file;
        public String fileName;
        public MediaType contentType;
        public long fileSize;

        public FileWrapper(File file, String fileName, MediaType contentType) {
            this.file = file;
            this.fileName = fileName;
            this.contentType = contentType;
            this.fileSize = file.length();
        }

        public String getFileName() {
            if (fileName != null) {
                return fileName;
            } else {
                return "nofilename";
            }
        }
    }

    protected String getParamString(String url) {
        try {
            if(urlParams != null){
                StringBuilder sb = new StringBuilder();
                sb.append(url);
                if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
                else sb.append("?");
                for (ConcurrentHashMap.Entry<String, String> param : urlParams.entrySet()) {
                    String urlValue = URLEncoder.encode(param.getValue(), "UTF-8");
                    sb.append(param.getKey()).append("=").append(urlValue).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
