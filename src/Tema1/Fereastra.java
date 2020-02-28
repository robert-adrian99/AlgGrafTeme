package Tema1;
import Tema3.SortareTopologica;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;
import java.util.Random;
import javax.swing.JPanel;

public class Fereastra extends JPanel {

    private int numarNod = 1;
    private int diametruNod = 30;
    private Vector<Nod> listaNoduri;
    private Vector<Arc> listaArce;
    private Vector<Integer> listaTopologica = new Vector<Integer>();

    public Fereastra(ReprezentareGraf graf) {

        listaNoduri = new Vector<Nod>();
        listaArce = new Vector<Arc>();
        for (int nod = 0; nod < graf.matriceAdiacenta.size(); nod++) {
            boolean seSuprapune = true;
            while (seSuprapune) {
                seSuprapune = false;
                Random numarRandom = new Random();
                int coordonataX = numarRandom.nextInt(550) + 100;
                int coordonataY = numarRandom.nextInt(350) + 100;
                for (Nod value : listaNoduri) {
                    if (coordonataX <= value.getCoordonataX() + 50 &&
                            coordonataX >= value.getCoordonataX() - 50 &&
                            coordonataY <= value.getCoordonataY() + 50 &&
                            coordonataY >= value.getCoordonataY() - 50) {
                        seSuprapune = true;
                        break;
                    }
                }
                if(!seSuprapune) {
                    adaugaNod(coordonataX, coordonataY);
                }
            }
        }
        for (int linie = 0; linie < graf.matriceAdiacenta.size(); linie++) {
            for (int coloana = 0; coloana < graf.matriceAdiacenta.get(linie).size(); coloana++) {
                if (graf.matriceAdiacenta.get(linie).get(coloana) == 1) {
                    int xStart = 0, yStart = 0, xStop = 0, yStop = 0;
                    for (Nod nod : listaNoduri) {
                        if (nod.getNumarNod() == linie + 1) {
                            xStart = nod.getCoordonataX() + 15;
                            yStart = nod.getCoordonataY() + 15;
                        }
                        if (nod.getNumarNod() == coloana + 1) {
                            xStop = nod.getCoordonataX() + 15;
                            yStop = nod.getCoordonataY() + 15;
                        }
                    }
                    adaugaArc(xStart, yStart, xStop, yStop);
                }
            }
        }

    }

    private void adaugaNod(int coordonataX, int coordonataY) {

        Nod nod = new Nod(coordonataX, coordonataY, numarNod);
        listaNoduri.add(nod);
        numarNod++;
        repaint();
    }

    private void adaugaArc(int xStart, int yStart, int xStop, int yStop) {

        Point start = new Point(xStart, yStart);
        Point stop = new Point(xStop, yStop);
        Arc arc = new Arc(start, stop);
        listaArce.add(arc);
        repaint();
    }

    public void sortareTopologicaAction(ReprezentareGraf graf) {

        System.out.println("Sortarea topologica a fost realizata!");
        SortareTopologica st = new SortareTopologica();
        int numarNoduri = listaNoduri.size();
        int nodSursa = 1;
        listaTopologica = st.ProgramST(numarNoduri, nodSursa, graf.listeAdiacenta);
        System.out.println(listaTopologica);
        for (int nod = 0; nod < listaNoduri.size(); nod++) {
            listaNoduri.get(nod).setNumarNod(listaTopologica.get(nod));
        }
        repaint();
    }

    protected void paintComponent(Graphics grafica) {

        super.paintComponent(grafica);
        grafica.setFont(new Font("TimesRoman", Font.BOLD, 15));
        grafica.drawString("Graful este:", 50, 70);
        for (Arc arc : listaArce) {
            arc.desenareArc(grafica);
        }
        for (Nod nod : listaNoduri) {
            nod.desenareNod(grafica, diametruNod);
        }
    }
}
