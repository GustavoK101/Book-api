package po23s.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import po23s.model.Book;
import po23s.model.BookDeserializer;
import po23s.model.SearchResult;

public class BookApi {
    
    Gson gson;
    ClienteHttp clienteHttp;
    
    public BookApi() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Book.class, new BookDeserializer());
        gson = builder.create();
        clienteHttp = new ClienteHttp();
    }
    
    public SearchResult pesquisar(String pesquisa){        
        String retornoJSON = clienteHttp.buscaDados("https://www.googleapis.com/books/v1/volumes?q=" + pesquisa);
        return gson.fromJson(retornoJSON, SearchResult.class);
    }
}
