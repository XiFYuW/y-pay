package com.y.pay.wx;

import com.y.pay.exception.WxRuntimeException;
import com.y.pay.wx.tool.WXPay;
import com.y.pay.wx.tool.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author XiFYuW
 * @since  2020/09/13 8:38
 */
@Component
@Slf4j
public class WxFactoryConfig {

    @Value("${system.wx-cre-path}")
    private String wxCrePath = "";

    public WXPay wxPay(final String parentId) {
        try {

                final WxConfig wxConfig = new WxConfig(
                        "users.getWxappid()",
                        "users.getWxmchid()",
                        "users.getWxapikey()",
                        wxCrePath/*C:\Program Files\Apache Software Foundation\cres\1603923711_20201107_cert\apiclient_cert.p12*/);
                final WXPay wxPay = new WXPay(wxConfig);
                wxPay.checkWXPayConfig();
                return wxPay;

        }catch (Exception e) {
            e.printStackTrace();
        }
        throw new WxRuntimeException("微信支付配置错误");
    }

    public WXPay wxJsPay(final String parentId) {
        try {

                final WxConfig wxConfig = new WxConfig(
                        "usersConfigNew.getWxMpAppid()",/*微信公众号appid*/
                        "users.getWxmchid()",
                        "users.getWxapikey()",
                        wxCrePath);
                final WXPay wxJsPay = new WXPay(wxConfig);
                wxJsPay.checkWXPayConfig();
                return wxJsPay;

        }catch (Exception e) {
            e.printStackTrace();
        }
        throw new WxRuntimeException("微信jsapi支付配置错误");
    }
}
