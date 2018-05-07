package dataStructures.finalProject.patient.blood;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.patient.blood
 *     Created by Braden
 *     5/4/2018
 ****************************************/

public class PatientBloodNode {

    private String bloodType;
    private int bloodUnitAmount;

    //need to be Integer instead of int because int can't be null
    private Integer firstChild;
    private Integer secondChild;
    private Integer parallelCousin;

    public PatientBloodNode(int cousin, int units, String blood, int first, int second) {
        this.bloodType = blood;
        this.bloodUnitAmount = units;
        this.firstChild = first;
        this.secondChild = second;
        this.parallelCousin = cousin;
    }

    public PatientBloodNode(int cousin, int units, String blood, int first) {
        this.bloodType = blood;
        this.bloodUnitAmount = units;
        this.firstChild = first;
        this.parallelCousin = cousin;
    }

    public PatientBloodNode(int cousin, int units, String blood) {
        this.bloodType = blood;
        this.bloodUnitAmount = units;
        this.parallelCousin = cousin;
    }

    public PatientBloodNode(int units, String blood, int first, int second) {
        this.bloodType = blood;
        this.bloodUnitAmount = units;
        this.firstChild = first;
        this.secondChild = second;
    }

    public PatientBloodNode(int units, String blood, int first) {
        this.bloodType = blood;
        this.bloodUnitAmount = units;
        this.firstChild = first;
    }

    public PatientBloodNode(int units, String blood) {
        this.bloodType = blood;
        this.bloodUnitAmount = units;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getBloodUnitAmount() {
        return bloodUnitAmount;
    }

    public void setBloodUnitAmount(int bloodUnitAmount) {
        this.bloodUnitAmount = bloodUnitAmount;
    }

    public Integer getFirstChild() {
        return firstChild;
    }

    public Integer getSecondChild() {
        return secondChild;
    }

    public Integer getParallelCousin() {
        return parallelCousin;
    }
}
