# JUnit 5 Parameterised tests

Same test is executed with different parameters, parameters are defined outside the test. 

## Override name of the iteration

Name of the iteration might be built based on provided data: 

```java
    @ParameterizedTest(name = "{index} -> {arguments}")
    @DisplayName("String values")
    @ValueSource(strings = {"first", "second", "third"})
    void withStrings(String value) {
        log.info("Value is {}", value);
    }
```

Names of iterations: 

```markdown
* 1 -> first
* 2 -> second
* 3 -> third
```

## Int values are parameters

Just an example of using `@ValueSource` annotation: 

```java
    @ParameterizedTest
    @DisplayName("Int values")
    @ValueSource(ints = {1, 2, 3})
    void withNumbers(int value) {
        log.info("Value is {}", value);
    }
```

## Null and empty string values

There are two annotations `@NullSource` and `@EmptySource` that provide `null` and empty values. 
Can be combined using `@NullAndEmptySource`: 

```java
    @ParameterizedTest
    @DisplayName("Null and empty values")
    @NullAndEmptySource
    void nullValues(String value) {
        log.info("Value is {}", value);
    }
```

## All values from `enum`

All values will be taken: 

```java
    @ParameterizedTest
    @DisplayName("All values")
    @EnumSource(MyEnum.class)
    void allValues(MyEnum value) {
        log.info("Value is {}", value);
    }
```

## Included values from enum only

Only allowed values will be used

```java
    @ParameterizedTest
    @DisplayName("Only included values")
    @EnumSource(value = MyEnum.class, mode = EnumSource.Mode.INCLUDE, names = {"FIRST", "SECOND"})
    void includedValues(MyEnum value) {
        log.info("Value is {}", value);
    }
```

## All values from enum except excluded

```java
    @ParameterizedTest
    @DisplayName("Without excluded values")
    @EnumSource(value = MyEnum.class, mode = EnumSource.Mode.EXCLUDE, names = {"FIRST"})
    void excludeValues(MyEnum value) {
        log.info("Value is {}", value);
    }
```

## CSV as a source of values

Values are defined as primitive types, one group of values per string: 

```java
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
```

## CSV that has `null` value

By default `null` in the CSV will be considered as a string with `null` value. To define
a value to be considered as `null` use `nullValues` property: 

```java
        @ParameterizedTest
        @DisplayName("CSV with null")
        @CsvSource(value = {
                "nil, 1"
        }, nullValues = "nil")
        void csvWithNull(String str, int number) {
            log.info("String is {}, int is {}", str, number);
        }
```

## Access to the element in the CSV

There might be many columns in the CSV, it's possible to get access to each and every
element directly using `ArgumentsAccessor`: 

```java
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
```

## Convert a CSV row into an object

Let's assume there is a DTO wit the following props: 

```java
    @Builder
    static class TestCase {
        private String string;
        private int number;
    }
```

To read a line and convert to the `TestCase` it's necessary to use an argument aggregator and
point out to it using `@AggregateWith` annotation: 

```java
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
```

The aggregator itself: 

```java
    static class MyArgumentAggregator implements ArgumentsAggregator {
        @Override
        public TestCase aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return TestCase.builder()
                    .string(accessor.getString(0))
                    .number(accessor.getInteger(1))
                    .build();
        }
    }
```

## Convert values with argument converters

There might be necessary to convert some values from strings using custom logic, for example, parse
dates in a specific format. There is a `@ConvertWith` annotation that defines a converter to be used: 

```java
    @ParameterizedTest
    @DisplayName("With argument converter")
    @ValueSource(strings = {
            "2023.12.10",
            "2024.11.08"
    })
    void withCustomArgumentConverter(@ConvertWith(MyDateConverter.class) LocalDate date) {
        log.info("Date is {}", date);
    }
```

And the converter itself: 

```java
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
```

## Use external CSV file as a source of values

Values are defined in the external file, referred using `@CsvFileSource` annotation: 

```java
    @ParameterizedTest
    @DisplayName("External CSV source")
    @CsvFileSource(resources = "/data_1.csv", numLinesToSkip = 1)
    void externalCsvSource(String str, int number) {
        log.info("String is {}, int is {}", str, number);
    }
```

Use `numLinesToSkip` if a CSV file has headers. 

## `ArgumentsProvider` to share the same arguments between tests

```java
    @ParameterizedTest
    @DisplayName("With arguments provider")
    @ArgumentsSource(MyArgumentProvider.class)
    void withArgumentsProvider(String str, int number) {
        log.info("String is {}, int is {}", str, number);
    }
```

The provider is a separate class: 

```java
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
```

## Method source - parameters are provided by a method

The method might return a collection of `Arguments` but it is more convenient to return
a collection or a stream of DTOs. 

```java
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
```
