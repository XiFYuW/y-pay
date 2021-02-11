package com.y.pay.wx;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * https://pay.weixin.qq.com/wiki/doc/api/index.html
 * @author XiFYuW
 * @since  2020/09/12 18:14
 */
@Data
@Builder
public class WxPayRequest implements Serializable {

    public static final DecimalFormat WX_DF = new DecimalFormat("0");

    /*请求参数*/
    private Map<String, String> params;

    /*代理商id*/
    private String parentId;
}
