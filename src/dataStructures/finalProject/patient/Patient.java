package dataStructures.finalProject.patient;

import dataStructures.finalProject.utilities.ConsoleColors;
import dataStructures.finalProject.patient.blood.PatientBloodInfo;

import java.util.Objects;

/********************************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class Patient {

    private String firstName, lastName;
    private int id;

    private PatientContactInfo patientContactInfo;
    private PatientOrganInfo patientOrganInfo;
    private PatientBloodInfo patientBloodInfo;

    public Patient(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public PatientContactInfo getPatientContactInfo() {
        return patientContactInfo;
    }

    public void setPatientContactInfo(PatientContactInfo patientContactInfo) {
        this.patientContactInfo = patientContactInfo;
    }

    public PatientOrganInfo getPatientOrganInfo() {
        return patientOrganInfo;
    }

    public void setPatientOrganInfo(PatientOrganInfo patientOrganInfo) {
        this.patientOrganInfo = patientOrganInfo;
    }

    public PatientBloodInfo getPatientBloodInfo() {
        return patientBloodInfo;
    }

    public void setPatientBloodInfo(PatientBloodInfo patientBloodInfo) {
        this.patientBloodInfo = patientBloodInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return getId() == patient.getId() &&
                Objects.equals(getFirstName(), patient.getFirstName()) &&
                Objects.equals(getLastName(), patient.getLastName()) &&
                Objects.equals(patientContactInfo, patient.patientContactInfo) &&
                Objects.equals(patientOrganInfo, patient.patientOrganInfo) &&
                Objects.equals(patientBloodInfo, patient.patientBloodInfo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirstName(), getLastName(), getId(), patientContactInfo, patientOrganInfo, patientBloodInfo);
    }

    @Override
    public String toString() {
        return ConsoleColors.RED + "Patient: " +
                ConsoleColors.RESET + id +
                " - " + firstName +
                " " + lastName;
    }
}
