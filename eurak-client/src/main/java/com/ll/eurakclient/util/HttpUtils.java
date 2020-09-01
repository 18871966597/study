package com.ll.eurakclient.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpUtils {
    static BasicCookieStore cookieStore = new BasicCookieStore();// cookie存储器

    /**
     * get请求
     *
     * @param uri  请求地址
     * @param nvps 请求参数
     * @return 响应内容
     * @throws IOException
     */
    public static String getRequest(String uri, NameValuePair... nvps) throws IOException {
        return getRequest(uri, null, Arrays.asList(nvps));
    }

    /**
     * get请求
     *
     * @param uri     请求地址
     * @param headers 自定义请求头
     * @return 响应内容
     * @throws IOException
     */
    public static String getRequest(String uri, Header... headers) throws IOException {
        return getRequest(uri, Arrays.asList(headers), new ArrayList<NameValuePair>());
    }

    /**
     * get请求
     *
     * @param uri     请求地址
     * @param headers 自定义请求头
     * @param nvps    请求参数
     * @return 响应内容
     * @throws IOException
     */
    public static String getRequest(String uri, List<Header> headers, List<NameValuePair> nvps) throws IOException {
        return commonRequest(RequestBuilder.get(uri), new UrlEncodedFormEntity(nvps), headers);
    }

    /**
     * post请求
     *
     * @param uri  请求地址
     * @param nvps 请求参数
     * @return 响应内容
     * @throws IOException
     */
    public static String postRequest(String uri, NameValuePair... nvps) throws IOException {
        return postRequest(uri, null, Arrays.asList(nvps));
    }

    /**
     * post请求
     *
     * @param uri     请求地址
     * @param headers 自定义请求头
     * @param nvps    请求参数
     * @return 响应内容
     * @throws IOException
     */
    public static String postRequest(String uri, List<Header> headers, List<NameValuePair> nvps) throws IOException {
        return commonRequest(RequestBuilder.post(uri), new UrlEncodedFormEntity(nvps), headers);
    }

    /**
     * post 请求
     *
     * @param uri     请求地址
     * @param jsonStr 请求的json数据
     * @return 响应内容
     * @throws IOException
     */
    public static String postRequest(String uri, String jsonStr) throws IOException {
        return postRequest(uri, null, jsonStr);
    }


    /**
     * post请求
     *
     * @param uri     请求地址
     * @param headers 自定义请求头
     * @param jsonStr 请求的json数据
     * @return 响应内容
     * @throws IOException
     */
    public static String postRequest(String uri, List<Header> headers, String jsonStr) throws IOException {
        return commonRequest(RequestBuilder.post(uri), new StringEntity(jsonStr, ContentType.APPLICATION_JSON), headers);
    }


    /**
     * 公共的请求方法，get和post请求通用
     *
     * @param requestBuilder 请求构建器
     * @param requestEntity  请求体
     * @param headers        自定义的请求头
     * @return 响应内容
     * @throws IOException
     */
    private static String commonRequest(RequestBuilder requestBuilder, HttpEntity requestEntity, List<Header> headers) throws IOException {
        String result = "";
        CloseableHttpClient client = HttpClients.custom() // 获取一个可以自定义配置的客户端构建器
                .setDefaultCookieStore(cookieStore) // 设置默认的cookie存储器
                .build();// 构建一个客户端对象

        // 如果有自定义请求头，就将自定义请求头添加到请求构建器中
        if (headers != null) {
            for (Header header : headers) {
                requestBuilder.setHeader(header);
            }
        }

        requestBuilder.setEntity(requestEntity); // 设置请求体
        HttpUriRequest request = requestBuilder.build(); // 构建请求对象
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request); // 执行请求
            result = EntityUtils.toString(response.getEntity()); // 获取响应体
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
        return result;
    }
}
