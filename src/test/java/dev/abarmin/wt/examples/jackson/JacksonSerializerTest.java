package dev.abarmin.wt.examples.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class JacksonSerializerTest {
    private final Collection<Vehicle> VEHICLES = List.of(
            Car.builder()
                    .model("Tesla")
                    .seats(5)
                    .build(),
            Truck.builder()
                    .model("Volvo")
                    .capacity(25)
                    .build()
    );
    private final String JSON = """
            [ {
              "model" : "Tesla",
              "type" : "car",
              "seats" : 5
            }, {
              "model" : "Volvo",
              "type" : "truck",
              "capacity" : 25
            } ]
            """;

    @Test
    void serialize_shouldIncludeType() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .registerModule(new SimpleModule().addSerializer(new VehicleSerializer()))
                .enable(SerializationFeature.INDENT_OUTPUT);

        final String serialized = objectMapper.writeValueAsString(VEHICLES);
        assertThat(serialized).isEqualToIgnoringWhitespace(JSON);
    }

    @ParameterizedTest
    @ValueSource(classes = {VehicleDeserializer.class, VehicleStreamingDeserializer.class})
    void deserialize_shouldConsiderTypes(Class<JsonDeserializer<Vehicle>> deserializer) throws Exception {
        final JsonDeserializer<Vehicle> instance = deserializer.getConstructor().newInstance();
        final Collection<Object> vehicles = new ObjectMapper()
                .findAndRegisterModules()
                .registerModule(new SimpleModule().addDeserializer(Vehicle.class, instance))
                .readerForListOf(Vehicle.class)
                .readValue(JSON);

        assertThat(vehicles).isEqualTo(VEHICLES);
    }
}
