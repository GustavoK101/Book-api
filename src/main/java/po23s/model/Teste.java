package po23s.model;

import po23s.http.BookApi;

import javax.swing.*;

public class Teste {

    public static void main(String[] args) {
        BookApi api = new BookApi();

        SwingWorker<SearchResult, Void> worker = api.pesquisar("python", (result1, erro) -> {
            if (erro != null) {
                erro.printStackTrace();
            } else {
                for (Book book : result1.getItems()) {
                    System.out.println(book.getTitle());
                }
            }
        });

        try {
            worker.get();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}