package dev.abarmin.junit.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("Test name generation")
public class JUnitCustomNameGeneratorTest {
    @Nested
    @DisplayName("Standard name generator")
    @DisplayNameGeneration(DisplayNameGenerator.Standard.class)
    class WithDefaultNames extends BaseTest {

    }

    @Nested
    @DisplayName("Simple name generator")
    @DisplayNameGeneration(DisplayNameGenerator.Simple.class)
    class WithSimpleNames extends BaseTest {

    }

    @Nested
    @DisplayName("Replaces underscore")
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class WithUnderscoreReplace extends BaseTest {

    }

    @Nested
    @DisplayName("Concats with parent display name")
    @DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
    class WithSentencesGeneration extends BaseTest {

    }

    abstract class BaseTest {
        @Test
        void firstTest() {
            log.info("First test");
        }

        @Test
        void given_when_then() {
            log.info("Second test");
        }
    }
}
