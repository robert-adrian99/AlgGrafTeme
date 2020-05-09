package Tema1;
import java.util.Scanner;
import java.io.File;
import java.util.Vector;

public class ReprezentareGraf {

    public Vector<Vector<Integer>> matriceAdiacenta = new Vector<Vector<Integer>>();
    public  Vector<Vector<Integer>> listeAdiacenta = new Vector<Vector<Integer>>();

    public ReprezentareGraf() {

        matriceAdiacenta = new Vector<Vector<Integer>>();
        listeAdiacenta = new Vector<Vector<Integer>>();
    }

    public void citireMatriceAdiacenta() {

        try {
            File fisier = new File("src/Tema1/MatriceAdiacenta.txt");
            Scanner citire = new Scanner(fisier);
            int numarNoduri = citire.nextInt();
            for (int linie = 0; linie < numarNoduri; linie++) {
                Vector<Integer> vectorLinie = new Vector<Integer>();
                for (int nod = 0; nod < numarNoduri; nod++) {
                    int numarNod = citire.nextInt();
                    vectorLinie.add(numarNod);
                }
                matriceAdiacenta.add(vectorLinie);
            }
            citire.close();
        } catch (Exception exceptie) {
            System.out.println("A aparut o eroare");
        }
    }

    public void afisareMatriceAdiacenta() {

        for (int index = 0; index < matriceAdiacenta.size(); index++) {
            System.out.println(matriceAdiacenta.get(index));
        }
    }

    public void citireListeAdiacenta() {

        try {
            File fisier = new File("src/Tema1/ListeAdiacenta.txt");
            Scanner citire = new Scanner(fisier);
            int numarNoduri = citire.nextInt();
            for (int nod = 0; nod < numarNoduri; nod++) {
                int numarNoduriAdiacente = citire.nextInt();
                Vector<Integer> listaNod = new Vector<Integer>();
                for (int indiceListaAdiacenta = 0; indiceListaAdiacenta < numarNoduriAdiacente; indiceListaAdiacenta++) {
                    int nodAdiacent = citire.nextInt();
                    listaNod.add(nodAdiacent);
                }
                listeAdiacenta.add(listaNod);
            }
            citire.close();
        } catch (Exception exceptie) {
            System.out.println("A aparut o eroare!");
        }
    }

    public void afisareListeAdiacenta() {

        for (int nod = 0; nod < listeAdiacenta.size(); nod++) {
            System.out.println((nod + 1) + ": " + listeAdiacenta.get(nod));
        }
    }

    public void transformaInMatrice() {

        for (int nod = 0; nod < listeAdiacenta.size(); nod++) {
            Vector<Integer> vectorLinie = new Vector<Integer>();
            for (int nodDinLista = 0; nodDinLista < listeAdiacenta.get(nod).size(); nodDinLista++) {
                int nodulDinLista = listeAdiacenta.get(nod).get(nodDinLista);
                nodulDinLista -= vectorLinie.size();
                --nodulDinLista;
                while (nodulDinLista > 0) {
                    vectorLinie.add(0);
                    --nodulDinLista;
                }
                vectorLinie.add(1);
            }
            while (listeAdiacenta.size() - vectorLinie.size() > 0) {
                vectorLinie.add(0);
            }
            matriceAdiacenta.add(vectorLinie);
        }
    }

    public void transformaInListe() {

        listeAdiacenta = new Vector<Vector<Integer>>(matriceAdiacenta.size());
        for (int linie = 0; linie < matriceAdiacenta.size(); linie++) {
            Vector<Integer> listaNod = new Vector<Integer>();
            for (int coloana = 0; coloana < matriceAdiacenta.get(linie).size(); coloana++) {
                if (matriceAdiacenta.get(linie).get(coloana) == 1) {
                    listaNod.add(coloana + 1);
                }
            }
            listeAdiacenta.add(listaNod);
        }
    }
}
