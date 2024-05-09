# JUnit 5 Conditional tests

It's possible to switch on and off tests based on some conditions. 

## Always disabled test

It's possible to explain a reason why the test is disabled: 

```java
    @Test
    @DisplayName("Always disabled test")
    @Disabled("Because I can disable the test")
    void alwaysDisabledTest() {
        log.info("Always disabled");
    }
```

## Based on operation's system architecture

Can be used with both `@EnabledOnOs` and `@DisabledOnOs`:

```java
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
```

## By JRE

Different tests for different JRE versions, both `@EnabledOnJre` and `@DisabledOnJre` are supported: 

```java
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
```

## Tests for GraalVM native images

```java
    @Nested
    @DisplayName("Native images")
    class NativeImages {
        @Test
        @EnabledInNativeImage
        void onlyWhenBuiltWithGraal() {
            log.info("Only for native images");
        }
    }
```

## Based on system properties

Property is obtained via `System.getProperty()`

```java
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
```

## Based on environment variable

Variable can be obtained via `System.getenv()`

```java
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
```

## Custom conditions

Method that represents a condition may be declared in the same class: 

```java
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
    }
```

Or in the external class: 

```java
    @Nested
    @DisplayName("With custom conditions")
    class CustomCondition {
        @Test
        @DisplayName("With custom external condition")
        @EnabledIf("dev.abarmin.junit.basics.ExternalCondition#anotherCondition")
        void externalCondition() {
            log.info("With external condition");
        }
    }

    public class ExternalCondition {
        static public boolean anotherCondition() {
            return new Random().nextBoolean();
        }
    }    
```

## Assumptions

Allow to execute part of the test only when condition is met. If not met, the test will not be executed: 

```java
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
```