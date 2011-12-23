/**
 * @author: Patrick Buchter <patrick.buchter@stud.hslu.ch>, Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
package ch.programmiertier.tetris.model;

import java.util.Random;
import java.awt.Color;

public class Shape {

    //Relative Koordinaten zum Nullpunkt
    public int[][] shape = new int[4][2];
    //Laufnummer Shape
    private int type;
    private static int id = 0;
    //private static int type = 0;
    //Zufallsgenerator
    Random rnd = new Random();

    public Shape() {
        genStatic();
        //generateShape();
        id++;


    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public Color getColor(int id) {
        int i = id % 7;
        i++;
        switch (i) {
            case 1: return (new Color(32, 145, 0)); //grün
            case 2: return (new Color(189, 0, 0));//rot
            case 3: return (new Color(245, 184, 0));//Orange
            case 4: return (new Color(255, 51, 102));//Pink
            case 5: return (new Color(51, 102, 255));//hellblau
            case 6: return (new Color(150, 0, 102)); //violett
            case 7: return (new Color(0, 100, 102));//dunkelblau
            default: return (Color.BLACK); //schwarz
        }
    }

    /**
     * Gibt die Koordinaten eines Blocks zurück
     * @param row
     * @param slot
     * @return int
     */
    public int getShape(int row, int slot) {
        return shape[row][slot];
    }

    public void setShape(int row, int slot, int value) {
        shape[row][slot] = value;
    }

    public void setShape(int[][] newShape) {
        shape = newShape;
    }

    private void genStatic() {
        type = rnd.nextInt(7) + 1;
        // type 1 := T
        // type 2 := I
        // type 3 := Z
        // type 4 := S
        // type 5 := J
        // type 6 := L
        // type 7 := O
        switch (type) {

            case 1:
                shape[0][0] = 0;
                shape[0][1] = 0;
                shape[1][0] = 0;
                shape[1][1] = -1;
                shape[2][0] = 0;
                shape[2][1] = 1;
                shape[3][0] = 1;
                shape[3][1] = 0;

                break;

            case 2:
                shape[0][0] = 0;
                shape[0][1] = 0;
                shape[1][0] = 0;
                shape[1][1] = -1;
                shape[2][0] = 0;
                shape[2][1] = 1;
                shape[3][0] = 0;
                shape[3][1] = 2;

                break;

            case 3:
                shape[0][0] = 0;
                shape[0][1] = 0;         //   --  --  --
                shape[1][0] = 0;         //   --
                shape[1][1] = -1;
                shape[2][0] = 1;
                shape[2][1] = 0;
                shape[3][0] = 1;
                shape[3][1] = 1;

                break;

            case 4:
                shape[0][0] = 0;
                shape[0][1] = 0;
                shape[1][0] = 0;
                shape[1][1] = 1;
                shape[2][0] = 1;
                shape[2][1] = 0;
                shape[3][0] = 1;
                shape[3][1] = -1;

                break;

            case 5:
                shape[0][0] = 0;
                shape[0][1] = 0;
                shape[1][0] = 0;
                shape[1][1] = -1;
                shape[2][0] = 0;
                shape[2][1] = 1;
                shape[3][0] = 1;
                shape[3][1] = 1;
                //hellblau

                break;

            case 6:
                shape[0][0] = 0;
                shape[0][1] = 0;
                shape[1][0] = 0;
                shape[1][1] = -1;
                shape[2][0] = 1;
                shape[2][1] = -1;
                shape[3][0] = 0;
                shape[3][1] = 1;

                break;

            case 7:
                shape[0][0] = 0;
                shape[0][1] = 0;
                shape[1][0] = 1;
                shape[1][1] = 0;
                shape[2][0] = 0;
                shape[2][1] = 1;
                shape[3][0] = 1;
                shape[3][1] = 1;

                break;
        }
    }
}
