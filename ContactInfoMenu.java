
package finalProject.dataStructures.menus;

import finalProject.dataStructures.ConsoleColors;
import finalProject.dataStructures.patient.Patient;
import finalProject.dataStructures.patient.PatientBinarySearch;
import finalProject.dataStructures.patient.PatientContactInfo;
import finalProject.dataStructures.patient.PatientQuickSort;

import java.util.LinkedList;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.menus
 *     Created by Braden
 *     4/25/2018
 ****************************************/


public class ContactInfoMenu {

    private MainMenu mainMenu;
    private PatientQuickSort patientQuickSort;
    private PatientBinarySearch patientBinarySearch;

    private int id = 100;

    public ContactInfoMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.patientQuickSort = new PatientQuickSort();
        this.patientBinarySearch = new PatientBinarySearch();
    }

    public void run() {
        while (menuOptions()) {
            menuOptions();
        }
    }

    private boolean menuOptions() {
        try {
            int response;
            System.out.print(ConsoleColors.YELLOW_BOLD + "\n\n Patient Info\n--------------" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "\nWhat would you like to do?" + ConsoleColors.RESET);
            System.out.println("\t1. Add Patient");
            System.out.println("\t2. Update Contact Info");
            System.out.println("\t3. View Patient(s)");
            System.out.println("\t4. View Patient List");
            System.out.println("\t5. Back to Main Menu");
            System.out.println("\t6. Exit Program");
            response = Integer.parseInt(MainMenu.in.next());
            if (response < 1 || response > 6) {
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
                userAddPatient();
                break;
            case 2:
                updateContactInfo();
                break;
            case 3:
                viewPatient();
                break;
            case 4:
                viewPatientList();
                break;
            case 5:
                mainMenu.run();
                break;
            case 6:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
        }
    }

    public Patient userAddPatient() {
        String firstName, lastName, phoneNumber, emailAddress, homeAddress;
        System.out.print("First Name: ");
        firstName = MainMenu.in.next();
        System.out.print("Last Name: ");
        lastName = MainMenu.in.next();
        MainMenu.in.nextLine();
        System.out.print("Phone Number: ");
        phoneNumber = MainMenu.in.nextLine();
        System.out.print("Email Address: ");
        emailAddress = MainMenu.in.nextLine();
        System.out.print("Home Address: ");
        homeAddress = MainMenu.in.nextLine();
        Patient p = addPatient(firstName, lastName);
        p.setPatientContactInfo(new PatientContactInfo(convertPhoneNumberToLong(phoneNumber), emailAddress, homeAddress));
        if (p != null)
            System.out.println(ConsoleColors.YELLOW + "\n\t" + p.getFirstName() + " " + p.getLastName() + " added\n\t" +
                    "Patient ID: " + p.getId() + ConsoleColors.RESET);
        return p;
    }

    public Patient addPatient(String firstName, String lastName) {
        Patient p = new Patient(id, firstName, lastName);
        mainMenu.getPatientLinkedList().add(p);
        id++;
        return p;
    }

    private Patient updateContactInfo() {
        LinkedList<Integer> searchResults = searchForPatient();
        Patient updatedPatient = null;

        if (searchResults.size() == 0) {
            // Search method takes care of this
        } else if (searchResults.size() == 1) {
            updatedPatient = mainMenu.getPatientLinkedList().get(searchResults.get(0));
            while (true) {
                try {
                    if (mainMenu.doubleCheck(updatedPatient, "update")) {
                        updatedPatient = updatePatient(updatedPatient);
                        System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " has been updated." + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            }
        }
        return updatedPatient;
    }

    private Patient updatePatient(Patient patient) {
        System.out.println(ConsoleColors.CYAN + "\nWhat do you want to update?" + ConsoleColors.RESET);
        System.out.println("\t1. Phone Number\n\t2. Email Address\n\t3. Home Address\n\t4. All");
        try {
            int response = Integer.parseInt(mainMenu.in.next());
            if (response < 1 || response > 4) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                updatePatient(patient);
            } else {
                System.out.println();
                switch (response) {
                    case 1:
                        updatePhoneNumber(patient);
                        break;
                    case 2:
                        updateEmailAddress(patient);
                        break;
                    case 3:
                        updateHomeAddress(patient);
                        break;
                    case 4:
                        updatePhoneNumber(patient);
                        updateEmailAddress(patient);
                        updateHomeAddress(patient);
                        break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
            updatePatient(patient);
        }
        return patient;
    }

    private void updatePhoneNumber(Patient patient) {
        System.out.print(patient.getFirstName() + " " + patient.getLastName() + "'s phone number: \t");
        mainMenu.in.nextLine();
        String inputNumber = mainMenu.in.nextLine();
        long phoneNumberInt = convertPhoneNumberToLong(inputNumber);
        patient.getPatientContactInfo().setPhoneNumber(phoneNumberInt);
    }

    private void updateEmailAddress(Patient patient) {
        System.out.print(patient.getFirstName() + " " + patient.getLastName() + "'s email address: \t");
        String emailAddress = mainMenu.in.next();
        patient.getPatientContactInfo().setEmailAddress(emailAddress);
    }

    private void updateHomeAddress(Patient patient) {
        System.out.print(patient.getFirstName() + " " + patient.getLastName() + "'s home address: \t");
        mainMenu.in.nextLine();
        String homeAddress = mainMenu.in.nextLine();
        patient.getPatientContactInfo().setHomeAddress(homeAddress);
    }

    private void viewPatient() {
        LinkedList<Integer> searchResults = searchForPatient();
        if (searchResults.size() == 0) {
            return;
        } else {
            System.out.println(ConsoleColors.CYAN + "\nFound Patient:" + ConsoleColors.RESET);
            System.out.println("\t" + mainMenu.getPatientLinkedList().get(searchResults.get(0)) + mainMenu.getPatientLinkedList().get(searchResults.get(0)).getPatientContactInfo());
        }
    }

    public LinkedList<Integer> searchForPatient() {
        LinkedList<Integer> searchResults = new LinkedList<>();
        LinkedList<Patient> tempPatientList = mainMenu.getPatientLinkedList();

        if (tempPatientList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            while (true) {
                System.out.println(ConsoleColors.CYAN + "\nSearch by:" + ConsoleColors.RESET);
                System.out.println("\t1. Patient ID");
                System.out.println("\t2. Last Name");
                System.out.println("\t3. Phone Number");
                try {
                    int response = Integer.parseInt(mainMenu.in.next());
                    if (response < 1 || response > 3) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        switch (response) {
                            case 1:
                                System.out.print("\nID of Patient: ");
                                int id = Integer.parseInt(mainMenu.in.next());
                                searchResults = patientBinarySearch.bSearchID(tempPatientList, id, 0, tempPatientList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this ID ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 2:
                                System.out.print("\nLast Name: ");
                                String lastName = mainMenu.in.next();
                                searchResults = patientBinarySearch.bSearchLastName(tempPatientList, lastName, 0, tempPatientList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this last name ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 3:
                                System.out.print("\nPhone Number: ");
                                mainMenu.in.nextLine();
                                long phoneNumber = convertPhoneNumberToLong(mainMenu.in.nextLine());
                                searchResults = patientBinarySearch.bSearchPhoneNumber(tempPatientList, phoneNumber, 0, tempPatientList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this phone number ***" + ConsoleColors.RESET);
                                }
                                break;
                        }
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            }
        }
        return searchResults;
    }

    private void viewPatientList() {
        LinkedList<Patient> patientLinkedList = mainMenu.getPatientLinkedList();
        if (patientLinkedList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            String response;
            int optionNumber;
            System.out.println(ConsoleColors.CYAN + "\nHow would you like to sort the list?" + ConsoleColors.RESET);
            System.out.println("\t1. Patient ID");
            System.out.println("\t2. Last Name");
            System.out.println("\t3. Phone Number");
            response = mainMenu.in.next();
            try {
                optionNumber = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                viewPatientList();
                return;
            }
            if (optionNumber < 1 || optionNumber > 3) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                viewPatientList();
                return;
            } else {
                switch (optionNumber) {
                    case 1:
                        patientQuickSort.quickSortID(patientLinkedList, 0, patientLinkedList.size() - 1);
                        patientListTable(patientLinkedList);
                        break;
                    case 2:
                        patientQuickSort.quickSortLastName(patientLinkedList, 0, patientLinkedList.size() - 1);
                        patientListTable(patientLinkedList);
                        break;
                    case 3:
                        patientQuickSort.quickSortPhoneNumber(patientLinkedList, 0, patientLinkedList.size() - 1);
                        patientListTable(patientLinkedList);
                        break;
                }
            }
        }
    }

    private void patientListTable(LinkedList<Patient> list) {
        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
        System.out.format("%-5s%-16s%-16s%-18s%-28s%-28s",
                "| " + ConsoleColors.YELLOW_BOLD + "ID# " + ConsoleColors.RESET +
                        "|", "   " + ConsoleColors.YELLOW_BOLD + "First Name   ", ConsoleColors.RESET +
                        "|    " + ConsoleColors.YELLOW_BOLD + "Last Name    ", ConsoleColors.RESET +
                        "|   " + ConsoleColors.YELLOW_BOLD + "Phone Number   ", ConsoleColors.RESET +
                        "|      " + ConsoleColors.YELLOW_BOLD + " Email Address      ", ConsoleColors.RESET +
                        "|      " + ConsoleColors.YELLOW_BOLD + "Home Address      " + ConsoleColors.RESET + "|\n");
        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
        for (Patient p : list) {
            String phoneNumberStr = String.valueOf(p.getPatientContactInfo().getPhoneNumber());
            System.out.format("%-5s%-16s%-18s%-16s%-28s%-28s",
                    "| " + p.getId(),
                    " |   " + p.getFirstName(),
                    "  |   " + p.getLastName(),
                    "  |  (" + phoneNumberStr.substring(0, 3) + ") " + phoneNumberStr.substring(3, 6) + "-" + phoneNumberStr.substring(6, 10),
                    "  |   " + p.getPatientContactInfo().getEmailAddress(),
                    " |   " + p.getPatientContactInfo().getHomeAddress() + "   |");
            System.out.println();
        }
        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
    }

    private long convertPhoneNumberToLong(String inputNumber) {
        String phoneNumberStr = "";
        for (int i = 0; i < inputNumber.length(); i++) {
            if (Character.isDigit(inputNumber.charAt(i))) {
                phoneNumberStr = phoneNumberStr + inputNumber.charAt(i);
            }
        }
        long phoneNumberInt = 0;
        for (int i = 0; i < 10; i++) {
            phoneNumberInt = phoneNumberInt + (long) (Math.pow(10, 9 - i) * Integer.parseInt(phoneNumberStr.substring(i, i+1)));
        }
        return phoneNumberInt;
    }
}