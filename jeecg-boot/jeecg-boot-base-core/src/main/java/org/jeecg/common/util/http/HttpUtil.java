package org.jeecg.common.util.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jeecg.common.util.PropertiesFileUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpClient工具类
 */
public class HttpUtil {
    public static final String HEADER_X_REAL_IP = "x-real-ip";
    public static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";
    public static final String HEADER_X_REAL_HOST = "x-real-host";
    public static final String HEADER_X_REAL_CONTEXT = "x-real-context";
    public static final String HEADER_REAL_CONTEXT_PATH = "real-context-path";
    public static final String HEADER_HOST = "host";
    public static final String LOCALHOST_IP = "127.0.0.1";
    /**
     * 发送get请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws IOException
     */
    public static ResponseData get(String url, Map<String, Object> params, Map<String, Object> headers, String proxyHost, Integer port) throws Exception{
        return get(url, params, headers, HttpConstant.CONNECT_TIMEOUT, HttpConstant.SOCKET_TIMEOUT, HttpConstant.CONNECT_REQ_TIMEOUT, proxyHost, port);
    }

    /**
     * 获取GET返回JSONObject
     * @param url
     * @param params
     * @param headers
     * @param proxyHost
     * @param port
     * @return
     */
    public static JSONObject getForJSONObject(String url, Map<String, Object> params, Map<String, Object> headers, String proxyHost, Integer port) throws Exception{
        ResponseData resp=get(url, params, headers, HttpConstant.CONNECT_TIMEOUT, HttpConstant.SOCKET_TIMEOUT, HttpConstant.CONNECT_REQ_TIMEOUT, proxyHost, port);
        if(resp.getCode()!= HttpStatus.SC_OK){
            return null;
        }
        String result = resp.getBody();
        if(StringUtils.isEmpty(result)){
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return  jsonObject;
    }
    /**
     * 发送get请求
     *
     * @param url
     * @param params
     * @param headers
     * @param connectionTimeout
     * @param socketTimeout
     * @return
     * @throws IOException
     */
    public static ResponseData get(String url, Map<String, Object> params, Map<String, Object> headers, int connectionTimeout, int socketTimeout, int connectionRequestTimeout, String proxyHost, Integer port) throws Exception{
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
                    if (stringObjectEntry.getValue() != null) {
                        builder.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue().toString());
                    }
                }
            }
            httpClient = HttpClients.createDefault();// 创建httpClient实例
            HttpGet httpGet = new HttpGet(builder.build());// 创建httpget实例
            if (proxyHost != null) {
                HttpHost proxy = new HttpHost(proxyHost, port.intValue());
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(connectionRequestTimeout).setProxy(proxy).build();
                httpGet.setConfig(requestConfig);
            } else {
                /* 设置超时 */
                RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
                httpGet.setConfig(defaultRequestConfig);
            }

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, Object> header : headers.entrySet()) {
                    if (header.getValue() != null) {
                        httpGet.addHeader(header.getKey(), header.getValue().toString());
                    }
                }
            }
            response = httpClient.execute(httpGet);// 执行http get请求
            HttpEntity entity = response.getEntity();// 获取返回实体
            int statusCode = response.getStatusLine().getStatusCode();
            ResponseData data = new ResponseData(statusCode, entity);
            return data;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();// response关闭
                }
                if (httpClient != null) {
                    httpClient.close();// httpClient关闭
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws IOException
     */
    public static ResponseData post(String url, Map<String, Object> params, Map<String, Object> headers, String proxyHost, Integer port) throws Exception{
        return post(url, params, headers, HttpConstant.CONNECT_TIMEOUT, HttpConstant.SOCKET_TIMEOUT, HttpConstant.CONNECT_REQ_TIMEOUT, proxyHost, port);
    }

    /**
     * 获取post返回JSONObject
     * @param url
     * @param params
     * @param headers
     * @param proxyHost
     * @param port
     * @return
     * @throws IOException
     */
    public static JSONObject postForJSONObject(String url, Map<String, Object> params, Map<String, Object> headers, String proxyHost, Integer port) throws Exception{
        ResponseData resp=post(url, params, headers, HttpConstant.CONNECT_TIMEOUT, HttpConstant.SOCKET_TIMEOUT, HttpConstant.CONNECT_REQ_TIMEOUT, proxyHost, port);
        if(resp.getCode()!= HttpStatus.SC_OK){
            return null;
        }
        String result = resp.getBody();
        if(StringUtils.isEmpty(result)){
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return  jsonObject;
    }

    public static JSONObject postBodyForJSONObject(String url, Map<String, Object> params, Map<String, Object> headers, JSONObject bodyRaw, String proxyHost, Integer port) throws Exception{
        ResponseData resp=postBodyRaw(url, params, headers, bodyRaw, HttpConstant.CONNECT_TIMEOUT, HttpConstant.SOCKET_TIMEOUT, HttpConstant.CONNECT_REQ_TIMEOUT, proxyHost, port);
        if(resp.getCode()!= HttpStatus.SC_OK){
            return null;
        }
        String result = resp.getBody();
        if(StringUtils.isEmpty(result)){
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return  jsonObject;
    }

    /**
     * 发送post请求,form参数
     *
     * @param url
     * @param params
     * @param headers
     * @param connectionTimeout
     * @param socketTimeout
     * @param connectionRequestTimeout
     * @param proxyHost
     * @param port
     * @return
     * @throws IOException
     */
    public static ResponseData post(String url, Map<String, Object> params, Map<String, Object> headers, int connectionTimeout, int socketTimeout, int connectionRequestTimeout, String proxyHost, Integer port) throws Exception{
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();// 创建httpClient实例
            HttpPost httpPost = new HttpPost(url);// 创建httpget实例

            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(list, HttpConstant.UTF8_ENCODE);
            httpPost.setEntity(urlEntity);

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, Object> header : headers.entrySet()) {
                    if (header.getValue() != null) {
                        httpPost.addHeader(header.getKey(), header.getValue().toString());
                    }
                }
            }

            if (proxyHost != null) {
                HttpHost proxy = new HttpHost(proxyHost, port);
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(connectionRequestTimeout).setProxy(proxy).build();
                httpPost.setConfig(requestConfig);
            } else {
                /* 设置超时 */
                RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
                httpPost.setConfig(defaultRequestConfig);
            }
            response = httpClient.execute(httpPost);// 执行http get请求
            HttpEntity entity = response.getEntity();// 获取返回实体
            int statusCode = response.getStatusLine().getStatusCode();
            ResponseData data = new ResponseData(statusCode, entity);
            return data;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();// response关闭
                }
                if (httpClient != null) {
                    httpClient.close();// httpClient关闭
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResponseData postBodyRaw(String url, Map<String, Object> params, Map<String, Object> headers, JSONObject bodyRaw, String proxyHost, Integer port) throws Exception{
        return postBodyRaw(url, params, headers, bodyRaw, HttpConstant.CONNECT_TIMEOUT, HttpConstant.SOCKET_TIMEOUT, HttpConstant.CONNECT_REQ_TIMEOUT, proxyHost, port);
    }

    /**
     * postBodyRaw返回结果JSONObject
     * @param url
     * @param params
     * @param headers
     * @param bodyRaw
     * @param proxyHost
     * @param port
     * @return
     * @throws IOException
     */
    public static JSONObject postBodyRawForJSONObject(String url, Map<String, Object> params, Map<String, Object> headers, JSONObject bodyRaw, String proxyHost, Integer port) throws Exception{
        ResponseData resp=postBodyRaw(url, params, headers, bodyRaw, HttpConstant.CONNECT_TIMEOUT, HttpConstant.SOCKET_TIMEOUT, HttpConstant.CONNECT_REQ_TIMEOUT, proxyHost, port);
        if(resp.getCode()!= HttpStatus.SC_OK){
            return null;
        }
        String result = resp.getBody();
        if(StringUtils.isEmpty(result)){
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return  jsonObject;
    }

    public static ResponseData postBodyRaw(String url, Map<String, Object> params, Map<String, Object> headers, JSONObject bodyRaw, int connectionTimeout, int socketTimeout, int connectionRequestTimeout, String proxyHost, Integer port) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();// 创建httpClient实例
        HttpPost httpPost = new HttpPost(url);// 创建httpget实例

        // 设置报文和通讯格式
        StringEntity stringEntity = new StringEntity(bodyRaw.toString(), HttpConstant.UTF8_ENCODE);
        stringEntity.setContentEncoding(HttpConstant.UTF8_ENCODE);
        stringEntity.setContentType(HttpConstant.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> header : headers.entrySet()) {
                if (header.getValue() != null) {
                    httpPost.addHeader(header.getKey(), header.getValue().toString());
                }
            }
        }

        if (proxyHost != null) {
            HttpHost proxy = new HttpHost(proxyHost, port);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(connectionRequestTimeout).setProxy(proxy).build();
            httpPost.setConfig(requestConfig);
        } else {
            /* 设置超时 */
            RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
            httpPost.setConfig(defaultRequestConfig);
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);// 执行http get请求
        HttpEntity entity = response.getEntity();// 获取返回实体
        int statusCode = response.getStatusLine().getStatusCode();
        ResponseData data = new ResponseData(statusCode, entity);
        return data;
    }

    /**
     * 获取不包含contextPath的uri
     *
     * @param request
     * @return
     */
    public static String getRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI(),
                ctx = request.getContextPath();
        if (ctx != null && ctx.length() > 0)
            return uri.substring(ctx.length());
        else
            return uri;
    }

    /**
     * 获取客户端地址，在反向代理后面时，尝试使用x-real-ip或x-forwarded-for获取ip
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        if (request == null)
            return null;
        String ip = request.getHeader(HEADER_X_REAL_IP);
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader(HEADER_X_FORWARDED_FOR);
            if (ip != null && ip.length() > 0) {
                int pcomma = ip.indexOf(',');
                if (pcomma != -1)
                    ip = ip.substring(0, pcomma);
            }
        }
        if (ip == null || ip.length() == 0)
            ip = request.getRemoteAddr();
        return ip;
    }

    /**
     * 获取host，在反向代理后面时先获取x-real-host
     *
     * @param request
     * @return
     */
    public static String getSiteHost(HttpServletRequest request) {
        String host = request.getHeader(HEADER_X_REAL_HOST);
        if (host == null || host.length() == 0)
            host = request.getHeader(HEADER_HOST);
        return host;
    }

    /**
     * 获取带协议名的站点地址，比如http://my.site.com/
     *
     * @param request
     * @return
     */
    public static String getSiteFullHost(HttpServletRequest request) {
        String protocol = request.getScheme();
        String host = getSiteHost(request);

        // TODO resolve ip for local-specified hosts
        int pcolon = host.indexOf(':');
        if ("localhost".equals(pcolon == -1 ? host : host.substring(0, pcolon)))
            host = pcolon == -1 ? LOCALHOST_IP : ("127.0.0.1" + host.substring(pcolon));

        return String.format("%s://%s/", protocol, host);
    }

    /**
     * 获取contextPath，在反向代理后面时首先获取x-real-context/real-context-path
     *
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        String contextPath = request.getHeader(HEADER_X_REAL_CONTEXT);
        if (contextPath == null || contextPath.length() == 0)
            contextPath = request.getHeader(HEADER_REAL_CONTEXT_PATH);
        if (contextPath == null || contextPath.length() == 0)
            contextPath = request.getContextPath();
        return contextPath;
    }

    /**
     * 获取带根目录的站点地址，比如http://my.site.com/root/
     *
     * @param request
     * @return
     */
    public static String getSiteRootWithHost(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        if (contextPath == null || contextPath.length() == 0)
            return getSiteFullHost(request);
        if (contextPath.startsWith("/"))
            contextPath = contextPath.substring(1);
        if (contextPath.length() > 0 && !contextPath.endsWith("/"))
            contextPath += "/";
        return String.format("%s%s", getSiteFullHost(request), contextPath);
    }

    /**
     * 获取反向代理前的url
     *
     * @param request
     * @param withQueryString
     * @return
     */
    public static String getFullRealUrl(HttpServletRequest request, boolean withQueryString) {
        String baseUrl = getSiteRootWithHost(request);
        String uri = request.getRequestURI(),
                context = request.getContextPath(),
                query = withQueryString ? request.getQueryString() : null;
        StringBuilder url = new StringBuilder(baseUrl.length() + uri.length() + (query == null ? 0 : query.length()));
        url.append(baseUrl);
        if (context != null && context.length() > 0)
            url.append(uri.substring(context.length() + 1)); // baseUrl以/结尾
        else
            url.append(uri);
        if (query != null && query.length() > 0)
            url.append('?').append(query);
        return url.toString();
    }


    /**
     * 获取地址中的参数，只提取第一个参数，大小写敏感
     *
     * @param uri
     * @param paramName
     * @return
     */
    public static String getUriParameter(String uri, String paramName) {
        int i = uri.indexOf('?');
        if (i == -1)
            return null;
        String query = uri.substring(i + 1);
        return getQueryParameter(query, paramName);
    }

    /**
     * 检查两个uri是否指向同一个页面，如果其中有一个地址为相对地址则需要转换为绝对地址
     *
     * @param uri1
     * @param uri2
     * @return
     */
    public static boolean isSamePage(String uri1, String uri2) {
        if (uri1 == null || uri2 == null)
            return false;
        String p1 = getRelativePath(uri1);
        String p2 = getRelativePath(uri2);
        return p1.equalsIgnoreCase(p2);
    }

    /**
     * 确定目标地址的绝对位置，如果目标地址为相对地址则根据当前地址推算
     *
     * @param currentUri
     * @param uri
     * @return
     */
    public static String getRelativePath(String currentUri, String uri) {
        if (uri.startsWith("/"))
            return uri;
        int plvl = 0;
        while (true) {
            if (uri.startsWith("../")) {
                ++plvl;
                uri = uri.substring(3);
            } else if (uri.startsWith("./")) {
                uri = uri.substring(2);
            } else {
                break;
            }
        }
        int p = currentUri.lastIndexOf('/');
        currentUri = currentUri.substring(0, p + 1);
        while (plvl > 0) {
            p = currentUri.lastIndexOf('/');
            currentUri = currentUri.substring(0, p + 1);
        }
        return currentUri + uri;
    }

    /**
     * 从queryString中取出参数(只提取第一个参数)
     *
     * @param query
     * @param paramName
     * @return
     */
    public static String getQueryParameter(String query, String paramName) {
        if (query == null || query.length() == 0)
            return null;
        Pattern p = Pattern.compile(PARAM_PATTERN);
        Matcher m = p.matcher(query);
        while (m.find()) {
            String key = decodeUriComponent(m.group(1));
            if (key.equals(paramName))
                return m.group(2).substring(1);
        }
        return null;
    }

    /**
     * 替换地址中的参数，如果map中的值为空则删除参数，否则替换，地址中不存在的参数在后面添加<br>
     * 如果存在重名的参数则应用到全部，大小写敏感
     *
     * @param uri
     * @param paramsToReplace
     * @return
     */
    public static String replaceUriParameters(String uri, Map<String, String> paramsToReplace) {
        if (paramsToReplace == null || paramsToReplace.size() == 0)
            return uri;
        int i = uri.indexOf('?');
        String query = i == -1 ? null : uri.substring(i + 1);
        uri = i == -1 ? uri : uri.substring(0, i);
        query = replaceQueryParameters(query, paramsToReplace);
        return (query == null || query.length() == 0) ? uri : (uri + '?' + query);
    }


    private static final String PARAM_PATTERN = "([^&=]+)(=.*?)?(&|$)";

    /**
     * 替换queryString中的参数
     *
     * @param query
     * @param paramsToReplace
     * @return
     */
    public static String replaceQueryParameters(String query, Map<String, String> paramsToReplace) {
        if (paramsToReplace == null || paramsToReplace.size() == 0)
            return query;
        if (query == null)
            query = "";

        StringBuilder buff = new StringBuilder(query.length() + paramsToReplace.size() * 64);
        Pattern p = Pattern.compile(PARAM_PATTERN);
        Matcher m = p.matcher(query);
        Set<String> usedKeys = new HashSet<String>(paramsToReplace.size());
        // 替换存在的参数值
        while (m.find()) {
            String key = decodeUriComponent(m.group(1));
            if (paramsToReplace.containsKey(key)) {
                String val = paramsToReplace.get(key);
                if (val != null && val.length() > 0) {
                    buff.append(key);
                    buff.append('=');
                    buff.append(encodeUriComponent(val));
                    buff.append('&');
                    usedKeys.add(key);
                }
            } else {
                buff.append(key);
                buff.append(m.group(2));
                buff.append('&');
            }
        }
        // 添加剩余的参数值
        for (String key : paramsToReplace.keySet()) {
            if (!usedKeys.contains(key)) {
                String val = paramsToReplace.get(key);
                if (val != null && val.length() > 0) {
                    buff.append(key);
                    buff.append('=');
                    buff.append(encodeUriComponent(val));
                    buff.append('&');
                }
            }
        }
        if (buff.length() > 0)
            buff.deleteCharAt(buff.length() - 1);
        return buff.toString();
    }

    private static final String REGEX_HOST = "^(\\w+://.*?)(/.*)?";

    /**
     * 获取站点host之后的路径，比如http://www.baidu.com/test/a.htm，返回/test/a.htm
     *
     * @param url
     * @return
     */
    public static final String getRelativePath(String url) {
        Pattern p = Pattern.compile(REGEX_HOST);
        Matcher m = p.matcher(url);
        if (m.matches()) {
            String h = m.group(1);
            url = url.substring(h.length());
            //    } else if (!(url.startsWith("/") || url.startsWith("./") || url.startsWith("../"))) {
            //      int i = url.indexOf('/');
            //      if (i == -1)
            //        url = "";
            //      else
            //        url = url.substring(i);
        }
        int i = url.indexOf('?');
        if (i == -1)
            return url;
        else
            return url.substring(0, i);
    }

    /**
     * uri解码
     *
     * @param c
     * @return
     */
    public static String decodeUriComponent(String c) {
        try {
            return URLDecoder.decode(c, PropertiesFileUtil.ENCODING);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * uri编码
     *
     * @param c
     * @return
     */
    public static String encodeUriComponent(String c) {
        try {
            return URLEncoder.encode(c, PropertiesFileUtil.ENCODING);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 下载网络文件
     * @param fileUrl
     * @param destPath
     * @throws MalformedURLException
     */
    public static void downloadFileFromNet(String fileUrl,String destPath) throws MalformedURLException {
        // 下载网络文件
        int byteread;
        FileOutputStream fs=null;
        try {
            URL url = new URL(fileUrl);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            fs = new FileOutputStream(destPath);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            System.out.println("下载文件获取失败");
        } catch (IOException e) {
            System.out.println("下载文件获取失败");
        }finally {
            if(fs!=null){
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * http响应内容
     */
    public static class ResponseData {
        public ResponseData(int statusCode, HttpEntity entity) throws IOException {
            this.code = statusCode;
            this.body = EntityUtils.toString(entity, "UTF-8");
            ;
        }

        private int code;
        private Map<String, String> headers = new LinkedHashMap<String, String>();
        private String body;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
    }

    public static void main(String[] args) throws IOException {
//        ResponseData responseData = get("http://www.baidu.com", null, null,null,null);
//        System.out.println(responseData.getBody());
    }
}
