package po23s.view;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import po23s.components.BookGrid;
import po23s.components.ImageButton;
import po23s.http.BookApi;
import po23s.model.Book;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
public class MainScreen extends JFrame {
    JTextField campoBusca;
    BookApi api;

    private List<Book> books = new ArrayList<>();
    BookGrid bookGrid;
    BookDetails bookDetailPane;

    ImageButton botaoBusca;

    Integer maxResults = 20;

    Integer gridSize = 5;


    public MainScreen() {
        setTitle("Pesquisa de Livros");
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
        api.pesquisar(termo, this.maxResults, (result, exception) -> {
            this.campoBusca.setEnabled(true);
            this.botaoBusca.setLoading(false);
            if (exception != null) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar livros:\n" + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
                return;
            }
            bookGrid.clear();
            if (result.getItems() != null) {
                bookGrid.addBooks(result.getItems());
            } else {
                bookGrid.addBooks(new ArrayList<>());
            }
        });
    }

    void initComponents() {
        // add padding

        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 16", "[]".repeat(12)));
        setContentPane(mainPanel);
        // add logo
        URL logoUrl = MainScreen.class.getResource("/logo.png");
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
        campoBusca.setText("casa do código");


        botaoBusca = new ImageButton("Buscar", "icon-search");
        botaoBusca.addActionListener(e -> realizarBusca());

        campoBusca.setMargin(new Insets(4, 4, 4, 4));
        botaoBusca.setMargin(new Insets(4, 4, 4, 16));
        mainPanel.add(campoBusca, "span 10, pushx, growx");
        mainPanel.add(botaoBusca, "growx, wrap");

        bookGrid = new BookGrid(this.gridSize, books);

        JPanel resultsPanel = new JPanel(new MigLayout("fillx, insets 0", "[grow, fill][300px]"));

        JPanel actionPanel = new JPanel(new MigLayout("insets 0", "[]", "[]"));
        mainPanel.add(actionPanel, "span 12, growx, pushx, wrap");

        JComboBox<Integer> maxResultsComboBox = new JComboBox<>(new Integer[]{10, 20, 30, 40});
        maxResultsComboBox.setSelectedItem(maxResults);
        maxResultsComboBox.addActionListener(e -> {
            this.maxResults = (Integer) maxResultsComboBox.getSelectedItem();
            realizarBusca();
        });

        actionPanel.add(new JLabel("Resultados"));
        actionPanel.add(maxResultsComboBox, "");

        JComboBox<Integer> gridSizeComboBox = new JComboBox<>(new Integer[]{3, 4, 5, 6, 7, 8, 9});

        gridSizeComboBox.setSelectedItem(this.gridSize);

        gridSizeComboBox.setMaximumSize(new Dimension(50, 30));


        gridSizeComboBox.addActionListener(e -> {
            this.gridSize = (Integer) gridSizeComboBox.getSelectedItem();
            bookGrid.setGridWidth(gridSize);
        });

        JButton btnPlus = new JButton("+");
        btnPlus.addActionListener(e -> {
            gridSizeComboBox.setSelectedItem(gridSize + 1);
        });

        JButton btnMinus = new JButton("-");
        btnMinus.addActionListener(e -> {
            gridSizeComboBox.setSelectedItem(gridSize - 1);
        });


        actionPanel.add(new JLabel("Densidade"));
        actionPanel.add(btnMinus, "");
        actionPanel.add(gridSizeComboBox, "");
        actionPanel.add(btnPlus, "");

        mainPanel.add(resultsPanel, "span 12, grow, push, wrap");
        resultsPanel.add(bookGrid, "grow 2, push 1");


        bookDetailPane = new BookDetails();
        JScrollPane bookDetailScroll = new JScrollPane(bookDetailPane);
        bookDetailScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bookDetailScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bookDetailScroll.setBorder(BorderFactory.createEmptyBorder());
        bookDetailScroll.getVerticalScrollBar().setUnitIncrement(16);
        resultsPanel.add(bookDetailScroll, "grow, push");
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
//        System.setProperty("sun.java2d.uiScale", "2");

        FlatLightLaf.setup();
        SwingUtilities.invokeLater(MainScreen::new);
    }
}
