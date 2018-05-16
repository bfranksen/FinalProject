package dataStructures.finalProject.utilities;

import dataStructures.finalProject.patient.Patient;

import java.util.LinkedList;

/********************************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class QuickSort {

    public static LinkedList<Patient> quickSortID(LinkedList<Patient> list, int left, int right) {

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

    public static LinkedList<Patient> quickSortPhoneNumber(LinkedList<Patient> list, int left, int right) {

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

    public static LinkedList<Patient> quickSortUrgency(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (compareUrgency(list.get(i), list.get(pivot)) < 0) {
                i++;
            }
            while (compareUrgency(list.get(j), list.get(pivot)) > 0) {
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


    public static LinkedList<Patient> quickSortLastName(LinkedList<Patient> list, int left, int right) {

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


    public static LinkedList<Patient> quickSortOrgan(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (compareOrgan(list.get(i), list.get(pivot)) < 0) {
                i++;
            }
            while (compareOrgan(list.get(j), list.get(pivot)) > 0) {
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

    public static LinkedList<Patient> quickSortBloodType(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (list.get(i).getPatientBloodInfo().getBloodType().compareToIgnoreCase(list.get(pivot).getPatientBloodInfo().getBloodType()) < 0) {
                i++;
            }
            while (list.get(j).getPatientBloodInfo().getBloodType().compareToIgnoreCase(list.get(pivot).getPatientBloodInfo().getBloodType()) > 0) {
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
            quickSortBloodType(list, left, j);
        }
        if (right > i) {
            quickSortBloodType(list, i, right);
        }
        return list;
    }

    public static LinkedList<Patient> quickSortEligibleDonors(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (compareEligibleDonors(list.get(i), list.get(pivot)) > 0) {
                i++;
            }
            while (compareEligibleDonors(list.get(j), list.get(pivot)) < 0) {
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
            quickSortEligibleDonors(list, left, j);
        }
        if (right > i) {
            quickSortEligibleDonors(list, i, right);
        }
        return list;
    }

    public static LinkedList<Patient> quickSortWillingDonors(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (compareWillingDonors(list.get(i), list.get(pivot)) > 0) {
                i++;
            }
            while (compareWillingDonors(list.get(j), list.get(pivot)) < 0) {
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
            quickSortWillingDonors(list, left, j);
        }
        if (right > i) {
            quickSortWillingDonors(list, i, right);
        }
        return list;
    }

    public static LinkedList<Patient> quickSortEligibleAndWillingDonors(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list;
        }

        int pivot = right;
        int i = left;
        int j = right;

        while (i <= j) {
            while (compareEligibleAndWillingDonors(list.get(i), list.get(pivot)) > 0) {
                i++;
            }
            while (compareEligibleAndWillingDonors(list.get(j), list.get(pivot)) < 0) {
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
            quickSortEligibleAndWillingDonors(list, left, j);
        }
        if (right > i) {
            quickSortEligibleAndWillingDonors(list, i, right);
        }
        return list;
    }

    private static int compareOrgan(Patient p1, Patient p2) {
        int organResult = p1.getPatientOrganInfo().getOrgan().compareToIgnoreCase(p2.getPatientOrganInfo().getOrgan());

        if (organResult != 0) {
            return organResult;
        }

        int urgencyResult;

        if (p1.getPatientOrganInfo().getUrgency() > p2.getPatientOrganInfo().getUrgency()) {
            urgencyResult = -1;
        } else if (p1.getPatientOrganInfo().getUrgency() < p2.getPatientOrganInfo().getUrgency()) {
            urgencyResult = 1;
        } else {
            urgencyResult = 0;
        }

        return urgencyResult;
    }

    private static int compareUrgency(Patient p1, Patient p2) {
        int urgencyResult;

        if (p1.getPatientOrganInfo().getUrgency() > p2.getPatientOrganInfo().getUrgency()) {
            urgencyResult = 1;
        } else if (p1.getPatientOrganInfo().getUrgency() < p2.getPatientOrganInfo().getUrgency()) {
            urgencyResult = -1;
        } else {
            urgencyResult = 0;
        }

        if (urgencyResult != 0) {
            return urgencyResult;
        }

        return p1.getPatientOrganInfo().getOrgan().compareToIgnoreCase(p2.getPatientOrganInfo().getOrgan());

    }

    private static int compareEligibleDonors(Patient p1, Patient p2) {
        boolean e1 = p1.getPatientBloodInfo().isEligibleDonor();
        boolean e2 = p2.getPatientBloodInfo().isEligibleDonor();
        int eligibleResult = Boolean.compare(e1, e2);

        if (eligibleResult != 0) {
            return eligibleResult;
        }

        return -p1.getPatientBloodInfo().getBloodType().compareToIgnoreCase(p2.getPatientBloodInfo().getBloodType());
    }

    private static int compareWillingDonors(Patient p1, Patient p2) {
        boolean w1 = p1.getPatientBloodInfo().isWillingDonor();
        boolean w2 = p2.getPatientBloodInfo().isWillingDonor();
        int willingResult = Boolean.compare(w1, w2);

        if (willingResult != 0) {
            return willingResult;
        } else {
            return -(p1.getPatientBloodInfo().getBloodType().compareToIgnoreCase(p2.getPatientBloodInfo().getBloodType()));
        }
    }

    private static int compareEligibleAndWillingDonors(Patient p1, Patient p2) {
        boolean e1 = p1.getPatientBloodInfo().isEligibleDonor();
        boolean e2 = p2.getPatientBloodInfo().isEligibleDonor();
        int eligibleResult = Boolean.compare(e1, e2);

        if (eligibleResult != 0) {
            return eligibleResult;
        }

        boolean w1 = p1.getPatientBloodInfo().isWillingDonor();
        boolean w2 = p2.getPatientBloodInfo().isWillingDonor();
        int willingResult = Boolean.compare(w1, w2);

        if (willingResult != 0) {
            return willingResult;
        }

        return -p1.getPatientBloodInfo().getBloodType().compareToIgnoreCase(p2.getPatientBloodInfo().getBloodType());
    }
}