# Registering custom Jackson serializers and deserializers in Spring Boot

To register a custom serializer or deserializer in the Spring Application it is necessary to create
a class and annotate it with `@JsonComponent` annotation. Next the class may have public inner classes
for serialization or deserialization (extend `JsonSerializer<?>` and `JsonDeserializer<?>` respectively), 
or maybe type adapter itself. 

Example: 

```java
@JsonComponent
public class LocalDateConverter {
    public static class Deserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) {
            // do deserialization logic
        }
    }
}
```

The next step is to test these classes. Good idea is to use `@JsonTest` annotation on test and inject
an instance of `JacksonTester<?>` object (`GsonTester<?>` for Gson):

```java
@JsonTest
@ContextConfiguration(classes = LocalDateConverter.class)
class LocalDateConverterTest {
    @Autowired
    JacksonTester<TestObject> json;

    @Test
    void converter_canReadDates() {
        String content = """
                {
                    "date": "10.12.2024"
                }
                """;
        assertThat(json.parse(content))
                .hasFieldOrPropertyWithValue("date", LocalDate.of(2024, Month.DECEMBER, 10));
    }
    
    // ...
}
```