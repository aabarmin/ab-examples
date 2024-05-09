package dev.abarmin.junit.extension.builtin;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

@Slf4j
class JUnitWithTemporaryDirectoryTest {
    @Test
    @DisplayName("With temporary directory")
    void temporaryDirectoryAwareTest(@TempDir Path dir) {
        log.info("Temp dir is {}", dir);
    }
}
