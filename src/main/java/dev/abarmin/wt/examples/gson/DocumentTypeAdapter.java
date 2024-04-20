package dev.abarmin.wt.examples.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

public class DocumentTypeAdapter implements JsonSerializer<Document>, JsonDeserializer<Document> {
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

    @Override
    public JsonElement serialize(Document document,
                                 Type typeOfSrc,
                                 JsonSerializationContext context) {

        final JsonObject resultObject = new JsonObject();
        resultObject.addProperty("title", document.getTitle());

        switch (document) {
            case DigitalDocument d -> {
                resultObject.addProperty("path", d.getPath());
                resultObject.addProperty("type", "digital");
            }
            case PaperDocument p -> {
                resultObject.addProperty("pages", p.getPages());
                resultObject.addProperty("type", "paper");
            }
        }

        return resultObject;
    }
}
