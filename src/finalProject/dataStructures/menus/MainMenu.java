package finalProject.dataStructures.menus;

import finalProject.dataStructures.ConsoleColors;
import finalProject.dataStructures.patient.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/********************************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class MainMenu {

    protected static Scanner in = new Scanner(System.in);

    private LinkedList<Patient> patientLinkedList;
    private PatientQuickSort patientQuickSort;
    private PatientBinarySearch patientBinarySearch;

    private ContactInfoMenu contactInfoMenu;
    private OrganTransplantMenu organTransplantMenu;
    //private BloodTransfusionMenu bloodTransfusionMenu = new BloodTransfusionMenu(this);
    //private RoomSchedulerMenu roomSchedulerMenu = new RoomSchedulerMenu(this);

    public MainMenu() {
        patientLinkedList = new LinkedList<>();
        patientQuickSort = new PatientQuickSort();
        patientBinarySearch = new PatientBinarySearch();
        contactInfoMenu = new ContactInfoMenu(this);
        organTransplantMenu = new OrganTransplantMenu(this);
        initiatePrebuiltLists();
    }

    private void initiatePrebuiltLists() {
        File patientFile = new File("C:\\Users\\Braden\\IdeaProjects\\datastructures-finalproject\\src\\Patient List");
        Scanner reader = null;
        try {
            reader = new Scanner(patientFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (reader.hasNextLine()) {
                String next = reader.next();
                if (next.equalsIgnoreCase("contact")) {
                    reader.nextLine();
                } else if (next.equalsIgnoreCase("organ")) {
                    break;
                } else {
                    String firstName = next;
                    String lastName = reader.next();
                    long phone = reader.nextLong();
                    String email = reader.next();
                    Patient p = contactInfoMenu.addPatient(firstName, lastName);
                    p.setPatientContactInfo(new PatientContactInfo(phone, email, "123 Generic Street"));
                }
            }
            while (reader.hasNextLine()) {
                String next = reader.next();
                if (next.equalsIgnoreCase("organ")) {
                    reader.nextLine();
                } else if (next.equalsIgnoreCase("blood")) {
                    break;
                } else {
                    String firstName = next;
                    String lastName = reader.next();
                    String organ = reader.next();
                    int urgency = reader.nextInt();
                    int existingPatientIndex = patientBinarySearch.bSearchLastName(patientLinkedList, lastName, 0, patientLinkedList.size() - 1).get(0);
                    Patient existingPatient = patientLinkedList.get(existingPatientIndex);
                    if ((firstName + lastName).equalsIgnoreCase(existingPatient.getFirstName() + existingPatient.getLastName())) {
                        existingPatient.setPatientOrganInfo(new PatientOrganInfo(organ, urgency));
                        organTransplantMenu.getPatientOrganLinkedList().add(existingPatient);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (menuOptions()) {
            menuOptions();
        }
        in.close();
    }


    private boolean menuOptions() {
        try {
            int response;
            System.out.print(ConsoleColors.YELLOW_BOLD + "\n\n Main Menu\n-----------" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "\nWhat would you like to work on?" + ConsoleColors.RESET);
            System.out.println("\t1. Patient Info");
            System.out.println("\t2. Organ Transplant List");
            System.out.println("\t3. Blood Transfusion List");
            System.out.println("\t4. Room Scheduler");
            System.out.println("\t5. Exit Program");
            response = Integer.parseInt(in.next());
            if (response < 1 || response > 5) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
            } else {
                runOption(response);
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
        }
        return true;
    }

    private void runOption(int response) {
        switch (response) {
            case 1:
                contactInfoMenu.run();
                break;
            case 2:
                organTransplantMenu.run();
                break;
            /*case 3:
                bloodTransfusionMenu.run();
                break;
            case 4:
                roomSchedulerMenu.run();
                break;*/
            case 5:
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }

    public boolean doubleCheck(Patient patient, String string) {
        int response;
        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nIs this the patient you would like to " + string + "?" + ConsoleColors.RESET);
            System.out.println("\t" + patient);
            System.out.println("\t\t1. Yes\n\t\t2. No");
            try {
                response = Integer.parseInt(in.next());
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

    public LinkedList<Patient> getPatientLinkedList() {
        return patientLinkedList;
    }

    /*public PatientQuickSort getPatientQuickSort() {
        return patientQuickSort;
    }

    public PatientBinarySearch getPatientBinarySearch() {
        return patientBinarySearch;
    }*/

    public ContactInfoMenu getContactInfoMenu() {
        return contactInfoMenu;
    }
}
