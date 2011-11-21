package ch.programmiertier.tetris.control;

import ch.programmiertier.tetris.model.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Steuert den Programmablauf
 * @author Patrick Buchter <patrick.buchter@stud.hslu.ch>
 */
public class Controller extends Thread
{

    private Field field;
    private ShapeHandler handler;
    private Deleter deleter;
    private ArrayList<Integer> NoL;
//

    /**
     * Konstruktor
     * @param field
     */
    public Controller(Field field)
    {
        this.field = field;
        deleter = new Deleter(this.field, this);
        handler = new ShapeHandler(this.field, this.deleter);
        NoL = new ArrayList<Integer>();



    }

    public void setNoL(ArrayList<Integer> NoL) {
         this.NoL = NoL;
    }

    /**
     * Startet eine neue Runde Tetris
     *
     */
    @Override
    public synchronized void start()
    {
        handler.newShape();
        //testCase();
        while (!field.getGameOver()) {
            try {
                for (int i = 0; i * 10 < field.getSpeed(); i++) {
                    if (NoL.size() > 0) {
                        blink(NoL);
                        deleter.deLines(NoL);
                        NoL.clear();
                        continue;
                    } else {
                        wait(10);
                    }       
                }
                while (field.isPaused()) {
                    wait(10);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            handler.moveDown();
            if (field.getScore() >= field.getLevel() * 1000) {
                field.setLevel(field.getLevel() + 1);
            }
        }
    }

        public void blink(ArrayList<Integer> NoL) {

        try {
            for (int a = 0; a < 6; a ++) {
                for (int actuaLine = 0; actuaLine < NoL.size(); actuaLine ++) {
                    for (int j = 0; j < field.getSlots(); j ++) {
                        if(field.getField(NoL.get(actuaLine), j)>=0) {
                            field.set(NoL.get(actuaLine), j, -1);
                        } else {
                            field.set(NoL.get(actuaLine), j, 0);
                        }

                    }
                }
                sleep(50);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Deleter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * M�gliche Keywords: SPACE, LEFT, RIGHT, UP
     * @param input
     */
    public void input(String input)
    {
        //steuerung
        if ( ! field.isPaused()) {
            if (input.equals("SPACE")) {
                handler.drop();
            }
            if (input.equals("UP")) {
                handler.rotate();
            }
            if (input.equals("LEFT")) {
                handler.moveLeft();
            }
            if (input.equals("RIGHT")) {
                handler.moveRight();
            }
            if (input.equals("DOWN")) {
                handler.moveDown();
            }

        }

        //Programm beenden
        if (input.equals("CLOSE") || input.equals("Q")) {
            System.exit(0);
        }

        //Pause ein/aus
        if (input.equals("P")) {
            if (field.isPaused()) {
                field.setPaused(false);
            } else {
                field.setPaused(true);
            }
        }
    }
}
