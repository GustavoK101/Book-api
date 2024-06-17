package po23s.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ImageButton extends JButton {
    private String iconName;

    @SuppressWarnings("DataFlowIssue")
    public ImageButton(String text, String iconName) {
        super(text);
        try {
            Image img = ImageIO.read(getClass().getResource("/" + iconName + ".png"));
            Image scaledInstance = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledInstance));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
