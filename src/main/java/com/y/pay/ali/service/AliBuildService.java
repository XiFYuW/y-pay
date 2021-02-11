package com.y.pay.ali.service;

import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.y.pay.exception.AliRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 10:45
 */
@Service
@Slf4j
public class AliBuildService {

    public Map<String, Object> buildPay(final AliBuildPayContext ailBuildPayContext){
        try {
            final AlipayTradeAppPayResponse alipayTradeAppPayResponse = ailBuildPayContext.buildPayContext();
            if (ResponseChecker.success(alipayTradeAppPayResponse)) {
                return alipayTradeAppPayResponse.toMap();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AliRuntimeException(e.getMessage());
        }
        return null;
    }

    public Map<String, Object> buildWapPay(final AliBuildWapPayContext aliBuildWapPayContext){
        try {
            final AlipayTradeWapPayResponse alipayTradeWapPayResponse = aliBuildWapPayContext.buildWapPayContext();
            if (ResponseChecker.success(alipayTradeWapPayResponse)) {
                return alipayTradeWapPayResponse.toMap();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AliRuntimeException(e.getMessage());
        }
        return null;
    }

    public void buildNotifyContext(Map<String, String> params, final AliBuildNotifyContext ailBuildNotifyContext){
        //final Map<String, String> params = AliNotifyMap.getParams(request);
        log.info("支付宝验签参数：【{}】", params);
        try {
            ailBuildNotifyContext.buildNotifyContext(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AliRuntimeException(e.getMessage());
        }
    }

    public void buildGeneral(final AliBuildGeneralContext ailBuildGeneralContext) {
        try {
            ailBuildGeneralContext.buildGeneralContext();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AliRuntimeException(e.getMessage());
        }
    }

    public void buildGenericExecute(final AliBuildGenericExecuteContext ailBuildGenericExecuteContext){
        try {
            ailBuildGenericExecuteContext.buildGenericExecuteContext();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AliRuntimeException(e.getMessage());
        }
    }

    public interface AliBuildGeneralContext{
        void buildGeneralContext() throws Exception;
    }

    public interface AliBuildPayContext{
        AlipayTradeAppPayResponse buildPayContext() throws Exception;
    }

    public interface AliBuildWapPayContext{
        AlipayTradeWapPayResponse buildWapPayContext() throws Exception;
    }

    public interface AliBuildNotifyContext{
        void buildNotifyContext(Map<String, String> params) throws Exception;
    }

    public interface AliBuildGenericExecuteContext{
        void buildGenericExecuteContext() throws Exception;
    }
}
