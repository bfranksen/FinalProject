package dataStructures.finalProject.menus;

import dataStructures.finalProject.utilities.Utils;
import dataStructures.finalProject.patient.Patient;
import dataStructures.finalProject.patient.blood.PatientBloodInfo;
import dataStructures.finalProject.patient.blood.PatientBloodNode;
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

public class BloodDonorMenu {

    private MainMenu mainMenu;
    private BloodDonorAdvancedMenu bloodDonorAdvancedMenu;
    private LinkedList<Patient> patientBloodLinkedList;


    public BloodDonorMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.bloodDonorAdvancedMenu = new BloodDonorAdvancedMenu(mainMenu, this);
        this.patientBloodLinkedList = new LinkedList<>();
    }

    public void run() {
        while (menuOptions()) {
            menuOptions();
        }
    }

    private boolean menuOptions() {
        int response;
        try {
            System.out.print(ConsoleColors.YELLOW_BOLD + "\n\n Blood Donor List\n------------------" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "\nWhat would you like to do?" + ConsoleColors.RESET);
            System.out.println("\t1. Add Patient");
            System.out.println("\t2. Remove Patient");
            System.out.println("\t3. Update Patient");
            System.out.println("\t4. View Patient(s)");
            System.out.println("\t5. View Patient List");
            System.out.println("\t6. Advanced Options");
            System.out.println("\t7. Back to Main Menu");
            System.out.println("\t8. Exit Program");
            response = Integer.parseInt(Utils.input.next());
            if (response < 1 || response > 8) {
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
                bloodDonorAdvancedMenu.run();
                break;
            case 7:
                mainMenu.run();
                break;
            case 8:
                mainMenu.writePatientDataToFile();
                System.out.println("Goodbye!");
                System.exit(0);
                break;
        }
    }

    private Patient userAddPatient() {
        Patient newPatient;
        ContactInfoMenu contactInfoMenu = mainMenu.getContactInfoMenu();

        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nCreate new patient or add existing patient?" + ConsoleColors.RESET);
            System.out.println("\t1. Create new patient\n\t2. Add existing patient");
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
                    patientBloodLinkedList.add(newPatient);
                }
                return newPatient;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                continue;
            }
        }
    }

    private Patient addNewPatient(Patient newPatient) {
        String bloodType, preMsg;
        boolean eligible, willing;
        int response;

        preMsg = ConsoleColors.CYAN + "\n" + newPatient.getFirstName() + " " + newPatient.getLastName() + "'s Blood Type?" + ConsoleColors.RESET;
        bloodType = askForPatientBloodType(preMsg);

        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nIs " + newPatient.getFirstName() + " " + newPatient.getLastName() + " eligible to donate blood?" + ConsoleColors.RESET);
            System.out.println("\t1. Yes\n\t2. No");
            try {
                response = Integer.parseInt(Utils.input.next());
                if (response == 1) {
                    eligible = true;
                } else if (response == 2) {
                    eligible = false;
                } else {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***\n" + ConsoleColors.RESET);
                continue;
            }
        }
        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nIs " + newPatient.getFirstName() + " " + newPatient.getLastName() + " willing to donate blood?" + ConsoleColors.RESET);
            System.out.println("\t1. Yes\n\t2. No");
            try {
                response = Integer.parseInt(Utils.input.next());
                if (response == 1) {
                    willing = true;
                } else if (response == 2) {
                    willing = false;
                } else {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***\n" + ConsoleColors.RESET);
                continue;
            }
        }
        newPatient.setPatientBloodInfo(new PatientBloodInfo(bloodType, eligible, willing));
        System.out.println(ConsoleColors.YELLOW + "\n\t" + newPatient.getFirstName() + " " + newPatient.getLastName() + " added to the blood donor list.\n\t" +
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
            if (patientBloodLinkedList.contains(newPatient)) {
                System.out.println(ConsoleColors.RED + "\n*** " + newPatient.getFirstName() + " " + newPatient.getLastName() + " is already in the blood donor list. ***" + ConsoleColors.RESET);
                System.out.println("\n\t" + newPatient.toString() + "\t\t" + newPatient.getPatientBloodInfo().toString());
                return null;
            } else {
                while (true) {
                    try {
                        if (Utils.doubleCheck(newPatient, "add")) {
                            addNewPatient(newPatient);
                            return newPatient;
                        } else {
                            System.out.println(ConsoleColors.YELLOW + "\n\t" + newPatient.getFirstName() + " " + newPatient.getLastName() + "was not added to the blood donor list.");
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
                                if (patientBloodLinkedList.contains(newPatient)) {
                                    System.out.println(ConsoleColors.RED + "\n*** " + newPatient.getFirstName() + " " + newPatient.getLastName() + " is already in the blood donor list. ***" + ConsoleColors.RESET);
                                    System.out.println("\n\t" + newPatient.toString() + "\t\t" + newPatient.getPatientBloodInfo().toString());
                                    return null;
                                } else {
                                    addNewPatient(newPatient);
                                }
                                return newPatient;
                            } catch (NumberFormatException e) {
                                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                                continue;
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            }
        }
        if (newPatient != null)
            System.out.println(ConsoleColors.YELLOW + "\n\t" + newPatient.getFirstName() + " " + newPatient.getLastName() + " added to the blood donor list.\n\t" +
                    "Patient ID: " + newPatient.getId() + ConsoleColors.RESET);
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
            removedPatient = patientBloodLinkedList.get(searchResults.get(0));
            if (Utils.doubleCheck(removedPatient, "remove")) {
                patientBloodLinkedList.remove(removedPatient);
                if (removedPatient != null)
                    System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + removedPatient.getFirstName() + " " + removedPatient.getLastName() + " has been removed from the blood donor list." + ConsoleColors.RESET);
            } else {
                removedPatient = null;
                System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
            }
        } else {
            while (true) {
                tempPatientIndex = 0;
                System.out.println(ConsoleColors.CYAN + "\nWhich patient would you like to remove?" + ConsoleColors.RESET);
                for (Integer i : searchResults) {
                    removeCandidates[tempPatientIndex] = patientBloodLinkedList.get(i);
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
                            patientBloodLinkedList.remove(removedPatient);
                            if (removedPatient != null)
                                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + removedPatient.getFirstName() + " " + removedPatient.getLastName() + " has been removed from the blood donor list." + ConsoleColors.RESET);
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
        String eligible, willing;

        if (searchResults.size() == 0) {
            updatedPatient = null;
        } else if (searchResults.size() == 1) {
            updatedPatient = patientBloodLinkedList.get(searchResults.get(0));
            eligible = updatedPatient.getPatientBloodInfo().isEligibleDonor() ? "Yes" : "No";
            willing = updatedPatient.getPatientBloodInfo().isWillingDonor() ? "Yes" : "No";
            while (true) {
                updateChosenPatient(updatedPatient, eligible, willing);
                break;
            }
        } else {
            while (true) {
                tempPatientIndex = 0;
                System.out.println(ConsoleColors.CYAN + "\nWhich patient would you like to update?" + ConsoleColors.RESET);
                for (Integer i : searchResults) {
                    updateCandidates[tempPatientIndex] = patientBloodLinkedList.get(i);
                    System.out.println("\t" + (tempPatientIndex + 1) + ". " + updateCandidates[tempPatientIndex]);
                    tempPatientIndex++;
                }
                try {
                    chosenPatient = Integer.parseInt(Utils.input.next());
                    if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        updatedPatient = updateCandidates[chosenPatient - 1];
                        eligible = updatedPatient.getPatientBloodInfo().isEligibleDonor() ? "Yes" : "No";
                        willing = updatedPatient.getPatientBloodInfo().isWillingDonor() ? "Yes" : "No";
                        updateChosenPatient(updatedPatient, eligible, willing);
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

    private void updateChosenPatient(Patient updatedPatient, String eligible, String willing) {
        if (Utils.doubleCheck(updatedPatient, "update")) {
            while (true) {
                try {
                    System.out.print(ConsoleColors.CYAN + "\nWhat do you want to update?\n\t" + ConsoleColors.RESET);
                    System.out.print("1. Eligibility to donate blood " + ConsoleColors.YELLOW + "(Currently " + eligible + ")\n\t" + ConsoleColors.RESET);
                    System.out.print("2. Willingness to donate blood " + ConsoleColors.YELLOW + "(Currently " + willing + ")\n\t" + ConsoleColors.RESET);
                    System.out.print("3. Both\n");
                    int response = Integer.parseInt(Utils.input.next());
                    if (response < 1 || response > 3) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        updateEligibilityAndWillingness(updatedPatient, response);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
                break;
            }
        } else {
            System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
        }
    }

    private void updateEligibilityAndWillingness(Patient patient, int response) {
        Patient updatedPatient = patient;
        if (response == 1) {
            if (!updatedPatient.getPatientBloodInfo().isEligibleDonor()) {
                updatedPatient.getPatientBloodInfo().setEligibleDonor(true);
                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " is now eligible to donate blood." + ConsoleColors.RESET);
            } else if (updatedPatient.getPatientBloodInfo().isEligibleDonor()) {
                updatedPatient.getPatientBloodInfo().setEligibleDonor(false);
                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " is no longer eligible to donate blood." + ConsoleColors.RESET);
            }
        } else if (response == 2) {
            if (!updatedPatient.getPatientBloodInfo().isWillingDonor()) {
                updatedPatient.getPatientBloodInfo().setWillingDonor(true);
                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " is now willing to donate blood." + ConsoleColors.RESET);
            } else if (updatedPatient.getPatientBloodInfo().isWillingDonor()) {
                updatedPatient.getPatientBloodInfo().setWillingDonor(false);
                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " is no longer willing to donate blood." + ConsoleColors.RESET);
            }
        } else {
            boolean eligible = false, willing = false;
            if (!updatedPatient.getPatientBloodInfo().isEligibleDonor()) {
                updatedPatient.getPatientBloodInfo().setEligibleDonor(true);
                eligible = true;
            } else if (updatedPatient.getPatientBloodInfo().isEligibleDonor()) {
                updatedPatient.getPatientBloodInfo().setEligibleDonor(false);
                eligible = false;
            }

            if (!updatedPatient.getPatientBloodInfo().isWillingDonor()) {
                updatedPatient.getPatientBloodInfo().setWillingDonor(true);
                willing = true;
            } else if (updatedPatient.getPatientBloodInfo().isWillingDonor()) {
                updatedPatient.getPatientBloodInfo().setWillingDonor(false);
                willing = false;
            }

            if (eligible == true && willing == true) {
                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " is now eligible and willing to donate blood." + ConsoleColors.RESET);
            } else if (eligible == true && willing == false) {
                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " is now eligible, but no longer willing to donate blood." + ConsoleColors.RESET);
            } else if (eligible == false && willing == true) {
                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " is no longer eligible, but is now willing to donate blood." + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.YELLOW + "\n\tPatient " + updatedPatient.getFirstName() + " " + updatedPatient.getLastName() + " is no longer eligible or willing donate blood." + ConsoleColors.RESET);
            }
        }
    }

    protected void viewPatient() {
        LinkedList<Integer> searchResults = searchForPatient();
        if (searchResults.size() == 0) {
            return;
        } else {
            System.out.println(ConsoleColors.CYAN + "\nFound Patient(s):" + ConsoleColors.RESET);
            for (Integer i : searchResults) {
                System.out.println("\t" + patientBloodLinkedList.get(i) + patientBloodLinkedList.get(i).getPatientBloodInfo());
            }
        }
        return;
    }

    private void viewPatientList() {
        if (patientBloodLinkedList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            int response;
            System.out.println(ConsoleColors.CYAN + "\nHow would you like to sort the list?" + ConsoleColors.RESET);
            System.out.println("\t1. Patient ID");
            System.out.println("\t2. Last Name");
            System.out.println("\t3. Blood Type");
            System.out.println("\t4. Eligibility");
            System.out.println("\t5. Willingness");
            try {
                response = Integer.parseInt(Utils.input.next());
                if (response < 1 || response > 5) {
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
                    QuickSort.quickSortID(patientBloodLinkedList, 0, patientBloodLinkedList.size() - 1);
                    break;
                case 2:
                    QuickSort.quickSortLastName(patientBloodLinkedList, 0, patientBloodLinkedList.size() - 1);
                    break;
                case 3:
                    QuickSort.quickSortBloodType(patientBloodLinkedList, 0, patientBloodLinkedList.size() - 1);
                    break;
                case 4:
                    QuickSort.quickSortEligibleDonors(patientBloodLinkedList, 0, patientBloodLinkedList.size() - 1);
                    break;
                case 5:
                    QuickSort.quickSortWillingDonors(patientBloodLinkedList, 0, patientBloodLinkedList.size() - 1);
                    break;
            }
            patientListTable(patientBloodLinkedList);
        }
    }

    public LinkedList<Integer> searchForPatient() {
        LinkedList<Integer> searchResults = new LinkedList<>();
        String bloodType, preMsg;
        int response;

        if (patientBloodLinkedList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no patients! ***" + ConsoleColors.RESET);
        } else {
            while (true) {
                System.out.println(ConsoleColors.CYAN + "\nSearch by:" + ConsoleColors.RESET);
                System.out.println("\t1. Patient ID");
                System.out.println("\t2. Last Name");
                System.out.println("\t3. Blood Type");
                System.out.println("\t4. Eligible Donors");
                System.out.println("\t5. Willing Donors");
                try {
                    response = Integer.parseInt(Utils.input.next());
                    if (response < 1 || response > 5) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }

                switch (response) {
                    case 1:
                        System.out.print("\nID of Patient: ");
                        int id = Integer.parseInt(Utils.input.next());
                        searchResults = BinarySearch.bSearchID(patientBloodLinkedList, id, 0, patientBloodLinkedList.size() - 1);
                        if (searchResults.isEmpty()) {
                            System.out.println(ConsoleColors.RED + "\n*** There are no patients with this ID ***" + ConsoleColors.RESET);
                        }
                        break;
                    case 2:
                        Utils.input.nextLine();
                        System.out.print("\nLast Name: ");
                        String lastName = Utils.input.nextLine();
                        searchResults = BinarySearch.bSearchLastName(patientBloodLinkedList, lastName, 0, patientBloodLinkedList.size() - 1);
                        if (searchResults.isEmpty()) {
                            System.out.println(ConsoleColors.RED + "\n*** There are no patients with this last name ***" + ConsoleColors.RESET);
                        }
                        break;
                    case 3:
                        preMsg = ConsoleColors.CYAN + "\nWhich blood type?" + ConsoleColors.RESET;
                        bloodType = askForPatientBloodType(preMsg);
                        searchResults = BinarySearch.bSearchBloodType(patientBloodLinkedList, bloodType, 0, patientBloodLinkedList.size() - 1);
                        if (searchResults.isEmpty()) {
                            System.out.println(ConsoleColors.RED + "\n*** There are no patients with this blood type ***" + ConsoleColors.RESET);
                        }
                        break;
                    case 4:
                        while (true) {
                            System.out.println(ConsoleColors.CYAN + "\nSearch for:\n\t" + ConsoleColors.RESET +
                                    "1. All eligible donors\n\t2. Eligible donors for specific blood type\n");
                            try {
                                response = Integer.parseInt(Utils.input.next());
                                if (response == 1) {
                                    searchResults = BinarySearch.bSearchEligibleDonors(patientBloodLinkedList, true, 0, patientBloodLinkedList.size() - 1);
                                } else if (response == 2) {
                                    preMsg = ConsoleColors.CYAN + "\nWhich blood type?" + ConsoleColors.RESET;
                                    bloodType = askForPatientBloodType(preMsg);
                                    LinkedList<Integer> tempList = BinarySearch.bSearchBloodType(patientBloodLinkedList, bloodType, 0, patientBloodLinkedList.size() - 1);
                                    for (Integer i : tempList) {
                                        if (patientBloodLinkedList.get(i).getPatientBloodInfo().isEligibleDonor())
                                            searchResults.add(i);
                                    }
                                } else {
                                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                                    continue;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                                continue;
                            }
                            break;
                        }
                        if (searchResults.isEmpty()) {
                            System.out.println(ConsoleColors.RED + "\n*** There are no eligible donors ***" + ConsoleColors.RESET);
                        }
                        break;
                    case 5:
                        while (true) {
                            System.out.println(ConsoleColors.CYAN + "\nSearch for:\n\t" + ConsoleColors.RESET +
                                    "1. All willing donors\n\t2. Willing donors for specific blood type\n");
                            try {
                                response = Integer.parseInt(Utils.input.next());
                                if (response == 1) {
                                    searchResults = BinarySearch.bSearchWillingDonors(patientBloodLinkedList, true, 0, patientBloodLinkedList.size() - 1);
                                } else if (response == 2) {
                                    preMsg = ConsoleColors.CYAN + "\nWhich blood type?" + ConsoleColors.RESET;
                                    bloodType = askForPatientBloodType(preMsg);
                                    LinkedList<Integer> tempList = BinarySearch.bSearchBloodType(patientBloodLinkedList, bloodType, 0, patientBloodLinkedList.size() - 1);
                                    for (Integer i : tempList) {
                                        if (patientBloodLinkedList.get(i).getPatientBloodInfo().isWillingDonor())
                                            searchResults.add(i);
                                    }
                                } else {
                                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                                    continue;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                                continue;
                            }
                            break;
                        }
                        if (searchResults.isEmpty()) {
                            System.out.println(ConsoleColors.RED + "\n*** There are no eligible donors ***" + ConsoleColors.RESET);
                        }
                        break;
                }
                break;
            }
        }
        return searchResults;
    }

    private void patientListTable(LinkedList<Patient> list) {
        System.out.println("+-----------------------------------------------------------------------------+");
        System.out.format("%-5s%-18s%-18s%-10s%-10s%-10s",
                "| " + ConsoleColors.YELLOW_BOLD + "ID# " + ConsoleColors.RESET +
                        "|", "    " + ConsoleColors.YELLOW_BOLD + "First Name    ", ConsoleColors.RESET +
                        "|  " + ConsoleColors.YELLOW_BOLD + "  Last Name    ", ConsoleColors.RESET +
                        "|  " + ConsoleColors.YELLOW_BOLD + "Blood Type ", ConsoleColors.RESET +
                        "| " + ConsoleColors.YELLOW_BOLD + "Eligible ", ConsoleColors.RESET +
                        "| " + ConsoleColors.YELLOW_BOLD + "Willing " + ConsoleColors.RESET + "|\n");
        System.out.println("+-----------------------------------------------------------------------------+");
        for (Patient p : list) {
            String eligible = p.getPatientBloodInfo().isEligibleDonor() ? "Yes" : "No";
            String willing = p.getPatientBloodInfo().isWillingDonor() ? "Yes" : "No";
            System.out.format("%-5s%-16s%-18s%-10s%-10s%-10s", "| " + p.getId() + " |  ", p.getFirstName(), "|  " + p.getLastName(), "|\t " + p.getPatientBloodInfo().getBloodType(), " |   " + eligible, "  |\t" + willing + "\t  |");
            System.out.println();
        }
        System.out.println("+-----------------------------------------------------------------------------+");
    }

    protected String askForPatientBloodType(String message) {
        String bloodType = "";
        int response;

        while (true) {
            System.out.println(message);
            for (int i = 0; i < Utils.bloodArray.length; i++) {
                System.out.println("\t" + (i + 1) + ". " + Utils.bloodArray[i].getBloodType());
            }
            try {
                response = Integer.parseInt(Utils.input.next());
                if (response < 1 || response > 8) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***\n" + ConsoleColors.RESET);
                    continue;
                } else {
                    switch (response) {
                        case 1:
                            bloodType = Utils.bloodArray[0].getBloodType();
                            break;
                        case 2:
                            bloodType = Utils.bloodArray[1].getBloodType();
                            break;
                        case 3:
                            bloodType = Utils.bloodArray[2].getBloodType();
                            break;
                        case 4:
                            bloodType = Utils.bloodArray[3].getBloodType();
                            break;
                        case 5:
                            bloodType = Utils.bloodArray[4].getBloodType();
                            break;
                        case 6:
                            bloodType = Utils.bloodArray[5].getBloodType();
                            break;
                        case 7:
                            bloodType = Utils.bloodArray[6].getBloodType();
                            break;
                        case 8:
                            bloodType = Utils.bloodArray[7].getBloodType();
                            break;
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***\n" + ConsoleColors.RESET);
                continue;
            }
        }
        return bloodType;
    }

    public void setBloodTypes() {
        Utils.bloodArray[0] = new PatientBloodNode(4, 0, "AB+", 1, 2);
        Utils.bloodArray[1] = new PatientBloodNode(5, 0, "A+", 3);
        Utils.bloodArray[2] = new PatientBloodNode(6, 0, "B+", 3);
        Utils.bloodArray[3] = new PatientBloodNode(1, 0, "O+");
        Utils.bloodArray[4] = new PatientBloodNode(0, "AB-", 5, 6);
        Utils.bloodArray[5] = new PatientBloodNode(0, "A-", 7);
        Utils.bloodArray[6] = new PatientBloodNode(0, "B-", 7);
        Utils.bloodArray[7] = new PatientBloodNode(0, "O-");
    }

    public LinkedList<Patient> getPatientBloodLinkedList() {
        return patientBloodLinkedList;
    }
}
