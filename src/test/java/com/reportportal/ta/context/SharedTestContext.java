package com.reportportal.ta.context;

import org.springframework.stereotype.Component;

@Component
public class SharedTestContext extends AbstractTestContext<Object> {

    public static final String DASHBOARD_NAME = "dashboard_name";

    public <T> T getTestObject(String key, Class<T> clazz) {
        return clazz.cast(get(key));
    }

    public void setTestObject(String key, Object object) {
        map.put(key, object);
    }
}
