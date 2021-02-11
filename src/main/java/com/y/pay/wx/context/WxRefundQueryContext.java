package com.y.pay.wx.context;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:33
 */
public interface WxRefundQueryContext {
    /**
     * 回调微信退款结果查询
     * @param refundStatus 退款状态，参考RefundStatusEnum
     */
    void buildRefundQueryContext(final String refundStatus);
}
