/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package po23s.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import po23s.model.Book;
import po23s.model.BookDeserializer;
import po23s.model.SearchResult;

/**
 *
 * @author gk
 */
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
        SearchResult searchResult = gson.fromJson(retornoJSON, SearchResult.class);
        return searchResult;
    }
}
