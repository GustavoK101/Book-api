package po23s.model;

import java.util.ArrayList;
import java.util.List;


public class SearchResult {
    private Integer totalItems;
    private List<Book> items;

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        if (items == null) {
            items = new ArrayList<>();
        }
        this.items = items;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }


}
