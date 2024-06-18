package po23s.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class BookDeserializer implements JsonDeserializer<Book> {

    @Override
    public Book deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jo = je.getAsJsonObject();
        JsonObject volumeInfo = jo.getAsJsonObject("volumeInfo");
        String title;
        if (volumeInfo.has("title")) {
            title = volumeInfo.get("title").getAsString();
        } else {
            title = "Sem título";
        }
        JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
        String imgUrl;
        if (imageLinks != null) {
            imgUrl = imageLinks.get("thumbnail").getAsString();
        } else {
            imgUrl = null;
        }
        return new Book(title, imgUrl);
    }

}
