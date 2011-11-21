/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.programmiertier.tetris.view;

import ch.programmiertier.tetris.model.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;

/**
 *
 * @author silvano
 */
public class Panel extends JPanel {

    private int dx;
    private int dy;
    protected int rows;
    protected int slots;
    protected Field field;
    protected Shape shape;

    public Panel(Field field, Dimension dimension) {

        //-1 rechnen, damit die letze Linie sichtbar wird
        this.dx = (dimension.width / field.getSlots()) - 1;
        this.dy = (dimension.height / field.getRows()) - 1;
        this.field = field;
        this.rows = field.getRows();
        this.slots = field.getSlots();
    }

    public int getDeltaX() {return dx;}

    public int getDeltaY() {return dy;}

    /**
     * Zeichnet einn Rahmen um das Panel
     * @param g
     * @param color
     */
    protected void drawFrame(Graphics g, Color color) {
        g.setColor(color);
        g.drawRect(0, 0, dx * slots, dy * rows);
    }

    /**
     * Zeichnet ein von der Feldgrösse und blockSize abhängiges Gitter
     * @param g
     * @param color
     */
    protected void drawGrid(Graphics g, Color color) {
        g.setColor(color);
        for (int x = 0; x <= slots; x++) {
            g.drawLine(x * dy, 0, x * dx, rows * dy);
        }
        for (int y = 0; y <= rows; y++) {
            g.drawLine(0, y * dy, slots * dx, y * dy);
        }
    }
    
    /**
     * Zeichnet einen einzelnen Tetrisblock
     * @param g
     * @param color
     * @param x x-Koordinate
     * @param y y width-Koordinate
     * @param width Breite
     * @param height Höhe
     */
    protected void drawBlock(Graphics g, Color color, int x, int y, int width, int height) {
        g.setColor(color);
        g.fill3DRect(x, y, width, height, true);
        g.fill3DRect(x + 1, y + 1, width - 2, height - 2, true);
    }

    /**
     * Legt die Hintergrundfarbe des Panels fest
     * @param g
     * @param color
     */
    protected void setBackgroundColor(Graphics g, Color color) {
        g.setColor(Color.WHITE);
        g.fillRect(0 + 1, 0 + 1, dx * slots - 1, dy * rows - 1);
    }

    protected void drawDigit(int digit, Graphics g, Color color,  int x, int y, int width, int height) {
        switch(digit) {
            case 0:
                g.drawString("0", x, y); break;
            case 1:
                g.drawString("1", x, y); break;
            case 2:
                g.drawString("2", x, y); break;
            case 3:
                g.drawString("3", x, y); break;
            case 4:
                g.drawString("4", x, y); break;
            case 5:
                g.drawString("5", x, y); break;
            case 6:
                g.drawString("6", x, y); break;
            case 7:
                g.drawString("7", x, y); break;
            case 8:
                g.drawString("8", x, y); break;
            case 9:
                g.drawString("9", x, y); break;
            default: break;
       }
    }
}
