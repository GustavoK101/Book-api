package po23s.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import po23s.model.Book;
import po23s.model.BookDeserializer;
import po23s.model.SearchResult;

import javax.swing.*;
import java.net.URLEncoder;

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
    public SwingWorker<SearchResult, Void> pesquisar(String pesquisa, int maxResults, Callback<SearchResult> callback) {
        SwingWorker<SearchResult, Void> worker = new SwingWorker<>() {
            @Override
            protected SearchResult doInBackground() {
                // urlencode  pesquisa param

                String search = "q=" + URLEncoder.encode(pesquisa, java.nio.charset.StandardCharsets.UTF_8);


                String url = "https://www.googleapis.com/books/v1/volumes?" + search + "&maxResults=" + maxResults;
                System.out.println("URL: " + url);
                String retornoJSON = clienteHttp.buscaDados(url);
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
