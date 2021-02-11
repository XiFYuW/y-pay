package com.y.pay.wx.service.impl;

import com.y.pay.connce.Consequence;
import com.y.pay.connce.ConsequenceQueryEnum;
import com.y.pay.connce.OrderQueryPoolExecutorService;
import com.y.pay.connce.QueryProcess;
import com.y.pay.wx.WxFactoryConfig;
import com.y.pay.wx.WxPayRequest;
import com.y.pay.wx.context.*;
import com.y.pay.wx.service.WxBuildService;
import com.y.pay.wx.service.WxPayEasyService;
import com.y.pay.wx.tool.WXPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:21
 */
@Service
@Slf4j
public class WxPayEasyServiceImpl implements WxPayEasyService {

    private final WxBuildService wxBuildService;

    private final OrderQueryPoolExecutorService orderQueryPoolExecutorService;

    private final WxFactoryConfig wxFactoryConfig;

    public WxPayEasyServiceImpl(WxBuildService wxBuildService,
                                OrderQueryPoolExecutorService orderQueryPoolExecutorService,
                                WxFactoryConfig wxFactoryConfig) {

        this.wxBuildService = wxBuildService;
        this.orderQueryPoolExecutorService = orderQueryPoolExecutorService;
        this.wxFactoryConfig = wxFactoryConfig;
    }

    @Override
    public Map<String, String> appPay(WxPayRequest wxPayRequest) {
        log.info("parentId: {}", wxPayRequest.getParentId());
        WXPay wxPay = wxFactoryConfig.wxPay(wxPayRequest.getParentId());
        return wxBuildService.buildPay(() -> wxPay.unifiedOrder(wxPayRequest.getParams()));
    }

    @Override
    public Map<String, String> jsPay(WxPayRequest wxPayRequest) {
        log.info("parentId: {}", wxPayRequest.getParentId());
        WXPay wxJsPay = wxFactoryConfig.wxJsPay(wxPayRequest.getParentId());
        return wxBuildService.buildPay(() -> wxJsPay.unifiedOrder(wxPayRequest.getParams()));
    }

    @Override
    public Map<String, String> wapPay(WxPayRequest wxPayRequest) {
        log.info("parentId: {}", wxPayRequest.getParentId());
        WXPay wxPay = wxFactoryConfig.wxPay(wxPayRequest.getParentId());
        return wxBuildService.buildPay(() -> wxPay.unifiedOrder(wxPayRequest.getParams()));
    }

    @Override
    public void orderQuery(WxPayRequest wxPayRequest, boolean queryProcess, WxOrderQueryContext wxOrderQueryContext) {
        WXPay wxPay = wxFactoryConfig.wxPay(wxPayRequest.getParentId());
        final Map<String, String> resp = wxBuildService.buildGeneral(() -> wxPay.orderQuery(wxPayRequest.getParams()));
        if (!queryProcess) {
            log.info("orderNo：{} store false", wxPayRequest.getParams());
            return;
        }
        wxOrderQueryContext.buildOrderQueryContext(resp);
    }

    @Override
    public void notify(Map<String, String> map, WxNotifyContext wxNotifyContext, String parentId) {
        wxBuildService.buildNotifyContext(map, params -> {
            try {
                WXPay wxPay = wxFactoryConfig.wxPay(parentId);
                boolean isValid = wxPay.isPayResultNotifySignatureValid(params);
                log.info("微信验签结果：【{}】", isValid);
                if (isValid && "SUCCESS".equals(params.get("result_code"))) {
                    wxNotifyContext.buildNotifyContext(params);
                } else {
                    log.info("微信验签失败，启动重入");
                    final Consequence consequence = new Consequence(
                            true,
                            params.get("out_trade_no"),
                            parentId,
                            ConsequenceQueryEnum.WX_QUERY.getPayWay(),
                            this,
                            new QueryProcess(orderQueryPoolExecutorService.getStoreInterface())
                    );
                    orderQueryPoolExecutorService.submitRequest(consequence);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("微信验签异常，启动重入");
                final Consequence consequence = new Consequence(
                        true,
                        params.get("out_trade_no"),
                        parentId,
                        ConsequenceQueryEnum.WX_QUERY.getPayWay(),
                        this,
                        new QueryProcess(orderQueryPoolExecutorService.getStoreInterface())
                );
                orderQueryPoolExecutorService.submitRequest(consequence);
            }

        });

    }

    @Override
    public void refund(WxPayRequest wxPayRequest, WxRefundContext wxRefundContext) {
        WXPay wxPay = wxFactoryConfig.wxPay(wxPayRequest.getParentId());
        final Map<String, String> data = wxBuildService.buildGeneral(() -> wxPay.refund(wxPayRequest.getParams()));
        wxRefundContext.buildRefundContext(data);
    }

    @Override
    public void refundQuery(WxPayRequest wxPayRequest, WxRefundQueryContext wxRefundQueryContext) {
        WXPay wxPay = wxFactoryConfig.wxPay(wxPayRequest.getParentId());
        final Map<String, String> resp = wxBuildService.buildGeneral(() -> wxPay.refundQuery(wxPayRequest.getParams()));
        wxRefundQueryContext.buildRefundQueryContext(resp.get("refund_status_$n"));
    }

    @Override
    public void refundNotify(Map<String, String> map, WxRefundNotifyContext wxRefundNotifyContext, String parentId) {
        WXPay wxPay = wxFactoryConfig.wxPay(parentId);
        wxBuildService.buildNotifyContext(map, params -> {
            if (wxPay.isPayResultNotifySignatureValid(params)) {
                wxRefundNotifyContext.buildRefundNotifyContext();
            }
        });
    }

    @Override
    public void closeOrder(WxPayRequest wxPayRequest, WxCloseOrderContext wxCloseOrderContext) {
        WXPay wxPay = wxFactoryConfig.wxPay(wxPayRequest.getParentId());
        final Map<String, String> data = wxBuildService.buildGeneral(() -> wxPay.closeOrder(wxPayRequest.getParams()));
        wxCloseOrderContext.buildCloseOrderContext(data);
    }

    @Override
    public void reverse(WxPayRequest wxPayRequest, WxReverseContext wxReverseContext) {
        WXPay wxPay = wxFactoryConfig.wxPay(wxPayRequest.getParentId());
        final Map<String, String> data = wxBuildService.buildGeneral(() -> wxPay.reverse(wxPayRequest.getParams()));
        wxReverseContext.buildReverseContext(data);
    }

    @Override
    public void transfers(WxPayRequest wxPayRequest, WxTransfersContext wxTransfersContext) {
        WXPay wxPay = wxFactoryConfig.wxPay(wxPayRequest.getParentId());
        final Map<String, String> data = wxBuildService.buildGeneral(() -> wxPay.transfers(wxPayRequest.getParams()));
        wxTransfersContext.buildTransfersContext(data);
    }


}
