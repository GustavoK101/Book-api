package po23s.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import po23s.model.Book;
import po23s.model.BookDeserializer;
import po23s.model.SearchResult;

import javax.swing.*;

public class BookApi {

    Gson gson;
    ClienteHttp clienteHttp;

    public BookApi() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Book.class, new BookDeserializer());
        gson = builder.create();
        clienteHttp = new ClienteHttp();
    }


    // receives a query, a success callback that receives the result, and an error callback that receives the exception
    public SwingWorker<SearchResult, Void> pesquisar(String pesquisa, Callback<SearchResult> callback) {
        SwingWorker<SearchResult, Void> worker = new SwingWorker<>() {
            @Override
            protected SearchResult doInBackground() {
                String retornoJSON = clienteHttp.buscaDados("https://www.googleapis.com/books/v1/volumes?maxResults=40&q=" + pesquisa.replace(" ", "+"));
                return gson.fromJson(retornoJSON, SearchResult.class);
            }

            @Override
            protected void done() {
                try {
                    callback.onDone(get(), null);
                } catch (Exception e) {
                    callback.onDone(null, e);
                }
            }
        };
        worker.execute();
        return worker;
    }
}
