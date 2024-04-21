package dev.abarmin.jackson.custom.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

class VehicleDeserializer extends StdDeserializer<Vehicle> {
    public VehicleDeserializer() {
        super(Vehicle.class);
    }

    @Override
    public Vehicle deserialize(JsonParser p,
                               DeserializationContext ctxt) throws IOException, JacksonException {

        final JsonNode jsonNode = ctxt.readTree(p);
        final String type = jsonNode.get("type").asText();

        if ("car".equals(type)) {
            return Car.builder()
                    .model(jsonNode.get("model").asText())
                    .seats(jsonNode.get("seats").asInt())
                    .build();
        } else if ("truck".equals(type)) {
            return Truck.builder()
                    .model(jsonNode.get("model").asText())
                    .capacity(jsonNode.get("capacity").asInt())
                    .build();
        }

        throw new UnsupportedOperationException(String.format(
                "Unknown type %s",
                type
        ));
    }
}
