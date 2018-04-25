package mini.dataStructures;

/********************************************************
 *     MiniProject
 *     mini.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class Patient {

    private String firstName, lastName, organ;
    private int id, urgency;

    Patient(int id, String firstName, String lastName, String organ, int urgency) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organ = organ;
        this.urgency = urgency;
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

    public String getOrgan() {
        return organ;
    }

    public int getUrgency() {
        return urgency;
    }

    public boolean setUrgency(int urgency) {
        if (urgency < 1 || urgency > 8) {
            System.out.println(ConsoleColors.RED + "\n*** Invalid Urgency Level (1-8) ***" + ConsoleColors.RESET);
            return false;
        } else {
            this.urgency = urgency;
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (getId() != patient.getId()) return false;
        if (getUrgency() != patient.getUrgency()) return false;
        if (getFirstName() != null ? !getFirstName().equals(patient.getFirstName()) : patient.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(patient.getLastName()) : patient.getLastName() != null)
            return false;
        return getOrgan() != null ? getOrgan().equals(patient.getOrgan()) : patient.getOrgan() == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getOrgan() != null ? getOrgan().hashCode() : 0);
        result = 31 * result + getId();
        result = 31 * result + getUrgency();
        return result;
    }

    @Override
    public String toString() {
        return "Patient{ " + id +
                " -- " + firstName +
                " " + lastName +
                " -- " + organ +
                " -- " + urgency + '}';
    }
}
