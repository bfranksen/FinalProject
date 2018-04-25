package mini.dataStructures;

import java.util.LinkedList;

/********************************************************
 *     MiniProject
 *     mini.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class PatientQuickSort {

    public LinkedList<Patient> quickSortID(LinkedList<Patient> list, int left, int right) {

        if (left >= right) {
            return list; }

        int pivot = right;
        int i = left;
        int j = right;

        while(i<= j) {
            while(list.get(i).getId() < list.get(pivot).getId()){
                i++;
            }
            while(list.get(j).getId() > list.get(pivot).getId()){
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



    public LinkedList<Patient> quickSortUrgency(LinkedList<Patient> list, int left, int right){

        if (left >= right) {
            return list; }

        int pivot = right;
        int i = left;
        int j = right;

        while(i<= j) {
            while(list.get(i).getUrgency() < list.get(pivot).getUrgency()){
                i++;
            }
            while(list.get(j).getUrgency() > list.get(pivot).getUrgency()){
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



    public LinkedList<Patient> quickSortLastName(LinkedList<Patient> list, int left, int right){

        if (left >= right) {
            return list; }

        int pivot = right;
        int i = left;
        int j = right;

        while(i<= j) {
            while(list.get(i).getLastName().compareToIgnoreCase(list.get(pivot).getLastName()) < 0){
                i++;
            }
            while(list.get(j).getLastName().compareToIgnoreCase(list.get(pivot).getLastName()) > 0){
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



    public LinkedList<Patient> quickSortOrgan(LinkedList<Patient> list, int left, int right){

        if (left >= right) {
            return list; }

        int pivot = right;
        int i = left;
        int j = right;

        while(i<= j) {
            while(list.get(i).getOrgan().compareToIgnoreCase(list.get(pivot).getOrgan()) < 0){
                i++;
            }
            while(list.get(j).getOrgan().compareToIgnoreCase(list.get(pivot).getOrgan()) > 0){
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

