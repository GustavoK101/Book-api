package po23s.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import po23s.model.Book;

public class BookTableModel extends AbstractTableModel {
    
    private List <Book> listaLivros = new ArrayList<>();

    public void setListaLivros(List<Book> listaLivros) {
        if (listaLivros == null) {
            listaLivros = new ArrayList<>();
        }
        this.listaLivros = listaLivros;
    }

    
    @Override
    public int getRowCount() {
        return listaLivros.size();        
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int row, int column) {
        if(column == 0 ){
            return listaLivros.get(row).getTitle();
        }else{
            return listaLivros.get(row).getImgUrl();
        }
        
    }
    
    @Override
    public String getColumnName(int column) {
        if(column == 0){
            return "Título";
        }else{
            return "UrlImagem";
        }
    }
     
    
}

