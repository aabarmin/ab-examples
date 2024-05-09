package dev.abarmin.junit.extension.custom;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Slf4j
@ExtendWith({
        MyParameterExtension.class,
        SimpleLifecycleExtension.class
})
class JUnitBasicTest {
    @Test
    @DisplayName("Simple test")
    void doNothing() {
        log.info("Test that does nothing");
    }

    @Test
    @DisplayName("Test with custom parameter")
    void testWithCustomParameter(@MyParameter int randomValue) {
        log.info("Value is {}", randomValue);
    }
}
