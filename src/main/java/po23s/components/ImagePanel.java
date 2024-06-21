package po23s.components;

import net.miginfocom.swing.MigLayout;
import po23s.ImageManager;
import po23s.http.Callback;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class ImagePanel extends JPanel implements Callback<Image> {
    private String url;

    private static final ImageIcon LOADING_ICON = new ImageIcon(Objects.requireNonNull(ImagePanel.class.getResource("/loading.gif")));
    private final int maxWidth;


    public ImagePanel(String url, int maxWidth, int maxHeight) {
        this.url = url;
        setLayout(new MigLayout("fill, insets 0"));

        this.maxWidth = maxWidth;
        setMaximumSize(new Dimension(maxWidth, maxHeight));
        loadImage();
        setBackground(Color.WHITE);
        // remove padding
        setBorder(BorderFactory.createEmptyBorder());

    }


    public void loadImage() {
        setIcon(LOADING_ICON);
        if (url == null || url.isEmpty()) {
            setIcon(new ImageIcon(ImageManager.getInstance().getDefaultImage()));
            return;
        }
        ImageManager.getInstance().getImageAsync(url, this);
    }

    private void setIcon(ImageIcon loadingIcon) {
        removeAll();
        add(new JLabel(loadingIcon), "grow, pushy");
        revalidate();
        repaint();
    }

    @Override
    public void onDone(Image result, Exception erro) {
        if (erro != null) {
            erro.printStackTrace();
            setIcon(new ImageIcon(ImageManager.getInstance().getDefaultImage()));
            return;
        }
        removeAll();
        Image image;

        if (result == null) {
            image = ImageManager.getInstance().getDefaultImage();
        } else {
            image = result;
        }
        image = image.getScaledInstance(maxWidth, -1, Image.SCALE_SMOOTH);


        setIcon(new ImageIcon(image));
        revalidate();
        repaint();
    }

    public void setUrl(String imgUrl) {
        if (this.url != null && this.url.equals(imgUrl)) return;
        this.url = imgUrl;
        loadImage();
    }
}
