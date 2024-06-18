package po23s.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageButton extends JButton {
    private final String loadingIconName = "loading.gif";
    private final ImageIcon loadingImage;
    private final ImageIcon defaultImage;

    @SuppressWarnings("DataFlowIssue")
    public ImageButton(String text, String iconName) {
        super(text);
        try {
            Image img = ImageIO.read(getClass().getResource("/" + iconName + ".png"));
            Image scaledInstance = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            defaultImage = new ImageIcon(scaledInstance);
            URL loadingIcon = getClass().getResource("/" + loadingIconName);
            loadingImage = new ImageIcon(loadingIcon);
            setIcon(defaultImage);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showLoading() {
        setIcon(loadingImage);
    }

    public void hideLoading() {
        setIcon(defaultImage);
    }


    public void setLoading(boolean loading) {
        if (loading) {
            showLoading();
        } else {
            hideLoading();
        }
    }
}
