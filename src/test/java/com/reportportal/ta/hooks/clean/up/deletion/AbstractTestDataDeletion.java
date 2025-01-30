package com.reportportal.ta.hooks.clean.up.deletion;

import com.reportportal.ta.context.DeletionTestContext;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTestDataDeletion implements TestDataDeletion {

    @Autowired
    protected DeletionTestContext deletionTestContext;

    protected Optional<List<String>> getDataForDeletion(final String key) {
        return deletionTestContext.contains(key)
            ? Optional.of(deletionTestContext.getListObjectForDeletion(key))
            : Optional.empty();
    }
}
