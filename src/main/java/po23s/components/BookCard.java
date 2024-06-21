package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.model.Book;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BookCard extends JPanel {
    JLabel title;
    private OnBookClickedListener listener = null;

    private final Book book;

    private boolean showTitle;

    public BookCard(Book book, int width, boolean showTitle) {
        this.book = book;
        this.showTitle = showTitle;
        setLayout(new MigLayout("insets 0, fill", "[]"));
        setBackground(Color.WHITE);

        // change mouse to pointer
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        // compute height, keep 9:16 aspect ratio
        int height = (int) (width / 0.5625);

        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

        title = new JLabel(book.getTitle());
        title.setMaximumSize(new Dimension(width, 20));
        title.setFont(new Font("Arial", Font.PLAIN, 14));
        // add padding
        title.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));


        String newUrl = "";
        if (book.getCoverUrl() != null) {
            newUrl = book.getCoverUrl().replace("zoom=1", "zoom=2");
        }

        ImagePanel imagePanel = new ImagePanel(newUrl, width - 2, height);

        // add image top with 1px top margin
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(imagePanel, "grow, pushy, top, wrap");

        // push title to bottom
        add(title, "wrap, growx, pushy, bottom");

        if (!showTitle) {
            title.setVisible(false);
            remove(title);
            title.setMaximumSize(new Dimension(0, 0));
        }

        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // if height is too small hide title
        if (height < 250) {
            title.setVisible(false);
        }

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

        if (!showTitle) {
            return;
        }

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
