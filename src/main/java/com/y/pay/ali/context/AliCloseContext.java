package com.y.pay.ali.context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 9:52
 */
public interface AliCloseContext {
    /**
     * 回调支付宝订单关闭
     */
    void buildCloseContext(final Map<String, Object> map);
}
