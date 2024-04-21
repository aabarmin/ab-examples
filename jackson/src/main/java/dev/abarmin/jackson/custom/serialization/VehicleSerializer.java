package dev.abarmin.jackson.custom.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

class VehicleSerializer extends StdSerializer<Vehicle> {
    VehicleSerializer() {
        super(Vehicle.class);
    }

    @Override
    public void serialize(Vehicle value,
                          JsonGenerator gen,
                          SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("model", value.getModel());

        switch (value) {
            case Car c -> {
                gen.writeStringField("type", "car");
                gen.writeNumberField("seats", c.getSeats());
            }
            case Truck t -> {
                gen.writeStringField("type", "truck");
                gen.writeNumberField("capacity", t.getCapacity());
            }
        }

        gen.writeEndObject();
    }
}
