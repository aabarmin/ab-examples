# GSON Type Adapter

GSON Type Adapter allows to define custom serializers and deserializers for types. Create a class
that implements `JsonSerializer<?>` or `JsonDeserializer<?>` interfaces. Next register them in `GsonBuilder`:

```java
private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeHierarchyAdapter(Document.class, new DocumentTypeAdapter())
        .create();
```