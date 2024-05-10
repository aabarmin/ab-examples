# JUnit 5 Test tagging

Tests might me grouped together, groups might be executed by Gradle. To define a group, put `@Tag` annotation
on top of the test: 

```java
@DisplayName("Tests with tags")
class JUnitTaggedTest {
    @Test
    @Tag("Slow")
    @DisplayName("First slow test")
    void firstSlowTest() {
        log.info("First slow test");
    }
    
    @Test
    @Tag("Quick")
    void secondQuickTest() {
        log.info("Second quick test");
    }
}
```

Execution of tests group by tag in Gradle: 

```groovy
task slowTests(type: Test) {
    useJUnitPlatform {
        includeTags("Slow")
    }
}

task quickTests(type: Test) {
    useJUnitPlatform {
        includeTags("Quick")
    }
}
```