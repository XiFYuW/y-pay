package com.y.pay.connce;

import com.y.pay.ali.AliPayRequest;
import com.y.pay.ali.context.AliQueryContext;
import com.y.pay.ali.service.AliPayEasyService;
import com.y.pay.exception.ConsequenceException;
import com.y.pay.inter.PayEasy;
import com.y.pay.wx.WxPayRequest;
import com.y.pay.wx.context.WxOrderQueryContext;
import com.y.pay.wx.service.WxPayEasyService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author XiFYuW
 * @since  2020/10/20 13:51
 */
public final class Consequence implements Serializable {

    /*是否开启支付结果查询*/
    private final boolean isConsequence;

    /*商户订单号*/
    private final String outTradeNo;

    /*代理商id*/
    private final String parentId;

    /*支付方式1：支付宝 2：微信*/
    private final Integer payWay;

    private final PayEasy payEasy;

    private final AtomicBoolean checkPayWay = new AtomicBoolean(true);

    private final QueryProcess queryProcess;

    public Consequence(boolean isConsequence,
                       String outTradeNo,
                       String parentId,
                       Integer payWay,
                       PayEasy payEasy,
                       QueryProcess queryProcess) {
        this.isConsequence = isConsequence;
        this.outTradeNo = outTradeNo;
        this.parentId = parentId;
        this.payWay = payWay;
        this.payEasy = payEasy;
        this.queryProcess = queryProcess;
        checkPayWay();
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public boolean isConsequence() {
        return isConsequence;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public String getParentId() {
        return parentId;
    }

    private void checkPayWay(){
        final ConsequenceQueryEnum[] consequenceQueryEnum = ConsequenceQueryEnum.values();
        for (ConsequenceQueryEnum c : consequenceQueryEnum) {
            checkPayWay.set(checkPayWay.get() || c.getPayWay() == payWay);
        }
    }

    public void ConsequenceQuery(){
        if (!checkPayWay.get()) {
            throw new ConsequenceException("payWay err");
        }

        if (payWay.compareTo(ConsequenceQueryEnum.ALI_QUERY.getPayWay()) == 0 && isConsequence) {
            aliConsequenceQuery((AliPayEasyService) this.payEasy);
        }

        if (payWay.compareTo(ConsequenceQueryEnum.WX_QUERY.getPayWay()) == 0 && isConsequence) {
            wxConsequenceQuery((WxPayEasyService) this.payEasy);
        }
    }

    private void aliConsequenceQuery(final AliPayEasyService aliPayEasyService){
        final AliPayRequest aliPayRequest = AliPayRequest.builder().outTradeNo(outTradeNo).parentId(parentId).build();
        aliPayEasyService.query(
                aliPayRequest,
                queryProcess.checkIsPerform(outTradeNo),
                (AliQueryContext) ConsequenceQueryEnum.ALI_QUERY.getContext()
        );
    }

    private void wxConsequenceQuery(final WxPayEasyService wxPayEasyService){
        final Map<String, String> params = new HashMap<>();
        params.put("out_trade_no", outTradeNo);
        final WxPayRequest wxPayRequest = WxPayRequest.builder().params(params).parentId(parentId).build();
        wxPayEasyService.orderQuery(
                wxPayRequest,
                queryProcess.checkIsPerform(outTradeNo),
                (WxOrderQueryContext) ConsequenceQueryEnum.WX_QUERY.getContext()

        );
    }
}
