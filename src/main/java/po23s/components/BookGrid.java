package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BookGrid extends JPanel {

    JPanel panel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panel);
    private List<Book> books;

    private final List<OnBookClickedListener> listeners = new ArrayList<>();

    private int gridWidth = 3;


    public BookGrid(int gridWidth, List<Book> books) {
        super(new MigLayout("fill"));
        this.books = books;
        this.gridWidth = gridWidth;
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, "grow, wrap, push");

        panel.setLayout(new MigLayout("fillx,  insets 8, gap 8", "[]".repeat(gridWidth)));
        updateGrid();
    }

    public void addOnBookClickedListener(OnBookClickedListener listener) {
        listeners.add(listener);
    }


    public void updateGrid() {
        System.out.println("Updating grid");
        System.out.println("Books size: " + books.size());
        for (int i = 0; i < books.size(); i++) {
            String constraints = "growx,";
            if ((i + 1) % gridWidth == 0) {
                constraints += " wrap";
            }
            System.out.println("Adding book " + i + " to grid");
            Component component = createBookItem(books.get(i));
            panel.add(component, constraints);
        }
        panel.revalidate();
        panel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private Component createBookItem(Book book) {
        System.out.println("Creating book item" + book.getTitle());
        BookItemPanel component = new BookItemPanel(book);
        component.setOnBookClickedListener(clickedBook -> {

            for (OnBookClickedListener listener : listeners) {
                listener.onBookClicked(clickedBook);
            }

            for (Component c : panel.getComponents()) {
                BookItemPanel bookItemPanel = (BookItemPanel) c;


                // if current book is selected but not the clicked book, deselect it
                Book currBook = bookItemPanel.getBook();
                if (currBook.isSelected() && bookItemPanel.getBook() != clickedBook) {
                    bookItemPanel.getBook().setSelected(false);
                    bookItemPanel.refresh();
                }
                // if current book is clicked book, toggle selection
                else if (bookItemPanel.getBook() == clickedBook) {
                    bookItemPanel.getBook().setSelected(true);
                    bookItemPanel.refresh();
                }
            }

        });


        return component;
    }

    public void clear() {
        this.books.clear();
        panel.removeAll();
        scrollPane.getVerticalScrollBar().setValue(0);

    }


    public void addBooks(List<Book> items) {
        this.books.addAll(items);
        updateGrid();
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
        panel.removeAll();
        updateGrid();
    }
}
