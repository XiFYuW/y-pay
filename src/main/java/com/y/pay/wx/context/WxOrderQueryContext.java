package com.y.pay.wx.context;

import com.y.pay.inter.Context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:33
 */
public interface WxOrderQueryContext extends Context {

    /**
     * 回调微信订单查询响应
     */
    void buildOrderQueryContext(final Map<String, String> resp);
}
