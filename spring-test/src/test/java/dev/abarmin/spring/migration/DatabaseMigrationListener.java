package dev.abarmin.spring.migration;

import org.flywaydb.core.Flyway;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.transaction.TestContextTransactionUtils;

import javax.sql.DataSource;

public class DatabaseMigrationListener implements TestExecutionListener {
    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        DataSource dataSource = TestContextTransactionUtils.retrieveDataSource(testContext, "dataSource");

        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }
}
