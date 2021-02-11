package com.y.pay.ali;

/**
 * @author XiFYuW
 * @since  2020/09/13 10:26
 */
public enum TradeStatusEnum {
    WAIT_BUYER_PAY("交易创建，等待买家付款","WAIT_BUYER_PAY", 0),
    TRADE_CLOSED("未付款交易超时关闭，或支付完成后全额退款","TRADE_CLOSED", 1),
    TRADE_SUCCESS("交易支付成功","TRADE_SUCCESS", 2),
    TRADE_FINISHED("交易结束，不可退款","TRADE_FINISHED", 3);

    private String msg;

    private String value;

    private Integer code;

    TradeStatusEnum(String msg, String value, Integer code) {
        this.msg = msg;
        this.value = value;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }
}
