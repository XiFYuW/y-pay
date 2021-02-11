package com.y.pay.connce.store;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsequencePay implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*1.是 0.否*/
    private Integer isConsequence;

    /*商户订单号*/
    private String orderNo;

    /*0.没有执行 1.已执行*/
    private Integer isPerform;

    /*支付方式1：支付宝 2：微信*/
    private Integer payWay;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;

    /*"0.WAIT_BUYER_PAY.交易创建，等待买家付款\n" +
            "1.TRADE_CLOSED.未付款交易超时关闭，或支付完成后全额退款\n" +
            "2.TRADE_SUCCESS.交易支付成功\n" +
            "3.TRADE_FINISHED.交易结束，不可退款"*/
    private Integer aliOrderStatus;


    /*"2.SUCCESS.支付成功\n" +
            "3.REFUND.转入退款\n" +
            "0.NOTPAY.未支付\n" +
            "4.REVOKED.已撤销（付款码支付）\n" +
            "5.USERPAYING.用户支付中（付款码支付）\n" +
            "1.PAYERROR.支付失败(其他原因，如银行返回失败\n" +
            "6.CLOSED.已关闭"*/
    private Integer wxOrderStatus;

    /*代理商id*/
    private String parentId;
}
