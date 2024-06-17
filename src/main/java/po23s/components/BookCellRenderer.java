package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.model.Book;

import javax.swing.*;
import java.awt.*;

public class BookCellRenderer implements ListCellRenderer<Book> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Book> list, Book book, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel(new MigLayout("debug", "[]"));
        panel.setMaximumSize(new Dimension(80, 120));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel(book.getTitle());
        title.setMaximumSize(new Dimension(120, 20));
        if (cellHasFocus) {
            title.setFont(title.getFont().deriveFont(Font.BOLD));
        }

        ImagePanel imagePanel = new ImagePanel(book.getImgUrl(), 150, 150);
        panel.add(imagePanel, "wrap");
        panel.add(title, "wrap");


//        ImageButton imageButton = new ImageButton(value.getTitle(), "icon-search");
//        imageButton.setPreferredSize(new Dimension(80, 20));
//        imageButton.setMaximumSize(new Dimension(80, 20));
//        if (cellHasFocus) {
//            imageButton.setBackground(Color.RED);
//        }
//        panel.add(imageButton, "wrap");

        return panel;
    }
}
