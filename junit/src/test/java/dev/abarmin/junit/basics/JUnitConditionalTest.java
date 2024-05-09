package dev.abarmin.junit.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledInNativeImage;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.util.Random;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Slf4j
class JUnitConditionalTest {
    @Test
    @DisplayName("Always disabled test")
    @Disabled("Because I can disable the test")
    void alwaysDisabledTest() {
        log.info("Always disabled");
    }

    @Nested
    @DisplayName("By operation system")
    class OperationSystem {
        @Test
        @EnabledOnOs(OS.MAC)
        void enabledOnMacOnly() {
            log.info("This is MacOS only test");
        }

        @Test
        @EnabledOnOs(OS.LINUX)
        void enabledOnLinuxOnly() {
            log.info("This is linux only test");
        }

        @Test
        @EnabledOnOs(value = OS.MAC, architectures = "aarch64")
        void enabledOnAarch64() {
            log.info("This is Apple silicon test");
        }

        @Test
        @EnabledOnOs(value = OS.MAC, architectures = "x86_64")
        void enabledOnIntel() {
            log.info("This is Intel test");
        }
    }

    @Nested
    @DisplayName("By JRE")
    class Jre {
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void java8TestOnly() {
            log.info("I'm Java 8 test");
        }

        @Test
        @EnabledForJreRange(min = JRE.JAVA_17, max = JRE.JAVA_21)
        void java17To21() {
            log.info("JRE range test");
        }

        @Test
        @EnabledForJreRange(max = JRE.JAVA_17)
        void upToJava17() {
            log.info("Up to Java 17");
        }
    }

    @Nested
    @DisplayName("Native images")
    class NativeImages {
        @Test
        @EnabledInNativeImage
        void onlyWhenBuiltWithGraal() {
            log.info("Only for native images");
        }
    }

    @Nested
    @DisplayName("By system properties")
    class SystemProperties {
        @Test
        @DisplayName("OS arch matches .*64")
        @EnabledIfSystemProperty(named = "os.arch", matches = ".*64") // regex
        void whenArchIsAppleSilicon() {
            log.info("Apple silicon test");
        }
    }

    @Nested
    @DisplayName("By environment variables")
    class EnvironmentVariables {
        @Test
        @DisplayName("On GitHub Actions only")
        @EnabledIfEnvironmentVariable(named = "CI", matches = "true") // regex
        void onGithubActionsOnly() {
            log.info("On GitHub only");
        }

        @Test
        @DisplayName("When gradle is installed with SDKMan")
        @EnabledIfEnvironmentVariable(named = "GRADLE_HOME", matches = ".*\\.sdkman.*")
        void whenGradleFromSdkMan() {
            log.info("Custom gradle");
            log.info(System.getenv("GRADLE_HOME"));
        }
    }

    @Nested
    @DisplayName("With custom conditions")
    class CustomCondition {
        @Test
        @EnabledIf("myCondition")
        @DisplayName("With custom complicated condition")
        void moreComplicatedCondition() {
            log.info("Enabled if condition is true");
        }

        boolean myCondition() {
            return new Random().nextBoolean();
        }

        @Test
        @DisplayName("With custom external condition")
        @EnabledIf("dev.abarmin.junit.basics.ExternalCondition#anotherCondition")
        void externalCondition() {
            log.info("With external condition");
        }
    }

    @Nested
    @DisplayName("With assumptions")
    class WithAssumption {
        @Test
        @DisplayName("Positive assumption")
        void positiveAssumption() {
            assumeTrue(() -> System.getenv("GRADLE_HOME").contains(".sdkman"));
            log.info("This is executed only when gradle is installed via SDKMan");
        }

        @Test
        @DisplayName("Negative assumption")
        void negativeAssumption() {
            assumeFalse(() -> System.getenv("GRADLE_HOME").contains(".sdkman"));
            log.info("This is executed only when gradle is installed as a package");
        }
    }
}
