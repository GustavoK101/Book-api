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

        // compute grid size based on screen size, and update when the window resizes
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int width = getWidth();
                int gridWidth = width / 200;
                bookGrid.setGridWidth(gridWidth);
            }
        });

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
                exception.printStackTrace();
                return;
            }
            bookGrid.clear();
            bookGrid.addBooks(result.getItems());
//            listModel.clear();
//            result.getItems().forEach(listModel::addElement);
        });
    }

    void initComponents() {
        // add padding

        JPanel panel = new JPanel(new MigLayout("fillx, insets 16", "[]".repeat(12)));
        setContentPane(panel);
        // add logo
        URL logoUrl = TelaPesquisa.class.getResource("/logo.png");
        try {
            JLabel logoTitle = new JLabel("Buscador de Livros");
            logoTitle.setFont(new Font("Arial", Font.BOLD, 24));
            // change logo color
            panel.add(logoTitle, "span 6, pushx, align left");

            BufferedImage logoImg = ImageIO.read(logoUrl);

            JLabel logo = new JLabel(new ImageIcon(logoImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            // add centered
            panel.add(logo, "span 12, align right, wrap");

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
        panel.add(campoBusca, "span 10, pushx, growx");
        panel.add(botaoBusca, "growx, wrap");

        bookGrid = new BookGrid(4, books);
        panel.add(bookGrid, "span 12, grow, push, wrap");


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setSize(800, 720);

        setLocationRelativeTo(null);

        setVisible(true);

    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(TelaPesquisa::new);
    }
}
