# Include information about types in hierarchy

To include information about type from the hierarchy, add the `@JsonTypeInfo` annotation to the parent class, 
enumerate all the subtypes in the `@JsonSubTypes` annotation: 

```java
@Data
@SuperBuilder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DigitalDocument.class, name = "digital"),
        @JsonSubTypes.Type(value = PaperDocument.class, name = "paper")
})
@NoArgsConstructor
abstract sealed class Document permits DigitalDocument, PaperDocument {

}
```