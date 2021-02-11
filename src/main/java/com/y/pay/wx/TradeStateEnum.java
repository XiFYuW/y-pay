package com.y.pay.wx;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:45
 */
public enum TradeStateEnum {
    SUCCESS("支付成功", "SUCCESS", 2),
    REFUND("转入退款", "REFUND", 3),
    NOTPAY("未支付","NOTPAY", 0),
    REVOKED("已撤销（付款码支付）", "REVOKED", 4),
    USERPAYING("用户支付中（付款码支付）","USERPAYING", 5),
    PAYERROR("支付失败(其他原因，如银行返回失败)", "NOTPAY", 1),
    CLOSED("已关闭","CLOSED", 6);

    private String msg;

    private String value;

    private Integer code;

    TradeStateEnum(String msg, String value, Integer code) {
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
