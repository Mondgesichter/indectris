package ch.programmiertier.tetris.model;

import java.util.ArrayList;
import ch.programmiertier.tetris.view.*;

/**
 * Aufgabe: Speichert den Zustand des Felds
 * @author Patrick Buchter <patrick.buchter@stud.hslu.ch>, Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public class Field {

    private int[][] field;  //xy-Koordinaten des Spielfelds
    private int rows;   //Anzahl Reihen
    private int slots;  //anzahl Spalten
    private int score;  //Spielstand
    private int level;  //Schwierigkeitsgrad
    private int actualRow;
    private int actualSlot;
    private Shape shape;
    private Shape nextShape;
    private boolean gameOver;
    private boolean paused;

    //Listener, die über änderungen am Field benachrichtigt werden sollen
    private ArrayList<Listener> listeners = new ArrayList<Listener>();

    /**
     * Konstruktor, initialisiert das Field
     * @param rows Anzahl Reihen
     * @param slots Anzahl Spalten
     */
    public Field(int rows, int slots) {
        gameOver = false;
        this.rows = rows;
        this.slots = slots;
        field = new int[rows][slots];
        level = 1;
        paused = true;
        reset();
    }

    /**
     * Setzt alle Felder des Spielfelds auf Null
     */
    private void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < slots; j++) {
                field[i][j] = 0;
            }
        }
        updateListeners();
    }

    /**
     * setzt einen Wert an den entsprechenden Koordinaten
     * @param row
     * @param slot
     * @param value
     */
    public void set(int row, int slot, int value) {
        field[row][slot] = value;
        updateListeners();
    }

    /**
     * erhöht den spielstand um den angegeben wert und passt ggf. die geschwindigkeit an
     * @param amount
     */
    public void increaseScore(int amount) {
        score += amount;
        updateListeners();
    }

    public void setLevel(int level) {
       this.level = level;
       updateListeners();

    }

    public long getSpeed() {
        long speed = 1000 - (getLevel()*100);
        return speed;
    }

    /**
     * gibt das Feld an der Position i,j aus
     * @param i Row
     * @param j Slot
     * @return Eintrag an diesen Koordinaten
     */
    public int getField(int i, int j) {return field[i][j];}
    public Shape getNextShape() {return nextShape;}
    public Shape getShape() {return shape;}
    public int getLevel() { return level; }
    public int getActualRow() {return actualRow;}
    public int getActualSlot() {return actualSlot;}

    public int[] getActualPos() {int[] pos = {actualRow, actualSlot}; return pos;}
    public void setActualRow(int r) {actualRow = r;}
    public void setActualSlot(int s) {actualSlot = s;}
    public void setGameOver(boolean go) {gameOver=go;updateListeners();}
    public boolean getGameOver() {return gameOver;}
    public boolean isPaused() {return paused;}

    public void setPaused(boolean status) {
        paused = status;
        updateListeners();
    }

    /**
     * Liefert die Anzahl Reihen des Spielfeldes
     * erleichtert das iterieren durch das Field
     * @return Anzahl Reihen des Spielfelds als Int
     */
    public int getRows() {return rows;}

    /**
     * gibt den momentanen spielstand zurück
     * @return
     */
    public int getScore() {return score;}

    /**
     * Liefert die Anzahl Spalten des Spielfeldes
     * erleichtert das iterieren durch das Field
     * @return Anzahl Spalten des Spielfelds als Int
     */
    public int getSlots() {return slots;}

    public void setNextShape(Shape nextShape) {
        this.nextShape = nextShape;
        updateListeners();
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        updateListeners();
    }

    /**
     * Benachrichtigt alle registrierten Listener über Änderungen im Model
     */
    private void updateListeners() {
        for (Listener listener : listeners) {
            listener.changed();
        }
    }

    /**
     * Fügt einen Listener hinzu
     * @param listener
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * Löscht einen Listener
     * @param listener
     */
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}
