package finalProject.dataStructures.patient;

import java.util.Objects;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden
 *     4/25/2018
 ****************************************/

public class PatientContactInfo {

    private String emailAddress, homeAddress;
    private long phoneNumber;

    public PatientContactInfo(long phoneNumber, String emailAddress, String homeAddress) {
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientContactInfo that = (PatientContactInfo) o;
        return Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
                Objects.equals(getEmailAddress(), that.getEmailAddress()) &&
                Objects.equals(getHomeAddress(), that.getHomeAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneNumber(), getEmailAddress(), getHomeAddress());
    }

    @Override
    public String toString() {
        String phoneNumberStr = String.valueOf(phoneNumber);
        return "\n\t\tPhone Number:\t(" + phoneNumberStr.substring(0, 3) + ") " + phoneNumberStr.substring(3, 6) + "-" + phoneNumberStr.substring(6, 10) +
                "\n\t\tEmail Address:\t" + emailAddress +
                "\n\t\tHome Address:\t" + homeAddress;
    }
}
