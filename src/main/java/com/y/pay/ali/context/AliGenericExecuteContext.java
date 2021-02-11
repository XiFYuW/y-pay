package com.y.pay.ali.context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/10/15 10:20
 */
public interface AliGenericExecuteContext {

    /**
     * 回调支付宝其他接口
     */
    void buildGenericExecuteContext(final Map<String,Object> map);
}
