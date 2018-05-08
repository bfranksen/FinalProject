
package dataStructures.finalProject.menus;

import dataStructures.finalProject.utilities.Utils;
import dataStructures.finalProject.patient.Patient;
import dataStructures.finalProject.patient.PatientContactInfo;
import dataStructures.finalProject.utilities.BinarySearch;
import dataStructures.finalProject.utilities.ConsoleColors;
import dataStructures.finalProject.utilities.QuickSort;

import java.util.LinkedList;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.menus
 *     Created by Braden
 *     4/25/2018
 ****************************************/


public class ContactInfoMenu {

    private MainMenu mainMenu;
    private LinkedList<Patient> patientLinkedList;

    public ContactInfoMenu(MainMenu mainMenu, LinkedList<Patient> patientLinkedList) {
        this.mainMenu = mainMenu;
        this.patientLinkedList = patientLinkedList;
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
            System.out.println("\t2. Remove Patient");
            System.out.println("\t3. Update Patient");
            System.out.println("\t4. View Patient(s)");
            System.out.println("\t5. View Patient List");
            System.out.println("\t6. Back to Main Menu");
            System.out.println("\t7. Exit Program");
            response = Integer.parseInt(Utils.input.next());
            if (response < 1 || response > 7) {
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
                removePatient();
                break;
            case 3:
                updateContactInfo();
                break;
            case 4:
                viewPatient();
                break;
            case 5:
                viewPatientList();
                break;
            case 6:
                mainMenu.run();
                break;
            case 7:
                mainMenu.writePatientDataToFile();
                System.out.println("Goodbye!");
                System.exit(0);
                break;
        }
    }

    public Patient userAddPatient() {
        String firstName, lastName, phoneNumber, emailAddress, homeAddress;
        Utils.input.nextLine();
        System.out.print("First Name: ");
        firstName = Utils.input.nextLine();
        System.out.print("Last Name: ");
        lastName = Utils.input.nextLine();
        System.out.print("Phone Number: ");
        phoneNumber = Utils.input.nextLine();
        System.out.print("Email Address: ");
        emailAddress = Utils.input.nextLine();
        System.out.print("Home Address: ");
        homeAddress = Utils.input.nextLine();
        Patient p = addPatient(firstName, lastName);
        p.setPatientContactInfo(new PatientContactInfo(convertPhoneNumberToLong(phoneNumber), emailAddress, homeAddress));
        System.out.println(ConsoleColors.YELLOW + "\n\t" + p.getFirstName() + " " + p.getLastName() + " added\n\t" +
                "Patient ID: " + p.getId() + ConsoleColors.RESET);
        return p;
    }

    public Patient addPatient(String firstName, String lastName) {
        Patient p = new Patient(Utils.patIdPointer, firstName, lastName);
        patientLinkedList.add(p);
        Utils.patIdPointer++;
        return p;
    }

    private Patient removePatient() {
        int response;
        System.out.println(ConsoleColors.CYAN + "\nThis action will remove the patient from the entire database. Continue?" + ConsoleColors.RESET);
        System.out.println("\t1. Yes\n\t2. No");
        try {
            response = Integer.parseInt(Utils.input.next());
            if (response == 1) {
                // DO NOTHING! YAY
            } else if (response == 2) {
                System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made." + ConsoleColors.RESET);
                return null;
            } else {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                removePatient();
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
            removePatient();
            return null;
        }
        LinkedList<Integer> searchResults = searchForPatient();
        Patient[] removeCandidates = new Patient[searchResults.size()];
        Patient removedPatient;

        int tempPatientIndex;
        int chosenPatient;

        if (searchResults.size() == 0) {
            removedPatient = null;
        } else if (searchResults.size() == 1) {
            removedPatient = patientLinkedList.get(searchResults.get(0));
            if (Utils.doubleCheck(removedPatient, "remove")) {
                patientLinkedList.remove(removedPatient);
                mainMenu.getOrganTransplantMenu().getPatientOrganLinkedList().remove(removedPatient);
                mainMenu.getBloodDonorMenu().getPatientBloodLinkedList().remove(removedPatient);
                if (removedPatient != null)
                    System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + removedPatient.getFirstName() + " " + removedPatient.getLastName() + " has been removed from the database." + ConsoleColors.RESET);
            } else {
                removedPatient = null;
                System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
            }
        } else {
            while (true) {
                tempPatientIndex = 0;
                System.out.println(ConsoleColors.CYAN + "\nWhich patient would you like to remove?" + ConsoleColors.RESET);
                for (Integer i : searchResults) {
                    removeCandidates[tempPatientIndex] = patientLinkedList.get(i);
                    System.out.println("\t" + (tempPatientIndex + 1) + ". " + removeCandidates[tempPatientIndex]);
                    tempPatientIndex++;
                }
                try {
                    chosenPatient = Integer.parseInt(Utils.input.next());
                    if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        removedPatient = removeCandidates[chosenPatient - 1];
                        if (Utils.doubleCheck(removedPatient, "remove")) {
                            patientLinkedList.remove(removedPatient);
                            mainMenu.getOrganTransplantMenu().getPatientOrganLinkedList().remove(removedPatient);
                            mainMenu.getBloodDonorMenu().getPatientBloodLinkedList().remove(removedPatient);
                            if (removedPatient != null)
                                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + removedPatient.getFirstName() + " " + removedPatient.getLastName() + " has been removed from the database." + ConsoleColors.RESET);
                        } else {
                            removedPatient = null;
                            System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
                        }
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            }
        }
        return removedPatient;
    }

    private Patient updateContactInfo() {
        LinkedList<Integer> searchResults = searchForPatient();
        Patient updatedPatient = null;

        if (searchResults.size() == 0) {
            // Search method takes care of this
        } else if (searchResults.size() == 1) {
            updatedPatient = patientLinkedList.get(searchResults.get(0));
            while (true) {
                try {
                    if (Utils.doubleCheck(updatedPatient, "update")) {
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
            int response = Integer.parseInt(Utils.input.next());
            if (response < 1 || response > 4) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                updatePatient(patient);
            } else {
                System.out.println();
                switch (response) {
                    case 1:
                        Utils.input.nextLine();
                        updatePhoneNumber(patient);
                        break;
                    case 2:
                        Utils.input.nextLine();
                        updateEmailAddress(patient);
                        break;
                    case 3:
                        Utils.input.nextLine();
                        updateHomeAddress(patient);
                        break;
                    case 4:
                        Utils.input.nextLine();
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
        String inputNumber = Utils.input.nextLine();
        long phoneNumberInt = convertPhoneNumberToLong(inputNumber);
        patient.getPatientContactInfo().setPhoneNumber(phoneNumberInt);
    }

    private void updateEmailAddress(Patient patient) {
        System.out.print(patient.getFirstName() + " " + patient.getLastName() + "'s email address: \t");
        String emailAddress = Utils.input.nextLine();
        patient.getPatientContactInfo().setEmailAddress(emailAddress);
    }

    private void updateHomeAddress(Patient patient) {
        System.out.print(patient.getFirstName() + " " + patient.getLastName() + "'s home address: \t");
        String homeAddress = Utils.input.nextLine();
        patient.getPatientContactInfo().setHomeAddress(homeAddress);
    }

    private void viewPatient() {
        LinkedList<Integer> searchResults = searchForPatient();
        if (searchResults.size() == 0) {
            return;
        } else {
            System.out.println(ConsoleColors.CYAN + "\nFound Patient(s):" + ConsoleColors.RESET);
            for (Integer i : searchResults) {
                System.out.println("\t" + patientLinkedList.get(i) + patientLinkedList.get(i).getPatientContactInfo());
            }
        }
        return;
    }

    public LinkedList<Integer> searchForPatient() {
        LinkedList<Integer> searchResults = new LinkedList<>();
        LinkedList<Patient> tempPatientList = patientLinkedList;

        if (tempPatientList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            while (true) {
                System.out.println(ConsoleColors.CYAN + "\nSearch by:" + ConsoleColors.RESET);
                System.out.println("\t1. Patient ID");
                System.out.println("\t2. Last Name");
                System.out.println("\t3. Phone Number");
                try {
                    int response = Integer.parseInt(Utils.input.next());
                    if (response < 1 || response > 3) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        switch (response) {
                            case 1:
                                System.out.print("\nID of Patient: ");
                                int id = Integer.parseInt(Utils.input.next());
                                searchResults = BinarySearch.bSearchID(tempPatientList, id, 0, tempPatientList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this ID ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 2:
                                System.out.print("\nLast Name: ");
                                Utils.input.nextLine();
                                String lastName = Utils.input.nextLine();
                                searchResults = BinarySearch.bSearchLastName(tempPatientList, lastName, 0, tempPatientList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this last name ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 3:
                                System.out.print("\nPhone Number: ");
                                Utils.input.nextLine();
                                long phoneNumber = convertPhoneNumberToLong(Utils.input.nextLine());
                                searchResults = BinarySearch.bSearchPhoneNumber(tempPatientList, phoneNumber, 0, tempPatientList.size() - 1);
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
        if (patientLinkedList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            int response;
            System.out.println(ConsoleColors.CYAN + "\nHow would you like to sort the list?" + ConsoleColors.RESET);
            System.out.println("\t1. Patient ID");
            System.out.println("\t2. Last Name");
            System.out.println("\t3. Phone Number");
            try {
                response = Integer.parseInt(Utils.input.next());
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                viewPatientList();
                return;
            }
            if (response < 1 || response > 3) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                viewPatientList();
                return;
            } else {
                switch (response) {
                    case 1:
                        QuickSort.quickSortID(patientLinkedList, 0, patientLinkedList.size() - 1);
                        patientListTable(patientLinkedList);
                        break;
                    case 2:
                        QuickSort.quickSortLastName(patientLinkedList, 0, patientLinkedList.size() - 1);
                        patientListTable(patientLinkedList);
                        break;
                    case 3:
                        QuickSort.quickSortPhoneNumber(patientLinkedList, 0, patientLinkedList.size() - 1);
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
        if (phoneNumberStr.length() != 10) {
            return 0;
        }
        long phoneNumberInt = 0;
        for (int i = 0; i < 10; i++) {
            phoneNumberInt = phoneNumberInt + (long) (Math.pow(10, 9 - i) * Integer.parseInt(phoneNumberStr.substring(i, i + 1)));
        }
        return phoneNumberInt;
    }
}