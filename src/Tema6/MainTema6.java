package Tema6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainTema6 {

    public static void initializareInterfata(Graf graf) {
        Fereastra fereastra = new Fereastra(graf);
        Button butonFluxMaxim = new Button();
        butonFluxMaxim.setSize(150, 50);
        butonFluxMaxim.setLabel("Flux Maxim");
        Button butonResetare = new Button();
        butonResetare.setSize(150, 50);
        butonResetare.setLocation(200, 0);
        butonResetare.setLabel("Resetare");
        JFrame cadru = new JFrame("Algoritmica Grafurilor");
        cadru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cadru.add(butonFluxMaxim);
        cadru.add(butonResetare);
        cadru.add(fereastra);
        cadru.setSize(800, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        cadru.setLocation(dim.width/2-cadru.getSize().width/2, dim.height/2-cadru.getSize().height/2);
        cadru.setVisible(true);
        butonFluxMaxim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fereastra.desenareFlux(graf);
            }
        });
        butonResetare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cadru.setVisible(false);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Graf graf = new Graf();
                        initializareInterfata(graf);
                    }
                });
            }
        });
    }

    public static void main(String[] args) {
        Graf graf = new Graf();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializareInterfata(graf);
            }
        });
    }
}
