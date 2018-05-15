package dataStructures.finalProject.menus;

import dataStructures.finalProject.patient.Patient;
import dataStructures.finalProject.patient.blood.PatientBloodSearch;
import dataStructures.finalProject.patient.blood.PatientBloodSupplyLevels;
import dataStructures.finalProject.utilities.BinarySearch;
import dataStructures.finalProject.utilities.ConsoleColors;
import dataStructures.finalProject.utilities.Utils;

import java.util.InputMismatchException;
import java.util.LinkedList;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.menus
 *     Created by Braden
 *     5/6/2018
 ****************************************/

public class BloodDonorAdvancedMenu {

    private MainMenu mainMenu;
    private BloodDonorMenu bloodDonorMenu;
    PatientBloodSearch patientBloodSearch;

    public BloodDonorAdvancedMenu(MainMenu mainMenu, BloodDonorMenu bloodDonorMenu) {
        this.mainMenu = mainMenu;
        this.bloodDonorMenu = bloodDonorMenu;
        this.patientBloodSearch = new PatientBloodSearch();
    }

    public void run() {
        while (menuOptions()) {
            menuOptions();
        }
    }

    private boolean menuOptions() {
        int response;
        try {
            System.out.print(ConsoleColors.YELLOW + "\n\n Blood Donor Advanced Options\n------------------------------" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "\nWhat would you like to do?" + ConsoleColors.RESET);
            System.out.println("\t1. Checkout Blood For Patient");
            System.out.println("\t2. Arrange for Transfer");
            System.out.println("\t3. Check Supply Levels");
            System.out.println("\t4. View Patient(s)");
            System.out.println("\t5. Back to Blood Donor Menu");
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
                checkoutBlood();
                break;
            case 2:
                arrangeTransfer();
                break;
            case 3:
                checkSupplyLevels();
                break;
            case 4:
                bloodDonorMenu.viewPatient();
                break;
            case 5:
                bloodDonorMenu.run();
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

    private void checkoutBlood() {
        int response;

        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nSearch by patient or blood type?\n\t" + ConsoleColors.RESET + "1. Patient\n\t2. Blood Type");
            try {
                response = Integer.parseInt(Utils.input.next());
                if (response == 1) {
                    Utils.input.nextLine();
                    checkoutByPatient();
                } else if (response == 2) {
                    Utils.input.nextLine();
                    checkoutByBloodType();
                } else {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                continue;
            }
            break;
        }
    }

    private void checkoutByPatient() {
        int[] bloodWithdrawal = new int[8];
        LinkedList<Integer> searchResults;
        Patient bloodRecipient;
        String response;

        while (true) {
            System.out.print(ConsoleColors.CYAN + "\nEnter the patient's ID or last name\n\t" + ConsoleColors.RESET);
            try {
                response = Utils.input.nextLine();
                if ((Character.isDigit(response.charAt(0)) && response.length() < 3) || (Character.isDigit(response.charAt(0)) && Character.isDigit(response.charAt(1)) && response.length() < 3)) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                } else if (Character.isDigit(response.charAt(0)) && Character.isDigit(response.charAt(1)) && Character.isDigit(response.charAt(2)) && Integer.parseInt(response) > 99 && Integer.parseInt(response) < 1000) {
                    try {
                        searchResults = BinarySearch.bSearchID(bloodDonorMenu.getPatientBloodLinkedList(), Integer.parseInt(response), 0, bloodDonorMenu.getPatientBloodLinkedList().size() - 1);
                        bloodRecipient = bloodDonorMenu.getPatientBloodLinkedList().get(searchResults.get(0));
                        doBloodCheckout(bloodRecipient, bloodWithdrawal, patientBloodSearch);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(ConsoleColors.RED + "\n*** There are no patients with that ID ***" + ConsoleColors.RESET);
                        continue;
                    }
                } else if (!Character.isDigit(response.charAt(0)) && response.length() > 1) {
                    searchResults = BinarySearch.bSearchLastName(bloodDonorMenu.getPatientBloodLinkedList(), response, 0, bloodDonorMenu.getPatientBloodLinkedList().size() - 1);
                    if (searchResults.size() == 0) {
                        System.out.println(ConsoleColors.RED + "\n*** There are no patients with that last name ***" + ConsoleColors.RESET);
                        continue;
                    } else if (searchResults.size() == 1) {
                        bloodRecipient = bloodDonorMenu.getPatientBloodLinkedList().get(searchResults.get(0));
                        doBloodCheckout(bloodRecipient, bloodWithdrawal, patientBloodSearch);
                    } else {
                        Patient[] patientCandidates = new Patient[searchResults.size()];
                        int tempPatientIndex, chosenPatient;

                        while (true) {
                            tempPatientIndex = 0;
                            System.out.println(ConsoleColors.CYAN + "\nWhich patient would you like to get blood for?" + ConsoleColors.RESET);
                            for (Integer i : searchResults) {
                                patientCandidates[tempPatientIndex] = bloodDonorMenu.getPatientBloodLinkedList().get(i);
                                System.out.println("\t" + (tempPatientIndex + 1) + ". " + patientCandidates[tempPatientIndex]);
                                tempPatientIndex++;
                            }
                            try {
                                chosenPatient = Integer.parseInt(Utils.input.next());
                                if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                                    System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                                    continue;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                                continue;
                            }
                            doBloodCheckout(patientCandidates[chosenPatient - 1], bloodWithdrawal, patientBloodSearch);
                            break;
                        }
                    }
                } else {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                continue;
            }
        }
    }

    private void checkoutByBloodType() {
        int[] bloodWithdrawal = new int[8];
        LinkedList<Integer> searchResults;
        Patient bloodRecipient;
        String bloodType, response;

        bloodType = bloodDonorMenu.askForPatientBloodType(ConsoleColors.CYAN + "\nWhich blood type do you need?" + ConsoleColors.RESET);
        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nIs this blood for a specific patient?" + ConsoleColors.RESET + "\n\t1. Yes\n\t2. No\n");
            Utils.input.nextLine();
            response = Utils.input.nextLine();
            if (response.equalsIgnoreCase("1")) {
                searchResults = BinarySearch.bSearchBloodType(bloodDonorMenu.getPatientBloodLinkedList(), bloodType, 0, bloodDonorMenu.getPatientBloodLinkedList().size() - 1);
                if (searchResults.size() == 0) {
                    System.out.println(ConsoleColors.RED + "\n*** There are no patients with that last name ***" + ConsoleColors.RESET);
                } else if (searchResults.size() == 1) {
                    bloodRecipient = bloodDonorMenu.getPatientBloodLinkedList().get(searchResults.get(0));
                    doBloodCheckout(bloodRecipient, bloodWithdrawal, patientBloodSearch);
                } else {
                    Patient[] patientCandidates = new Patient[searchResults.size()];
                    int tempPatientIndex, chosenPatient;

                    while (true) {
                        tempPatientIndex = 0;
                        System.out.println(ConsoleColors.CYAN + "\nWhich patient would you like to get blood for?" + ConsoleColors.RESET);
                        for (Integer i : searchResults) {
                            patientCandidates[tempPatientIndex] = bloodDonorMenu.getPatientBloodLinkedList().get(i);
                            System.out.println("\t" + (tempPatientIndex + 1) + ". " + patientCandidates[tempPatientIndex]);
                            tempPatientIndex++;
                        }
                        try {
                            chosenPatient = Integer.parseInt(Utils.input.next());
                            if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                            continue;
                        }
                        bloodRecipient = patientCandidates[chosenPatient - 1];
                        doBloodCheckout(bloodRecipient, bloodWithdrawal, patientBloodSearch);
                        break;
                    }
                }
            } else if (response.equalsIgnoreCase("2")) {
                int amount;
                while (true) {
                    System.out.print("\n" + ConsoleColors.CYAN + "Blood type requested: " + ConsoleColors.YELLOW + bloodType +
                            ConsoleColors.RESET + "\n\tUnits of " + ConsoleColors.YELLOW + bloodType + ConsoleColors.RESET + " needed: ");
                    try {
                        amount = Utils.input.nextInt();
                        if (amount <= 0) {
                            System.out.println(ConsoleColors.RED + "\n*** Must enter a value greater than 0 ***" + ConsoleColors.RESET);
                            continue;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    }
                    break;
                }
                bloodWithdrawal = patientBloodSearch.bloodSearch(Utils.bloodArray, getBloodIndex(bloodType), amount, bloodWithdrawal, bloodDonorMenu.getPatientBloodLinkedList());
                for (int i = 0; i < 8; i++) {
                    Utils.bloodArray[i].setBloodUnitAmount(Utils.bloodArray[i].getBloodUnitAmount() - bloodWithdrawal[i]);
                }
                int dCount = 0;
                for (int i = 0; i < bloodWithdrawal.length; i++) {
                    if (bloodWithdrawal[i] > 0) {
                        dCount++;
                        break;
                    }
                }
                if (dCount > 0) {
                    System.out.println("\n" + ConsoleColors.CYAN + "Blood Checkout Summary" + ConsoleColors.RESET);
                } else {
                    System.out.println("\n" + ConsoleColors.RED + "*** There is no blood available at this time. Arrange for transfer immediately. ***" + ConsoleColors.RESET);
                }
                for (int i = 0; i < bloodWithdrawal.length; i++) {
                    if (bloodWithdrawal[i] > 0) {
                        if (Utils.bloodArray[i].getBloodType().equalsIgnoreCase("AB+") || Utils.bloodArray[i].getBloodType().equalsIgnoreCase("AB-")) {
                            System.out.print("\t" + Utils.bloodArray[i].getBloodType() + ": -" + bloodWithdrawal[i] + " units (" + Utils.bloodArray[i].getBloodUnitAmount() + " remaining)\n");
                        } else {
                            System.out.print("\t" + Utils.bloodArray[i].getBloodType() + ":  -" + bloodWithdrawal[i] + " units (" + Utils.bloodArray[i].getBloodUnitAmount() + " remaining)\n");
                        }
                    }
                }
            } else {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                continue;
            }
            break;
        }
    }

    private void doBloodCheckout(Patient bloodRecipient, int[] bloodWithdrawal, PatientBloodSearch patientBloodSearch) {
        int response;
        if (Utils.doubleCheck(bloodRecipient, "get blood for")) {
            while (true) {
                System.out.print("\n" + ConsoleColors.CYAN + bloodRecipient.getFirstName() + " " + bloodRecipient.getLastName() + "'s" + " blood type: " + ConsoleColors.YELLOW + bloodRecipient.getPatientBloodInfo().getBloodType() +
                        ConsoleColors.RESET + "\n\tUnits of " + ConsoleColors.YELLOW + bloodRecipient.getPatientBloodInfo().getBloodType() + ConsoleColors.RESET + " needed: ");
                try {
                    Utils.input.nextLine();
                    response = Utils.input.nextInt();
                    if (response <= 0) {
                        System.out.println(ConsoleColors.RED + "\n*** Must enter a value greater than 0 ***" + ConsoleColors.RESET);
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
                break;
            }
            bloodWithdrawal = patientBloodSearch.bloodSearch(Utils.bloodArray, getBloodIndex(bloodRecipient.getPatientBloodInfo().getBloodType()), response, bloodWithdrawal, bloodDonorMenu.getPatientBloodLinkedList());
            for (int i = 0; i < 8; i++) {
                Utils.bloodArray[i].setBloodUnitAmount(Utils.bloodArray[i].getBloodUnitAmount() - bloodWithdrawal[i]);
            }
            int dCount = 0;
            for (int i = 0; i < bloodWithdrawal.length; i++) {
                if (bloodWithdrawal[i] > 0) {
                    dCount++;
                    break;
                }
            }
            if (dCount > 0) {
                System.out.println("\n" + ConsoleColors.CYAN + "Blood Checkout Summary" + ConsoleColors.RESET);
            } else {
                System.out.println("\n" + ConsoleColors.RED + "*** There is no blood available at this time. Arrange for transfer immediately. ***" + ConsoleColors.RESET);
            }
            for (int i = 0; i < bloodWithdrawal.length; i++) {
                if (bloodWithdrawal[i] > 0) {
                    if (Utils.bloodArray[i].getBloodType().equalsIgnoreCase("AB+") || Utils.bloodArray[i].getBloodType().equalsIgnoreCase("AB-")) {
                        System.out.print("\t" + Utils.bloodArray[i].getBloodType() + ": -" + bloodWithdrawal[i] + " units (" + Utils.bloodArray[i].getBloodUnitAmount() + " remaining)\n");
                    } else {
                        System.out.print("\t" + Utils.bloodArray[i].getBloodType() + ":  -" + bloodWithdrawal[i] + " units (" + Utils.bloodArray[i].getBloodUnitAmount() + " remaining)\n");
                    }
                }
            }
        } else {
            System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
        }
    }

    private void checkSupplyLevels() {
        PatientBloodSupplyLevels.bloodSupplyLevels(Utils.bloodArray, bloodDonorMenu.getPatientBloodLinkedList());
    }

    private void arrangeTransfer() {
        int response;
        while (true) {
            System.out.println(ConsoleColors.CYAN + "\nIs this an emergency?" + ConsoleColors.RESET + "\n\t1. Yes\n\t2. No");
            try {
                response = Integer.parseInt(Utils.input.next());
                if (response == 1) {
                    emergencyTransfer();
                } else if (response == 2) {
                    nonEmergencyTransfer();
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
    }

    private void emergencyTransfer() {
        LinkedList<Patient> eligibleDonors = new LinkedList<>();
        LinkedList<Patient> tempList = new LinkedList<>();
        String[] validBloodTypes = new String[8];
        int[] bloodDeposit = new int[8];
        double random;

        PatientBloodSupplyLevels.bloodSupplyLevels(Utils.bloodArray, bloodDonorMenu.getPatientBloodLinkedList());
        String bloodType = bloodDonorMenu.askForPatientBloodType(ConsoleColors.CYAN + "\nWhich blood type do you need?" + ConsoleColors.RESET);

        if (bloodType.equalsIgnoreCase("AB+") || bloodType.equalsIgnoreCase("AB-")) {
            System.out.println(ConsoleColors.CYAN + "\n Eligible donors for blood type " + bloodType + "\n------------------------------------" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.CYAN + "\n Eligible donors for blood type " + bloodType + "\n-----------------------------------" + ConsoleColors.RESET);
        }
        for (int i = 0; i < 8; i++) {
            Utils.bloodArray[i].setBloodUnitAmount(Utils.bloodArray[i].getBloodUnitAmount() + 1);
        }
        bloodDeposit = patientBloodSearch.bloodSearch(Utils.bloodArray, getBloodIndex(bloodType), 9999, bloodDeposit, bloodDonorMenu.getPatientBloodLinkedList());
        for (int i = 0; i < 8; i++) {
            if (bloodDeposit[i] > 0) {
                for (int j = 0; j < 8; j++) {
                    if (validBloodTypes[j] == null) {
                        validBloodTypes[j] = Utils.bloodArray[i].getBloodType();
                        for (Integer k : BinarySearch.bSearchBloodType(bloodDonorMenu.getPatientBloodLinkedList(), validBloodTypes[j], 0, bloodDonorMenu.getPatientBloodLinkedList().size() - 1)) {
                            if (bloodDonorMenu.getPatientBloodLinkedList().get(k).getPatientBloodInfo().isEligibleDonor()) {
                                eligibleDonors.add(bloodDonorMenu.getPatientBloodLinkedList().get(k));
                            }
                        }
                        break;
                    }
                }
            }
        }
        LinkedList<Patient> presentPatients = new LinkedList<>();
        for (int j = 0; j < validBloodTypes.length; j++) {
            int pCount = 0;
            for (Patient p : eligibleDonors) {
                if (p.getPatientBloodInfo().getBloodType().equalsIgnoreCase(validBloodTypes[j])) {
                    pCount++;
                    break;
                }
            }
            if (pCount > 0) {
                System.out.print(ConsoleColors.YELLOW + "Blood Type: " + validBloodTypes[j] + ConsoleColors.RED + "\n\tPatient ID(s): " + ConsoleColors.RESET + "(");
            } else {
                continue;
            }
            for (int i = 0; i < eligibleDonors.size(); i++) {
                if (presentPatients.contains(eligibleDonors.get(i))) {
                    continue;
                } else {
                    try {
                        if (!eligibleDonors.get(i + 1).getPatientBloodInfo().getBloodType().equalsIgnoreCase(eligibleDonors.get(i).getPatientBloodInfo().getBloodType())) {
                            System.out.print(eligibleDonors.get(i).getId());
                            presentPatients.add(eligibleDonors.get(i));
                            break;
                        } else {
                            System.out.print(eligibleDonors.get(i).getId() + ", ");
                            presentPatients.add(eligibleDonors.get(i));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.print(eligibleDonors.get(i).getId());
                        presentPatients.add(eligibleDonors.get(i));
                    }
                }
            }
            System.out.print(")\n");
        }
        tempList.addAll(eligibleDonors);
        for (Patient p : tempList) {
            random = Math.random();
            if (random < 0.4) {
                eligibleDonors.remove(p);
            }
        }
        System.out.println();
        addToSupplyLevels(eligibleDonors, bloodType);
    }

    private void nonEmergencyTransfer() {
        LinkedList<Patient> eligibleAndWillingDonors = new LinkedList<>();
        LinkedList<Patient> tempList = new LinkedList<>();
        String[] validBloodTypes = new String[8];
        int[] bloodDeposit = new int[8];
        double random;

        PatientBloodSupplyLevels.bloodSupplyLevels(Utils.bloodArray, bloodDonorMenu.getPatientBloodLinkedList());
        String bloodType = bloodDonorMenu.askForPatientBloodType(ConsoleColors.CYAN + "\nWhich blood type do you need?" + ConsoleColors.RESET);

        if (bloodType.equalsIgnoreCase("AB+") || bloodType.equalsIgnoreCase("AB-")) {
            System.out.println(ConsoleColors.CYAN + "\n Eligible and willing donors for blood type " + bloodType + "\n------------------------------------------------" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.CYAN + "\n Eligible and willing donors for blood type " + bloodType + "\n-----------------------------------------------" + ConsoleColors.RESET);
        }
        for (int i = 0; i < 8; i++) {
            Utils.bloodArray[i].setBloodUnitAmount(Utils.bloodArray[i].getBloodUnitAmount() + 1);
        }
        bloodDeposit = patientBloodSearch.bloodSearch(Utils.bloodArray, getBloodIndex(bloodType), 9999, bloodDeposit, bloodDonorMenu.getPatientBloodLinkedList());
        for (int i = 0; i < 8; i++) {
            if (bloodDeposit[i] > 0) {
                for (int j = 0; j < 8; j++) {
                    if (validBloodTypes[j] == null) {
                        validBloodTypes[j] = Utils.bloodArray[i].getBloodType();
                        for (Integer k : BinarySearch.bSearchBloodType(bloodDonorMenu.getPatientBloodLinkedList(), validBloodTypes[j], 0, bloodDonorMenu.getPatientBloodLinkedList().size() - 1)) {
                            if (bloodDonorMenu.getPatientBloodLinkedList().get(k).getPatientBloodInfo().isEligibleDonor() && bloodDonorMenu.getPatientBloodLinkedList().get(k).getPatientBloodInfo().isWillingDonor()) {
                                eligibleAndWillingDonors.add(bloodDonorMenu.getPatientBloodLinkedList().get(k));
                            }
                        }
                        break;
                    }
                }
            }
        }
        for (int j = 0; j < 8; j++) {
            if (validBloodTypes[j] != null) {
                int pCount = 0;
                for (Patient p : eligibleAndWillingDonors) {
                    if (p.getPatientBloodInfo().getBloodType().equalsIgnoreCase(validBloodTypes[j])) {
                        pCount++;
                        break;
                    }
                }
                if (pCount > 0) {
                    System.out.print(ConsoleColors.YELLOW + "Blood Type: " + validBloodTypes[j] + ConsoleColors.RED + "\n\tPatient ID(s): " + ConsoleColors.RESET + "(");
                } else {
                    continue;
                }
                for (int i = 0; i < eligibleAndWillingDonors.size(); i++) {
                    if (tempList.contains(eligibleAndWillingDonors.get(i))) {
                        continue;
                    } else {
                        try {
                            if (!eligibleAndWillingDonors.get(i + 1).getPatientBloodInfo().getBloodType().equalsIgnoreCase(eligibleAndWillingDonors.get(i).getPatientBloodInfo().getBloodType())) {
                                System.out.print(eligibleAndWillingDonors.get(i).getId());
                                tempList.add(eligibleAndWillingDonors.get(i));
                                break;
                            } else {
                                System.out.print(eligibleAndWillingDonors.get(i).getId() + ", ");
                                tempList.add(eligibleAndWillingDonors.get(i));
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.print(eligibleAndWillingDonors.get(i).getId());
                            tempList.add(eligibleAndWillingDonors.get(i));
                        }
                    }
                }
                System.out.print(")\n");
            }
        }
        tempList.clear();
        tempList.addAll(eligibleAndWillingDonors);
        for (Patient p : tempList) {
            random = Math.random();
            if (random < 0.4) {
                eligibleAndWillingDonors.remove(p);
            }
        }
        addToSupplyLevels(eligibleAndWillingDonors, bloodType);
    }

    private void addToSupplyLevels(LinkedList<Patient> donors, String bloodType) {
        int[] bloodDeposit = new int[8];
        int index;

        for (Patient p : donors) {
            index = getBloodIndex(p.getPatientBloodInfo().getBloodType());
            if (p.getPatientBloodInfo().getBloodType().equalsIgnoreCase(bloodType)) {
                bloodDeposit[index] += 6;
            } else {
                bloodDeposit[index] += 3;
            }
        }
        for (int i = 0; i < 8; i++) {
            Utils.bloodArray[i].setBloodUnitAmount(Utils.bloodArray[i].getBloodUnitAmount() + bloodDeposit[i] - 1);
        }

        int dCount = 0;
        for (int i = 0; i < bloodDeposit.length; i++) {
            if (bloodDeposit[i] > 0) {
                dCount++;
                break;
            }
        }
        if (dCount > 0) {
            System.out.println("\n" + ConsoleColors.CYAN + "Blood Transfer Summary" + ConsoleColors.RESET);
        } else {
            System.out.println("\n" + ConsoleColors.RED + "*** No patients could donate blood at this time ***" + ConsoleColors.RESET);
            return;
        }
        for (int i = 0; i < bloodDeposit.length; i++) {
            if (bloodDeposit[i] > 0) {
                if (Utils.bloodArray[i].getBloodType().equalsIgnoreCase("AB+") || Utils.bloodArray[i].getBloodType().equalsIgnoreCase("AB-")) {
                    System.out.print("\t" + Utils.bloodArray[i].getBloodType() + ": +" + bloodDeposit[i] + " units (" + Utils.bloodArray[i].getBloodUnitAmount() + " remaining)\n");
                } else {
                    System.out.print("\t" + Utils.bloodArray[i].getBloodType() + ":  +" + bloodDeposit[i] + " units (" + Utils.bloodArray[i].getBloodUnitAmount() + " remaining)\n");
                }
            }
        }

    }

    private int getBloodIndex(String bloodType) {
        int bloodIndex = -1;
        switch (bloodType) {
            case "AB+":
                bloodIndex = 0;
                break;
            case "A+":
                bloodIndex = 1;
                break;
            case "B+":
                bloodIndex = 2;
                break;
            case "O+":
                bloodIndex = 3;
                break;
            case "AB-":
                bloodIndex = 4;
                break;
            case "A-":
                bloodIndex = 5;
                break;
            case "B-":
                bloodIndex = 6;
                break;
            case "O-":
                bloodIndex = 7;
                break;
        }
        return bloodIndex;
    }
}
