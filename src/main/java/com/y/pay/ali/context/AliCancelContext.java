package com.y.pay.ali.context;

import com.y.pay.inter.Context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 9:52
 */
public interface AliCancelContext extends Context {
    /**
     * 回调支付宝订单撤销
     */
    void buildCancelContext(final Map<String, Object> map);
}
