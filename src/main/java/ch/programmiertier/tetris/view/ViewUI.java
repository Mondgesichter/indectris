package ch.programmiertier.tetris.view;

import ch.programmiertier.tetris.model.*;
import ch.programmiertier.tetris.control.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author silvano brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public class ViewUI extends JPanel implements Listener, KeyListener, WindowListener {

    //ArrayList für die angemeldeten Listener
    private List<Controller> control = new ArrayList<Controller>();
    //Grösse des Spielfelds
    private Dimension dimension;
    //Auf diesem Panel wird das Feld gezeichnet
    private TetrisPanel tetris;
    //Auf diesem Panel wird die Preview und der Punktestand angezeigt
    private InfoPanel info;

    public ViewUI(Field field, int blockSize) {

        //blockSize in Spielfeld-Dimension umrechnen
        setDimension(blockSize, field.getRows(), field.getSlots());

        //Grafisches Spielfeld erzeugen
        tetris = new TetrisPanel(field, dimension);
        tetris.setOpaque(true); //Sichtbar machen
        tetris.setLayout(new BorderLayout()); //Borderlayout

        //grösse des Spielfeldes festlegen
        tetris.setPreferredSize(dimension);

        //Vorschaufeld erzeugen
        info = new InfoPanel(field, dimension);
        info.setOpaque(true); //Sichtbar machen
        info.setLayout(new BorderLayout()); //Borderlayout

        //grösse des Infofeldes festlegen
        setDimension(blockSize, field.getRows(), 4);
        info.setPreferredSize(dimension);

        //ViewUI zusammenbauen;
        this.add(tetris, BorderLayout.CENTER);
        this.add(info, BorderLayout.PAGE_START);
    }

    /**
     * Legt die Grösse des UI fest. Ausschlaggebend dabei ist die Grösse
     * der Blöcke. Diese muss als Integer überbenen werden
     * @param blockSize Anzahl Pixel, die ein Block haben soll (Minimum 3)
     */
    private void setDimension(int blockSize, int rows, int slots) {
        dimension = new Dimension(slots * (blockSize + 1), rows * (blockSize + 1));
    }

     /**
     * Die Komponenten des UI werden neu gezeichnet
     * Diese Funktion wird vom Publisher aufgerufen, bei dem das UI als Listener
     * angemeldet ist.
     */
    public void changed() {
        tetris.repaint();
        info.repaint();
    }

    /**
     * Benachrichtigt die angemeldeten Listener, wenn die
     * Tasten SPACE, UP, DOWN, LEFT, RIGHT gedrückt wurden.
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        String input = null;

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            input = "SPACE";
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            input = "UP";
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            input = "DOWN";
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            input = "LEFT";
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            input = "RIGHT";
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            input = "P";
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            input = "Q";
        }

        if (!input.isEmpty()) {
            this.updateListeners(input);
        }

    }

    /**
     * Fügt einen Listener hinzu
     * @param listener
     */
    public void addListener(Controller c) {
        control.add(c);
    }

    /**
     * **
     * Benachrichtigt alle registrierten Listener über Tastatureingaben
     * @param input KEYWORD (SPACE, UP, DOWN, LEFT, RIGHT)
     */
    private void updateListeners(String input) {
        for (Controller c : control) {
            c.input(input);
        }
    }

    public void windowClosing(WindowEvent e) {
        updateListeners("CLOSE");
    }

    //Nicht implementierte methoden
    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
