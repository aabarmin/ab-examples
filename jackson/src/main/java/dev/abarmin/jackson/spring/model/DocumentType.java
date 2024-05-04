package dev.abarmin.jackson.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DocumentType {
    PAPER("Paper Document"),
    DIGITAL("Digital Document"),
    UNKNOWN("Unknown");

    private final String value;

    public static DocumentType byValue(String value) {
        return Arrays.stream(values())
                .filter(dt -> StringUtils.equalsIgnoreCase(dt.value, value))
                .findFirst()
                .orElse(DocumentType.UNKNOWN);
    }
}
