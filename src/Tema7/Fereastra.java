package Tema7;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;
import java.util.Random;
import javax.swing.JPanel;

public class Fereastra extends JPanel {

    private int diametruNod = 30;
    private Vector<Nod> listaNoduri;
    private Vector<Arc> listaArce;

    public Fereastra(Graf graf) {
        listaNoduri = new Vector<Nod>();
        listaArce = new Vector<Arc>();
        for (int nod = 0; nod < graf.GetNoduri().size(); nod++) {
            boolean seSuprapune = true;
            while (seSuprapune) {
                seSuprapune = false;
                Random numarRandom = new Random();
                int coordonataX = numarRandom.nextInt(550) + 100;
                int coordonataY = numarRandom.nextInt(350) + 100;
                for (Nod nod2 : graf.GetNoduri()) {
                    if (coordonataX <= nod2.GetCoordonataX() + 50 &&
                            coordonataX >= nod2.GetCoordonataX() - 50 &&
                            coordonataY <= nod2.GetCoordonataY() + 50 &&
                            coordonataY >= nod2.GetCoordonataY() - 50) {
                        seSuprapune = true;
                        break;
                    }
                }
                if (!seSuprapune) {
                    adaugaNod(coordonataX, coordonataY, graf.GetNoduri().get(nod).GetNumarNod());
                }
            }
        }
        for (Arc arc : graf.GetArce()) {
            Nod nodStart = new Nod(), nodStop = new Nod();
            for (Nod nod : listaNoduri) {
                if (nod.GetNumarNod() == arc.GetNodInitial().GetNumarNod()) {
                    nodStart = new Nod(nod);
                }
                if (nod.GetNumarNod() == arc.GetNodFinal().GetNumarNod()) {
                    nodStop = new Nod(nod);
                }
            }
            adaugaArc(nodStart, nodStop, arc.GetFlux(), arc.GetCapacitate());
        }
    }

    private void adaugaNod(int coordonataX, int coordonataY, int numarNod) {
        Nod nod = new Nod(coordonataX, coordonataY, numarNod);
        listaNoduri.add(nod);
        repaint();
    }

    private void adaugaArc(Nod nodStart, Nod nodStop, int flux, int capacitate) {
        Arc arc = new Arc(nodStart, nodStop, flux, capacitate);
        listaArce.add(arc);
        repaint();
    }

    public void desenareFlux(Graf graf) {
        Vector<Nod> parinte = new Vector<>();
        Nod s = new Nod(1);
        Nod t = new Nod(4);
        System.out.println("Graful inainte de Flux");
        for (Arc arc : graf.GetArce()) {
            System.out.println(arc.GetNodInitial().GetNumarNod() + "-" + arc.GetFlux() + "-" + arc.GetCapacitate() + "-" + arc.GetNodFinal().GetNumarNod());
        }
        System.out.println();

        Vector<Arc> Arce = new Vector<>();
        listaNoduri = graf.ProgramFFE(s, t, listaNoduri, Arce);

        System.out.println("Graful dupa Flux");
        for (Arc arc : graf.GetArce()) {
            System.out.println(arc.GetNodInitial().GetNumarNod() + "-" + arc.GetFlux() + "-" + arc.GetCapacitate() + "-" + arc.GetNodFinal().GetNumarNod());
        }
        System.out.println();

        listaArce = new Vector<>();
        for (Arc arc : Arce) {
            Nod nodStart = new Nod(), nodStop = new Nod();
            for (Nod nod : listaNoduri) {
                if (nod.GetNumarNod() == arc.GetNodInitial().GetNumarNod()) {
                    nodStart = new Nod(nod);
                }
                if (nod.GetNumarNod() == arc.GetNodFinal().GetNumarNod()) {
                    nodStop = new Nod(nod);
                }
            }
            adaugaArc(nodStart, nodStop, arc.GetFlux(), arc.GetCapacitate());
        }
        repaint();
    }

    protected void paintComponent(Graphics grafica) {
        super.paintComponent(grafica);
        grafica.setFont(new Font("TimesRoman", Font.BOLD, 15));
        grafica.drawString("Graful este:", 50, 70);
        for (Arc arc : listaArce) {
            arc.DesenareArc(grafica);
        }
        for (Nod nod : listaNoduri) {
            nod.DesenareNod(grafica, diametruNod);
        }
    }
}
