# Custom Serializers and Deserializers

Create a class that extends `StdSerializer<?>` or `StdDeserializer<?>`, register it in the module: 

```java
        final ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .registerModule(new SimpleModule().addSerializer(new VehicleSerializer()))
                .enable(SerializationFeature.INDENT_OUTPUT);
```