package po23s.model;

import java.time.LocalDate;
import java.util.List;

public class Book {
    private String title;
    private String coverUrl;

    private transient boolean isSelected = false;
    private List<String> authors;
    private String publisher;

    private Integer year;

    private Integer pageCount;

    private List<String> categories;

    private String language;
    private String price;
    private boolean pdfAvailable;
    private String webReaderLink;


    public Book(String title, String coverUrl, List<String> authors) {
        this.title = title;
        this.coverUrl = coverUrl;
        this.authors = authors;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> autores) {
        this.authors = autores;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isPdfAvailable() {
        return pdfAvailable;
    }

    public void setPdfAvailable(boolean pdfAvailable) {
        this.pdfAvailable = pdfAvailable;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }
}