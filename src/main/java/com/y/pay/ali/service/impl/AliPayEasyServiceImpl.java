package com.y.pay.ali.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.Context;
import com.alipay.easysdk.kms.aliyun.AliyunKMSClient;
import com.alipay.easysdk.kms.aliyun.AliyunKMSSigner;
import com.alipay.easysdk.payment.common.models.*;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.aliyun.tea.TeaModel;
import com.y.pay.ali.AliFactoryConfig;
import com.y.pay.ali.AliPayRequest;
import com.y.pay.ali.context.*;
import com.y.pay.ali.service.AliBuildService;
import com.y.pay.ali.service.AliPayEasyService;
import com.y.pay.connce.Consequence;
import com.y.pay.connce.ConsequenceQueryEnum;
import com.y.pay.connce.OrderQueryPoolExecutorService;
import com.y.pay.connce.QueryProcess;
import com.y.pay.exception.AliRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 8:54
 */
@Service
@Slf4j
public class AliPayEasyServiceImpl implements AliPayEasyService {

    private final AliBuildService aliBuildService;

    private final OrderQueryPoolExecutorService orderQueryPoolExecutorService;

    private final AliFactoryConfig aliFactoryConfig;

    public AliPayEasyServiceImpl(AliBuildService aliBuildService,
                                 OrderQueryPoolExecutorService orderQueryPoolExecutorService,
                                 AliFactoryConfig aliFactoryConfig) {
        this.aliBuildService = aliBuildService;
        this.orderQueryPoolExecutorService = orderQueryPoolExecutorService;
        this.aliFactoryConfig = aliFactoryConfig;
    }

    private com.alipay.easysdk.kernel.Context getContext(Config options) {
        try {
            com.alipay.easysdk.kernel.Context context = new Context(options, "alipay-easysdk-java-2.0.0");
            if ("AliyunKMS".equals(context.getConfig("signProvider"))) {
                context.setSigner(new AliyunKMSSigner(new AliyunKMSClient(TeaModel.buildMap(options))));
            }
            return context;
        } catch (Exception var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    @Override
    public Map<String, Object> appPay(AliPayRequest aliPayRequest) {
        log.info("parentId: {}", aliPayRequest.getParentId());
        final Config config = aliFactoryConfig.config(aliPayRequest.getParentId());
        return aliBuildService.buildPay(() -> new com.alipay.easysdk.payment.app.Client(
                new com.alipay.easysdk.kernel.Client(getContext(config))
        )
                .asyncNotify(aliPayRequest.getAsyncNotify())
                .pay(
                        aliPayRequest.getSubject(),
                        aliPayRequest.getOutTradeNo(),
                        aliPayRequest.getTotalAmount()
                ));
    }

    @Override
    public Map<String, Object> wapPay(AliPayRequest aliPayRequest) {
        log.info("parentId: {}", aliPayRequest.getParentId());
        final Config config = aliFactoryConfig.config(aliPayRequest.getParentId());
        return aliBuildService.buildWapPay(() -> new com.alipay.easysdk.payment.wap.Client(
                new com.alipay.easysdk.kernel.Client(getContext(config))
        )
                .asyncNotify(aliPayRequest.getAsyncNotify())
                .pay(
                        aliPayRequest.getSubject(),
                        aliPayRequest.getOutTradeNo(),
                        aliPayRequest.getTotalAmount(),
                        aliPayRequest.getQuitUrl(),
                        aliPayRequest.getReturnUrl()
                ));
    }

    @Override
    public void notify(Map<String, String> map, AliNotifyContext aliNotifyContext, String parentId) {
        aliBuildService.buildNotifyContext(map, params -> {
            try {
                final Config config = aliFactoryConfig.config(parentId);
                boolean isVerify = new com.alipay.easysdk.payment.common.Client(
                        new com.alipay.easysdk.kernel.Client(getContext(config))
                ).verifyNotify(params);
                log.info("支付宝验签结果：【{}】", isVerify);
                if (isVerify && "TRADE_SUCCESS".equals(params.get("trade_status"))) {
                    aliNotifyContext.buildNotifyContext(params);
                } else {
                    log.info("支付宝验签失败，启动重入");
                    final Consequence consequence = new Consequence(
                            true,
                            params.get("out_trade_no"),
                            parentId,
                            ConsequenceQueryEnum.ALI_QUERY.getPayWay(),
                            this,
                            new QueryProcess(orderQueryPoolExecutorService.getStoreInterface())
                    );
                    orderQueryPoolExecutorService.submitRequest(consequence);
                }
            }catch (Exception e) {
                e.printStackTrace();
                log.info("支付宝验签异常，启动重入");
                final Consequence consequence = new Consequence(
                        true,
                        params.get("out_trade_no"),
                        parentId,
                        ConsequenceQueryEnum.ALI_QUERY.getPayWay(),
                        this,
                        new QueryProcess(orderQueryPoolExecutorService.getStoreInterface())
                );
                orderQueryPoolExecutorService.submitRequest(consequence);
            }
        });

    }

    @Override
    public void refund(AliPayRequest aliPayRequest, AliRefundContext aliRefundContext) {
        final Config config = aliFactoryConfig.config(aliPayRequest.getParentId());
        Factory.setOptions(aliFactoryConfig.config(aliPayRequest.getParentId()));
        aliBuildService.buildGeneral(() -> {
            final AlipayTradeRefundResponse alipayTradeRefundResponse = new com.alipay.easysdk.payment.common.Client(
                    new com.alipay.easysdk.kernel.Client(getContext(config))
            )
                    .refund(
                            aliPayRequest.getOutTradeNo(),
                            aliPayRequest.getRefundAmount()
                    );
            log.info("支付宝退款结果：【{}】", JSON.toJSONString(alipayTradeRefundResponse));
            if (!"10000".equals(alipayTradeRefundResponse.code)) {
                throw new AliRuntimeException(alipayTradeRefundResponse.subMsg);
            }
            aliRefundContext.buildRefundContext(alipayTradeRefundResponse.toMap());
        });
    }

    @Override
    public void query(AliPayRequest aliPayRequest, boolean queryProcess, AliQueryContext aliQueryContext) {
        final Config config = aliFactoryConfig.config(aliPayRequest.getParentId());
        aliBuildService.buildGeneral(() -> {
            final AlipayTradeQueryResponse alipayTradeQueryResponse = new com.alipay.easysdk.payment.common.Client(
                    new com.alipay.easysdk.kernel.Client(getContext(config))
            )
                    .query(aliPayRequest.getOutTradeNo());
            log.info("支付宝订单查询结果：【{}】", JSON.toJSONString(alipayTradeQueryResponse));
            if (!"10000".equals(alipayTradeQueryResponse.code)) {
                log.info("orderNo：{} err info {}", aliPayRequest.getOutTradeNo(), alipayTradeQueryResponse.subMsg);
                return;
            }
            if (!queryProcess) {
                log.info("orderNo：{} store false", aliPayRequest.getOutTradeNo());
                return;
            }
            aliQueryContext.buildQueryContext(alipayTradeQueryResponse.toMap());
        });
    }

    @Override
    public void queryRefund(AliPayRequest aliPayRequest, AliQueryRefundContext aliQueryRefundContext) {
        final Config config = aliFactoryConfig.config(aliPayRequest.getParentId());
        aliBuildService.buildGeneral(() -> {
            final AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse = new com.alipay.easysdk.payment.common.Client(
                    new com.alipay.easysdk.kernel.Client(getContext(config))
            )
                    .queryRefund(
                            aliPayRequest.getOutTradeNo(),
                            aliPayRequest.getRequestNo()
                    );
            log.info("支付宝退款订单查询结果：【{}】", JSON.toJSONString(alipayTradeFastpayRefundQueryResponse));
            if (!"10000".equals(alipayTradeFastpayRefundQueryResponse.code)) {
                throw new AliRuntimeException(alipayTradeFastpayRefundQueryResponse.subMsg);
            }
            aliQueryRefundContext.buildQueryRefundContext(alipayTradeFastpayRefundQueryResponse.toMap());
        });
    }

    @Override
    public void cancel(AliPayRequest aliPayRequest, AliCancelContext aliCancelContext) {
        final Config config = aliFactoryConfig.config(aliPayRequest.getParentId());
        aliBuildService.buildGeneral(() -> {
            final AlipayTradeCancelResponse alipayTradeCancelResponse = new com.alipay.easysdk.payment.common.Client(
                    new com.alipay.easysdk.kernel.Client(getContext(config))
            )
                    .cancel(aliPayRequest.getOutTradeNo());
            log.info("支付宝撤销交易结果：【{}】", JSON.toJSONString(alipayTradeCancelResponse));
            if (!"10000".equals(alipayTradeCancelResponse.code)) {
                throw new AliRuntimeException(alipayTradeCancelResponse.subMsg);
            }
            aliCancelContext.buildCancelContext(alipayTradeCancelResponse.toMap());
        });
    }

    @Override
    public void close(AliPayRequest aliPayRequest, AliCloseContext aliCloseContext) {
        final Config config = aliFactoryConfig.config(aliPayRequest.getParentId());
        aliBuildService.buildGeneral(() -> {
            final AlipayTradeCloseResponse alipayTradeCloseResponse = new com.alipay.easysdk.payment.common.Client(
                    new com.alipay.easysdk.kernel.Client(getContext(config))
            )
                    .close(aliPayRequest.getOutTradeNo());
            log.info("支付宝关闭交易结果：【{}】", JSON.toJSONString(alipayTradeCloseResponse));
            if (!"10000".equals(alipayTradeCloseResponse.code)) {
                throw new AliRuntimeException(alipayTradeCloseResponse.subMsg);
            }
            aliCloseContext.buildCloseContext(alipayTradeCloseResponse.toMap());
        });
    }

    @Override
    public void GenericExecute(AliPayRequest aliPayRequest, AliGenericExecuteContext aliGenericExecuteContext) {
        final Config config = aliFactoryConfig.config(aliPayRequest.getParentId());
        aliBuildService.buildGenericExecute(() -> {
            final AlipayOpenApiGenericResponse alipayOpenApiGenericResponse = new com.alipay.easysdk.util.generic.Client(
                    new com.alipay.easysdk.kernel.Client(getContext(config))
            )
                    .execute(
                            aliPayRequest.getMethod(),
                            aliPayRequest.getTextParams(),
                            aliPayRequest.getBizParams()
                    );
            log.info("支付宝调用【{}】返回结果：【{}】", aliPayRequest.getMethod(), JSON.toJSONString(alipayOpenApiGenericResponse));
            if (!"10000".equals(alipayOpenApiGenericResponse.code)) {
                throw new AliRuntimeException(alipayOpenApiGenericResponse.subMsg);
            }
            aliGenericExecuteContext.buildGenericExecuteContext(alipayOpenApiGenericResponse.toMap());
        });
    }
}
