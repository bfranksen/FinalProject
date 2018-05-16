package dataStructures.finalProject.patient.blood;

import dataStructures.finalProject.patient.Patient;
import dataStructures.finalProject.utilities.BinarySearch;
import dataStructures.finalProject.utilities.QuickSort;

import java.util.LinkedList;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.patient.blood
 *     Created by Braden
 *     5/4/2018
 ****************************************/

public class PatientBloodSearch {

    public int[] bloodSearch(PatientBloodNode[] BloodyA, int bloodIndex, int bloodAmountNeeded, int[] diffBlood,
                             LinkedList<Patient> list) {


        LinkedList<Patient> sortedList = QuickSort.quickSortBloodType(list, 0, list.size() - 1);
        int bloodLeft = bloodAmountNeeded;
        BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() - diffBlood[bloodIndex]);
        int placeholder = diffBlood[bloodIndex];

        if (BloodyA[bloodIndex].getBloodUnitAmount() >= bloodLeft) {
            if (bloodLeft > 0) {
                diffBlood[bloodIndex] = +bloodLeft;
            }
            BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() + placeholder);

            // calling program should extract the blood.
            return diffBlood;

        } else {

            // O or at the start
            if (BloodyA[bloodIndex].getFirstChild() == null) {
                if (BloodyA[bloodIndex].getParallelCousin() == null) {

                    bloodLeft = bloodLeft - BloodyA[bloodIndex].getBloodUnitAmount();
                    if (BloodyA[bloodIndex].getBloodUnitAmount() > 0) {
                        diffBlood[bloodIndex] = +BloodyA[bloodIndex].getBloodUnitAmount();
                    }
                    BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() + placeholder);

                    // Calling program should also mention that there is not enough blood.
                    return diffBlood;
                }

                bloodLeft = bloodLeft - BloodyA[bloodIndex].getBloodUnitAmount();
                if (BloodyA[bloodIndex].getBloodUnitAmount() > 0) {
                    diffBlood[bloodIndex] = +BloodyA[bloodIndex].getBloodUnitAmount();
                }
                BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() + placeholder);

                // move into
                diffBlood = bloodSearch(BloodyA, bloodIndex + 4, bloodLeft, diffBlood, sortedList);

                return diffBlood;

            }

            diffBlood = BloodSearch2(BloodyA, bloodIndex, bloodLeft, diffBlood, sortedList);

            return diffBlood;

        }
    }

    public int[] BloodSearch2(PatientBloodNode[] BloodyA, int bloodIndex, int bloodAmountNeeded, int[] diffBlood, LinkedList<Patient> sortedList) {

        int bloodLeft = bloodAmountNeeded;
        BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() - diffBlood[bloodIndex]);
        int placeholder = diffBlood[bloodIndex];

        if (BloodyA[bloodIndex].getBloodUnitAmount() >= bloodLeft) {
            if (bloodLeft > 0) {
                diffBlood[bloodIndex] = +bloodLeft;
            }
            BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() + placeholder);
            //calling program should extract the blood.
            return diffBlood;

        }


        bloodLeft = bloodLeft - BloodyA[bloodIndex].getBloodUnitAmount();
        if (BloodyA[bloodIndex].getBloodUnitAmount() > 0) {
            diffBlood[bloodIndex] = +BloodyA[bloodIndex].getBloodUnitAmount();
        }
        BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() + placeholder);


        //O or  at the start  Top
        if (BloodyA[bloodIndex].getFirstChild() == null) {
            if (BloodyA[bloodIndex].getParallelCousin() == null) {


                //Calling program should also mention that there is not enough blood.
                return diffBlood;
            }

            //O+

            //Do Nothing, return back to the A or B so they can move over to negative


            return diffBlood;

        }

        //if second child is null is either A, B or A- or B-
        if (BloodyA[bloodIndex].getSecondChild() == null) {
            //A- or B-

            if (BloodyA[bloodIndex].getParallelCousin() == null) {


                // move to 0-

                diffBlood = BloodSearch2(BloodyA, 7, bloodLeft, diffBlood, sortedList);

                return diffBlood;
            }

            //A+ or B+ move to 0+\

            int bloodLeft2 = bloodLeft - BloodyA[3].getBloodUnitAmount();

            diffBlood = BloodSearch2(BloodyA, 3, bloodLeft, diffBlood, sortedList);
            // then move to A- or B-


            if (bloodLeft2 > 0) {


                if (bloodIndex == 1) {


                    diffBlood = BloodSearch2(BloodyA, 5, bloodLeft2, diffBlood, sortedList);
                }

                if (bloodIndex == 2) {

                    diffBlood = BloodSearch2(BloodyA, 6, bloodLeft2, diffBlood, sortedList);
                }
            }

            return diffBlood;
        }


        // AB -

        if (BloodyA[bloodIndex].getParallelCousin() == null) {
            int aBlood = BloodyA[5].getBloodUnitAmount();
            int bBlood = BloodyA[6].getBloodUnitAmount();


            LinkedList<Integer> AM = BinarySearch.bSearchBloodType(sortedList, "A-", 0, sortedList.size() - 1);
            LinkedList<Integer> BM = BinarySearch.bSearchBloodType(sortedList, "B-", 0, sortedList.size() - 1);

            int aUsers = (AM.size() > 0 ? AM.size() : 1);
            int bUsers = (BM.size() > 0 ? BM.size() : 1);

            double aSupplyDays = aBlood + 1 / aUsers;
            double bSupplyDays = bBlood + 1 / bUsers;

            if (aSupplyDays >= bSupplyDays) {

                double ratio = bSupplyDays / (aSupplyDays + bSupplyDays);
                double bTaker = bloodAmountNeeded * ratio;
                int bTake = (int) bTaker;
                int aTake = bloodAmountNeeded - bTake;

                diffBlood = BloodSearch2(BloodyA, 5, aTake, diffBlood, sortedList);
                diffBlood = BloodSearch2(BloodyA, 6, bTake, diffBlood, sortedList);

            }

            if (bSupplyDays > aSupplyDays) {

                double ratio = aSupplyDays / (aSupplyDays + bSupplyDays);
                double aTaker = bloodAmountNeeded * ratio;
                int aTake = (int) aTaker;
                int bTake = bloodAmountNeeded - aTake;

                diffBlood = BloodSearch2(BloodyA, 5, aTake, diffBlood, sortedList);
                diffBlood = BloodSearch2(BloodyA, 6, bTake, diffBlood, sortedList);

            }


            return diffBlood;

        }

        diffBlood = BloodSearch3(BloodyA, bloodIndex, bloodLeft, diffBlood, sortedList);

        return diffBlood;

    }


    // for AB+
    public int[] BloodSearch3(PatientBloodNode[] BloodyA, int bloodIndex, int bloodAmountNeeded, int[] diffBlood, LinkedList<Patient> sortedlist) {
        int bloodLeft = bloodAmountNeeded;
        BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() - diffBlood[bloodIndex]);
        int placeholder = diffBlood[bloodIndex];


        if (BloodyA[bloodIndex].getBloodUnitAmount() >= bloodLeft) {

            diffBlood[bloodIndex] = diffBlood[bloodIndex] + bloodLeft;
            BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() + placeholder);


            //calling program should extract the blood.
            return diffBlood;

        }


        bloodLeft = bloodLeft - BloodyA[bloodIndex].getBloodUnitAmount();

        diffBlood[bloodIndex] = diffBlood[bloodIndex] + BloodyA[bloodIndex].getBloodUnitAmount();
        BloodyA[bloodIndex].setBloodUnitAmount(BloodyA[bloodIndex].getBloodUnitAmount() + placeholder);


        //O or  at the start  Top
        if (BloodyA[bloodIndex].getFirstChild() == null) {
            if (BloodyA[bloodIndex].getParallelCousin() == null) {


                //Calling program should also mention that there is not enough blood.
                return diffBlood;
            }

            //O+
            if (bloodIndex == 3) {
                diffBlood = BloodSearch3(BloodyA, 4, bloodLeft, diffBlood, sortedlist);
            }


            //Do Nothing, return back to the A or B so they can move over to negative


            return diffBlood;

        }


        //if second child is null is either A, B or A- or B-
        if (BloodyA[bloodIndex].getSecondChild() == null) {
            //A- or B-

            if (BloodyA[bloodIndex].getParallelCousin() == null) {

                // move to 0-

                diffBlood = BloodSearch3(BloodyA, 7, bloodLeft, diffBlood, sortedlist);

                return diffBlood;
            }

            //A+ or B+ move to 0+\


            diffBlood = BloodSearch3(BloodyA, 3, bloodLeft, diffBlood, sortedlist);


            // then move to AB-


            return diffBlood;
        }


        // AB -

        if (BloodyA[bloodIndex].getParallelCousin() == null) {


            int aBlood = BloodyA[5].getBloodUnitAmount();
            int bBlood = BloodyA[6].getBloodUnitAmount();

            LinkedList<Integer> AM = BinarySearch.bSearchBloodType(sortedlist, "A-", 0, sortedlist.size() - 1);
            LinkedList<Integer> BM = BinarySearch.bSearchBloodType(sortedlist, "B-", 0, sortedlist.size() - 1);

            int aUsers = (AM.size() > 0 ? AM.size() : 1);
            int bUsers = (BM.size() > 0 ? BM.size() : 1);

            double aSupplyDays = aBlood + 1 / aUsers;
            double bSupplyDays = bBlood + 1 / bUsers;

            if (aSupplyDays >= bSupplyDays) {

                double ratio = bSupplyDays / (aSupplyDays + bSupplyDays);
                double bTaker = bloodAmountNeeded * ratio;
                int bTake = (int) bTaker;
                int aTake = bloodAmountNeeded - bTake;

                diffBlood = BloodSearch3(BloodyA, 5, aTake, diffBlood, sortedlist);
                diffBlood = BloodSearch3(BloodyA, 6, bTake, diffBlood, sortedlist);

            }

            if (bSupplyDays > aSupplyDays) {

                double ratio = aSupplyDays / (aSupplyDays + bSupplyDays);
                double aTaker = bloodAmountNeeded * ratio;
                int aTake = (int) aTaker;
                int bTake = bloodAmountNeeded - aTake;

                diffBlood = BloodSearch3(BloodyA, 5, aTake, diffBlood, sortedlist);
                diffBlood = BloodSearch3(BloodyA, 6, bTake, diffBlood, sortedlist);

            }


            return diffBlood;

        }

        //AB+  rules


        int aBlood = BloodyA[5].getBloodUnitAmount() + BloodyA[1].getBloodUnitAmount();
        int bBlood = BloodyA[6].getBloodUnitAmount() + BloodyA[2].getBloodUnitAmount();

        LinkedList<Integer> AM = BinarySearch.bSearchBloodType(sortedlist, "A-", 0, sortedlist.size() - 1);
        LinkedList<Integer> BM = BinarySearch.bSearchBloodType(sortedlist, "B-", 0, sortedlist.size() - 1);
        LinkedList<Integer> AP = BinarySearch.bSearchBloodType(sortedlist, "A+", 0, sortedlist.size() - 1);
        LinkedList<Integer> BP = BinarySearch.bSearchBloodType(sortedlist, "B+", 0, sortedlist.size() - 1);

        int aUsers = (AM.size() > 0 ? AM.size() : 1) + (AP.size() > 0 ? AP.size() : 1);
        int bUsers = (BM.size() > 0 ? BM.size() : 1) + (BP.size() > 0 ? BP.size() : 1);

        double aSupplyDays = aBlood + 1 / aUsers;
        double bSupplyDays = bBlood + 1 / bUsers;


        if (aSupplyDays >= bSupplyDays) {

            double ratio = bSupplyDays / (aSupplyDays + bSupplyDays);

            double bTaker = bloodAmountNeeded * ratio;
            int bTake = (int) bTaker;
            int aTake = bloodAmountNeeded - bTake;

            diffBlood = BloodSearch3(BloodyA, 1, aTake, diffBlood, sortedlist);
            diffBlood = BloodSearch3(BloodyA, 2, bTake, diffBlood, sortedlist);

        }

        if (bSupplyDays > aSupplyDays) {

            double ratio = aSupplyDays / (aSupplyDays + bSupplyDays);
            double aTaker = bloodAmountNeeded * ratio;
            int aTake = (int) aTaker;
            int bTake = bloodAmountNeeded - aTake;

            diffBlood = BloodSearch3(BloodyA, 1, aTake, diffBlood, sortedlist);
            diffBlood = BloodSearch3(BloodyA, 2, bTake, diffBlood, sortedlist);

        }


        return diffBlood;
    }
}