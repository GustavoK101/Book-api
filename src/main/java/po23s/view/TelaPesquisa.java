package po23s.view;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import po23s.components.BookGrid;
import po23s.components.ImageButton;
import po23s.http.BookApi;
import po23s.model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
public class TelaPesquisa extends JFrame {
    JTextField campoBusca;
    BookApi api;

    private List<Book> books = new ArrayList<>();

    public TelaPesquisa() {
        setTitle("Pesquisa de Livros");
        for (int i = 0; i < 50; i++) {
            books.add(new Book("Java " + i, "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"));
        }
        initComponents();
        campoBusca.requestFocus();
        campoBusca.addActionListener(e -> {
            realizarBusca();
        });

        api = new BookApi();

    }

    private void realizarBusca() {
        String termo = campoBusca.getText();
        if (termo == null || termo.isEmpty()) {
            return;
        }
        api.pesquisar(termo, (result, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }
//            listModel.clear();
//            result.getItems().forEach(listModel::addElement);
        });
    }

    void initComponents() {

        setLayout(new MigLayout("fill"));
        JPanel panel = new JPanel(new MigLayout());

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new MigLayout("fillx", "[][][][][][][][][][][][]"));
        setContentPane(panel);


        campoBusca = new JTextField();


        JButton botaoBusca = new ImageButton("Buscar", "icon-search");

        campoBusca.setMargin(new Insets(4, 4, 4, 4));
        botaoBusca.setMargin(new Insets(4, 4, 4, 16));
        panel.add(campoBusca, "span 10, pushx, growx");
        panel.add(botaoBusca, "growx, wrap");
//        JList<Book> list = new JList<>(listModel);
//        list.setVisibleRowCount(-1);

//        list.setCellRenderer(new BookCellRenderer());
//        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);


//        JScrollPane listScroller = new JScrollPane(list);
//        listScroller.setAlignmentX(LEFT_ALIGNMENT);

        BookGrid bookGrid = new BookGrid(4, books);
        panel.add(bookGrid, "span 12, grow, wrap");
        panel.add(new JLabel("Resultado da busca"), "span 12, wrap");


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setSize(800, 600);

        setLocationRelativeTo(null);

        setVisible(true);

    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(TelaPesquisa::new);
    }
}
