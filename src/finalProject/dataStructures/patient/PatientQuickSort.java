package finalProject.dataStructures.patient;

import java.util.LinkedList;

/********************************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class PatientQuickSort {

    public LinkedList<Patient> quickSortID(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (list.get(i).getId() < list.get(pivot).getId()) {
                i++;
            }
            while (list.get(j).getId() > list.get(pivot).getId()) {
                j--;
            }
            if (i <= j) {
                Patient node = list.get(i);
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

    public LinkedList<Patient> quickSortPhoneNumber(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (list.get(i).getPatientContactInfo().getPhoneNumber() < list.get(pivot).getPatientContactInfo().getPhoneNumber()) {
                i++;
            }
            while (list.get(j).getPatientContactInfo().getPhoneNumber() > list.get(pivot).getPatientContactInfo().getPhoneNumber()) {
                j--;
            }
            if (i <= j) {
                Patient node = list.get(i);
                list.set(i, list.get(j));
                list.set(j, node);
                i++;
                j--;
            }
        }
        if (left < j) {
            quickSortPhoneNumber(list, left, j);
        }
        if (right > i) {
            quickSortPhoneNumber(list, i, right);
        }
        return list;
    }

    public LinkedList<Patient> quickSortUrgency(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (list.get(i).getPatientOrganInfo().getUrgency() < list.get(pivot).getPatientOrganInfo().getUrgency()) {
                i++;
            }
            while (list.get(j).getPatientOrganInfo().getUrgency() > list.get(pivot).getPatientOrganInfo().getUrgency()) {
                j--;
            }
            if (i <= j) {
                Patient node = list.get(i);
                list.set(i, list.get(j));
                list.set(j, node);
                i++;
                j--;
            }
        }
        if (left < j) {
            quickSortUrgency(list, left, j);
        }
        if (right > i) {
            quickSortUrgency(list, i, right);
        }
        return list;
    }


    public LinkedList<Patient> quickSortLastName(LinkedList<Patient> list, int left, int right) {

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
                Patient node = list.get(i);
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


    public LinkedList<Patient> quickSortOrgan(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (list.get(i).getPatientOrganInfo().getOrgan().compareToIgnoreCase(list.get(pivot).getPatientOrganInfo().getOrgan()) < 0) {
                i++;
            }
            while (list.get(j).getPatientOrganInfo().getOrgan().compareToIgnoreCase(list.get(pivot).getPatientOrganInfo().getOrgan()) > 0) {
                j--;
            }
            if (i <= j) {
                Patient node = list.get(i);
                list.set(i, list.get(j));
                list.set(j, node);
                i++;
                j--;
            }
        }
        if (left < j) {
            quickSortOrgan(list, left, j);
        }
        if (right > i) {
            quickSortOrgan(list, i, right);
        }
        return list;
    }
}

