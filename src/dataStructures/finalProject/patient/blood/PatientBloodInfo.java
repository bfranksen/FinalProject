package dataStructures.finalProject.patient.blood;

import java.util.Objects;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.patient.blood
 *     Created by Braden
 *     5/4/2018
 ****************************************/

public class PatientBloodInfo {

    private String bloodType;
    private boolean eligibleDonor, willingDonor;

    public PatientBloodInfo(String bloodType, boolean eligibleDonor, boolean willingDonor) {
        this.bloodType = bloodType;
        this.eligibleDonor = eligibleDonor;
        this.willingDonor = willingDonor;
    }

    public String getBloodType() {
        return bloodType;
    }

    public boolean isEligibleDonor() {
        return eligibleDonor;
    }

    public void setEligibleDonor(boolean eligibleDonor) {
        this.eligibleDonor = eligibleDonor;
    }

    public boolean isWillingDonor() {
        return willingDonor;
    }

    public void setWillingDonor(boolean willingDonor) {
        this.willingDonor = willingDonor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientBloodInfo that = (PatientBloodInfo) o;
        return isEligibleDonor() == that.isEligibleDonor() &&
                isWillingDonor() == that.isWillingDonor() &&
                Objects.equals(getBloodType(), that.getBloodType());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBloodType(), isEligibleDonor(), isWillingDonor());
    }

    @Override
    public String toString() {
        String eligible, willing;
        eligible = eligibleDonor ? "Yes" : "No";
        willing = willingDonor ? "Yes" : "No";
        return "\n\t\tBlood Type: \t\t" + bloodType +
                "\n\t\tEligible Donor: \t" + eligible +
                "\n\t\tWilling Donor: \t\t" + willing;
    }
}
