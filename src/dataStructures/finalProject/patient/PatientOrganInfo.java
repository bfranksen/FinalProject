package dataStructures.finalProject.patient;

import dataStructures.finalProject.utilities.ConsoleColors;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden
 *     4/25/2018
 ****************************************/

public class PatientOrganInfo {

    private int urgency;
    private String organ;

    public PatientOrganInfo(String organ, int urgency) {
        this.organ = organ;
        this.urgency = urgency;
    }

    public String getOrgan() {
        return organ;
    }

    public int getUrgency() {
        return urgency;
    }

    public boolean setUrgency(int urgency) {
        if (urgency < 1 || urgency > 8) {
            System.out.println(ConsoleColors.RED + "\n*** Invalid Urgency Level (1-8) ***" + ConsoleColors.RESET);
            return false;
        } else {
            this.urgency = urgency;
            return true;
        }
    }

    @Override
    public String toString() {
        return "\n\t\tOrgan Needed:\t" + organ +
                "\n\t\tUrgency Level:\t" + urgency;
    }
}