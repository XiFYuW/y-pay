package com.y.pay.wx.service;

import com.y.pay.exception.WxRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 10:45
 */
@Service
@Slf4j
public class WxBuildService {

    public Map<String, String> buildPay(final WxBuildPayContext wxBuildPayContext){
        try {
            return wxBuildPayContext.buildPayContext();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WxRuntimeException(e.getMessage());
        }
    }

    public Map<String, String> buildGeneral(final WxBuildGeneralContext wxBuildGeneralContext) {
        try {
            final Map<String, String> data = wxBuildGeneralContext.buildGeneralContext();
            if (!data.get("return_code").equals("SUCCESS")) {
                throw new WxRuntimeException(data.get("return_msg"));
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WxRuntimeException(e.getMessage());
        }
    }

    public void buildNotifyContext(final Map<String, String> params, final WxBuildNotifyContext wxBuildNotifyContext){
        try {
//            final Map<String, String> params = WxNotifyMap.getParams(request);
            log.info("微信验签参数：【{}】", params);
            wxBuildNotifyContext.buildNotifyContext(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WxRuntimeException(e.getMessage());
        }
    }

    public interface WxBuildGeneralContext{
        Map<String, String> buildGeneralContext() throws Exception;
    }

    public interface WxBuildPayContext{
        Map<String, String> buildPayContext() throws Exception;
    }

    public interface WxBuildNotifyContext{
        void buildNotifyContext(Map<String, String> params) throws Exception;
    }

}
