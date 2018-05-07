package dataStructures.finalProject.patient.blood;

import dataStructures.finalProject.patient.Patient;
import dataStructures.finalProject.utilities.BinarySearch;
import dataStructures.finalProject.utilities.ConsoleColors;
import dataStructures.finalProject.utilities.QuickSort;

import java.util.LinkedList;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.patient.blood
 *     Created by Braden
 *     5/4/2018
 ****************************************/

public class PatientBloodSupplyLevels {

    public static void bloodSupplyLevels(PatientBloodNode[] blood, LinkedList<Patient> list) {

        LinkedList<Patient> sortedList = QuickSort.quickSortBloodType(list, 0, list.size() - 1);
        System.out.println(ConsoleColors.YELLOW + "\n Current Blood Supply Levels\n-----------------------------");

        LinkedList<Integer> ABP = BinarySearch.bSearchBloodType(sortedList, blood[0].getBloodType(), 0, list.size() - 1);
        if (blood[0].getBloodUnitAmount() / (ABP.size() + 1) < 3) {
            System.out.println(criticalMessage(blood[0].getBloodType()));
        } else if (blood[0].getBloodUnitAmount() / (ABP.size() + 1) < 7) {
            System.out.println(lowMessage(blood[0].getBloodType()));
        } else {
            System.out.println(normalMessage(blood[0].getBloodType()));
        }


        LinkedList<Integer> AP = BinarySearch.bSearchBloodType(sortedList, blood[1].getBloodType(), 0, list.size() - 1);
        if (blood[1].getBloodUnitAmount() / (AP.size() + 1) < 3) {
            System.out.println(criticalMessage(blood[1].getBloodType()));
        } else if (blood[1].getBloodUnitAmount() / (AP.size() + 1) < 7) {
            System.out.println(lowMessage(blood[1].getBloodType()));
        } else {
            System.out.println(normalMessage(blood[1].getBloodType()));
        }


        LinkedList<Integer> BP = BinarySearch.bSearchBloodType(sortedList, blood[2].getBloodType(), 0, list.size() - 1);
        if (blood[2].getBloodUnitAmount() / (BP.size() + 1) < 3) {
            System.out.println(criticalMessage(blood[2].getBloodType()));
        } else if (blood[2].getBloodUnitAmount() / (BP.size() + 1) < 7) {
            System.out.println(lowMessage(blood[2].getBloodType()));
        } else {
            System.out.println(normalMessage(blood[2].getBloodType()));
        }


        LinkedList<Integer> OP = BinarySearch.bSearchBloodType(sortedList, blood[3].getBloodType(), 0, list.size() - 1);
        if (blood[3].getBloodUnitAmount() / (OP.size() + 1) < 3) {
            System.out.println(criticalMessage(blood[3].getBloodType()));
        } else if (blood[3].getBloodUnitAmount() / (OP.size() + 1) < 7) {
            System.out.println(lowMessage(blood[3].getBloodType()));
        } else {
            System.out.println(normalMessage(blood[3].getBloodType()));
        }


        LinkedList<Integer> ABM = BinarySearch.bSearchBloodType(sortedList, blood[4].getBloodType(), 0, list.size() - 1);
        if (blood[4].getBloodUnitAmount() / (ABM.size() + 1) < 3) {
            System.out.println(criticalMessage(blood[4].getBloodType()));
        } else if (blood[4].getBloodUnitAmount() / (ABM.size() + 1) < 7) {
            System.out.println(lowMessage(blood[4].getBloodType()));
        } else {
            System.out.println(normalMessage(blood[4].getBloodType()));
        }


        LinkedList<Integer> AM = BinarySearch.bSearchBloodType(sortedList, blood[5].getBloodType(), 0, list.size() - 1);
        if (blood[5].getBloodUnitAmount() / (AM.size() + 1) < 3) {
            System.out.println(criticalMessage(blood[5].getBloodType()));
        } else if (blood[5].getBloodUnitAmount() / (AM.size() + 1) < 7) {
            System.out.println(lowMessage(blood[5].getBloodType()));
        } else {
            System.out.println(normalMessage(blood[5].getBloodType()));
        }


        LinkedList<Integer> BM = BinarySearch.bSearchBloodType(sortedList, blood[6].getBloodType(), 0, list.size() - 1);
        if (blood[6].getBloodUnitAmount() / (BM.size() + 1) < 3) {
            System.out.println(criticalMessage(blood[6].getBloodType()));
        } else if (blood[6].getBloodUnitAmount() / (BM.size() + 1) < 7) {
            System.out.println(lowMessage(blood[6].getBloodType()));
        } else {
            System.out.println(normalMessage(blood[6].getBloodType()));
        }


        LinkedList<Integer> ON = BinarySearch.bSearchBloodType(sortedList, blood[7].getBloodType(), 0, list.size() - 1);
        if (blood[7].getBloodUnitAmount() / (ON.size() + 1) < 3) {
            System.out.println(criticalMessage(blood[7].getBloodType()));
        } else if (blood[7].getBloodUnitAmount() / (ON.size() + 1) < 7) {
            System.out.println(lowMessage(blood[7].getBloodType()));
        } else {
            System.out.println(normalMessage(blood[7].getBloodType()));
        }
    }

    private static String criticalMessage(String bloodType) {
        if (bloodType.equalsIgnoreCase("A") || bloodType.equalsIgnoreCase("B") || bloodType.equalsIgnoreCase("O")) {
            return ConsoleColors.CYAN + "\tBlood levels for " + bloodType + ConsoleColors.RESET + ":\t\t| " +
                    ConsoleColors.RED + "CRITICAL" + ConsoleColors.RESET +
                    " |\t Arrange for a transfer immediately and call all eligible donors.";
        } else {
            return ConsoleColors.CYAN + "\tBlood levels for " + bloodType + ConsoleColors.RESET + ":\t| " +
                    ConsoleColors.RED + "CRITICAL" + ConsoleColors.RESET +
                    " |\t Arrange for a transfer immediately and call all eligible donors.";
        }
    }

    private static String lowMessage(String bloodType) {
        if (bloodType.equalsIgnoreCase("A") || bloodType.equalsIgnoreCase("B") || bloodType.equalsIgnoreCase("O")) {
            return ConsoleColors.CYAN + "\tBlood levels for " + bloodType + ConsoleColors.RESET + ":\t\t| " +
                    ConsoleColors.YELLOW + "  LOW" + ConsoleColors.RESET +
                    "    |\t Arrange for a transfer if possible and call all eligible and willing donors.";
        } else {
            return ConsoleColors.CYAN + "\tBlood levels for " + bloodType + ConsoleColors.RESET + ":\t| " +
                    ConsoleColors.YELLOW + "  LOW" + ConsoleColors.RESET +
                    "    |\t Arrange for a transfer if possible and call all eligible and willing donors.";
        }
    }

    private static String normalMessage(String bloodType) {
        if (bloodType.equalsIgnoreCase("A") || bloodType.equalsIgnoreCase("B") || bloodType.equalsIgnoreCase("O")) {
            return ConsoleColors.CYAN + "\tBlood levels for " + bloodType + ConsoleColors.RESET + ":\t\t| " +
                    ConsoleColors.GREEN + " NORMAL" + ConsoleColors.RESET +
                    "  |\t No immediate action necessary.";
        } else {
            return ConsoleColors.CYAN + "\tBlood levels for " + bloodType + ConsoleColors.RESET + ":\t| " +
                    ConsoleColors.GREEN + " NORMAL" + ConsoleColors.RESET +
                    "  |\t No immediate action necessary.";
        }
    }
}
