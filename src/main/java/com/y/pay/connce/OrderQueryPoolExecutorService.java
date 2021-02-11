package com.y.pay.connce;

import com.y.pay.connce.store.StoreInterface;

/**
 * @author XiFYuW
 * @since  2020/10/20 16:33
 */
public interface OrderQueryPoolExecutorService /*订单查询池*/{
    void start();

    void shutdown();

    void submitRequest(final Consequence consequence);

    StoreInterface getStoreInterface();
}
