package Tema1;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Nod {

    private int coordonataX;
    private int coordonataY;
    private int numarNod;

    public Nod(int coordonataX, int coordonataY, int numar) {

        this.coordonataX = coordonataX;
        this.coordonataY = coordonataY;
        this.numarNod = numar;
    }

    public int getCoordonataX() {

        return coordonataX;
    }

    public void setCoordonataX(int coordonataX) {

        this.coordonataX = coordonataX;
    }

    public int getCoordonataY() {

        return coordonataY;
    }

    public void setCoordonataY(int coordonataY) {

        this.coordonataY = coordonataY;
    }

    public int getNumarNod() {

        return numarNod;
    }

    public void setNumarNod(int numarNod) {

        this.numarNod = numarNod;
    }

    public void desenareNod(Graphics grafica, int diametruNod) {

        grafica.setColor(Color.WHITE);
        grafica.setFont(new Font("TimesRoman", Font.BOLD, 15));
        grafica.fillOval(coordonataX, coordonataY, diametruNod, diametruNod);
        grafica.setColor(Color.BLACK);
        grafica.drawOval(coordonataX, coordonataY, diametruNod, diametruNod);
        if(numarNod < 10) {
            grafica.drawString(((Integer) numarNod).toString(), coordonataX + 10, coordonataY + 20);
        } else {
            grafica.drawString(((Integer) numarNod).toString(), coordonataX + 8, coordonataY + 20);
        }
    }
}
