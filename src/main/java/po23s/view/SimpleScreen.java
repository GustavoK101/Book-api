package po23s.view;

import po23s.components.ImagePanel;

import javax.swing.*;

public class SimpleScreen {

    public static void main(String[] args) throws Exception {
        final String html =
                "<html><body>" +
                        "<img src='" +
                        "https://i.sstatic.net/OVOg3.jpg" +
                        "' width=160 height=120> " +
                        "<img src='" +
                        "https://i.sstatic.net/lxthA.jpg" +
                        "' width=160 height=120>" +
                        "<p>Look Ma, no hands!";
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JLabel hover = new JLabel("Point at me!");
                hover.setToolTipText(html);
                JOptionPane.showMessageDialog(null, hover);
            }
        });
    }
}