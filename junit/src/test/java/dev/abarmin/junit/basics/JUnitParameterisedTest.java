package dev.abarmin.junit.basics;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.TypedArgumentConverter;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class JUnitParameterisedTest {
    @Nested
    @DisplayName("Enumerated values")
    class EnumeratedValues {
        @ParameterizedTest(name = "{index} -> {arguments}")
        @DisplayName("String values")
        @ValueSource(strings = {"first", "second", "third"})
        void withStrings(String value) {
            log.info("Value is {}", value);
        }

        @ParameterizedTest
        @DisplayName("Int values")
        @ValueSource(ints = {1, 2, 3})
        void withNumbers(int value) {
            log.info("Value is {}", value);
        }

        @ParameterizedTest
        @DisplayName("Null and empty values")
        @NullAndEmptySource
        void nullValues(String value) {
            log.info("Value is {}", value);
        }
    }

    @Nested
    @DisplayName("Enum as value")
    class EnumValue {
        @ParameterizedTest
        @DisplayName("All values")
        @EnumSource(MyEnum.class)
        void allValues(MyEnum value) {
            log.info("Value is {}", value);
        }

        @ParameterizedTest
        @DisplayName("Only included values")
        @EnumSource(value = MyEnum.class, mode = EnumSource.Mode.INCLUDE, names = {"FIRST", "SECOND"})
        void includedValues(MyEnum value) {
            log.info("Value is {}", value);
        }

        @ParameterizedTest
        @DisplayName("Without excluded values")
        @EnumSource(value = MyEnum.class, mode = EnumSource.Mode.EXCLUDE, names = {"FIRST"})
        void excludeValues(MyEnum value) {
            log.info("Value is {}", value);
        }
    }

    @Nested
    @DisplayName("CSV as source")
    class CsvValues {
        @ParameterizedTest
        @DisplayName("Simple CSV source")
        @CsvSource({
                "first, 1",
                "second, 2",
                "third, 3"
        })
        void simpleCsvSource(String str, int number) {
            log.info("String is {}, int is {}", str, number);
        }

        @ParameterizedTest
        @DisplayName("CSV with null")
        @CsvSource(value = {
                "nil, 1"
        }, nullValues = "nil")
        void csvWithNull(String str, int number) {
            log.info("String is {}, int is {}", str, number);
        }

        @ParameterizedTest
        @DisplayName("CSV and argument aggregation")
        @CsvSource({
                "first, 1",
                "second, 2",
                "third, 3"
        })
        void withArgumentAccessor(ArgumentsAccessor accessor) {
            String str = accessor.getString(0);
            Integer number = accessor.getInteger(1);

            log.info("String is {}, int is {}", str, number);
        }

        @ParameterizedTest
        @DisplayName("CSV with custom aggregator")
        @CsvSource({
                "first, 1",
                "second, 2",
                "third, 3"
        })
        void withCustomAggregator(@AggregateWith(MyArgumentAggregator.class) TestCase testCase) {
            log.info("String is {}, int is {}", testCase.string, testCase.number);
        }

        @ParameterizedTest
        @DisplayName("External CSV source")
        @CsvFileSource(resources = "/data_1.csv", numLinesToSkip = 1)
        void externalCsvSource(String str, int number) {
            log.info("String is {}, int is {}", str, number);
        }
    }

    @Nested
    class WithArgumentConverter {
        @ParameterizedTest
        @DisplayName("With argument converter")
        @ValueSource(strings = {
                "2023.12.10",
                "2024.11.08"
        })
        void withCustomArgumentConverter(@ConvertWith(MyDateConverter.class) LocalDate date) {
            log.info("Date is {}", date);
        }
    }

    @Nested
    @DisplayName("Custom arguments provider")
    class WithArgumentProvider {
        @ParameterizedTest
        @DisplayName("With arguments provider")
        @ArgumentsSource(MyArgumentProvider.class)
        void withArgumentsProvider(String str, int number) {
            log.info("String is {}, int is {}", str, number);
        }
    }

    @Nested
    @DisplayName("Custom method argument provider")
    class WithMethodArgumentProvider {
        @ParameterizedTest
        @DisplayName("Custom method provides arguments")
        @MethodSource("testCases")
        void withCustomMethodArgumentProvider(TestCase testCase) {
            log.info("String is {}, int is {}", testCase.string, testCase.number);
        }

        static Collection<TestCase> testCases() {
            return List.of(
                    TestCase.builder()
                            .string("First")
                            .number(1)
                            .build(),
                    TestCase.builder()
                            .string("Second")
                            .number(2)
                            .build()
            );
        }
    }

    enum MyEnum {
        FIRST,
        SECOND,
        THIRD
    }

    @Builder
    static class TestCase {
        private String string;
        private int number;
    }

    static class MyArgumentAggregator implements ArgumentsAggregator {
        @Override
        public TestCase aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return TestCase.builder()
                    .string(accessor.getString(0))
                    .number(accessor.getInteger(1))
                    .build();
        }
    }

    static class MyDateConverter extends TypedArgumentConverter<String, LocalDate> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        protected MyDateConverter() {
            super(String.class, LocalDate.class);
        }

        @Override
        protected LocalDate convert(String source) throws ArgumentConversionException {
            return LocalDate.parse(source, FORMATTER);
        }
    }

    static class MyArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of("first", 1),
                    Arguments.of("second", 2),
                    Arguments.of("third", 3)
            );
        }
    }
}
