package dev.abarmin.wt.examples.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

class DocumentDeserializer implements JsonDeserializer<Document> {
    @Override
    public Document deserialize(JsonElement json,
                                Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final String type = jsonObject.get("type").getAsString();
        if ("paper".equals(type)) {
            return PaperDocument.builder()
                    .title(jsonObject.get("title").getAsString())
                    .pages(jsonObject.get("pages").getAsInt())
                    .build();
        } else if ("digital".equals(type)) {
            return DigitalDocument.builder()
                    .title(jsonObject.get("title").getAsString())
                    .path(jsonObject.get("path").getAsString())
                    .build();
        }

        throw new UnsupportedOperationException(String.format(
                "Type %s is not supported",
                type
        ));
    }
}
