package ch.programmiertier.tetris.control;

import ch.programmiertier.tetris.model.*;
import java.util.Random;

/**
 * Aufgabe: Shapes erzeugen und bewegen
 * @author Patrick Buchter <patrick.buchter@stud.hslu.ch>
 */
public class ShapeHandler {

    Field field;
    Deleter deleter;
    Shape shape;
    Random rnd;

    public ShapeHandler(Field field, Deleter deleter) {
        this.field = field;
        this.deleter = deleter;
        rnd = new Random();
    }

    /**
     * Erstellt neues Shape
     */
    public void newShape() {
        field.setActualRow(1);
        field.setActualSlot(4);
        field.increaseScore(deleter.check());
        
        if (field.getNextShape() == null) {
            shape = new Shape();
        } else {
            shape = field.getNextShape();
        }
        field.setShape(shape);
        field.setNextShape(new Shape());
        draw();
        if(checkCollision(0,3)) {field.setGameOver(true);}
        
   }

    public int getShapeId() {
        return shape.getId();
    }

    /**
     * prüft nur ob Shape von oben auf Widerstand stösst.
     * @return boolean
     */
    public boolean checkCollision(int actualRow, int actualSlot) {
        for (int i = 0; i < 4; i++) {
            // Shape stösst an Boden
            if (actualRow + shape.getShape(i, 0) > field.getRows() - 1) {
                return true;
            }
            // Shape stösst im nächsten Schritt weder auf Null noch auf shape.getId()
            if (!(field.getField(actualRow + shape.getShape(i, 0), actualSlot + shape.getShape(i, 1)) == 0 || field.getField(actualRow + shape.getShape(i, 0), actualSlot + shape.getShape(i, 1)) == shape.getId())) {
                return true;
            }
        }
        // Shape kollidiert nicht
        return false;
    }

    /**
     * prüft generell, ob nächster Schritt in Ordnung ist
     * @param temp
     * @return
     */
    public boolean notValid(int actualRow, int actualSlot) {
        for (int i = 0; i < 4; i++) {
            // Shape stösst an Rand
            if ((actualRow + shape.getShape(i, 0) < 0) || (actualSlot + shape.getShape(i, 1) < 0) || (actualRow + shape.getShape(i, 0) > field.getRows() - 1) || (actualSlot + shape.getShape(i, 1) > field.getSlots() - 1)) {
                return true;
            }
            // Shape kollidiert
            if (!(field.getField(actualRow + shape.getShape(i, 0), actualSlot + shape.getShape(i, 1)) == 0 || field.getField(actualRow + shape.getShape(i, 0), actualSlot + shape.getShape(i, 1)) == shape.getId())) {
                return true;
            }
        }
        // Shape kollidiert nicht
        return false;
    }

    /**
     * Zeichnet die neue Position von Shape ins Field
     */
    private void draw() {
        for (int i = 0; i < 4; i++) {
            field.set(field.getActualPos()[0] + shape.getShape(i, 0), field.getActualPos()[1] + shape.getShape(i, 1), shape.getId());
        }
    }

    /**
     * deletes the actual shape-position
     */
    private void reset() {
        for (int i = 0; i < 4; i++) {
            field.set(field.getActualPos()[0] + shape.getShape(i, 0), field.getActualPos()[1] + shape.getShape(i, 1), 0);
        }
    }

    /**
     * Shape rotiert um 45 Grad
     */
    public void rotate() {
        int temp[][] = new int[4][2];
        for (int i = 0; i < 4; i++) {
            temp[i][0] = shape.getShape(i, 0);
            temp[i][1] = shape.getShape(i, 1);
        }
        // type 1 := T
        // type 2 := I
        // type 3 := Z
        // type 4 := S
        // type 5 := J
        // type 6 := L
        // type 7 := O
        reset();
        switch (shape.getType()) {
            case 1:
            case 5:
            case 6:
                for (int i = 0; i < 4; i++) {
                    int tmp = shape.getShape(i, 1);
                    shape.setShape(i, 1, shape.getShape(i, 0));
                    shape.setShape(i, 0, tmp * (-1));
                }
                if (!notValid(field.getActualPos()[0], field.getActualPos()[1])) {
                } else {
                    for (int i = 0; i < 4; i++) {
                        shape.setShape(i, 0, temp[i][0]);
                        shape.setShape(i, 1, temp[i][1]);
                    }
                }
                break;
            case 2:
            case 3:
            case 4:
                for (int i = 0; i < 4; i++) {
                    int tmp = shape.getShape(i, 1);
                    shape.setShape(i, 1, shape.getShape(i, 0));
                    shape.setShape(i, 0, tmp * (-1));
                }
                if (!notValid(field.getActualPos()[0], field.getActualPos()[1])) {
                } else {
                    for (int i = 0; i < 4; i++) {
                        shape.setShape(i, 0, temp[i][0]);
                        shape.setShape(i, 1, temp[i][1]);
                    }
                }
                break;
            case 7:
                break;
        }
        draw();

    }
    public void moveDown() {
        if (!checkCollision(field.getActualPos()[0] + 1, field.getActualPos()[1])) {
            reset();
            field.setActualRow(field.getActualPos()[0] + 1);
        }
        else {newShape();}
        draw();

    }

    /**
     * Shape bewegt sich einen Slot nach links
     */
    public void moveLeft() {
        if (!notValid(field.getActualPos()[0], field.getActualPos()[1] - 1)) {
            reset();
            field.setActualSlot(field.getActualPos()[1] - 1);
            draw();
        }
    }

    /**
     * Shape bewegt sich einen Slot nach rechts
     */
    public void moveRight() {

        if (!notValid(field.getActualPos()[0], field.getActualPos()[1] + 1)) {
            reset();
            field.setActualSlot(field.getActualPos()[1] + 1);
            draw();
        }
    }

    /**
     * Shape fällt auf den Boden
     */
    public void drop() {
 
        while (!checkCollision(field.getActualPos()[0] + 1, field.getActualPos()[1])) {
                moveDown();
                draw();
        } 
        moveDown();
        draw();
    }
    
}
