package com.reportportal.ta.context;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DeletionTestContext extends AbstractTestContext<List<String>> {

    public static final String DASHBOARD_IDS = "dashboard_ids";

    public List<String> getListObjectForDeletion(String key) {
        return get(key);
    }

    public void setListObjectForDeletion(String key, List<String> deletionObjectList) {
        map.putIfAbsent(key, deletionObjectList);
    }

    public void addObjectForDeletion(String key, String deletionObject) {
        if (!contains(key)) {
            setListObjectForDeletion(key, new ArrayList<>(List.of(deletionObject)));
        } else {
            getListObjectForDeletion(key).add(deletionObject);
        }
    }
}