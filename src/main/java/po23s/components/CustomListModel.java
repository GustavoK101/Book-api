package po23s.components;

import javax.swing.*;

public class CustomListModel<E> extends DefaultListModel<E> {
    public void notifiyDataChanged() {
        fireContentsChanged(this, 0, getSize());
    }
}
