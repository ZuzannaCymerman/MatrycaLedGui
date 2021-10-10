package matryca_led_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Views {
    PickView pickView = new PickView();
    CreateView createView = new CreateView();
    public JPanel viewsPanel;
    private JPanel createViewPanel = createView.createViewPanel;
    private JPanel pickViewPanel = pickView.pickViewPanel;

    Views(){
        viewsPanel.add(createViewPanel, BorderLayout.WEST);
        viewsPanel.add(pickViewPanel, BorderLayout.EAST);
        createView.saveViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createView.saveView();
                pickView.setPickViewComboBox();
            }
        });
    }
}
