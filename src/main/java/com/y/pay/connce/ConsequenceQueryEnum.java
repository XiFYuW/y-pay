package com.y.pay.connce;

import com.y.pay.ali.TradeStatusEnum;
import com.y.pay.ali.context.AliQueryContext;
import com.y.pay.inter.Context;
import com.y.pay.wx.TradeStateEnum;
import com.y.pay.wx.context.WxOrderQueryContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author XiFYuW
 * @since 2020/10/20 14:37
 */
@Slf4j
public enum ConsequenceQueryEnum/*订单查询执行器*/ {

    ALI_QUERY(1, (AliQueryContext) map -> {
        String tradeStatus = String.valueOf(map.get("trade_status"));
        String outTradeNo = String.valueOf(map.get("out_trade_no"));
        log.info("重入处理订单：{}", outTradeNo);
        TradeStatusEnum tradeStatusEnum = TradeStatusEnum.valueOf(tradeStatus);


        String body = ConnceResponseUtils.requestBody(map);
        log.info("重入请求内容：{}", body);
        if (ConnceResponseUtils.isSUCCESS(body)) {
//            consequencePay.setUpdateTime(DateUtil.date());
//            consequencePay.setAliOrderStatus(tradeStatusEnum.getCode());
//            consequencePay.setIsPerform(1);
//            consequencePayMapper.updateById(consequencePay);
        } else {
            log.info("查询处理失败：{}", outTradeNo);
        }


    }),

    WX_QUERY(2, (WxOrderQueryContext) resp -> {
        String tradeState = resp.get("trade_state");
        String outTradeNo = resp.get("out_trade_no");
        log.info("重入处理订单：{}", outTradeNo);
        TradeStateEnum tradeStateEnum = TradeStateEnum.valueOf(tradeState);


        String body = ConnceResponseUtils.requestBody(resp);
        log.info("重入请求内容：{}", body);
        if (ConnceResponseUtils.isSUCCESS(body)) {
//            consequencePay.setUpdateTime(DateUtil.date());
//            consequencePay.setWxOrderStatus(tradeStateEnum.getCode());
//            consequencePay.setIsPerform(1);
//            consequencePayMapper.updateById(consequencePay);
        } else {
            log.info("查询处理失败：{}", outTradeNo);
        }

    });

    private final int payWay;

    private final Context context;

    ConsequenceQueryEnum(int payWay, Context context) {
        this.payWay = payWay;
        this.context = context;
    }

    public int getPayWay() {
        return payWay;
    }

    public Context getContext() {
        return context;
    }

}
