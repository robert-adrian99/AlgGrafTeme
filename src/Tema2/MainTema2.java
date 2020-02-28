package Tema2;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainTema2 {

    private static void initializareInterfata() {

        Labirint fereastra = new Labirint();
        Button butonAfisareDrumuri = new Button();
        butonAfisareDrumuri.setSize(150, 50);
        butonAfisareDrumuri.setLabel("Afisare drumuri");
        butonAfisareDrumuri.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                fereastra.butonApasat();
            }
        });
        Button butonResetare = new Button();
        butonResetare.setSize(150, 50);
        butonResetare.setLocation(200, 0);
        butonResetare.setLabel("Resetare");
        JFrame cadru = new JFrame("Algoritmica Grafurilor");
        cadru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cadru.add(butonResetare);
        cadru.add(butonAfisareDrumuri);
        cadru.add(fereastra);
        cadru.setSize(700, 700);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        cadru.setLocation(dim.width/2-cadru.getSize().width/2, dim.height/2-cadru.getSize().height/2);
        cadru.setVisible(true);
        butonResetare.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                cadru.setVisible(false);
                SwingUtilities.invokeLater( new Runnable() {

                    public void run() {

                        initializareInterfata();
                    }
                });
            }
        });
    }

    public static void main (String[] args) {

        SwingUtilities.invokeLater( new Runnable() {

            public void run() {

                initializareInterfata();
            }
        });
    }
}
