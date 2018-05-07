package dataStructures.finalProject.employee;

import dataStructures.finalProject.utilities.ConsoleColors;

import java.util.Objects;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden
 *     5/3/2018
 ****************************************/

public class Employee {

    private String firstName, lastName, userName;
    private int empId;

    public Employee(String firstName, String lastName, String userName, int empId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public int getEmpId() {
        return empId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getEmpId() == employee.getEmpId() &&
                Objects.equals(getFirstName(), employee.getFirstName()) &&
                Objects.equals(getLastName(), employee.getLastName()) &&
                Objects.equals(getUserName(), employee.getUserName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirstName(), getLastName(), getUserName(), getEmpId());
    }

    @Override
    public String toString() {
        return ConsoleColors.RED + "Employee: " +
                ConsoleColors.RESET + empId +
                " - " + firstName +
                " " + lastName;
    }
}
