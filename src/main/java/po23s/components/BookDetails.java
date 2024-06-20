package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.ImageManager;
import po23s.model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class BookDetails extends JPanel {
    private Book book;
    ImagePanel imagePanel;
    JLabel title;
    JLabel author;

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
        author = new JLabel("");
        author.setMaximumSize(new Dimension(250, 700));
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
            author.setText("");
        } else {
            title.setText("<html><body>" + book.getTitle() + "</body></html>");
            imagePanel.setVisible(true);

            String autoresText = "";
            int lastIndex = book.getAutores().size()-1;
            for (int i = 0; i < book.getAutores().size(); i++) {
                String autor = book.getAutores().get(i);
                boolean isFirst = i == 0;
                boolean isLast = i == lastIndex;
                if (isFirst){
                    autoresText += autor;
                    continue;
                }
                if(isLast){
                    autoresText += " e ";
                }else{
                    autoresText += ", ";
                }
                autoresText += autor;
            }
            if (book.getAutores().isEmpty()) {
                autoresText = "Autor desconhecido";
            }

            author.setText("<html><body>" + autoresText + "<html><body>");

            String newUrl = null;
            if (book.getImgUrl() != null) {
                newUrl = book.getImgUrl().replace("zoom=1", "zoom=6");
            }
            imagePanel.setUrl(newUrl);
        }

    }
}
