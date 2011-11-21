package ch.programmiertier.tetris.control;

import ch.programmiertier.tetris.model.*;
import java.util.ArrayList;

/**
 * Aufgaben: Volle Reihen erkennen und löschen
 * @author Patrick Buchter <patrick.buchter@stud.hslu.ch>
 */
public class Deleter
{

    private Field field;
    private Controller control;

    public Deleter(Field field, Controller control)
    {
        this.field = field;
        this.control = control;
    }

    /**
     * Returns the Value
     * @return
     */
    public int check()
    {
        boolean full = false;
        ArrayList<Integer> NoL = new ArrayList<Integer>();
        int score = 0;
        for (int i = 0; i < field.getRows(); i ++) {
            full = false;
            for (int j = 0; j < field.getSlots(); j ++) {
                if (field.getField(i, j) == 0) {
                    full = false;
                    break;
                }
                full = true;
            }
            if (full == true) {
                NoL.add(i);
            }
        }
        if(NoL.size()>0) {

//            blink(NoL);
            
            //deLines(NoL);
            control.setNoL(NoL);
            score += NoL.size() * 50 + field.getLevel() * 5;
//            field.setPaused(false);
        }
        return score;
    }

    public void deLines(ArrayList<Integer> NoL)
    {
        int actuaLine;
        // Array lines durchiterieren
        //field ist ein a*j Feld
        
        for (actuaLine = 0; actuaLine < NoL.size(); actuaLine ++) {
            for (int i = 0; i < 4; i++) {
                    field.set(field.getActualPos()[0] + field.getShape().getShape(i, 0), field.getActualPos()[1] + field.getShape().getShape(i, 1), 0);

             }
            // pro Anzahl Lines, die verschoben werden müssen
            for (int a = NoL.get(actuaLine); a > 0; a --) {
                
                for (int j = 0; j < field.getSlots(); j ++) {
                    field.set(a, j, field.getField(a - 1, j));
                }
             
            }
            
            for (int i = 0; i < 4; i++) {
                   
                    field.set(field.getActualPos()[0] + field.getShape().getShape(i, 0), field.getActualPos()[1] + field.getShape().getShape(i, 1), field.getShape().getId());
            }
            for (int j = 0; j < field.getSlots(); j ++) {
                field.set(0, j, 0);
            }

        }
    }
   
//    public void blink(ArrayList<Integer> NoL) {
//
//        try {
//            for (int a = 0; a < 6; a ++) {
//                for (int actuaLine = 0; actuaLine < NoL.size(); actuaLine ++) {
//                    for (int j = 0; j < field.getSlots(); j ++) {
//                        if(field.getField(NoL.get(actuaLine), j)>=0) {
//                            field.set(NoL.get(actuaLine), j, -1);
//                            System.out.println("-1");
//                        } else {
//                            field.set(NoL.get(actuaLine), j, 0);
//                            System.out.println("0");
//                        }
//
//                    }
//                }
//                sleep(100);
//            }
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Deleter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
