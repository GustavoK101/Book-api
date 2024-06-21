package po23s.model;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

        String publisher = null;

        if (volumeInfo.has("publisher")) {
            publisher = volumeInfo.get("publisher").getAsString();
        }

        String publishedDateStr = null;
        Integer publishedYear = null;

        if (volumeInfo.has("publishedDate")) {
            publishedDateStr = volumeInfo.get("publishedDate").getAsString();
            try {
                System.out.println(publishedDateStr);
                publishedYear = LocalDate.parse(publishedDateStr).getYear();
            } catch (DateTimeParseException e) {
                System.out.println("Error parsing date: " + publishedDateStr);
                try {
                    publishedYear = Integer.parseInt(publishedDateStr);
                } catch (NumberFormatException ex) {
                    System.out.println("Error parsing year: " + publishedDateStr);
                }

            }
        }

        Book book = new Book(title, imgUrl, autores);
        book.setPublisher(publisher);
        book.setYear(publishedYear);


        return book;
    }

}
