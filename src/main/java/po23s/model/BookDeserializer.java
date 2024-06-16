package po23s.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class BookDeserializer implements JsonDeserializer<Book>{

    @Override
    public Book deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jo = je.getAsJsonObject();
        JsonObject volumeInfo = jo.getAsJsonObject("volumeInfo");
        String title = volumeInfo.get("title").getAsString();
        System.out.println(volumeInfo);
        String imgUrl = volumeInfo.getAsJsonObject("imageLinks").get("smallThumbnail").getAsString();
        return new Book(title,imgUrl);    
    }
        
}
