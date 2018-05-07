package dataStructures.finalProject.employee;

import java.util.LinkedList;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.employee
 *     Created by Braden
 *     5/5/2018
 ****************************************/

public class EmployeeBinarySearch {

    private static LinkedList holder = new LinkedList();

    public static LinkedList<Integer> bSearchID(LinkedList<Employee> list, int id, int left, int right) {

        holder.clear();
        EmployeeQuickSort.quickSortID(list, left, right);

        int search = id;
        int pointer = (left + right) / 2;

        if (left < right - 1) {
            if (list.get(pointer).getEmpId() == search) {
                holder.add(pointer);
                for (int i = pointer - 1; i >= left; i--) {
                    if (list.get(i).getEmpId() == search) {
                        holder.add(i);
                    } else {
                        i = left;
                    }
                }
                for (int i = pointer + 1; i <= right; i++) {
                    if (list.get(i).getEmpId() == search) {
                        holder.add(i);
                    } else {
                        i = right;
                    }
                }
                return holder;
            } else if (list.get(pointer).getEmpId() < search) {
                bSearchID(list, id, pointer, right);
            } else {
                bSearchID(list, id, left, pointer);
            }
        } else if (pointer == list.size() - 2) {
            pointer = list.size() - 1;
            if (list.get(pointer).getEmpId() == search) {
                holder.add(pointer);
            }
        } else {
            pointer = 0;
            if (list.get(pointer).getEmpId() == search) {
                holder.add(pointer);
            }
        }
        return holder;
    }

    public static LinkedList<Integer> bSearchLastName(LinkedList<Employee> list, String lastName, int left, int right) {

        holder.clear();
        EmployeeQuickSort.quickSortLastName(list, left, right);

        String search = lastName;
        int pointer = (left + right) / 2;

        if (left < right - 1) {
            if (list.get(pointer).getLastName().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
                for (int i = pointer - 1; i >= left; i--) {
                    if (list.get(i).getLastName().compareToIgnoreCase(search) == 0) {
                        holder.add(i);
                    } else {
                        i = left;
                    }
                }
                for (int i = pointer + 1; i <= right; i++) {
                    if (list.get(i).getLastName().compareToIgnoreCase(search) == 0) {
                        holder.add(i);
                    } else {
                        i = right;
                    }
                }
                return holder;
            } else if (list.get(pointer).getLastName().compareToIgnoreCase(search) < 0) {
                bSearchLastName(list, lastName, pointer, right);
            } else {
                bSearchLastName(list, lastName, left, pointer);
            }
        } else if (pointer == list.size() - 2) {
            pointer = list.size() - 1;
            if (list.get(pointer).getLastName().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
            }
        } else {
            pointer = 0;
            if (list.get(pointer).getLastName().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
            }
        }
        return holder;
    }

    public static LinkedList<Integer> bSearchUsername(LinkedList<Employee> list, String userName, int left, int right) {

        holder.clear();
        EmployeeQuickSort.quickSortUsername(list, left, right);

        String search = userName;
        int pointer = (left + right) / 2;

        if (left < right - 1) {
            if (list.get(pointer).getUserName().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
                for (int i = pointer - 1; i >= left; i--) {
                    if (list.get(i).getUserName().compareToIgnoreCase(search) == 0) {
                        holder.add(i);
                    } else {
                        i = left;
                    }
                }
                for (int i = pointer + 1; i <= right; i++) {
                    if (list.get(i).getUserName().compareToIgnoreCase(search) == 0) {
                        holder.add(i);
                    } else {
                        i = right;
                    }
                }
                return holder;
            } else if (list.get(pointer).getUserName().compareToIgnoreCase(search) < 0) {
                bSearchUsername(list, userName, pointer, right);
            } else {
                bSearchUsername(list, userName, left, pointer);
            }
        } else if (pointer == list.size() - 2) {
            pointer = list.size() - 1;
            if (list.get(pointer).getUserName().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
            }
        } else {
            pointer = 0;
            if (list.get(pointer).getUserName().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
            }
        }
        return holder;
    }
}
