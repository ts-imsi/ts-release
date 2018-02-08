package com.transen.tsrelease.util;

import org.apache.log4j.Logger;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2017/9/25
 */
public class HttpUtil {
    private static final Logger logger = Logger.getLogger(HttpUtil.class);
    public static final String ERROR = "error";

    public static String connectURL(String address, String jsonstr,String requestMethod) {
        String result = "";
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000 * 60 * 5);
            conn.setReadTimeout(1000 * 60 * 5);
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            OutputStream out = conn.getOutputStream();
            out.write(jsonstr.getBytes("UTF-8"));
            out.flush();
            out.close();

            String resCode = new Integer(conn.getResponseCode()).toString();
            logger.info("http请求响应码:" + resCode);
            InputStream input = resCode.startsWith("2") ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            result = reader.readLine();
            logger.info("http请求返回数据:" + result);
        } catch(Exception e) {
            logger.error("http提交请求异常,", e);
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static String connectURLGET(String address,String xToken) {
        String rec_string = "";
        URL url = null;
        HttpURLConnection urlConn = null;
        try {
            url = new URL(address);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(1000 * 60 * 5);
            urlConn.setReadTimeout(1000 * 60 * 5);
            urlConn.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            urlConn.setRequestProperty("X-TOKEN", xToken);
            urlConn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = rd.read()) > -1) {
                sb.append((char) ch);
            }
            rec_string = sb.toString().trim();
            rd.close();
        } catch (Exception e) {
            logger.info("http请求连接异常", e);
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return rec_string;
    }

    public static String connectURLGET(String address) {
        String rec_string = "";
        URL url = null;
        HttpURLConnection urlConn = null;
        try {
            url = new URL(address);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(1000 * 60 * 5);
            urlConn.setReadTimeout(1000 * 60 * 5);
            urlConn.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            urlConn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = rd.read()) > -1) {
                sb.append((char) ch);
            }
            rec_string = sb.toString().trim();
            rd.close();
        } catch (Exception e) {
            logger.info("http请求连接异常", e);
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return rec_string;
    }

    /**
     * 上传文件
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @return
     */
    public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap, String JSESSIONID) {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            //连接超时时间10秒
            conn.setConnectTimeout(10000);
            //获取服务端返回数据超时时间30秒
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.setRequestProperty("Cookie", "JSESSIONID=" + JSESSIONID);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition:form-data;name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes("utf-8"));
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition:form-data;name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + "text/plain" + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                // 读取返回数据
                StringBuffer strBuf = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    strBuf.append(line).append("\n");
                }
                res = strBuf.toString();
                reader.close();
                reader = null;
            }else {
                res= String.valueOf(resCode);
            }
        } catch (Exception e) {
            logger.error("发送POST请求出错===" + e);
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }


    public static void main(String[] args) {
        /*String str = HttpUtil.connectURLtoCookie("http://passport.fenxibao.com/account/kjtest/user/10000347/department/shops/","629e3c6b1b43b9a73e714708a1760b179b2b4f66");
        String str2 = HttpUtil.connectURLGET("http://passport.fenxibao.com/account/kjtest/user/10000347/department/shops/");
        if (str != null) {

            System.out.printf(str2);
        }*/

        String str2 = HttpUtil.connectURLGET("http://192.168.18.7:18072/ts-authorize/operList/attence","87974011FC66809C1C757AAF23D47583.eyJuYW1lIjoiYWRtaW4iLCJwd2QiOiJhZG1pbiIsInNob3dOYW1lIjoi5byg5aSP5pmWIiwidXNlcklkIjoiMSJ9");
        if (str2 != null) {

            System.out.printf(str2);
        }



    }
}
