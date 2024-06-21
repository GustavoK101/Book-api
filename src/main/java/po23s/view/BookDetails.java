package po23s.view;

import net.miginfocom.swing.MigLayout;
import po23s.components.ImageButton;
import po23s.components.ImagePanel;
import po23s.model.Book;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.Objects;

public class BookDetails extends JPanel {
    private Book book;
    ImagePanel imagePanel;
    JLabel title;
    JLabel author;
    JLabel year;
    JLabel publisher;
    JLabel price;

    JLabel pageCount;

    JLabel pdfIcon;

    JButton openLink;


    public BookDetails() {
        initComponent();
    }


    private void initComponent() {
        setLayout(new MigLayout("", "[grow]"));
        setBackground(Color.WHITE);
        // set 8px padding
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new MigLayout("", "[]push[][]"));
        headerPanel.setBackground(Color.WHITE);
        add(headerPanel, "wrap,growx");


        price = new JLabel("");
        price.setMaximumSize(new Dimension(250, 700));
        price.setForeground(new Color(0x666666));
        headerPanel.add(price, "");

        pageCount = new JLabel("");
        pageCount.setMaximumSize(new Dimension(250, 700));
        pageCount.setForeground(new Color(0x666666));
        headerPanel.add(pageCount, "");

        pdfIcon = new JLabel();
        ImageIcon pdfIconImage = new ImageIcon(Objects.requireNonNull(ImagePanel.class.getResource("/icon-pdf-color.png")));
        pdfIconImage = new ImageIcon(pdfIconImage.getImage().getScaledInstance(18, 24, Image.SCALE_SMOOTH));
        this.pdfIcon.setIcon(pdfIconImage);
        this.pdfIcon.setVisible(false);
        headerPanel.add(this.pdfIcon, "wrap");


        // add image
        imagePanel = new ImagePanel("", 200, 700);
        imagePanel.setVisible(false);
        add(imagePanel, "wrap, growx, align center");
        title = new JLabel("<html><body>Clique em um livro para ver detalhes</body></html>");
        title.setMaximumSize(new Dimension(250, 700));
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, "wrap");


        year = new JLabel("");
        year.setFont(new Font("Arial", Font.PLAIN, 12));
        year.setMaximumSize(new Dimension(250, 700));
        year.setForeground(new Color(0x666666));
        add(year, "wrap");

        author = new JLabel("");
        author.setMaximumSize(new Dimension(250, 700));
        author.setForeground(new Color(0x666666));
        add(author, "wrap");

        // add divider
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(0xEEEEEE));
        add(separator, "span, growx, wrap");

        publisher = new JLabel("");
        publisher.setMaximumSize(new Dimension(250, 700));
        publisher.setForeground(new Color(0x666666));
        add(publisher, "wrap");

        openLink = new ImageButton("Abrir no Google Books", "open-in-new");
        openLink.addActionListener(e -> {
            if (book != null) {
                try {
                    Desktop.getDesktop().browse(new URI(book.getWebReaderLink()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // add divider
        JSeparator separator2 = new JSeparator();
        separator2.setForeground(new Color(0xEEEEEE));
        add(separator2, "span, growx, wrap");

        add(openLink, "growx, wrap");
        openLink.setVisible(false);


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

            String autoresText = getAuthors();

            author.setText("<html><body>" + autoresText + "<html><body>");

            String newUrl = null;
            if (book.getCoverUrl() != null) {
                newUrl = book.getCoverUrl().replace("zoom=1", "zoom=6");
            }
            imagePanel.setUrl(newUrl);

            if (book.getYear() != null) {
                year.setText(book.getYear().toString());
            } else {
                year.setText("");
            }

            if (book.getPublisher() != null) {
                publisher.setText(book.getPublisher());
            } else {
                publisher.setText("");
            }

            if (book.getPrice() != null) {
                price.setText(book.getPrice());
            } else {
                price.setText("");
            }

            if (book.getPageCount() != null) {
                pageCount.setText(book.getPageCount().toString() + " páginas");
            } else {
                pageCount.setText("");
            }

            pdfIcon.setVisible(book.isPdfAvailable());

            openLink.setVisible(book.getWebReaderLink() != null);


        }

    }

    private String getAuthors() {
        StringBuilder autoresText = new StringBuilder();
        int lastIndex = book.getAuthors().size() - 1;
        for (int i = 0; i < book.getAuthors().size(); i++) {
            String autor = book.getAuthors().get(i);
            boolean isFirst = i == 0;
            boolean isLast = i == lastIndex;
            if (isFirst) {
                autoresText.append(autor);
                continue;
            }
            if (isLast) {
                autoresText.append(" e ");
            } else {
                autoresText.append(", ");
            }
            autoresText.append(autor);
        }
        if (book.getAuthors().isEmpty()) {
            autoresText = new StringBuilder("Autor desconhecido");
        }
        return autoresText.toString();
    }
}
