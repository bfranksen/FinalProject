package dataStructures.finalProject.menus;

import dataStructures.finalProject.patient.Patient;
import dataStructures.finalProject.patient.PatientContactInfo;
import dataStructures.finalProject.patient.PatientOrganInfo;
import dataStructures.finalProject.patient.blood.PatientBloodInfo;
import dataStructures.finalProject.utilities.BinarySearch;
import dataStructures.finalProject.utilities.ConsoleColors;
import dataStructures.finalProject.utilities.Utils;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/********************************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class MainMenu {

    private LinkedList<Patient> patientLinkedList;
    private ContactInfoMenu contactInfoMenu;
    private OrganTransplantMenu organTransplantMenu;
    private BloodDonorMenu bloodDonorMenu;

    public MainMenu() {
        this.patientLinkedList = new LinkedList<>();
        this.contactInfoMenu = new ContactInfoMenu(this, patientLinkedList);
        this.organTransplantMenu = new OrganTransplantMenu(this);
        this.bloodDonorMenu = new BloodDonorMenu(this);
        readPatientDataFromFile();
    }

    private void readPatientDataFromFile() {
        LinkedList<Integer> searchResults;
        Patient existingPatient;

        File patientFile = new File("src/Patient List");
        Scanner reader = null;

        try {
            reader = new Scanner(patientFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Utils.patIdPointer = reader.nextInt();
            while (reader.hasNextLine()) {
                String next = Utils.convertDashesToSpaces(reader.next());
                if (next.equalsIgnoreCase("contact")) {
                    reader.nextLine();
                } else if (next.equalsIgnoreCase("organ")) {
                    break;
                } else {
                    String firstName = next;
                    String lastName = Utils.convertDashesToSpaces(reader.next());
                    long phone = reader.nextLong();
                    String email = Utils.convertDashesToSpaces(reader.next());
                    int id = reader.nextInt();
                    Patient p = new Patient(id, firstName, lastName);
                    p.setPatientContactInfo(new PatientContactInfo(phone, email, "123 Generic Street"));
                    patientLinkedList.add(p);
                }
            }
            while (reader.hasNextLine()) {
                String next = Utils.convertDashesToSpaces(reader.next());
                ;
                if (next.equalsIgnoreCase("organ")) {
                    reader.nextLine();
                } else if (next.equalsIgnoreCase("blood")) {
                    break;
                } else {
                    String firstName = next;
                    String lastName = Utils.convertDashesToSpaces(reader.next());
                    String organ = Utils.convertDashesToSpaces(reader.next());
                    int urgency = reader.nextInt();
                    searchResults = BinarySearch.bSearchLastName(patientLinkedList, lastName, 0, patientLinkedList.size() - 1);
                    for (Integer i : searchResults) {
                        if (patientLinkedList.get(i).getFirstName().equalsIgnoreCase(firstName)) {
                            existingPatient = patientLinkedList.get(i);
                            existingPatient.setPatientOrganInfo(new PatientOrganInfo(organ, urgency));
                            organTransplantMenu.getPatientOrganLinkedList().add(existingPatient);
                        } else {
                            continue;
                        }
                    }
                }
            }
            bloodDonorMenu.setBloodTypes();
            for (int i = 0; i < 8; i++) {
                Utils.bloodArray[i].setBloodUnitAmount(reader.nextInt());
            }
            while (reader.hasNextLine()) {
                String firstName = Utils.convertDashesToSpaces(reader.next());
                String lastName = Utils.convertDashesToSpaces(reader.next());
                String bloodType = reader.next();
                boolean eligible = reader.nextBoolean();
                boolean willing = reader.nextBoolean();
                searchResults = BinarySearch.bSearchLastName(patientLinkedList, lastName, 0, patientLinkedList.size() - 1);
                for (Integer i : searchResults) {
                    if (patientLinkedList.get(i).getFirstName().equalsIgnoreCase(firstName)) {
                        existingPatient = patientLinkedList.get(i);
                        existingPatient.setPatientBloodInfo(new PatientBloodInfo(bloodType, eligible, willing));
                        bloodDonorMenu.getPatientBloodLinkedList().add(existingPatient);
                    } else {
                        continue;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

    public void writePatientDataToFile() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("src/Patient List"), "utf-8"))) {
            writer.write(String.valueOf(Utils.patIdPointer) + "\n");
            writer.write("Contact");
            for (Patient p : patientLinkedList) {
                writer.write("\n" + Utils.convertSpacesToDashes(p.getFirstName()) + " " + Utils.convertSpacesToDashes(p.getLastName()) + " " + p.getPatientContactInfo().getPhoneNumber() + " " + p.getPatientContactInfo().getEmailAddress() + " " + p.getId());
            }
            writer.write("\nOrgan");
            for (Patient p : organTransplantMenu.getPatientOrganLinkedList()) {
                writer.write("\n" + Utils.convertSpacesToDashes(p.getFirstName()) + " " + Utils.convertSpacesToDashes(p.getLastName()) + " " + p.getPatientOrganInfo().getOrgan() + " " + p.getPatientOrganInfo().getUrgency());
            }
            writer.write("\nBlood\n");
            for (int i = 0; i < Utils.bloodArray.length; i++) {
                writer.write(Utils.bloodArray[i].getBloodUnitAmount() + " ");
            }
            for (Patient p : bloodDonorMenu.getPatientBloodLinkedList()) {
                writer.write("\n" + Utils.convertSpacesToDashes(p.getFirstName()) + " " + Utils.convertSpacesToDashes(p.getLastName()) + " " + p.getPatientBloodInfo().getBloodType() + " " + p.getPatientBloodInfo().isEligibleDonor() + " " + p.getPatientBloodInfo().isWillingDonor());
            }
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (menuOptions()) {
            menuOptions();
        }
    }

    private boolean menuOptions() {
        try {
            int response;
            System.out.print(ConsoleColors.YELLOW + "\n\n Main Menu\n-----------" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "\nWhat would you like to work on?" + ConsoleColors.RESET);
            System.out.println("\t1. Patient Info");
            System.out.println("\t2. Organ Transplant List");
            System.out.println("\t3. Blood Donor List");
            System.out.println("\t4. Exit Program");
            response = Integer.parseInt(Utils.input.next());
            if (response < 1 || response > 4) {
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
            case 3:
                bloodDonorMenu.run();
                break;
            case 4:
                writePatientDataToFile();
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }

    public LinkedList<Patient> getPatientLinkedList() {
        return patientLinkedList;
    }

    public ContactInfoMenu getContactInfoMenu() {
        return contactInfoMenu;
    }

    public OrganTransplantMenu getOrganTransplantMenu() {
        return organTransplantMenu;
    }

    public BloodDonorMenu getBloodDonorMenu() {
        return bloodDonorMenu;
    }
}
