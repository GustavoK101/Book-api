package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.model.Book;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BookItemPanel extends JPanel {
    JLabel title;
    private OnBookClickedListener listener = null;

    private final Book book;

    public BookItemPanel(Book book) {
        this.book = book;
        setLayout(new MigLayout("fill", "[]"));
        setBackground(Color.WHITE);

        // change mouse to pointer
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        // border for 'card' like effect
        Border border = BorderFactory.createEmptyBorder(0, 0, 4, 2);
        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));


        setPreferredSize(new Dimension(150, 250));

        title = new JLabel(book.getTitle());
        title.setMaximumSize(new Dimension(150, 20));

        ImagePanel imagePanel = new ImagePanel(book.getImgUrl(), 100);


        add(imagePanel, "span 2, wrap, pushy, center");
        add(title, "wrap");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onBookClicked(book);
            }
        });
    }

    public void setOnBookClickedListener(OnBookClickedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int shade = 0;
        int topOpacity = 100;
        int pixels = 4;
        if (book.isSelected()) {
            shade = 50;
            pixels = 6;
        }

        for (int i = 0; i < pixels; i++) {
            g.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
            // draw right border
            g.drawRect(this.getWidth() - pixels + i, i, 1, this.getHeight() - ((i * 2) + 1));
            // draw bottom border
            g.drawRect(i, this.getHeight() - pixels + i, this.getWidth() - ((i * 2) + 1), 1);

        }

    }


    public void refresh() {
        if (book == null) {
            return;
        }
        if (book.isSelected()) {
            title.setFont(title.getFont().deriveFont(Font.BOLD));
            setBackground(new Color(240, 240, 255));
        } else {
            title.setFont(title.getFont().deriveFont(Font.PLAIN));
            setBackground(Color.WHITE);
        }

        revalidate();
        repaint();

    }

    public void onBookClicked(Book book) {
        if (listener != null) {
            listener.onBookClicked(book);
        }
    }

    public void setSelected(boolean selected) {
        this.book.setSelected(selected);
    }

    public Book getBook() {
        return book;
    }
}
