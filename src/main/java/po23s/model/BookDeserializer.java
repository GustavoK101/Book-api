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

        if (volumeInfo.has("pageCount")) {
            book.setPageCount(volumeInfo.get("pageCount").getAsInt());
        }

        if (volumeInfo.has("language")) {
            book.setLanguage(volumeInfo.get("language").getAsString());
        }

        List<String> categories = new ArrayList<>();

        if (volumeInfo.has("categories")) {
            JsonArray categoriesArray = volumeInfo.getAsJsonArray("categories");
            for (int i = 0; i < categoriesArray.size(); i++) {
                categories.add(categoriesArray.get(i).getAsString());
            }
        }

        JsonObject accessInfo = jo.getAsJsonObject("accessInfo");
        if (accessInfo.has("webReaderLink")) {
            book.setWebReaderLink(accessInfo.get("webReaderLink").getAsString());
        }
        boolean hasPdf = false;

        if (accessInfo.has("pdf")) {
            JsonObject pdf = accessInfo.getAsJsonObject("pdf");
            if (pdf.has("isAvailable")) {
                hasPdf = pdf.get("isAvailable").getAsBoolean();
            }
        }

        JsonObject saleInfo = jo.getAsJsonObject("saleInfo");

        if (saleInfo.has("listPrice")) {
            JsonObject listPrice = saleInfo.getAsJsonObject("listPrice");
            if (listPrice.has("amount")) {
                Double price = listPrice.get("amount").getAsDouble();

                String formattedPrice = String.format("%.2f", price);
                String currencyCode = listPrice.get("currencyCode").getAsString();
                if (currencyCode.equals("BRL")) {
                    formattedPrice = "R$ " + formattedPrice;
                } else {
                    formattedPrice = formattedPrice + " " + currencyCode;
                }
                book.setPrice(formattedPrice);
            }
        }

        book.setPdfAvailable(hasPdf);
        book.setCategories(categories);


        return book;
    }

}
