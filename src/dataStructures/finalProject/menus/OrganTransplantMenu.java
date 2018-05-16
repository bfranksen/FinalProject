package dataStructures.finalProject.menus;

import dataStructures.finalProject.utilities.Utils;
import dataStructures.finalProject.utilities.ConsoleColors;
import dataStructures.finalProject.patient.Patient;
import dataStructures.finalProject.utilities.BinarySearch;
import dataStructures.finalProject.patient.PatientOrganInfo;
import dataStructures.finalProject.utilities.QuickSort;

import java.util.LinkedList;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.menus
 *     Created by Braden
 *     4/25/2018
 ****************************************/

public class OrganTransplantMenu {

    private MainMenu mainMenu;
    private LinkedList<Patient> patientOrganLinkedList;

    public OrganTransplantMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.patientOrganLinkedList = new LinkedList<>();
    }

    public void run() {
        while (menuOptions()) {
            menuOptions();
        }
    }

    private boolean menuOptions() {
        try {
            int response;
            System.out.print(ConsoleColors.YELLOW + "\n\n Organ Transplant List\n-----------------------" + ConsoleColors.RESET);
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
                updatePatient();
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

    private Patient userAddPatient() {
        ContactInfoMenu contactInfoMenu = mainMenu.getContactInfoMenu();
        Patient newPatient;

        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nCreate new patient or add existing patient?" + ConsoleColors.RESET);
            System.out.println("\t1. Create new patient\n\t2. Add existing patient\n");
            try {
                int response = Integer.parseInt(Utils.input.next());
                if (response == 1) {
                    newPatient = contactInfoMenu.userAddPatient();
                    addNewPatient(newPatient);
                } else if (response == 2) {
                    newPatient = addExistingPatient(contactInfoMenu);
                } else {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
                if (newPatient != null) {
                    patientOrganLinkedList.add(newPatient);
                }
                return newPatient;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                continue;
            }
        }
    }

    private Patient addNewPatient(Patient newPatient) {
        Utils.input.nextLine();
        System.out.print("\nOrgan Needed: ");
        String organ = Utils.input.nextLine();
        int urgency;
        while (true) {
            System.out.print("Urgency Level: ");
            try {
                urgency = Integer.parseInt(Utils.input.next());
                if (urgency < 1 || urgency > 8) {
                    System.out.println(ConsoleColors.RED + "\n*** Invalid Urgency Level (1-8) ***" + ConsoleColors.RESET);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                continue;
            }
        }
        newPatient.setPatientOrganInfo(new PatientOrganInfo(organ, urgency));
        if (newPatient != null)
            System.out.println(ConsoleColors.YELLOW + "\n\t" + newPatient.getFirstName() + " " + newPatient.getLastName() + " added to the organ transplant list.\n\t" +
                    "Patient ID: " + newPatient.getId() + ConsoleColors.RESET);
        return newPatient;
    }

    private Patient addExistingPatient(ContactInfoMenu contactInfoMenu) {
        LinkedList<Integer> searchResults = contactInfoMenu.searchForPatient();
        Patient[] patientCandidates = new Patient[searchResults.size()];
        Patient newPatient;
        int tempPatientIndex;
        int chosenPatient;

        if (searchResults.size() == 0) {
            newPatient = null;
        } else if (searchResults.size() == 1) {
            newPatient = mainMenu.getPatientLinkedList().get(searchResults.get(0));
            if (patientOrganLinkedList.contains(newPatient)) {
                System.out.println(ConsoleColors.RED + "\n*** " + newPatient.getFirstName() + " " + newPatient.getLastName() + " is already input the organ transplant list. ***" + ConsoleColors.RESET);
                System.out.println("\n\t" + newPatient.toString() + "\t\t" + newPatient.getPatientOrganInfo().toString());
                return null;
            } else {
                while (true) {
                    try {
                        if (Utils.doubleCheck(newPatient, "add")) {
                            addNewPatient(newPatient);
                        } else {
                            System.out.println(ConsoleColors.YELLOW + "\n\t" + newPatient.getFirstName() + " " + newPatient.getLastName() + "was not added to the organ transplant list.");
                            newPatient = null;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    }
                }
            }
        } else {
            while (true) {
                tempPatientIndex = 0;
                System.out.println(ConsoleColors.CYAN + "\nWhich patient would you like to add?" + ConsoleColors.RESET);
                for (Integer i : searchResults) {
                    patientCandidates[tempPatientIndex] = mainMenu.getPatientLinkedList().get(i);
                    System.out.println("\t" + (tempPatientIndex + 1) + ". " + patientCandidates[tempPatientIndex]);
                    tempPatientIndex++;
                }
                try {
                    chosenPatient = Integer.parseInt(Utils.input.next());
                    if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        while (true) {
                            try {
                                newPatient = patientCandidates[chosenPatient - 1];
                                if (patientOrganLinkedList.contains(newPatient)) {
                                    System.out.println(ConsoleColors.RED + "\n*** " + newPatient.getFirstName() + " " + newPatient.getLastName() + " is already input the organ transplant list. ***" + ConsoleColors.RESET);
                                    System.out.println("\n\t" + newPatient.toString() + "\t\t" + newPatient.getPatientOrganInfo().toString());
                                    return null;
                                } else {
                                    if (Utils.doubleCheck(newPatient, "add")) {
                                        addNewPatient(newPatient);
                                    } else {
                                        System.out.println(ConsoleColors.YELLOW + "\n\t" + newPatient.getFirstName() + " " + newPatient.getLastName() + "was not added to the organ transplant list.");
                                        newPatient = null;
                                    }
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                                continue;
                            }
                        }
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            }
        }
        return newPatient;
    }

    private Patient removePatient() {
        LinkedList<Integer> searchResults = searchForPatient();
        Patient[] removeCandidates = new Patient[searchResults.size()];
        Patient removedPatient;

        int tempPatientIndex;
        int chosenPatient;

        if (searchResults.size() == 0) {
            removedPatient = null;
        } else if (searchResults.size() == 1) {
            removedPatient = patientOrganLinkedList.get(searchResults.get(0));
            if (Utils.doubleCheck(removedPatient, "remove")) {
                patientOrganLinkedList.remove(removedPatient);
                if (removedPatient != null)
                    System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + removedPatient.getFirstName() + " " + removedPatient.getLastName() + " has been removed." + ConsoleColors.RESET);
            } else {
                removedPatient = null;
                System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
            }
        } else {
            while (true) {
                tempPatientIndex = 0;
                System.out.println(ConsoleColors.CYAN + "\nWhich patient would you like to remove?" + ConsoleColors.RESET);
                for (Integer i : searchResults) {
                    removeCandidates[tempPatientIndex] = patientOrganLinkedList.get(i);
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
                            patientOrganLinkedList.remove(removedPatient);
                            if (removedPatient != null)
                                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + removedPatient.getFirstName() + " " + removedPatient.getLastName() + " has been removed." + ConsoleColors.RESET);
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

    private Patient updatePatient() {
        LinkedList<Integer> searchResults = searchForPatient();
        Patient[] updateCandidates = new Patient[searchResults.size()];
        Patient updatedPatient;

        int tempPatientIndex;
        int chosenPatient;

        if (searchResults.size() == 0) {
            updatedPatient = null;
        } else if (searchResults.size() == 1) {
            updatedPatient = patientOrganLinkedList.get(searchResults.get(0));
            while (true) {
                try {
                    if (Utils.doubleCheck(updatedPatient, "update")) {
                        System.out.println(ConsoleColors.CYAN + "\nWhat is " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + "'s new urgency level?" + ConsoleColors.RESET);
                        System.out.print("\tUrgency Level: ");
                        int urgency = Integer.parseInt(Utils.input.next());
                        while (urgency < 1 || urgency > 8) {
                            System.out.println(ConsoleColors.RED + "\n*** Invalid Urgency Level (1-8) ***" + ConsoleColors.RESET);
                            System.out.println(ConsoleColors.CYAN + "\nWhat is " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + "'s new urgency level?" + ConsoleColors.RESET);
                            System.out.print("\tUrgency Level: ");
                            urgency = Integer.parseInt(Utils.input.next());
                        }
                        updatedPatient.getPatientOrganInfo().setUrgency(urgency);
                        System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " has been updated." + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
                        updatedPatient = null;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            }
        } else {
            while (true) {
                tempPatientIndex = 0;
                System.out.println(ConsoleColors.CYAN + "\nWhich patient would you like to update?" + ConsoleColors.RESET);
                for (Integer i : searchResults) {
                    updateCandidates[tempPatientIndex] = patientOrganLinkedList.get(i);
                    System.out.println("\t" + (tempPatientIndex + 1) + ". " + updateCandidates[tempPatientIndex]);
                    tempPatientIndex++;
                }
                try {
                    chosenPatient = Integer.parseInt(Utils.input.next());
                    if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        while (true) {
                            try {
                                updatedPatient = updateCandidates[chosenPatient - 1];
                                if (Utils.doubleCheck(updatedPatient, "update")) {
                                    System.out.println(ConsoleColors.CYAN + "\nWhat is " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + "'s new urgency level?" + ConsoleColors.RESET);
                                    System.out.print("\tUrgency Level: ");
                                    int urgency = Integer.parseInt(Utils.input.next());
                                    while (urgency < 1 || urgency > 8) {
                                        System.out.println(ConsoleColors.RED + "\n*** Invalid Urgency Level (1-8) ***" + ConsoleColors.RESET);
                                        System.out.println(ConsoleColors.CYAN + "\nWhat is " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + "'s new urgency level?" + ConsoleColors.RESET);
                                        System.out.print("\tUrgency Level: ");
                                        urgency = Integer.parseInt(Utils.input.next());
                                    }
                                    updatedPatient.getPatientOrganInfo().setUrgency(urgency);
                                    System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " has been updated." + ConsoleColors.RESET);
                                } else {
                                    System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
                                    updatedPatient = null;
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                                continue;
                            }
                        }
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

    private void viewPatient() {
        LinkedList<Integer> searchResults = searchForPatient();
        if (searchResults.size() == 0) {
            return;
        } else {
            System.out.println(ConsoleColors.CYAN + "\nFound Patient(s):" + ConsoleColors.RESET);
            for (Integer i : searchResults) {
                System.out.println("\t" + patientOrganLinkedList.get(i) + patientOrganLinkedList.get(i).getPatientOrganInfo());
            }
        }
        return;
    }

    private void viewPatientList() {
        if (patientOrganLinkedList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            int response;
            System.out.println(ConsoleColors.CYAN + "\nHow would you like to sort the list?" + ConsoleColors.RESET);
            System.out.println("\t1. Patient ID");
            System.out.println("\t2. Last Name");
            System.out.println("\t3. Organ Needed");
            System.out.println("\t4. Urgency Level");
            try {
                response = Integer.parseInt(Utils.input.next());
                if (response < 1 || response > 4) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                    viewPatientList();
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                viewPatientList();
                return;
            }
            switch (response) {
                case 1:
                    QuickSort.quickSortID(patientOrganLinkedList, 0, patientOrganLinkedList.size() - 1);
                    patientListTable(patientOrganLinkedList);
                    break;
                case 2:
                    QuickSort.quickSortLastName(patientOrganLinkedList, 0, patientOrganLinkedList.size() - 1);
                    patientListTable(patientOrganLinkedList);
                    break;
                case 3:
                    QuickSort.quickSortOrgan(patientOrganLinkedList, 0, patientOrganLinkedList.size() - 1);
                    patientListTable(patientOrganLinkedList);
                    break;
                case 4:
                    QuickSort.quickSortUrgency(patientOrganLinkedList, 0, patientOrganLinkedList.size() - 1);
                    patientListTable(patientOrganLinkedList);
                    break;
            }
        }
    }

    private LinkedList<Integer> searchForPatient() {
        LinkedList<Integer> searchResults = new LinkedList<>();
        if (patientOrganLinkedList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            while (true) {
                System.out.println(ConsoleColors.CYAN + "\nSearch by:" + ConsoleColors.RESET);
                System.out.println("\t1. Patient ID");
                System.out.println("\t2. Last Name");
                System.out.println("\t3. Organ Needed");
                System.out.println("\t4. Urgency Level");
                try {
                    int response = Integer.parseInt(Utils.input.next());
                    if (response < 1 || response > 4) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        switch (response) {
                            case 1:
                                System.out.print("\nID of Patient: ");
                                int id = Integer.parseInt(Utils.input.next());
                                searchResults = BinarySearch.bSearchID(patientOrganLinkedList, id, 0, patientOrganLinkedList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this ID ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 2:
                                Utils.input.nextLine();
                                System.out.print("\nLast Name: ");
                                String lastName = Utils.input.nextLine();
                                searchResults = BinarySearch.bSearchLastName(patientOrganLinkedList, lastName, 0, patientOrganLinkedList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this last name ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 3:
                                Utils.input.nextLine();
                                System.out.print("\nOrgan Needed: ");
                                String organ = Utils.input.nextLine();
                                searchResults = BinarySearch.bSearchOrgan(patientOrganLinkedList, organ, 0, patientOrganLinkedList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients that need this organ ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 4:
                                int urgency;
                                while (true) {
                                    System.out.print("Urgency Level: ");
                                    try {
                                        urgency = Integer.parseInt(Utils.input.next());
                                        if (urgency < 1 || urgency > 8) {
                                            System.out.println(ConsoleColors.RED + "\n*** Invalid Urgency Level (1-8) ***" + ConsoleColors.RESET);
                                            continue;
                                        }
                                        break;
                                    } catch (NumberFormatException e) {
                                        System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                                        continue;
                                    }
                                }
                                searchResults = BinarySearch.bSearchUrgency(patientOrganLinkedList, urgency, 0, patientOrganLinkedList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this urgency level ***" + ConsoleColors.RESET);
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

    private void patientListTable(LinkedList<Patient> list) {
        System.out.println("+---------------------------------------------------------------------+");
        System.out.format("%-5s%-18s%-19s%-16s%9s",
                "| " + ConsoleColors.YELLOW_BOLD + "ID# " + ConsoleColors.RESET +
                        "|", "    " + ConsoleColors.YELLOW_BOLD + "First Name    ", ConsoleColors.RESET +
                        "|  " + ConsoleColors.YELLOW_BOLD + "  Last Name     ", ConsoleColors.RESET +
                        "|     " + ConsoleColors.YELLOW_BOLD + "Organ     ", ConsoleColors.RESET + "| " +
                        ConsoleColors.YELLOW_BOLD + "Urgency " + ConsoleColors.RESET + "|\n");
        System.out.println("+---------------------------------------------------------------------+");
        for (Patient p : list) {
            System.out.format("%-5s%-16s%-18s%-16s%-7s", "| " + p.getId() + " |  ", p.getFirstName(), "|  " + p.getLastName(), " |  " + p.getPatientOrganInfo().getOrgan(), " |    " + p.getPatientOrganInfo().getUrgency() + "    |");
            System.out.println();
        }
        System.out.println("+---------------------------------------------------------------------+");
    }

    public LinkedList<Patient> getPatientOrganLinkedList() {
        return patientOrganLinkedList;
    }
}