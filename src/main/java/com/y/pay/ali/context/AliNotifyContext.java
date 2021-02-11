package com.y.pay.ali.context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 9:52
 */
public interface AliNotifyContext {
    /**
     * 回调支付宝异步通知接口处理
     * @param params 支付宝异步通知数据
     */
    void buildNotifyContext(final Map<String, String> params);
}
