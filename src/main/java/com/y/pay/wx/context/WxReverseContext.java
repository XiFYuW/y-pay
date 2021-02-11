package com.y.pay.wx.context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:33
 */
public interface WxReverseContext {
    /**
     * 回调微信订单撤销
     */
    void buildReverseContext(final Map<String, String> map);
}
