package matryca_led_gui;

import javax.swing.*;
import java.awt.*;

public class Views {
    PickView pickView = new PickView();
    CreateView createView = new CreateView();
    private JPanel viewsPanel;
    private JPanel createViewPanel = createView.createViewPanel;
    private JPanel pickViewPanel = pickView.pickViewPanel;

    Views(){
        viewsPanel.add(createViewPanel, BorderLayout.WEST);
        viewsPanel.add(pickViewPanel, BorderLayout.EAST);
    }
}
