package com.reportportal.ta.hooks;

import com.reportportal.ta.hooks.clean.up.TestDeletionProvider;
import io.cucumber.java.After;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CleanUpHooks {

    @Autowired
    private TestDeletionProvider testDeletionProvider;

    /**
     * Method deleted created test data during scenario.
     */
    @After(order = 1)
    public void cleanUp() {
        log.info("Clean up hooks working !!!");
        testDeletionProvider.deleteCreatedTestData();
    }
}