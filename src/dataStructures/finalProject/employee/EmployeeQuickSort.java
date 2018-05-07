package dataStructures.finalProject.employee;

import java.util.LinkedList;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.employee
 *     Created by Braden
 *     5/5/2018
 ****************************************/

public class EmployeeQuickSort {

    public static LinkedList<Employee> quickSortID(LinkedList<Employee> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (list.get(i).getEmpId() < list.get(pivot).getEmpId()) {
                i++;
            }
            while (list.get(j).getEmpId() > list.get(pivot).getEmpId()) {
                j--;
            }
            if (i <= j) {
                Employee node = list.get(i);
                list.set(i, list.get(j));
                list.set(j, node);
                i++;
                j--;
            }
        }
        if (left < j) {
            quickSortID(list, left, j);
        }
        if (right > i) {
            quickSortID(list, i, right);
        }
        return list;
    }

    public static LinkedList<Employee> quickSortLastName(LinkedList<Employee> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (list.get(i).getLastName().compareToIgnoreCase(list.get(pivot).getLastName()) < 0) {
                i++;
            }
            while (list.get(j).getLastName().compareToIgnoreCase(list.get(pivot).getLastName()) > 0) {
                j--;
            }
            if (i <= j) {
                Employee node = list.get(i);
                list.set(i, list.get(j));
                list.set(j, node);
                i++;
                j--;
            }
        }
        if (left < j) {
            quickSortLastName(list, left, j);
        }
        if (right > i) {
            quickSortLastName(list, i, right);
        }
        return list;
    }

    public static LinkedList<Employee> quickSortUsername(LinkedList<Employee> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (list.get(i).getUserName().compareToIgnoreCase(list.get(pivot).getUserName()) < 0) {
                i++;
            }
            while (list.get(j).getUserName().compareToIgnoreCase(list.get(pivot).getUserName()) > 0) {
                j--;
            }
            if (i <= j) {
                Employee node = list.get(i);
                list.set(i, list.get(j));
                list.set(j, node);
                i++;
                j--;
            }
        }
        if (left < j) {
            quickSortUsername(list, left, j);
        }
        if (right > i) {
            quickSortUsername(list, i, right);
        }
        return list;
    }
}
