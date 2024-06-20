package po23s.model;

import java.beans.Transient;
import java.util.List;

public class Book {
    private String title;
    private String imgUrl;

    private transient boolean isSelected = false;
    private List <String> autores;
    private String editora;
//    private Boolean dispPdf;
//    private double valor;


    public Book(String title, String imgUrl, List<String> autores) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.autores = autores;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}