package po23s.view;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import po23s.components.BookDetails;
import po23s.components.BookGrid;
import po23s.components.ImageButton;
import po23s.http.BookApi;
import po23s.model.Book;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
public class TelaPesquisa extends JFrame {
    JTextField campoBusca;
    BookApi api;

    private List<Book> books = new ArrayList<>();
    BookGrid bookGrid;
    BookDetails bookDetailPane;

    ImageButton botaoBusca;


    public TelaPesquisa() {
        setTitle("Pesquisa de Livros");
//        for (int i = 0; i < 50; i++) {
//            books.add(new Book("Java " + i, "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"));
//        }
        initComponents();
        // set campo busca hint
        campoBusca.requestFocus();
        campoBusca.addActionListener(e -> {
            realizarBusca();
            campoBusca.requestFocus();
        });

        api = new BookApi();

    }

    private void realizarBusca() {
        String termo = campoBusca.getText();


        if (termo == null || termo.isEmpty()) {
            return;
        }
        this.campoBusca.setEnabled(false);
        this.botaoBusca.setLoading(true);
        api.pesquisar(termo, (result, exception) -> {
            this.campoBusca.setEnabled(true);
            this.botaoBusca.setLoading(false);
            if (exception != null) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar livros:\n" + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
                return;
            }
            bookGrid.clear();
            bookGrid.addBooks(result.getItems());
        });
    }

    void initComponents() {
        // add padding

        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 16", "[]".repeat(12)));
        setContentPane(mainPanel);
        // add logo
        URL logoUrl = TelaPesquisa.class.getResource("/logo.png");
        try {
            JLabel logoTitle = new JLabel("Buscador de Livros");
            logoTitle.setFont(new Font("Arial", Font.BOLD, 24));
            // change logo color
            mainPanel.add(logoTitle, "span 6, pushx, align left");

            BufferedImage logoImg = ImageIO.read(logoUrl);

            JLabel logo = new JLabel(new ImageIcon(logoImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            // add centered
            mainPanel.add(logo, "span 12, align right, wrap");

        } catch (IOException e) {
            e.printStackTrace();
        }


        campoBusca = new JTextField();


        botaoBusca = new ImageButton("Buscar", "icon-search");
        botaoBusca.addActionListener(e -> {
            realizarBusca();
        });

        campoBusca.setMargin(new Insets(4, 4, 4, 4));
        botaoBusca.setMargin(new Insets(4, 4, 4, 16));
        mainPanel.add(campoBusca, "span 10, pushx, growx");
        mainPanel.add(botaoBusca, "growx, wrap");

        bookGrid = new BookGrid(4, books);

        JPanel resultsPanel = new JPanel(new MigLayout("fillx, insets 0", "[grow, fill][300px]"));
        mainPanel.add(resultsPanel, "span 12, grow, push, wrap");

        resultsPanel.add(bookGrid, "grow 2, push 1");


        bookDetailPane = new BookDetails();
        JScrollPane bookDetailScroll = new JScrollPane(bookDetailPane);
        bookDetailScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bookDetailScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bookDetailScroll.setBorder(BorderFactory.createEmptyBorder());
        bookDetailScroll.getVerticalScrollBar().setUnitIncrement(16);
        resultsPanel.add(bookDetailScroll, "grow, push");
//        bookDetailPane.setMaximumSize(new Dimension(250, 1080));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        bookDetailScroll.setMinimumSize(new Dimension(300, getHeight()));

        setSize(1280, 720);
        setMinimumSize(new Dimension(800, 600));
        setResizable(true);
        setLocationRelativeTo(null);

        setVisible(true);

        bookGrid.addOnBookClickedListener(book -> {
            bookDetailPane.setBook(book);
            if (!bookDetailPane.isVisible()) {
                bookDetailPane.setVisible(true);
            }
        });



    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(TelaPesquisa::new);
    }
}
