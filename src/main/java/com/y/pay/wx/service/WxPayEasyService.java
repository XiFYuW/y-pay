package com.y.pay.wx.service;

import com.y.pay.inter.PayEasy;
import com.y.pay.wx.WxPayRequest;
import com.y.pay.wx.context.*;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:20
 */
public interface WxPayEasyService extends PayEasy {

    Map<String, String> appPay(WxPayRequest wxPayRequest);

    Map<String, String> jsPay(WxPayRequest wxPayRequest);

    Map<String, String> wapPay(WxPayRequest wxPayRequest);

    void orderQuery(WxPayRequest wxPayRequest, boolean queryProcess, WxOrderQueryContext wxOrderQueryContext);

    void notify(Map<String, String> map, WxNotifyContext wxNotifyContext, String parentId);

    void refund(WxPayRequest wxPayRequest, WxRefundContext wxRefundContext);

    void refundQuery(WxPayRequest wxPayRequest, WxRefundQueryContext wxRefundQueryContext);

    void refundNotify(Map<String, String> map, WxRefundNotifyContext wxRefundNotifyContext, String parentId);

    void closeOrder(WxPayRequest wxPayRequest, WxCloseOrderContext wxCloseOrderContext);

    void reverse(WxPayRequest wxPayRequest, WxReverseContext wxReverseContext);

    void transfers(WxPayRequest wxPayRequest, WxTransfersContext wxTransfersContext);
}
