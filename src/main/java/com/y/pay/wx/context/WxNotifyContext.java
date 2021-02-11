package com.y.pay.wx.context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:48
 */
public interface WxNotifyContext {

    /**
     * 回调微信异步通知接口处理
     * @param params 微信异步通知数据
     */
    void buildNotifyContext(final Map<String, String> params);
}
