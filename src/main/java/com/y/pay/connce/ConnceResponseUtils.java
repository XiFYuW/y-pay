package com.y.pay.connce;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
@Slf4j
@Component
public class ConnceResponseUtils {

    private static String URL;
    public String getURL() {
        return URL;
    }
    @Value("${system.notify-url}")
    public void setURL(String notifyUrl) {
        ConnceResponseUtils.URL = notifyUrl + "api/selfOrder/manualPartakerNotify";
    }

    public static Map<String, String> getMap(String data){
        final JSONObject jsonObject = JSON.parseObject(data);
        final Set<String> set = jsonObject.keySet();
        AtomicReference<HashMap<String, String>> mapAtomicReference = new AtomicReference<>(new HashMap<>());
        for (String key: set) {
            HashMap<String, String> map = mapAtomicReference.get();
            map.put(key, jsonObject.getString(key));
            mapAtomicReference.set(map);
        }
        return mapAtomicReference.get();
    }

    public static boolean isSUCCESS(String data){
        final JSONObject jsonObject = JSON.parseObject(data);
        return jsonObject.getInteger("code") == 2000;
    }

    public static String requestBody(Object resp){
        log.info("请求：{}", URL);
        return HttpRequest
                .post(URL)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .form("data", JSON.toJSONString(resp))
                .execute()
                .body();
    }
}
