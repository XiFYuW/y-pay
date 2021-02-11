package com.y.pay.ali;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * https://opendocs.alipay.com/apis
 * https://opendocs.alipay.com/open/203/105286/
 * @author XiFYuW
 * @since  2020/09/12 18:14
 */
@Data
@Builder
public class AliPayRequest implements Serializable {

    public static final DecimalFormat ALI_DF = new DecimalFormat("0.00");

    public static final String RETURN_URL = "http://118.31.76.48/weblist/#/sbdpaytrue";

    /*代理商id*/
    private String parentId;

    /*订单标题*/
    private String subject;

    /*交易创建时传入的商户订单号*/
    private String outTradeNo;

    /*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
    private String totalAmount;

    /*异步通知地址*/
    private String asyncNotify;

    /*需要退款的金额，该金额不能大于订单金额，单位为元，支持两位小数*/
    private String refundAmount;

    /*请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号*/
    private String RequestNo;

    /*OpenAPI的名称，例如：alipay.trade.pay*/
    private String method;

    /*没有包装在biz_content下的请求参数集合，例如app_auth_token等参数*/
    private Map<String, String> textParams;

    /*被包装在biz_content下的请求参数集合*/
    private Map<String, String> bizParams;

    /*用户付款中途退出返回商户网站的地址*/
    private String quitUrl;

    /*支付成功后同步跳转的页面，是一个http/https开头的字符串*/
    private String returnUrl;
}
