package dev.abarmin.junit.extension.custom;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class SimpleLifecycleExtension implements Extension,
        BeforeEachCallback, AfterEachCallback,
        BeforeAllCallback, AfterAllCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        log.info("Executing some code before {}", context.getDisplayName());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        log.info("Executing some code after {}", context.getDisplayName());
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        log.info("Executing before all {}", context.getDisplayName());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        log.info("Executing after all {}", context.getDisplayName());
    }
}
