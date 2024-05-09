package dev.abarmin.junit.extension.custom;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class SimpleLifecycleExtension implements Extension,
        BeforeEachCallback, AfterEachCallback,
        BeforeAllCallback, AfterAllCallback,
        BeforeTestExecutionCallback, AfterTestExecutionCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        log.info("Executing before each");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        log.info("Executing after each");
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        log.info("Executing before all");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        log.info("Executing after all");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        log.info("Executing after test");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        log.info("Executing before test");
    }
}
