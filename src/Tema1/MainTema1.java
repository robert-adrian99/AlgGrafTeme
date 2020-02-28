package Tema1;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class MainTema1 {

    public static void initializareInterfata(ReprezentareGraf graf) {

        Fereastra fereastra = new Fereastra(graf);
        Button butonSortareTopologica = new Button();
        butonSortareTopologica.setSize(150, 50);
        butonSortareTopologica.setLabel("Sortare Topologica");
        butonSortareTopologica.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                fereastra.sortareTopologicaAction(graf);
            }
        });
        Button butonResetare = new Button();
        butonResetare.setSize(150, 50);
        butonResetare.setLocation(200, 0);
        butonResetare.setLabel("Resetare");
        JFrame cadru = new JFrame("Algoritmica Grafurilor");
        cadru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cadru.add(butonSortareTopologica);
        cadru.add(butonResetare);
        cadru.add(fereastra);
        cadru.setSize(800, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        cadru.setLocation(dim.width/2-cadru.getSize().width/2, dim.height/2-cadru.getSize().height/2);
        cadru.setVisible(true);
        butonResetare.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                cadru.setVisible(false);
                SwingUtilities.invokeLater( new Runnable() {

                    public void run() {

                        initializareInterfata(graf);
                    }
                });
            }
        });
    }

    public static void main(String[] args) 	{

        ReprezentareGraf graf = new ReprezentareGraf();
        int optiune = 0;
        try {
            Scanner citire = new Scanner(System.in);
            System.out.println("Optiuni:");
            System.out.println("0. Citirea Matricei de Adiacenta din fisier.");
            System.out.println("1. Citirea Listelor de Adiacenta din fisier.");
            System.out.println("Introduceti optiunea dvs.: ");
            optiune = citire.nextInt();
            citire.close();
        } catch (Exception exceptie) {
            System.out.println("A aparut o eroare!");
        }
        if (optiune == 0) {
            graf.citireMatriceAdiacenta();
            System.out.println("Aceasta este Matricea de Adiacenta citita:");
            graf.afisareMatriceAdiacenta();
            graf.transformaInListe();
            System.out.println("Listele de Adiacenta conform Matricei de Adiacenta sunt:");
            graf.afisareListeAdiacenta();
        } else {
            graf.citireListeAdiacenta();
            System.out.println("Acestea sunt Listele de Adiacenta citite:");
            graf.afisareListeAdiacenta();
            System.out.println();
            System.out.println("Matricea de Adiacenta conform Listelor de Adiacenta este:");
            graf.transformaInMatrice();
            graf.afisareMatriceAdiacenta();
        }
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                initializareInterfata(graf);
            }
        });
    }
}
