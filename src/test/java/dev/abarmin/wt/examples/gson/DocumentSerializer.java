package dev.abarmin.wt.examples.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

class DocumentSerializer implements JsonSerializer<Document> {
    @Override
    public JsonElement serialize(Document document,
                                 Type type,
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
