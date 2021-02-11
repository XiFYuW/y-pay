package com.y.pay.connce;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author XiFYuW
 * @since  2019/11/17 19:54
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    public static Object getBean(String name){
        return SpringContextUtil.applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return SpringContextUtil.applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return SpringContextUtil.applicationContext.getBean(name, clazz);
    }

}
