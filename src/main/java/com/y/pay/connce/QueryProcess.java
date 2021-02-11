package com.y.pay.connce;

import com.y.pay.connce.store.StoreInterface;

public class QueryProcess {

    private final StoreInterface storeInterface;

    public QueryProcess(StoreInterface storeInterface) {
        this.storeInterface = storeInterface;
    }

    boolean checkIsPerform(final String orderNo){
        return storeInterface.checkIsPerform(orderNo);
    }
}
