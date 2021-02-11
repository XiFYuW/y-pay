package com.y.pay;

import com.y.pay.ali.service.AliPayEasyService;
import com.y.pay.connce.OrderQueryPoolExecutorService;
import com.y.pay.wx.service.WxPayEasyService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner{

    private final WxPayEasyService wxPayEasyService;

    private final AliPayEasyService aliPayEasyService;

    private final OrderQueryPoolExecutorService orderQueryPoolExecutorService;

    public ApplicationRunnerImpl(WxPayEasyService wxPayEasyService,
                                 AliPayEasyService aliPayEasyService,
                                 OrderQueryPoolExecutorService orderQueryPoolExecutorService) {
        this.wxPayEasyService = wxPayEasyService;
        this.aliPayEasyService = aliPayEasyService;
        this.orderQueryPoolExecutorService = orderQueryPoolExecutorService;
    }

    @Override
    public void run(ApplicationArguments args) {
        orderQueryPoolExecutorService.start();
        Runtime.getRuntime().addShutdownHook(new Thread(orderQueryPoolExecutorService::shutdown));

//        final Consequence consequence = new Consequence(
//                true,
//                "PT20112015202298010008",
//                ConsequenceQueryEnum.WX_QUERY.getPayWay(),
//                wxPayEasyService
//        );
//        orderQueryPoolExecutorService.submitRequest(consequence);
    }


}
