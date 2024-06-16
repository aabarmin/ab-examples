package dev.abarmin.spring.repository;

import dev.abarmin.spring.migration.DatabaseMigrationListener;
import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@TestExecutionListeners(value = DatabaseMigrationListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public @interface PerformDatabaseMigration {
}
