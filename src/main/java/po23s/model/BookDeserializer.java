package po23s.model;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

        List<String> autores = new ArrayList<>();

        if (volumeInfo.has("authors")) {
            JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
            for (int i = 0; i < authorsArray.size(); i++) {
                String authorName = authorsArray.get(i).getAsString();
                autores.add(authorName);
            }
        }
        return new Book(title, imgUrl, autores);
    }

}
