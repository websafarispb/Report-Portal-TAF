package com.reportportal.ta.context;

import com.reportportal.ta.exception.TestDataException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTestContext<T> {

    private static final String CONTAINER_DOES_NOT_CONTAIN_OBJECT_BY_KEY_ERROR =
        "%s container doesn't contain the '%s' key";

    protected Map<String, T> map = new HashMap<>();

    protected T get(String key) {
        new TestDataException(String.format(CONTAINER_DOES_NOT_CONTAIN_OBJECT_BY_KEY_ERROR,
            this.getClass().getSimpleName(), key)).throwIfFalse(contains(key));
        return map.get(key);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }
}
