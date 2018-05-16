package dataStructures.finalProject.utilities;

import dataStructures.finalProject.patient.blood.PatientBloodNode;

import java.util.Scanner;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden
 *     5/4/2018
 ****************************************/

public class Utils {

    public static Scanner input = new Scanner(System.in);
    public static int empIdPointer;
    public static int patIdPointer;
    public static final PatientBloodNode[] bloodArray = new PatientBloodNode[8];

    public static boolean doubleCheck(Object object, String string) {
        int response;
        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nIs this the " + object.getClass().getSimpleName().toLowerCase() + " you would like to " + string + "?" + ConsoleColors.RESET);
            System.out.println("\t" + object);
            System.out.println("\t\t1. Yes\n\t\t2. No");
            try {
                response = Integer.parseInt(input.next());
                if (response == 1) {
                    return true;
                } else if (response == 2) {
                    return false;
                } else {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                continue;
            }
        }
    }

    public static String convertSpacesToDashes(String string) {
        String convertedString = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                convertedString = convertedString.substring(0, i) + '-';
            } else {
                convertedString = convertedString.substring(0, i) + string.charAt(i);
            }
        }
        return convertedString;
    }

    public static String convertDashesToSpaces(String string) {
        String convertedString = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '-') {
                convertedString = convertedString.substring(0, i) + ' ';
            } else {
                convertedString = convertedString.substring(0, i) + string.charAt(i);
            }
        }
        return convertedString;
    }
}