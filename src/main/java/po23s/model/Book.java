package po23s.model;

import java.beans.Transient;

public class Book {
    private String title;
    private String imgUrl;

    private transient boolean isSelected = false;
//    private String autores;
//    private String editora;
//    private Boolean dispPdf;
//    private float valor;


    public Book(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
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