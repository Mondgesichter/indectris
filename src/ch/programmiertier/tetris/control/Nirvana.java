package ch.programmiertier.tetris.control;

import ch.programmiertier.tetris.model.*;
import ch.programmiertier.tetris.view.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Patrick Buchter <patrick.buchter@stud.hslu.ch>, Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch
 */
public class Nirvana {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //so findis besser als inere eigene methode
        Field field = new Field(20, 10);        //Spielfeld erzeugen
        Controller control = new Controller(field); //Controller erzeugen

        //Listener gui = new ViewConsole(field);  //GUI erzeugen
        //field.addListener(gui);                 //GUI als Listener beim field anmelden

        initializeUI(field, control);
        control.start();
    }

    public static void initializeUI(final Field field, final Controller control) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {

                    ViewUI graphicUI = new ViewUI(field, 35);
                    JFrame frame = new JFrame();
                    frame.setTitle("IN_DECTRIS!");
                    //GUI in den Frame laden
                    frame.getContentPane().add(graphicUI);
                    frame.pack();
                    frame.setResizable(false);                 
                    //Frame sichtbar machen
                    frame.setVisible(true);
                    //beendet den Prozess nach dem schliessen des Fensters
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    //graphicUI als Listener beim Field-Model anmelden
                    field.addListener(graphicUI);
                    //graphicUI als KeyListener beim JFrame anmelden
                    frame.addKeyListener(graphicUI);
                    frame.addWindowListener(graphicUI);
                    //Controller beim UI als Listener anmelden
                    graphicUI.addListener(control);
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(Nirvana.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Nirvana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
