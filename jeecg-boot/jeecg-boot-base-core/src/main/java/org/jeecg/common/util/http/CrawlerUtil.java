package org.jeecg.common.util.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: httpclient
 * @Author: Kingpin
 * @Date: 2020-01-06 20:27:52
 **/
public class CrawlerUtil extends HttpUtil{
    public static String URL = "https://v2.jinrishici.com/one.json?client=browser-sdk/1.2&X-User-Token=";

    public static void main(String[] args) {
        //获取代理服务器信息
        crawlProxyFromApi(20);
//        //获取诗词信息
//        crawlPoetrys();
    }

    /**
     * 使用代理服务从接口获取poetry
     * @param url
     * @param proxyHost
     * @param port
     * @return
     * @throws Exception
     */
    public static JSONObject requestGETWithProxy(String url, String proxyHost, Integer port) throws Exception {
        Map headers=new HashMap();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("X-User-Token", "BDdQZ+8tF0ELLkHSEtx8VXXETeaU5MFL");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        JSONObject jsonObject = getForJSONObject(url,null,headers,proxyHost,port);
        return jsonObject;
    }

    /**
     * 从指定html获取代理服务器信息，写入到txt
     */
    public static void crawlProxyFromHtml() {
        OutputStreamWriter os = null;
        BufferedWriter out = null;
        try {
            String outFilePath = "G:\\tmp\\proxy.txt";
            os = new OutputStreamWriter(new FileOutputStream(outFilePath), "UTF-8");
            out = new BufferedWriter(os);
            for (int m = 1; m < 3; m++) {
                String url = "http://www.66ip.cn/areaindex_" + m + "/1.html";
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements trs = doc.select("table").select("tr");
                    for (int i = 0; i < trs.size(); i++) {
                        Elements tds = trs.get(i).select("td");
                        StringBuffer sb = new StringBuffer();
                        String firstText = tds.get(0).text();
                        if (!isIP(firstText)) {
                            continue;
                        }
                        for (int j = 0; j < tds.size(); j++) {
                            String text = tds.get(j).text();
                            if (j > 0) {
                                sb.append(":");
                            }
                            sb.append(text);
                        }
                        out.write(sb.toString() + "\r\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void crawlProxyFromApi(int page) {
        OutputStreamWriter os = null;
        BufferedWriter out = null;
        try {
            String outFilePath = "G:\\tmp\\proxy.txt";
            os = new OutputStreamWriter(new FileOutputStream(outFilePath), "UTF-8");
            out = new BufferedWriter(os);

            for(int j=0;j<page;j++){
//                String url="http://www.66ip.cn/mo.php?sxb=&tqsl=10&port=&export=&ktip=&sxa=&submit=%CC%E1++%C8%A1&textarea=";
                String url = "http://www.66ip.cn/nmtq.php?getnum=20&isp=0&anonymoustype=0&start=&ports=&export=&ipaddress=&area=1&proxytype=2&api=66ip";
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements body = doc.select("body");
                    List<Node> nodes=body.get(0).childNodes();
                    for (int i = 0; i < nodes.size(); i++) {
                        Node node=nodes.get(i);
                        String str=node.toString().trim();
                        if(!isIPWithPort(str)){
                            continue;
                        }
                        if((j+i)>0){
                            out.write("\r\n");
                        }
                        out.write(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void crawlProxyFromZhiMa(int page) {
        OutputStreamWriter os = null;
        BufferedWriter out = null;
        try {
            String outFilePath = "G:\\tmp\\proxy.txt";
            os = new OutputStreamWriter(new FileOutputStream(outFilePath), "UTF-8");
            out = new BufferedWriter(os);

            for(int j=0;j<page;j++){
                String url = "http://http.tiqu.alicdns.com/getip3?num=1&type=1&pro=110000&city=0&yys=0&port=1&pack=80683&ts=0&ys=0&cs=0&lb=1&sb=0&pb=45&mr=2&regions=&gm=4";
                try {
                    ResponseData proxy=get(url,null,null,5000,5000,5000,null,null);
                    out.write(proxy.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 指定数量获取poetry
     */
    public static void crawlPoetrys() {
        List<Map> proxys = readProxyFromTxt();
        if (CollectionUtils.isEmpty(proxys)) {
            return;
        }
        OutputStreamWriter os = null;
        BufferedWriter out = null;
        try {
            String outFilePath = "G:\\tmp\\poetry.txt";

            for (Map proxy : proxys) {
                for(int i=0;i<1;i++){
                    os = new OutputStreamWriter(new FileOutputStream(outFilePath, true), "UTF-8");
                    out = new BufferedWriter(os);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = requestGETWithProxy(URL, proxy.get("ip").toString(), (Integer) proxy.get("port"));
                    } catch (Exception e) {
                        System.out.println("通过代理获取失败>>>"+proxy.get("ip").toString()+":"+(Integer) proxy.get("port"));
                        break;
                    }

                    Object ipAddress = jsonObject.get("ipAddress");
                    Object warning = jsonObject.get("warning");
                    System.out.println(ipAddress);
                    System.out.println(warning);
                    String status = jsonObject.getString("status");
                    if (!status.equalsIgnoreCase("success")) {
                        break;
                    }

                    StringBuffer sb = new StringBuffer();
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject origin = data.getJSONObject("origin");
                    String dynasty = origin.getString("dynasty");
                    String author = origin.getString("author");
                    String title = origin.getString("title");
                    JSONArray conArr = origin.getJSONArray("content");
                    JSONArray transArr = origin.containsKey("translate")?origin.getJSONArray("translate"):null;
                    sb.append(title);
                    sb.append("|");
                    for (int j = 0; j < conArr.size(); j++) {
                        if (j > 0) {
                            sb.append("<p>");
                        }
                        sb.append(conArr.get(j));
                    }
                    sb.append("|");
                    sb.append(author);
                    sb.append("|");
                    sb.append(dynasty);
                    if(transArr!=null){
                        sb.append("|");
                        for (int j = 0; j < transArr.size(); j++) {
                            if (j > 0) {
                                sb.append("<p>");
                            }
                            sb.append(transArr.get(j));
                        }
                    }
                    out.write(sb.toString() + "\r\n");
                    System.out.println(sb.toString());
                    if (out != null) {
                        out.close();
                        out=null;
                    }
                    if (os != null) {
                        os.close();
                        os=null;
                    }
                    //加入的可用的代理文件
                    appendToProxyBakTxt(proxy.get("ip").toString()+":"+(Integer) proxy.get("port"));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行完成");
    }

    public static List<Map> readProxyFromTxt() {
        File bakFile = new File("G:\\tmp\\proxy-bak.txt");
        File file = new File("G:\\tmp\\proxy.txt");
        InputStreamReader inputReader = null;
        BufferedReader bf = null;
        List<Map> list = new ArrayList<>();
        try {
            inputReader = new InputStreamReader(new FileInputStream(bakFile));
            bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                if("".equalsIgnoreCase(str)){
                    continue;
                }
                Map ipMap = new HashMap();
                String[] ipArr = str.split(":");
                ipMap.put("ip", ipArr[0]);
                ipMap.put("port", Integer.parseInt(ipArr[1]));
                list.add(ipMap);
            }
            if (bf != null) {
                bf.close();
            }
            if (inputReader != null) {
                inputReader.close();
            }

            inputReader = new InputStreamReader(new FileInputStream(file));
            bf = new BufferedReader(inputReader);
            str=null;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                if("".equalsIgnoreCase(str)){
                    continue;
                }
                Map ipMap = new HashMap();
                String[] ipArr = str.split(":");
                ipMap.put("ip", ipArr[0]);
                ipMap.put("port", Integer.parseInt(ipArr[1]));
                list.add(ipMap);
            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 备份可用proxy
     * @param proxy
     */
    public static void appendToProxyBakTxt(String proxy){
        File bakFile = new File("G:\\tmp\\proxy-bak.txt");
        InputStreamReader inputReader = null;
        BufferedReader bf = null;
        List<String> list = new ArrayList<>();
        try {
            inputReader = new InputStreamReader(new FileInputStream(bakFile));
            bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                if("".equalsIgnoreCase(str)){
                    continue;
                }
                list.add(str);
            }
            if (bf != null) {
                bf.close();
            }
            if (inputReader != null) {
                inputReader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(list.contains(proxy)){
            return;
        }

        OutputStreamWriter os = null;
        BufferedWriter out = null;
        try {
            String outFilePath = "G:\\tmp\\proxy-bak.txt";
            os = new OutputStreamWriter(new FileOutputStream(outFilePath, true), "UTF-8");
            out = new BufferedWriter(os);
            out.write("\r\n");
            out.write(proxy);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static JSONObject getForJSONObject(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        if(headers==null){
            headers=new HashMap();
        }
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
        HttpUtil.ResponseData resp = get(url, params, headers, 3000, 10000, 1000, null, null);
        if (resp.getCode() != 200) {
            return null;
        } else {
            String result = resp.getBody();
            if (StringUtils.isEmpty(result)) {
                return null;
            } else {
                JSONObject jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            }
        }
    }

    /**
     * 随机生成ip
     *
     * @return
     */
    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());
        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1);
    }

    /**
     * 判断是否ip
     *
     * @param addr
     * @return
     */
    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        return ipAddress;
    }

    public static boolean isIPWithPort(String addr) {
        if (addr.length() < 7 || "".equals(addr.trim())) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}(:(\\d\\d\\d\\d|\\d\\d\\d|\\d\\d|\\d))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        return ipAddress;
    }
}
