package dev.abarmin.wt.examples.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class VehicleStreamingDeserializer extends StdDeserializer<Vehicle> {
    public VehicleStreamingDeserializer() {
        super(Vehicle.class);
    }

    @Override
    public Vehicle deserialize(JsonParser p,
                               DeserializationContext ctxt) throws IOException, JacksonException {

        Map<String, Object> values = new HashMap<>();
        while (p.nextToken() != JsonToken.END_OBJECT) {
            final String field = p.getCurrentName();
            if ("type".equals(field)) {
                values.put(field, p.nextTextValue());
            } else if ("model".equals(field)) {
                values.put(field, p.nextTextValue());
            } else if ("capacity".equals(field)) {
                values.put(field, p.nextIntValue(-1));
            } else if ("seats".equals(field)) {
                values.put(field, p.nextIntValue(-1));
            }
        }

        final String type = (String) values.get("type");
        if ("car".equals(type)) {
            return Car.builder()
                    .model((String) values.get("model"))
                    .seats((int) values.get("seats"))
                    .build();
        } else if ("truck".equals(type)) {
            return Truck.builder()
                    .model((String) values.get("model"))
                    .capacity((int) values.get("capacity"))
                    .build();
        }

        throw new UnsupportedOperationException(String.format(
                "Unsupported type %s",
                type
        ));
    }
}
