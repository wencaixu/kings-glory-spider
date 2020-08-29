package com.kings.glory.crawler.http;

import okhttp3.Request;

import java.util.Map;

public interface Crawler<T,R> {

   String doGet(String uri, Map<T,R> headers);

   default void setHttpHeaders(Request.Builder builder, Map<T,R> headers){
      if(headers == null || headers.isEmpty()){
         return ;
      }
      for(Map.Entry<T,R> entry : headers.entrySet()){
         builder.addHeader(String.valueOf(entry.getKey()),String.valueOf(entry.getValue()));
      }
   }
}
