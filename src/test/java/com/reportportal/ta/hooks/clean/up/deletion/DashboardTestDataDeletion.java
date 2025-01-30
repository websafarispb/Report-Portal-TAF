package com.reportportal.ta.hooks.clean.up.deletion;

import static com.reportportal.ta.context.DeletionTestContext.DASHBOARD_IDS;

import com.reportportal.ta.context.DeletionTestContext;
import com.reportportal.ta.services.api.ReportPortalDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Lazy
public class DashboardTestDataDeletion extends AbstractTestDataDeletion {

    @Autowired
    @Lazy
    ReportPortalDashboardService reportPortalDashboardService;

    @Override
    public void deleteCreatedTestData() {
        log.info("TRY TO DELETE SOMETHING !!! DASHBOARD");
        getDataForDeletion(DASHBOARD_IDS)
            .ifPresent(actions -> actions.forEach(reportPortalDashboardService::deleteDashboard));
    }
}
