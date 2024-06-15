/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package po23s.model;

import po23s.http.BookApi;

/**
 *
 * @author gk
 */
public class Teste {
    
    public static void main(String[] args) {
        BookApi api = new BookApi();
        SearchResult result = api.pesquisar("python"); 
        for(Book book : result.getItems()){
            System.out.println(book.getTitle());
        }
    }
}