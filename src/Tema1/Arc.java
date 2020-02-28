package Tema1;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arc {

    private Point start;
    private Point stop;

    public Arc(Point start, Point stop) {

        this.start = start;
        this.stop = stop;
    }

    public void desenareArc(Graphics grafica) {

        if (start != null) {
            grafica.setColor(Color.BLACK);
            grafica.drawLine(start.x, start.y, stop.x, stop.y);
            double xCerculet = stop.x - (double)1/6 * (double)(stop.x - start.x);
            double yCerculet = stop.y - (double)1/6 * (double)(stop.y - start.y);
            grafica.setColor(Color.BLACK);
            grafica.fillOval((int)xCerculet - 1, (int)yCerculet - 1, 7, 7);
            grafica.drawOval((int)xCerculet - 1, (int)yCerculet - 1, 7, 7);
        }
    }
}
