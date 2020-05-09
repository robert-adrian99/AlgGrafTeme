package Tema6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import static java.lang.Integer.min;

public class Graf {
    public int numarNoduri;
    private int numarArce;
    private Vector<Nod> noduri = new Vector<>();
    private Vector<Arc> arce = new Vector<>();
    Vector<Boolean> vizitatArce = new Vector<>();

    public Graf() {
        try {
            Scanner scan = new Scanner(new File("src/Tema6/Graf.txt"));
            numarNoduri = Integer.parseInt(scan.nextLine());
            numarArce = Integer.parseInt(scan.nextLine());
            for (int indice = 0; indice < numarNoduri; ++indice) {
                noduri.add(new Nod(Integer.parseInt(scan.nextLine())));
            }
            for (int indice = 0; indice < numarArce; ++indice) {
                try {
                    String linie = scan.nextLine();
                    String[] splittedLine;
                    splittedLine = linie.split("-");
                    arce.add(new Arc(new Nod(Integer.parseInt(splittedLine[0])), new Nod(Integer.parseInt(splittedLine[3])), Integer.parseInt(splittedLine[1]), Integer.parseInt(splittedLine[2])));

                } catch (NumberFormatException ex) {

                    System.out.println("Eroare!");
                }
            }
            for (int indice = 0; indice < numarArce; ++indice) {
                vizitatArce.add(false);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Eroare!");
        }
    }

    public Graf(Graf graf) {
        this.numarNoduri = graf.numarNoduri;
        this.numarArce = graf.numarArce;
        this.noduri = graf.noduri;
        arce = new Vector<>();
        for (int indice = 0; indice < numarArce; ++indice) {
            this.arce.add(new Arc(graf.arce.get(indice).GetNodInitial(), graf.arce.get(indice).GetNodFinal(), graf.arce.get(indice).GetFlux(), graf.arce.get(indice).GetCapacitate()));
        }
    }

    public Vector<Nod> GetNoduri() {
        return noduri;
    }

    public Vector<Arc> GetArce() {
        return arce;
    }

    public int GetCapacitateArc(Nod nodInitial, Nod nodFinal) {
        for (Arc arc : arce) {
            if (arc.GetNodInitial().GetNumarNod() == nodInitial.GetNumarNod() && arc.GetNodFinal().GetNumarNod() == nodFinal.GetNumarNod()) {
                return arc.GetCapacitate();
            }
        }
        return 0;
    }

    public void SetCapacitateArc(Nod nodInitial, Nod nodFinal, int capacitate) {
        boolean ok = false;
        for (Arc arc : arce) {
            if (arc.GetNodInitial().GetNumarNod() == nodInitial.GetNumarNod() && arc.GetNodFinal().GetNumarNod() == nodFinal.GetNumarNod()) {
                arc.SetCapacitate(capacitate);
                ok = true;
                return;
            }
        }
        if (ok == false && capacitate != 0) {
            arce.add(new Arc(nodInitial, nodFinal, 0, capacitate));
        }
    }

    public void SetFluxArc(Nod nodInitial, Nod nodFinal, int flux) {
        for (Arc arc : arce) {
            if (arc.GetNodInitial().GetNumarNod() == nodInitial.GetNumarNod() && arc.GetNodFinal().GetNumarNod() == nodFinal.GetNumarNod()) {
                arc.SetFlux(flux);
                return;
            }
        }
    }

    public boolean GetVizitatArc(Nod nodInitial, Nod nodFinal) {
        for (Arc arc : arce) {
            if (arc.GetNodInitial().GetNumarNod() == nodInitial.GetNumarNod() && arc.GetNodFinal().GetNumarNod() == nodFinal.GetNumarNod()) {
                return arc.GetVizitat();
            }
        }
        return false;
    }

    public void SetVizitatArc(Nod nodInitial, Nod nodFinal, boolean vizitat) {
        for (Arc arc : arce) {
            if (arc.GetNodInitial().GetNumarNod() == nodInitial.GetNumarNod() && arc.GetNodFinal().GetNumarNod() == nodFinal.GetNumarNod()) {
                arc.SetVizitat(vizitat);
                return;
            }
        }
    }

    public Vector<Nod> DMF(Graf grafRezidual, Nod s, Nod t) {
        Vector<Boolean> vizitat = new Vector<>();
        Vector<Nod> parinte = new Vector<>();
        for (int indice = 0; indice < numarNoduri; ++indice) {
            vizitat.add(false);
            parinte.add(new Nod(0));
        }
        Vector<Nod> coada = new Vector<>();
        coada.add(s);
        vizitat.set(s.GetNumarNod() - 1, true);
        parinte.set(s.GetNumarNod() - 1, new Nod(0));
        while (coada.size() != 0) {
            Nod u = coada.firstElement();
            coada.remove(coada.firstElement());
            for (int v = 0; v < numarNoduri; v++) {
                if (!vizitat.get(v) && !grafRezidual.GetVizitatArc(u, new Nod(v + 1)) && grafRezidual.GetCapacitateArc(u, new Nod(v + 1)) > 0) {
                    coada.add(new Nod(v + 1));
                    parinte.set(v, u);
                    vizitat.set(v, true);
                }
            }
        }
        return parinte;
    }

    private boolean verificDMF(Vector<Nod> parinte) {
        for (int indice = 0; indice < numarNoduri; ++indice) {
            if (parinte.get(indice).GetNumarNod() != 0) {
                return true;
            }
        }
        return false;
    }

    public void ProgramGenericFD(Nod s, Nod t) {
        Graf grafRezidual = new Graf(this);
        Vector<Nod> parinte = new Vector<>();
        parinte = DMF(grafRezidual, s, t);
        while (verificDMF(parinte)) {
            Nod nodParinte;
            int r = Integer.MAX_VALUE;
            for (Nod nodCopil = t; nodCopil.GetNumarNod() != s.GetNumarNod(); nodCopil = parinte.get(nodCopil.GetNumarNod() - 1)) {
                nodParinte = parinte.get(nodCopil.GetNumarNod() - 1);
                r = min(r, grafRezidual.GetCapacitateArc(nodParinte, nodCopil));
                grafRezidual.SetVizitatArc(nodParinte, nodCopil, true);
            }
            for (Nod nodCopil = t; nodCopil.GetNumarNod() != s.GetNumarNod(); nodCopil = parinte.get(nodCopil.GetNumarNod() - 1)) {
                nodParinte = parinte.get(nodCopil.GetNumarNod() - 1);
                grafRezidual.SetCapacitateArc(nodParinte, nodCopil, grafRezidual.GetCapacitateArc(nodParinte, nodCopil) - r);
                grafRezidual.SetCapacitateArc(nodCopil, nodParinte, grafRezidual.GetCapacitateArc(nodCopil, nodParinte) + r);
            }
            parinte = DMF(grafRezidual, s, t);
        }
        for (Arc arc : this.GetArce()) {
            int flux = this.GetCapacitateArc(arc.GetNodInitial(), arc.GetNodFinal()) - grafRezidual.GetCapacitateArc(arc.GetNodInitial(), arc.GetNodFinal());
            if (flux >= 0) {
                this.SetFluxArc(arc.GetNodInitial(), arc.GetNodFinal(), flux);
                this.SetFluxArc(arc.GetNodFinal(), arc.GetNodInitial(), 0);
            } else {
                this.SetFluxArc(arc.GetNodInitial(), arc.GetNodFinal(), 0);
                this.SetFluxArc(arc.GetNodFinal(), arc.GetNodInitial(), -flux);
            }
        }
    }
}
