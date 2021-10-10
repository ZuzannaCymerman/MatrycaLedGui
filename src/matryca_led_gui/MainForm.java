package matryca_led_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainForm extends JFrame implements ActionListener {
    private JPanel mainPanel;

    private Preferences preferences = new Preferences();

    private LEDs leds = new LEDs();
    private Menu menu = new Menu();

    private CreateView createView = new CreateView();
    private PickView pickView = new PickView();
    private Views views = new Views();

    CardLayout cl = new CardLayout();

    private JPanel preferencesPanel = preferences.preferencesPanel;
    private JPanel ledPanel = leds.led_panel;
    private JPanel menuPanel = menu.menuPanel;
    private JPanel viewsPanel = views.viewsPanel;

    public JMenuItem configMenuItem = menu.config;
    public JMenuItem ledsMenuItem = menu.LEDs;
    public JMenuItem viewsMenuItem = menu.views;

    MainForm(){
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMainPanel();
        setContentPane(menuPanel);
        setVisible(true);
    }
    public void setMainPanel(){
        mainPanel.setLayout(cl);
        mainPanel.add(preferencesPanel, "config");
        mainPanel.add(ledPanel, "LEDs");
        mainPanel.add(viewsPanel, "Views");
        menuPanel.add(mainPanel, BorderLayout.CENTER);
        menuActions();
    }


    public void menuActions(){
        configMenuItem.addActionListener(this);
        ledsMenuItem.addActionListener(this);
        viewsMenuItem.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== configMenuItem)
            cl.show(mainPanel, "config");
        if(e.getSource()== ledsMenuItem)
            cl.show(mainPanel, "LEDs");
        if(e.getSource()==viewsMenuItem)
            cl.show(mainPanel, "Views");
    }
}
