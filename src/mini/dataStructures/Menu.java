package mini.dataStructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/********************************************************
 *     MiniProject
 *     mini.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class Menu {

    private static Scanner in = new Scanner(System.in);

    private LinkedList<Patient> patientLinkedList = new LinkedList<>();
    private PatientQuickSort patientQuickSort = new PatientQuickSort();
    private PatientBinarySearch patientBinarySearch = new PatientBinarySearch();

    private int id = 100;

    public Menu() {
    }

    public void run() throws FileNotFoundException {
        File patientFile = new File("C:\\Users\\Braden\\IdeaProjects\\MiniProject\\src\\Patient List");
        Scanner reader = new Scanner(patientFile);
        try {
            while (reader.hasNextLine()) {
                String firstName = reader.next();
                String lastName = reader.next();
                String organ = reader.next();
                int urgency = reader.nextInt();
                addPatient(firstName, lastName, organ, urgency);
                /*if (patientLinkedList.size() > 20) {
                    break;
                }*/
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (menuOptions()) {
            menuOptions();
        }
        in.close();
    }

    private boolean menuOptions() {
        while (true) {
            try {
                int response;
                System.out.println(ConsoleColors.CYAN + "\nWhat would you like to do?" + ConsoleColors.RESET);
                System.out.println("\t1. Add Patient");
                System.out.println("\t2. Remove Patient");
                System.out.println("\t3. Update Patient");
                System.out.println("\t4. View Patient(s)");
                System.out.println("\t5. View Patient List");
                System.out.println("\t6. Exit Program");
                response = Integer.parseInt(in.next());
                if (response < 1 || response > 6) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                } else {
                    runOption(response);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                continue;
            }
        }
        return true;
    }

    private void runOption(int response) {
        Patient modifiedPatient;
        switch (response) {
            case 1:
                modifiedPatient = userAddPatient();
                if (modifiedPatient != null)
                    System.out.println(ConsoleColors.YELLOW + "\n\t" + modifiedPatient.getFirstName() + " " + modifiedPatient.getLastName() + " added\n\t" +
                            "Patient ID: " + modifiedPatient.getId() + ConsoleColors.RESET);
                break;
            case 2:
                modifiedPatient = removePatient();
                if (modifiedPatient != null)
                    System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + modifiedPatient.getFirstName() + " " + modifiedPatient.getLastName() + " has been removed." + ConsoleColors.RESET);
                break;
            case 3:
                modifiedPatient = updatePatient();
                if (modifiedPatient != null)
                    System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + modifiedPatient.getFirstName() + " " + modifiedPatient.getLastName() + " has been updated." + ConsoleColors.RESET);
                break;
            case 4:
                viewPatient();
                break;
            case 5:
                viewPatientList();
                break;
            case 6:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
        }
    }

    private Patient userAddPatient() {
        String firstName, lastName, organ;
        int urgency;
        System.out.print("First Name: ");
        firstName = in.next();
        System.out.print("Last Name: ");
        lastName = in.next();
        System.out.print("Organ Needed: ");
        organ = in.next();
        while (true) {
            System.out.print("Urgency Level: ");
            try {
                urgency = Integer.parseInt(in.next());
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
        return addPatient(firstName, lastName, organ, urgency);
    }

    private Patient addPatient(String firstName, String lastName, String organ, int urgency) {
        Patient p = new Patient(id, firstName, lastName, organ, urgency);
        patientLinkedList.add(p);
        id++;
        return p;
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
            removedPatient = patientLinkedList.get(searchResults.get(0));
            if (doubleCheck(removedPatient, "remove")) {
                patientLinkedList.remove(removedPatient);
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
                    chosenPatient = Integer.parseInt(in.next());
                    if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        removedPatient = removeCandidates[chosenPatient - 1];
                        if (doubleCheck(removedPatient, "remove")) {
                            patientLinkedList.remove(removedPatient);
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
            updatedPatient = patientLinkedList.get(searchResults.get(0));
            while (true) {
                try {
                    if (doubleCheck(updatedPatient, "update")) {
                        System.out.println(ConsoleColors.CYAN + "\nWhat is this patient's new urgency level?" + ConsoleColors.RESET);
                        System.out.print("\tUrgency Level: ");
                        int urgency = Integer.parseInt(in.next());
                        while (urgency < 1 || urgency > 8) {
                            System.out.println(ConsoleColors.RED + "\n*** Invalid Urgency Level (1-8) ***" + ConsoleColors.RESET);
                            System.out.println(ConsoleColors.CYAN + "\nWhat is this patient's new urgency level?" + ConsoleColors.RESET);
                            System.out.print("\tUrgency Level: ");
                            urgency = Integer.parseInt(in.next());
                        }
                        updatedPatient.setUrgency(urgency);
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
                    updateCandidates[tempPatientIndex] = patientLinkedList.get(i);
                    System.out.println("\t" + (tempPatientIndex + 1) + ". " + updateCandidates[tempPatientIndex]);
                    tempPatientIndex++;
                }
                try {
                    chosenPatient = Integer.parseInt(in.next());
                    if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        while (true) {
                            try {
                                updatedPatient = updateCandidates[chosenPatient - 1];
                                if (doubleCheck(updatedPatient, "update")) {
                                    System.out.println(ConsoleColors.CYAN + "\nWhat is this patient's new urgency level?" + ConsoleColors.RESET);
                                    System.out.print("\tUrgency Level: ");
                                    int urgency = Integer.parseInt(in.next());
                                    while (urgency < 1 || urgency > 8) {
                                        System.out.println(ConsoleColors.RED + "\n*** Invalid Urgency Level (1-8) ***" + ConsoleColors.RESET);
                                        System.out.println(ConsoleColors.CYAN + "\nWhat is this patient's new urgency level?" + ConsoleColors.RESET);
                                        System.out.print("\tUrgency Level: ");
                                        urgency = Integer.parseInt(in.next());
                                    }
                                    updatedPatient.setUrgency(urgency);
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

    private boolean doubleCheck(Patient patient, String string) {
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

    private void viewPatient() {
        LinkedList<Integer> searchResults = searchForPatient();
        if (searchResults.size() == 0) {
            return;
        } else {
            System.out.println(ConsoleColors.CYAN + "\nFound Patient(s):" + ConsoleColors.RESET);
            for (Integer i : searchResults) {
                System.out.println("\t" + patientLinkedList.get(i));
            }
        }
    }

    private void viewPatientList() {
        if (patientLinkedList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            String response;
            int optionNumber;
            System.out.println(ConsoleColors.CYAN + "\nHow would you like to sort the list?" + ConsoleColors.RESET);
            System.out.println("\t1. Patient ID");
            System.out.println("\t2. Last Name");
            System.out.println("\t3. Organ Needed");
            System.out.println("\t4. Urgency Level");
            response = in.next();
            try {
                optionNumber = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                viewPatientList();
                return;
            }
            if (optionNumber < 1 || optionNumber > 4) {
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
                        patientQuickSort.quickSortOrgan(patientLinkedList, 0, patientLinkedList.size() - 1);
                        patientListTable(patientLinkedList);
                        break;
                    case 4:
                        patientQuickSort.quickSortUrgency(patientLinkedList, 0, patientLinkedList.size() - 1);
                        patientListTable(patientLinkedList);
                        break;
                }
            }
        }
    }

    private LinkedList<Integer> searchForPatient() {
        LinkedList<Integer> searchResults = new LinkedList<>();
        if (patientLinkedList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            while (true) {
                System.out.println(ConsoleColors.CYAN + "\nSearch by:" + ConsoleColors.RESET);
                System.out.println("\t1. Patient ID");
                System.out.println("\t2. Last Name");
                System.out.println("\t3. Organ Needed");
                System.out.println("\t4. Urgency Level");
                try {
                    int response = Integer.parseInt(in.next());
                    if (response < 1 || response > 4) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        switch (response) {
                            case 1:
                                int id;
                                System.out.print("\nID of Patient: ");
                                id = Integer.parseInt(in.next());
                                searchResults = patientBinarySearch.bSearchID(patientLinkedList, id, 0, patientLinkedList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this ID ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 2:
                                String lastName;
                                System.out.print("\nLast Name: ");
                                lastName = in.next();
                                searchResults = patientBinarySearch.bSearchLastName(patientLinkedList, lastName, 0, patientLinkedList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with this last name ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 3:
                                String organ;
                                System.out.print("\nOrgan Needed: ");
                                organ = in.next();
                                searchResults = patientBinarySearch.bSearchOrgan(patientLinkedList, organ, 0, patientLinkedList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no patients that need this organ ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 4:
                                int urgency;
                                while (true) {
                                    System.out.print("Urgency Level: ");
                                    try {
                                        urgency = Integer.parseInt(in.next());
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
                                searchResults = patientBinarySearch.bSearchUrgency(patientLinkedList, urgency, 0, patientLinkedList.size() - 1);
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
            System.out.format("%-5s%-16s%-18s%-16s%-7s", "| " + p.getId() + " |  ", p.getFirstName(), "|  " + p.getLastName(), " |  " + p.getOrgan(), " |    " + p.getUrgency() + "    |");
            System.out.println();
        }
        System.out.println("+---------------------------------------------------------------------+");
    }
}
