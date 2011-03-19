package com.guaranacode.android.libs.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.guaranacode.android.libs.string.StringUtil;

/**
 * Performs HTTP operations.
 * 
 * @author abraao@guaranacode.com
 */
public final class HttpClient {
    /**
     * The encoding to use when encoding URLs
     */
    private static final String URL_ENCODING = "UTF-8";
    
    /**
     * Flag that represents the HTTP GET method.
     */
    private static final int HTTP_GET = 1;
    
    /**
     * Flag that represents the HTTP POST method.
     */
    private static final int HTTP_POST = 2;
    
    /**
     * Perform a HTTP get with the given parameters.
     * @param url
     * @param params
     * @param contentType   The content type of this HTTP request (optional).
     * @return
     */
    public static String get(String url, Map<String, String> params, String contentType) {
        return httpOperation(HTTP_GET, url, params, null, contentType);
    }
    
    /**
     * Perform a HTTP post with the given body.
     * @param url
     * @param body
     * @param contentType   The content type of this HTTP request (optional).
     * @return
     */
    public static String post(String url, String body, String contentType) {
        return httpOperation(HTTP_POST, url, null, body, contentType);
    }
    
    /**
     * Performs the specified HTTP operation with the given data. One of params or body must be specified.
     * @param httpMethod
     * @param url
     * @param params    The params used for a HTTP GET (optional).
     * @param body  The body used in a HTTP POST (optional).
     * @param contentType   The content type of this HTTP request (optional).
     */
    private static String httpOperation(int httpMethod, String url, Map<String, String> params, String body, String contentType) {
        if(StringUtil.isNullOrEmpty(url)) {
            return null;
        }
        
        String result = null;
        String fullUrl;
        
        final String queryString = getEncodedQueryString(params);

        if(!StringUtil.isNullOrEmpty(queryString)) {
            char joinChar = '?';
            
            if(url.indexOf('?') > 0) {
                joinChar = '&';
            }

            fullUrl = url + joinChar + queryString;
        }
        else {
            fullUrl = url;
        }
        
        Log.i("HttpClient", "HTTP method " + httpMethod + " to " + fullUrl);
        
        try {            
            final DefaultHttpClient client = new DefaultHttpClient();
            
            final HttpUriRequest method;
            if(HTTP_GET == httpMethod) {
                method = new HttpGet(new URI(fullUrl));
            }
            else if(HTTP_POST == httpMethod) {
                HttpPost postMethod = new HttpPost(new URI(fullUrl));
                postMethod.setEntity(new StringEntity(body));

                method = postMethod;
            }
            else {
                return null;
            }

            if(!StringUtil.isNullOrEmpty(contentType)) {
                method.addHeader("Content-Type", contentType);
            }
            
            final HttpResponse response = client.execute(method);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                final HttpEntity e = response.getEntity();
                
                if (null != e) {
                    result = StringUtil.fromStream(e.getContent());
                }
            }
        } catch (Exception e) {
            Log.e("HttpClient", "Error in performing HTTP to URL " + fullUrl, e);
        }
        
        Log.d("HttpClient", "HTTP get response " + result);
        
        return result;
    }
    
    /**
     * Decodes text using the appropriate encoding.
     * @param text
     * @return
     */
    public static String decodeText(String text) {
        if(StringUtil.isNullOrEmpty(text)) {
            return null;
        }
        
        try {
            return URLDecoder.decode(text, URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            Log.e("HttpClient", String.format("Error decoding text to %s: %s", text, URL_ENCODING), e);
            return null;
        }
    }

    /**
     * Encodes a map of strings into an ampersand delimited string that is URL encoded.
     * @param params
     * @return
     */
    private static String getEncodedQueryString(Map<String, String> params) {
        if(null == params || params.size() == 0) {
            return null;
        }
        
        final StringBuilder sb = new StringBuilder();
        
        try {
            for(String key : params.keySet()) {
                    sb.append(String.format("%s=%s&", URLEncoder.encode(key, URL_ENCODING), URLEncoder.encode(params.get(key))));
            }
        } catch (UnsupportedEncodingException e) {
            Log.e("HttpClient", String.format("Error encoding query params to %s: %s", params.toString(), URL_ENCODING), e);
        }

        return sb.toString();
    }
}
