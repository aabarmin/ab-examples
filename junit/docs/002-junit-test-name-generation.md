# JUnit 5 Test name generation

Let's assume there is a basic test class with the following tests: 

```java
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
```

## Standard name generator

Leaves name of the test as is:

```java
    @Nested
    @DisplayName("Standard name generator")
    @DisplayNameGeneration(DisplayNameGenerator.Standard.class)
    class WithDefaultNames extends BaseTest {

    }
```

```markdown
* firstTest()
* given_when_then()
```

## Simple name generator

Removes `()` from test names

```java
    @Nested
    @DisplayName("Simple name generator")
    @DisplayNameGeneration(DisplayNameGenerator.Simple.class)
    class WithSimpleNames extends BaseTest {

    }
```

```markdown
* firstTest
* given_when_then
```

## Replace underscore

Replaces `_` with just spaces

```java
    @Nested
    @DisplayName("Replaces underscore")
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class WithUnderscoreReplace extends BaseTest {

    }
```

```markdown
* firstTest
* given when then
```

## Indicative sentences

Joins name of the class with the name of the test: 

```java
    @Nested
    @DisplayName("Concats with parent display name")
    @DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
    class WithSentencesGeneration extends BaseTest {

    }
```

```markdown
* Concats with parent display name, firstTest()
* Concats with parent display name, given_when_then()
```