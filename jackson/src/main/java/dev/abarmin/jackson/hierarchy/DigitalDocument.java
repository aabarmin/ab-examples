package dev.abarmin.jackson.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(onConstructor_= @JsonCreator)
public final class DigitalDocument extends Document {
    private String path;
}
