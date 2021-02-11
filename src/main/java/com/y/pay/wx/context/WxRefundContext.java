package com.y.pay.wx.context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:33
 */
public interface WxRefundContext {
    /**
     * 回调微信订单退款
     */
    void buildRefundContext(final Map<String, String> data);
}
