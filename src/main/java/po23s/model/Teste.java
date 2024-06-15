package po23s.model;

import po23s.http.BookApi;

public class Teste {
    
    public static void main(String[] args) {
        BookApi api = new BookApi();
        SearchResult result = api.pesquisar("python"); 
        for(Book book : result.getItems()){
            System.out.println(book.getTitle());
        }
    }
}