package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.ImageManager;
import po23s.model.Book;

import javax.swing.*;
import java.awt.*;

public class BookDetails extends JPanel {
    private Book book;
    ImagePanel imagePanel;
    JLabel title;

    public BookDetails() {
        initComponent();
    }


    private void initComponent() {
        setLayout(new MigLayout("", "push[]push"));
        setBackground(Color.WHITE);
        // set 8px padding
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

//        add(new ImageButton("PDF", "icon-pdf"), "wrap");
//        add(new ImageButton("PDF", "icon-epub"), "wrap");


        // add image
        imagePanel = new ImagePanel("", 250, 700);
        imagePanel.setVisible(true);
        add(imagePanel, "wrap");
        title = new JLabel("<html><body>Clique em um livro para ver detalhes</body></html>");
        title.setMaximumSize(new Dimension(250, 700));
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, "wrap");
        JLabel author = new JLabel("Autor do livro");
        author.setForeground(new Color(0x666666));
        add(author, "wrap");
    }

    public void setBook(Book book) {
        this.book = book;
        refreshData();
    }

    private void refreshData() {
        if (book == null) {
            title.setText("Clique em um livro para ver detalhes");
            imagePanel.setVisible(false);
        } else {
            title.setText("<html><body>" + book.getTitle() + "</body></html>");
            imagePanel.setVisible(true);

            String newUrl = null;
            if (book.getImgUrl() != null) {
                newUrl = book.getImgUrl().replace("zoom=1", "zoom=6");
            }
            imagePanel.setUrl(newUrl);
        }

    }
}
