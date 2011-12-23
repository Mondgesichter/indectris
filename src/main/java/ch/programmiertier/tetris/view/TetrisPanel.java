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
 * @author silvano brugnoni<silvano.brugnoni@stud.hslu.ch>
 */
@SuppressWarnings("serial")
class TetrisPanel extends Panel {

    /**
     * Konstruktor für das Spielfeld.
     *
     * @param field
     * @param dimension
     */
    public TetrisPanel(Field field, Dimension dimension) {
        super(field, dimension);
    }

    /**
     * Methode der Klasse JPanel. Zusätzlich wird das das Spielfeld und die auf
     * die vorhandenen Blocke eingezeichnet.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.shape = field.getShape();

        int dy = super.getDeltaY();
        int dx = super.getDeltaX();

        //Hintergrundfarbe
        super.setBackgroundColor(g, Color.WHITE);

        //Rahmen
        super.drawFrame(g, Color.LIGHT_GRAY);

        //blöcke einzeichnen
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < slots; j++) {
                if (field.getField(i, j) > 0) {
                    super.drawBlock(g, shape.getColor(field.getField(i, j)), (j * dy) + 1, (i * dx) + 1, dx - 1, dy - 1);
                } else if (field.getField(i,j) == -1) {
                    super.drawBlock(g, Color.DARK_GRAY, (j * dy) + 1, (i * dx) + 1, dx - 1, dy - 1);
                        
                }
            }
        }

        if (field.getGameOver()) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < slots; j++) {
                    g.setColor(Color.BLACK);
                    super.drawBlock(g, Color.DARK_GRAY, (j * dy) + 1, (i * dx) + 1, dx - 1, dy - 1);
                }
            }
        }
    }
}
