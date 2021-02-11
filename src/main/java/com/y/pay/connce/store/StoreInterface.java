package com.y.pay.connce.store;

import com.y.pay.connce.Consequence;

public interface StoreInterface {

    /*清除订单查询任务*/
    void storeOrder();

    /*清除订单查询任务入存储*/
    void submitRequestLeft(Consequence consequence);

    boolean checkIsPerform(String orderNo);
}
