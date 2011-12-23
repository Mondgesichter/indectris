package ch.programmiertier.tetris.control;

import ch.programmiertier.tetris.model.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Steuert den Programmablauf.
 * @author Patrick Buchter <patrick.buchter@stud.hslu.ch>
 */
public class Controller extends Thread {

    private Field field;
    private ShapeHandler handler;
    private Deleter deleter;
    private List<Integer> numberOfLines;

    /**
     * Konstruktor.
     * @param field
     */
    public Controller(Field field) {
        this.field = field;
        deleter = new Deleter(this.field, this);
        handler = new ShapeHandler(this.field, this.deleter);
        numberOfLines = new ArrayList<Integer>();
    }

    public synchronized void setNumerOfLines(final List<Integer> lines) {
        this.numberOfLines = lines;
    }

    /**
     * Startet eine neue Runde Tetris.
     *
     */
    @Override
    public final synchronized void start() {
        handler.newShape();
        //testCase();
        while (!field.getGameOver()) {
            try {
                for (int i = 0; i * 10 < field.getSpeed(); i++) {
                    if (numberOfLines.size() > 0) {
                        blink(numberOfLines);
                        deleter.deLines(numberOfLines);
                        numberOfLines.clear();
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

    public final void blink(List<Integer> lines) {
        try {
            for (int a = 0; a < 6; a++) {
                for (int actuaLine = 0; actuaLine < lines.size(); actuaLine++) {
                    for (int j = 0; j < field.getSlots(); j++) {
                        if (field.getField(lines.get(actuaLine), j) >= 0) {
                            field.set(lines.get(actuaLine), j, -1);
                        } else {
                            field.set(lines.get(actuaLine), j, 0);
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
     * allowed Keywords: SPACE, LEFT, RIGHT, UP.
     * @param input pressed key
     */
    public final void input(String input) {
        //steuerung
        if (!field.isPaused()) {
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
        // Programm beenden
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
