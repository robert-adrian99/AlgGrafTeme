package Tema5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainTema5 {

    public static void initializareInterfata() {
        Fereastra fereastra = new Fereastra();
        JFrame cadru = new JFrame("Algoritmica Grafurilor");
        cadru.setSize(1383, 745);
        cadru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cadru.add(fereastra);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        cadru.setLocation(dim.width/2-cadru.getSize().width/2, dim.height/2-cadru.getSize().height/2);
        cadru.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializareInterfata();
            }
        });
    }
}
