package po23s.view;

import po23s.components.ImagePanel;

import javax.swing.*;

public class SimpleScreen extends JFrame {
    public SimpleScreen() {
        super("Simple Screen");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        add(new ImagePanel("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", 150, 100));

    }

    public static void main(String[] args) {
        new SimpleScreen();
    }
}
