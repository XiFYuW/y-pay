package com.y.pay.wx;

import com.y.pay.wx.tool.WXPayUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 9:43
 */
public class WxNotifyMap {

    //private static final DecimalFormat DF_WX = new DecimalFormat("0");

    public static Map<String, String> getParams(final HttpServletRequest request) throws Exception {
        final InputStream inStream = request.getInputStream();
        final ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String notifyData = new String(outSteam.toByteArray(), StandardCharsets.UTF_8);
        outSteam.close();
        inStream.close();
        return WXPayUtil.xmlToMap(notifyData);
    }

    public static Map<String, String> getRequestMap(final HttpServletRequest request,
                                                    final String body,
                                                    final String out_trade_no,
                                                    final BigDecimal total_fee,
                                                    final String notify_url){
        final Map<String, String> params = new HashMap<>();
        params.put("body", body);
        params.put("out_trade_no", out_trade_no);
        params.put("total_fee", WxPayRequest.WX_DF.format(total_fee.multiply(new BigDecimal(100))));
        params.put("spbill_create_ip", getIp(request));
        params.put("notify_url", notify_url);
        return params;
    }

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        if  ("127.0.0.1".equals(ip))  {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

}
