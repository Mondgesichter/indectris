/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.programmiertier.tetris.view;

import ch.programmiertier.tetris.model.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

/**
 *
 * @author Silvano Brugnoni<silvano.brugnoni@stud.hslu.ch>
 */
public class InfoPanel extends Panel {

    public InfoPanel(Field field, Dimension dimension) {
        super(field, dimension);
        rows = 4;
        slots = 4;
    }
    /**
     * Methode der Klasse JPanel. Zusätzlich wird das das Spielfeld und die auf
     * die vorhandenen Blocke eingezeichnet.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.shape = field.getNextShape();
        int dx = super.getDeltaX();
        int dy = super.getDeltaY();

        //Hintergrund
        super.setBackgroundColor(g, Color.WHITE);
        //Rahmen
        super.drawFrame(g, Color.LIGHT_GRAY);

        //Blöcke
        for (int i = 0; i < 4; i++) {
            g.setColor(shape.getColor(shape.getId() + 1));
            super.drawBlock(g, shape.getColor(shape.getId() + 1), (field.getNextShape().getShape(i, 1) + 1) * dy + 1, ((field.getNextShape().getShape(i, 0) + 1) * dx) + 1, dx - 1, dy - 1);
        }

        //Infos
        g.setColor(Color.BLACK);

        //g.drawString("SCORE: " + field.getScore(), 0, 6 * dy);
        

        int digit5 = 0;
        int digit4 = 0;
        int digit3 = 0;
        int digit2 = 0;
        int digit1 = 0;

        int score = field.getScore();

        if(score>9999) {
            digit5 = score/10000;
            score-=digit5*10000;
            }
        if(score>999) {
            digit4 = score/1000;
            score-=digit4*1000;
        }
        if(score>99) {
            digit3 = score/100;
            score-=digit3*100;
            }
        if(score>9) {
            digit2 = score/10;
            score-=digit2*10;
        }
        if(score>0) {
            digit1 = score;
            }

        super.drawDigit(digit5, g, Color.yellow,0, 6 * dy, dx/4, dy/4);
        super.drawDigit(digit4, g, Color.yellow,3*dx/4, 6 * dy, dx/4, dy/4);
        super.drawDigit(digit3, g, Color.yellow,6*dx/4, 6 * dy, dx/4, dy/4);
        super.drawDigit(digit2, g, Color.yellow,9*dx/4, 6 * dy, dx/4, dy/4);
        super.drawDigit(digit1, g, Color.yellow,12*dx/4, 6 * dy, dx/4, dy/4);


        g.drawString(field.getLevel()+"", 0, 7 * dy);
        if(field.isPaused()) {
             g.drawString("Pause", 0, 8 * dy);
        }
        

    }
}
