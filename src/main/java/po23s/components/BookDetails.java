package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.model.Book;

import javax.swing.*;
import java.awt.*;

public class BookDetails extends JDialog {

    private Book book;


    ImagePanel imagePanel;
    JLabel title;

    public BookDetails(Frame owner, String title) {
        super(owner, title, false);
        initComponent();

        // to the right of owner
        setLocation(owner.getX() + owner.getWidth() + 4, owner.getY());
    }

    private void initComponent() {
        setLayout(new MigLayout());

        // add image
        imagePanel = new ImagePanel("", 200, 200);
        imagePanel.setVisible(false);
        add(imagePanel, "wrap");
        title = new JLabel("<html><body>Clique em um livro para ver detalhes</body></html>");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        // wrap text if necessary
        add(title, "wrap");

        JLabel author = new JLabel("Autor do livro");
        // set a greyish color
        author.setForeground(new Color(0x666666));
        add(author, "wrap");

    }

    public void setBook(Book book) {
        this.book = book;
        updateUI();
    }

    private void updateUI() {
        if (book == null) {
            title.setText("Clique em um livro para ver detalhes");
            imagePanel.setVisible(false);
        } else {
            title.setText("<html><body>" + book.getTitle() + "</body></html>");
            imagePanel.setVisible(true);
            imagePanel.setUrl(book.getImgUrl());
        }

    }
}
