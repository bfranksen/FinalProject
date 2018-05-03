package finalProject.dataStructures.patient;

import java.util.LinkedList;

/********************************************************
 *     FinalProject
 *     finalProject.dataStructures
 *     Created by Braden, Aaron, Janveer, Saba
 *     3/14/2018
 *******************************************************/

public class PatientBinarySearch {

    private LinkedList holder = new LinkedList();
    private PatientQuickSort patientQuickSort = new PatientQuickSort();

    public LinkedList<Integer> bSearchID(LinkedList<Patient> list, int id, int left, int right) {

        holder.clear();
        patientQuickSort.quickSortID(list, left, right);

        int search = id;
        int pointer = (left + right) / 2;

        if (left < right - 1) {
            if (list.get(pointer).getId() == search) {
                holder.add(pointer);
                for (int i = pointer - 1; i >= left; i--) {
                    if (list.get(i).getId() == search) {
                        holder.add(i);
                    } else {
                        i = left;
                    }
                }
                for (int i = pointer + 1; i <= right; i++) {
                    if (list.get(i).getId() == search) {
                        holder.add(i);
                    } else {
                        i = right;
                    }
                }
                return holder;
            } else if (list.get(pointer).getId() < search) {
                bSearchID(list, id, pointer, right);
            } else {
                bSearchID(list, id, left, pointer);
            }
        } else if (pointer == list.size() - 2) {
            pointer = list.size() - 1;
            if (list.get(pointer).getId() == search) {
                holder.add(pointer);
            }
        } else {
            pointer = 0;
            if (list.get(pointer).getId() == search) {
                holder.add(pointer);
            }
        }
        return holder;
    }

    public LinkedList<Integer> bSearchPhoneNumber(LinkedList<Patient> list, long phoneNumber, int left, int right) {

        holder.clear();
        patientQuickSort.quickSortPhoneNumber(list, left, right);

        long search = phoneNumber;
        int pointer = (left + right) / 2;

        if (left < right - 1) {
            if (list.get(pointer).getPatientContactInfo().getPhoneNumber() == search) {
                holder.add(pointer);
                for (int i = pointer - 1; i >= left; i--) {
                    if (list.get(i).getPatientContactInfo().getPhoneNumber() == search) {
                        holder.add(i);
                    } else {
                        i = left;
                    }
                }
                for (int i = pointer + 1; i <= right; i++) {
                    if (list.get(i).getPatientContactInfo().getPhoneNumber() == search) {
                        holder.add(i);
                    } else {
                        i = right;
                    }
                }
                return holder;
            } else if (list.get(pointer).getPatientContactInfo().getPhoneNumber() < search) {
                bSearchPhoneNumber(list, phoneNumber, pointer, right);
            } else {
                bSearchPhoneNumber(list, phoneNumber, left, pointer);
            }
        } else if (pointer == list.size() - 2) {
            pointer = list.size() - 1;
            if (list.get(pointer).getPatientContactInfo().getPhoneNumber() == search) {
                holder.add(pointer);
            }
        } else {
            pointer = 0;
            if (list.get(pointer).getPatientContactInfo().getPhoneNumber() == search) {
                holder.add(pointer);
            }
        }
        return holder;
    }

    public LinkedList<Integer> bSearchUrgency(LinkedList<Patient> list, int urgency, int left, int right) {

        holder.clear();
        patientQuickSort.quickSortUrgency(list, left, right);

        int search = urgency;
        int pointer = (left + right) / 2;

        if (left < right - 1) {
            if (list.get(pointer).getPatientOrganInfo().getUrgency() == search) {
                holder.add(pointer);
                for (int i = pointer - 1; i >= left; i--) {
                    if (list.get(i).getPatientOrganInfo().getUrgency() == search) {
                        holder.add(i);
                    } else {
                        i = left;
                    }
                }
                for (int i = pointer + 1; i <= right; i++) {
                    if (list.get(i).getPatientOrganInfo().getUrgency() == search) {
                        holder.add(i);
                    } else {
                        i = right;
                    }
                }
                return holder;
            } else if (list.get(pointer).getPatientOrganInfo().getUrgency() < search) {
                bSearchUrgency(list, urgency, pointer, right);
            } else {
                bSearchUrgency(list, urgency, left, pointer);
            }
        } else if (pointer == list.size() - 2) {
            pointer = list.size() - 1;
            if (list.get(pointer).getPatientOrganInfo().getUrgency() == search) {
                holder.add(pointer);
            }
        } else {
            pointer = 0;
            if (list.get(pointer).getPatientOrganInfo().getUrgency() == search) {
                holder.add(pointer);
            }
        }
        return holder;
    }


    public LinkedList<Integer> bSearchOrgan(LinkedList<Patient> list, String organ, int left, int right) {

        holder.clear();
        patientQuickSort.quickSortOrgan(list, left, right);

        String search = organ;
        int pointer = (left + right) / 2;

        if (left < right - 1) {
            if (list.get(pointer).getPatientOrganInfo().getOrgan().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
                for (int i = pointer - 1; i >= left; i--) {
                    if (list.get(i).getPatientOrganInfo().getOrgan().compareToIgnoreCase(search) == 0) {
                        holder.add(i);
                    } else {
                        i = left;
                    }
                }
                for (int i = pointer + 1; i <= right; i++) {
                    if (list.get(i).getPatientOrganInfo().getOrgan().compareToIgnoreCase(search) == 0) {
                        holder.add(i);
                    } else {
                        i = right;
                    }
                }
                return holder;
            } else if (list.get(pointer).getPatientOrganInfo().getOrgan().compareToIgnoreCase(search) < 0) {
                bSearchOrgan(list, organ, pointer, right);
            } else {
                bSearchOrgan(list, organ, left, pointer);
            }
        } else if (pointer == list.size() - 2) {
            pointer = list.size() - 1;
            if (list.get(pointer).getPatientOrganInfo().getOrgan().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
            }
        } else {
            pointer = 0;
            if (list.get(pointer).getPatientOrganInfo().getOrgan().compareToIgnoreCase(search) == 0) {
                holder.add(pointer);
            }
        }
        return holder;
    }

    public LinkedList<Integer> bSearchLastName(LinkedList<Patient> list, String lastName, int left, int right) {

        holder.clear();
        patientQuickSort.quickSortLastName(list, left, right);

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
}
