package com.y.pay.wx.context;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/10/15 10:56
 */
public interface WxTransfersContext {
    /**
     * 回调微信转账
     */
    void buildTransfersContext(final Map<String, String> data);
}
