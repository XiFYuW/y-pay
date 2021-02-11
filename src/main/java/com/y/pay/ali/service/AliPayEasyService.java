package com.y.pay.ali.service;


import com.y.pay.ali.AliPayRequest;
import com.y.pay.ali.context.*;
import com.y.pay.connce.QueryProcess;
import com.y.pay.inter.PayEasy;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 8:54
 */
public interface AliPayEasyService extends PayEasy {

    Map<String, Object> appPay(AliPayRequest aliPayRequest);

    Map<String, Object> wapPay(AliPayRequest aliPayRequest);

    void notify(Map<String, String> params, AliNotifyContext aliNotifyContext, String parentId);

    void refund(AliPayRequest aliPayRequest, AliRefundContext aliRefundContext);

    void query(AliPayRequest aliPayRequest, boolean queryProcess, AliQueryContext aliQueryContext);

    void queryRefund(AliPayRequest aliPayRequest, AliQueryRefundContext aliQueryRefundContext);

    void cancel(AliPayRequest aliPayRequest, AliCancelContext aliCancelContext);

    void close(AliPayRequest aliPayRequest, AliCloseContext aliCloseContext);

    void GenericExecute(AliPayRequest aliPayRequest, AliGenericExecuteContext aliGenericExecuteContext);
}
