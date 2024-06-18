package po23s.components;

import po23s.ImageManager;
import po23s.http.Callback;

import javax.swing.*;
import java.awt.*;


public class ImagePanel extends JLabel implements Callback<Image> {
    private final String url;

    private static final ImageIcon LOADING_ICON = new ImageIcon(ImagePanel.class.getResource("/loading.gif"));

    public ImagePanel(String url, int preferredWidth) {
        super();
        this.url = url;
        setMaximumSize(new Dimension(150, 200));
        loadImage();

    }

    public void loadImage() {
        setIcon(LOADING_ICON);
        ImageManager.getInstance().getImageAsync(url, this);
    }

    @Override
    public void onDone(Image result, Exception erro) {
        if (erro != null) {
            erro.printStackTrace();
            setIcon(new ImageIcon(ImageManager.getInstance().getDefaultImage()));
            return;
        }
        System.out.println("Image loaded");
        removeAll();
        Image image;

        if (result == null) {
            image = ImageManager.getInstance().getDefaultImage();
        } else {
            image = result;
        }
        // scale image to fit a 150x150 box, keeping the aspect ratio
        image = image.getScaledInstance(150, -1, Image.SCALE_SMOOTH);


        setIcon(new ImageIcon(image));
        revalidate();
        repaint();
    }
}
