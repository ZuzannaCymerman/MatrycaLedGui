package matryca_led_gui;

import javax.swing.*;
import java.awt.*;

public class Menu{
    public JPanel menuPanel;
    public JMenuBar menu = new JMenuBar();
    public JMenu preferences = new JMenu("Preferences");
    public JMenu program = new JMenu("Program");
    public JMenuItem config = new JMenuItem("Configuration");
    public JMenuItem LEDs = new JMenuItem("LEDs");
    public JMenuItem createView = new JMenuItem("Create View");

    public Menu() {
        menuPanel.add(menu, BorderLayout.NORTH);
        menu.add(preferences);
        menu.add(program);
        preferences.add(config);
        program.add(LEDs);
        program.add(createView);
    }


}


