package Tema2;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.swing.JPanel;

public class Labirint extends JPanel {

    enum Culori {
        Alb,
        Albastru,
        Negru,
        Rosu,
        Necolorat
    }

    private Vector<Vector<Integer>> matriceLabirint = new Vector<Vector<Integer>>();
    private Vector<Vector<Integer>> matriceNoduri = new Vector<Vector<Integer>>();
    private Vector<Vector<Integer>> listeAdiacenta = new Vector<Vector<Integer>>();
    private Vector<Integer> iesiriDinLabirint = new Vector<Integer>();
    private Vector<Vector<Culori>> noduriColorate = new Vector<Vector<Culori>>();
    private int numarNod = 0;
    private int nodStart;
    private boolean apasat = false;
    private Vector<Integer> p = new Vector<Integer>();

    public Labirint() {

        citireMatrice();
        transformareMatrice();
        construireGraf();
        repaint();
    }

    public void butonApasat() {
        apasat = true;
        ProgramPBF();
    }

    public void citireMatrice() {

        File fisier = new File("/Users/robertadrianbucur/Downloads/_robert99_adrian27-alggraf-a2ef82ab94b5/AlgGrafTeme/src/Tema2/Labirint.txt");
        try {
            Scanner citire = new Scanner(fisier);
            int numarLinii = citire.nextInt();
            int numarColoane = citire.nextInt();
            for (int linie = 0; linie < numarLinii; linie++) {
                Vector<Integer> vectorLinie = new Vector<Integer>();
                for (int nod = 0; nod < numarColoane; nod++) {
                    int numarNod = citire.nextInt();
                    vectorLinie.add(numarNod);
                }
                matriceLabirint.add(vectorLinie);
            }
            citire.close();
        } catch (Exception exceptie) {
            System.out.println("A aparut o eroare!");
        }
    }

    public void transformareMatrice() {

        for (int linie = 0; linie < matriceLabirint.size(); linie++) {
            Vector<Integer> vectorLinie = new Vector<Integer>();
            Vector<Culori> culoriVector = new Vector<Culori>();
            for (int coloana = 0; coloana < matriceLabirint.get(linie).size(); coloana++) {
                if (matriceLabirint.get(linie).get(coloana) != 0) {
                    numarNod++;
                    vectorLinie.add(numarNod);
                    if (matriceLabirint.get(linie).get(coloana) == 2) {
                        nodStart = numarNod;
                    }
                    if (linie == 0
                            || linie == matriceLabirint.size() - 1
                            || coloana == 0
                            || coloana == matriceLabirint.get(linie).size() - 1) {
                        iesiriDinLabirint.add(numarNod);
                    }
                } else {
                    vectorLinie.add(0);
                }
                culoriVector.add(Culori.Necolorat);
            }
            noduriColorate.add(culoriVector);
            matriceNoduri.add(vectorLinie);
        }
    }

    public void construireGraf() {

        for (int indiceNod = 0; indiceNod < numarNod; indiceNod++) {
            Vector<Integer> vectorAuxiliar = new Vector<Integer>();
            listeAdiacenta.add(vectorAuxiliar);
        }
        for (int linie = 0; linie < matriceNoduri.size(); linie++) {
            for (int coloana = 0; coloana < matriceNoduri.get(linie).size(); coloana++) {
                if (matriceNoduri.get(linie).get(coloana) != 0) {
                    if (linie > 0 && matriceNoduri.get(linie - 1).get(coloana) != 0) {
                        listeAdiacenta.get(matriceNoduri.get(linie - 1).get(coloana) - 1).add(matriceNoduri.get(linie).get(coloana));
                    }
                    if (linie < matriceNoduri.size() - 1 && matriceNoduri.get(linie + 1).get(coloana) != 0) {
                        listeAdiacenta.get(matriceNoduri.get(linie + 1).get(coloana) - 1).add(matriceNoduri.get(linie).get(coloana));
                    }
                    if (coloana > 0 && matriceNoduri.get(linie).get(coloana - 1) != 0) {
                        listeAdiacenta.get(matriceNoduri.get(linie).get(coloana - 1) - 1).add(matriceNoduri.get(linie).get(coloana));
                    }
                    if (coloana < matriceNoduri.get(linie).size() - 1 && matriceNoduri.get(linie).get(coloana + 1) != 0) {
                        listeAdiacenta.get(matriceNoduri.get(linie).get(coloana + 1) - 1).add(matriceNoduri.get(linie).get(coloana));
                    }
                }
            }
        }
    }

    private void ProgramPBF() {

        int inf = Integer.MAX_VALUE;
        Vector<Boolean> U = new Vector<Boolean>();
        Vector<Integer> V = new Vector<Integer>();
        Vector<Integer> W = new Vector<Integer>();
        Vector<Integer> l = new Vector<Integer>();
        for (int indice = 0; indice < listeAdiacenta.size(); indice++) {
            p.add(0);
            l.add(inf);
            U.add(false);
        }
        U.set(nodStart - 1, true);
        V.add(nodStart);
        W = new Vector<Integer>();
        l.set(nodStart - 1, 0);
        while (V.size() != 0) {
            int x = V.firstElement();
            for (int y : listeAdiacenta.get(x - 1)) {
                if (U.get(y - 1) == false) {
                    U.set(y - 1, true);
                    V.add(y);
                    p.set(y - 1, x);
                    l.set(y - 1, l.get(x - 1) + 1);
                    if (iesiriDinLabirint.contains(y)) {
                        drumGasit(y);
                    }
                }
            }
            V.remove(V.firstElement());
            W.add(x);
        }
        repaint();
    }

    private void drumGasit(int iesire) {
        while (iesire != nodStart) {
            for (int linie = 0; linie < matriceNoduri.size(); linie++) {
                for (int coloana = 0; coloana < matriceNoduri.get(linie).size(); coloana++) {
                    if (matriceNoduri.get(linie).get(coloana) == iesire) {
                        noduriColorate.get(linie).set(coloana,Culori.Rosu);
                        break;
                    }
                }
            }
            iesire = p.get(iesire - 1);
        }
    }

    protected void paintComponent(Graphics grafica) {
        super.paintComponent(grafica);
        if (apasat == false) {
            for (int linie = 0; linie < matriceNoduri.size(); linie++) {
                for (int coloana = 0; coloana < matriceNoduri.get(linie).size(); coloana++) {
                    if (matriceNoduri.get(linie).get(coloana) == 0) {
                        noduriColorate.get(linie).set(coloana, Culori.Negru);
                        grafica.setColor(Color.BLACK);
                        grafica.fillRect(50 * coloana + 100, 50 * linie + 100, 50, 50);
                        grafica.setColor(Color.BLACK);
                        grafica.drawRect(50 * coloana + 100, 50 * linie + 100, 50, 50);
                    } else if (matriceNoduri.get(linie).get(coloana) == nodStart) {
                        noduriColorate.get(linie).set(coloana, Culori.Albastru);
                        grafica.setColor(Color.BLUE);
                        grafica.fillRect(50 * coloana + 100, 50 * linie + 100, 50, 50);
                        grafica.setColor(Color.BLACK);
                        grafica.drawRect(50 * coloana + 100, 50 * linie + 100, 50, 50);
                    } else if (matriceNoduri.get(linie).get(coloana) != 0) {
                        noduriColorate.get(linie).set(coloana, Culori.Alb);
                        grafica.setColor(Color.WHITE);
                        grafica.fillRect(50 * coloana + 100, 50 * linie + 100, 50, 50);
                        grafica.setColor(Color.BLACK);
                        grafica.drawRect(50 * coloana + 100, 50 * linie + 100, 50, 50);
                    }
                }
            }
        } else {
            for (int linie = 0; linie < matriceNoduri.size(); linie++) {
                for (int coloana = 0; coloana < matriceNoduri.get(linie).size(); coloana++) {
                    switch (noduriColorate.get(linie).get(coloana)) {
                        case Alb:
                            grafica.setColor(Color.WHITE);
                            break;
                        case Negru:
                            grafica.setColor(Color.BLACK);
                            break;
                        case Albastru:
                            grafica.setColor(Color.BLUE);
                            break;
                        case Rosu:
                            grafica.setColor(Color.RED);
                            break;
                        default:
                            break;
                    }
                    grafica.fillRect(50 * coloana + 100, 50 * linie + 100, 50, 50);
                    grafica.setColor(Color.BLACK);
                    grafica.drawRect(50 * coloana + 100, 50 * linie + 100,50, 50);
                }
            }
        }
    }
}
