package com.reportportal.ta.hooks.clean.up;

import com.reportportal.ta.hooks.clean.up.deletion.TestDataDeletion;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
@RequiredArgsConstructor
public class TestDeletionProviderImpl implements TestDeletionProvider {

    private final List<TestDataDeletion> testDataDeletions;

    @Override
    public void deleteCreatedTestData() {
        testDataDeletions.forEach(TestDataDeletion::deleteCreatedTestData);
    }
}
