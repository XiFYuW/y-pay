package com.y.pay.connce;

import com.y.pay.connce.store.StoreInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @author XiFYuW
 * @since  2020/10/20 16:31
 */
@Service
@Slf4j
public class OrderQueryPoolService implements OrderQueryPoolExecutorService {

    private final ThreadPoolExecutor orderQueryExecutor;

    private final ScheduledExecutorService cleanOrderQueryExecutors;/*订单查询清楚任务*/

    private final ScheduledExecutorService orderQueryLaterExecutors;/*订单查询入orderQueryExecutor失败，重试*/

    private final StoreInterface storeInterface;

    public OrderQueryPoolService(StoreInterface storeInterface) {
        this.storeInterface = storeInterface;
        BlockingQueue<Runnable> orderQueryQueue = new LinkedBlockingQueue<>();
        this.orderQueryExecutor = new ThreadPoolExecutor(
                1,
                2,
                1000 * 60,
                TimeUnit.MILLISECONDS,
                orderQueryQueue,
                new OrderQueryThreadFactoryImpl("OrderQueryExecutorThread_"));
        this.cleanOrderQueryExecutors = Executors.newSingleThreadScheduledExecutor(
                new OrderQueryThreadFactoryImpl("CleanOrderQueryExecutorsScheduledThread_")
        );
        this.orderQueryLaterExecutors = Executors.newSingleThreadScheduledExecutor(
                new OrderQueryThreadFactoryImpl("OrderQueryLaterExecutorsScheduledThread_")
        );
    }

    @Override
    public void start() {
        /*清除订单查询任务*/
        this.cleanOrderQueryExecutors.scheduleAtFixedRate(storeInterface::storeOrder, 1, 30 * 60, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        orderQueryExecutor.shutdown();
        cleanOrderQueryExecutors.shutdown();
        orderQueryLaterExecutors.shutdown();
    }

    @Override
    public void submitRequest(final Consequence consequence) {
        storeInterface.submitRequestLeft(consequence);
        submit(new ConsequenceRequest(consequence));
    }

    @Override
    public StoreInterface getStoreInterface() {
        return this.storeInterface;
    }

    private void submit(final ConsequenceRequest consequenceRequest){
        try {
            this.orderQueryExecutor.submit(consequenceRequest/*入池*/);
            log.info("订单：{}, 入orderQueryExecutor成功", consequenceRequest.getConsequence().getOutTradeNo());
        }catch (RejectedExecutionException e){
            submitLater(consequenceRequest/*失败 >> 入池*/);
            log.info("订单：{}, 入orderQueryLaterExecutors成功", consequenceRequest.getConsequence().getOutTradeNo());
        }
    }

    private void submitLater(final ConsequenceRequest consequenceRequest) {
        this.orderQueryLaterExecutors.schedule((Runnable) () -> OrderQueryPoolService.this.orderQueryExecutor.submit(consequenceRequest), 5000, TimeUnit.MILLISECONDS);
    }
}
