package com.y.pay.ali;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiFYuW
 * @since  2020/09/13 9:43
 */
public class AliNotifyMap {

    public static Map<String, String> getParams(final HttpServletRequest request) {
        final Map<String, String> params = new HashMap<>();
        final Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                valueStr.append((i == values.length - 1) ? values[i] : values[i] + ",");
            }
            params.put(name, valueStr.toString());
        }
        return params;
    }
}
