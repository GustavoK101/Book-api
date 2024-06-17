package po23s.components;

import po23s.ImageManager;
import po23s.http.Callback;

import javax.swing.*;
import java.awt.*;


public class ImagePanel extends JLabel implements Callback<Image> {
    private String url;

    SwingWorker<Image, Void> worker;


    public ImagePanel(String url, int preferredW, int preferredH) {
        super();
        // init with default image

        if (ImageManager.getInstance().isImageCached(url)) {
            Image image = ImageManager.getInstance().getImage(url);
            int w = image.getWidth(null);
            int h = image.getHeight(null);

            float ratio = (float) w / h;
            int newW = (int) (preferredH * ratio);
            image = image.getScaledInstance(newW, preferredH, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
            return;
        }
        Image defaultImage = ImageManager.getInstance().getDefaultImage();
        setIcon(new ImageIcon(defaultImage));

        worker = ImageManager.getInstance().getImageAsync(url, this);
        if (worker != null) {
            worker.execute();
        }

    }

    @Override
    public void onDone(Image result, Exception erro) {
        if (erro != null) {
            erro.printStackTrace();
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
        image = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(image));
        revalidate();
        repaint();
    }
}
