package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class BookGrid extends JPanel {

    JPanel panel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panel);
    private List<Book> books;

    private final List<OnBookClickedListener> listeners = new ArrayList<>();

    private int gridWidth = 3;

    private int baseItemWidth = 40;
    private int itemWidth = baseItemWidth;


    public int getItemWidth() {
        return itemWidth;
    }

    public BookGrid(int gridWidth, List<Book> books) {
        super(new MigLayout("fill"));
        this.books = books;
        this.gridWidth = gridWidth;
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, "grow, wrap, push");

        String colConstraints = getColConstraints(gridWidth);
        panel.setLayout(new MigLayout("fillx, insets 8, gapx 8, gapy 16, center", colConstraints, "[]"));

        updateGrid();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
//                // keep gridWidth at minimum 3
                recalculateItemWith();
                updateGrid();

            }
        });


    }

    private void recalculateItemWith() {
        int width = getWidth();
        if (width == 0) return;

        itemWidth = (width / gridWidth) - 16;

    }

    private static String getColConstraints(int gridWidth) {
        return "push" + "[]".repeat(gridWidth) + "push";
    }

    public void addOnBookClickedListener(OnBookClickedListener listener) {
        listeners.add(listener);
    }


    public void updateGrid() {
        panel.removeAll();
        ((MigLayout) panel.getLayout()).setColumnConstraints(getColConstraints(gridWidth));
        for (int i = 0; i < books.size(); i++) {
            String constraints = "";
            if ((i + 1) % gridWidth == 0) {
                constraints += "wrap";
            }
            Component component = createBookItem(books.get(i));
            panel.add(component, constraints);
        }

        panel.revalidate();
        panel.repaint();
    }


    private Component createBookItem(Book book) {
        BookCard component = new BookCard(book, itemWidth, gridWidth < 6);
        component.setOnBookClickedListener(clickedBook -> {

            for (OnBookClickedListener listener : listeners) {
                listener.onBookClicked(clickedBook);
            }

            for (Component c : panel.getComponents()) {
                BookCard bookCard = (BookCard) c;


                // if current book is selected but not the clicked book, deselect it
                Book currBook = bookCard.getBook();
                if (currBook.isSelected() && bookCard.getBook() != clickedBook) {
                    bookCard.getBook().setSelected(false);
                    bookCard.refresh();
                }
                // if current book is clicked book, toggle selection
                else if (bookCard.getBook() == clickedBook) {
                    bookCard.getBook().setSelected(true);
                    bookCard.refresh();
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
        recalculateItemWith();
        panel.removeAll();
        updateGrid();
    }
}
