package com.y.pay.wx;

/**
 * @author XiFYuW
 * @since  2020/09/13 17:45
 */
public enum RefundStatusEnum {
    SUCCESS("退款成功","SUCCESS"),
    REFUNDCLOSE("退款关闭","REFUNDCLOSE"),
    PROCESSING("未支付","PROCESSING"),
    /**
     * 退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款。$n为下标，从0开始编号。
     */
    CHANGE("退款异常","CHANGE");
    private String msg;

    private String code;

    RefundStatusEnum(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }
}
