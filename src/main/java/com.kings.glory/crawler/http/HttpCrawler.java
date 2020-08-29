package com.kings.glory.crawler.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.HttpStatusException;


import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static com.kings.glory.crawler.http.StatusMsg.http_error;

/**
 * @author wencai.xu
 */
public class HttpCrawler implements Crawler<String,String> {

    private OkHttpClient httpClient = new OkHttpClient();

    private static HttpCrawler instance = new HttpCrawler();

    @Override
    public String doGet(String uri, Map<String,String> headers) {
        assert uri != null;
        Request.Builder httpBuilder = new Request.Builder();
        // 设置请求头部
        setHttpHeaders(httpBuilder,headers);
        Request request = httpBuilder.url(uri).build();
        Response response;
        String page = "";
        try{
            response = httpClient.newCall(request).execute();
            if(!response.isSuccessful()){
                throw new HttpStatusException(http_error.getMsg(),response.code(),uri);
            }
            ResponseBody responseBody = response.body();
            if(Objects.nonNull(responseBody)){
                byte[] bytes = responseBody.bytes();
                page = new String(bytes,Charsets.GB2312.name());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    public static HttpCrawler getInstance() {
        return instance;
    }
}
