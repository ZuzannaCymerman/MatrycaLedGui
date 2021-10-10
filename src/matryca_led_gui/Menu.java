package matryca_led_gui;

import javax.swing.*;
import java.awt.*;

public class Menu{
    public JPanel menuPanel;
    public JMenuBar menu = new JMenuBar();

    public JMenu preferences = new JMenu("Ustawienia");
    public JMenu program = new JMenu("Program");

    public JMenuItem config = new JMenuItem("Konfiguracja");
    public JMenuItem LEDs = new JMenuItem("LEDs");
    public JMenuItem views = new JMenuItem("Widoki");

    public Menu() {
        menuPanel.add(menu, BorderLayout.NORTH);
        menu.add(preferences);
        menu.add(program);

        preferences.add(config);

        program.add(LEDs);
        program.add(views);
    }


}


