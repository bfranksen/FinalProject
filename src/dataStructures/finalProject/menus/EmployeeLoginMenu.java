package dataStructures.finalProject.menus;

import dataStructures.finalProject.utilities.Utils;
import dataStructures.finalProject.employee.Employee;
import dataStructures.finalProject.employee.EmployeeBinarySearch;
import dataStructures.finalProject.utilities.ConsoleColors;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/****************************************
 *     FinalProject
 *     finalProject.dataStructures.menus
 *     Created by Braden
 *     5/3/2018
 ****************************************/

public class EmployeeLoginMenu {

    private LinkedList<Employee> employeeList;
    private HashMap<String, Long> employeePasswords;

    public EmployeeLoginMenu() {
        this.employeeList = new LinkedList<>();
        this.employeePasswords = new HashMap<>();
        readEmployeeDataFromFile();
    }

    private void readEmployeeDataFromFile() {
        File employeeFile = new File("src/Employee List");
        Scanner reader = null;
        try {
            reader = new Scanner(employeeFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Utils.empIdPointer = reader.nextInt();
            while (reader.hasNextLine()) {
                String flag = reader.next();
                if (!flag.equalsIgnoreCase("login")) {
                    employeeList.add(new Employee(flag, reader.next(), reader.next(), reader.nextInt()));
                } else if (reader.hasNextLine()) {
                    reader.nextLine();
                    break;
                }
            }
            while (reader.hasNextLine()) {
                employeePasswords.put(reader.next(), reader.nextLong());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeEmployeeDataToFile() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("src/Employee List"), "utf-8"))) {
            writer.write(String.valueOf(Utils.empIdPointer));
            for (Employee e : employeeList) {
                writer.write("\n" + e.getFirstName() + " " + e.getLastName() + " " + e.getUserName() + " " + e.getEmpId());
            }
            writer.write("\nLogin");
            for (String k : employeePasswords.keySet()) {
                writer.write("\n" + k + " " + employeePasswords.get(k));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (menuOptions()) {
            menuOptions();
        }
    }

    private boolean menuOptions() {
        try {
            int response;
            System.out.println(ConsoleColors.YELLOW + "\n Employee Login\n----------------" + ConsoleColors.RESET);
            System.out.println("\t1. Login");
            System.out.println("\t2. Create Employee");
            System.out.println("\t3. Remove Employee");
            System.out.println("\t4. View Employee");
            System.out.println("\t5. Reset Password");
            System.out.println("\t6. Exit Program");
            response = Integer.parseInt(Utils.input.next());
            if (response < 1 || response > 6) {
                System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
            } else {
                runOption(response);
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
        }
        return true;
    }

    private void runOption(int response) {
        switch (response) {
            case 1:
                if (login()) {
                    writeEmployeeDataToFile();
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.run();
                }
                break;
            case 2:
                createEmployee();
                break;
            case 3:
                removeEmployee();
                break;
            case 4:
                viewEmployee();
                break;
            case 5:
                resetPassword();
                break;
            case 6:
                writeEmployeeDataToFile();
                System.out.println("Goodbye!");
                System.exit(0);
                break;
        }
    }

    private boolean login() {
        boolean login = false;
        int loginTries = 0;
        System.out.print("\nUser Name: ");
        String username = Utils.input.next();
        System.out.print("Password: ");
        String password = Utils.input.next();
        if (!employeePasswords.containsKey(username)) {
            System.out.println(ConsoleColors.RED + "\n*** The username or password is incorrect. Try again. ***" + ConsoleColors.RESET);
            return login;
        }
        while (hashPassword(password) != employeePasswords.get(username)) {
            loginTries++;
            if (loginTries < 3) {
                System.out.println(ConsoleColors.RED + "\n*** Incorrect password. Try again. ***" + ConsoleColors.RESET);
                System.out.print("\nUser Name: " + ConsoleColors.BLUE + username + ConsoleColors.RESET);
                System.out.print("\nPassword: ");
                password = Utils.input.next();
            } else {
                System.out.println(ConsoleColors.RED + "\n***Too many login failures. You will need to start over. ***" + ConsoleColors.RESET);
                return login;
            }
        }
        if (hashPassword(password) == employeePasswords.get(username)) {
            login = true;
        }
        return login;
    }

    private Employee createEmployee() {
        System.out.print("\nFirst Name: ");
        String firstName = Utils.input.next();
        System.out.print("Last Name: ");
        String lastName = Utils.input.next();
        System.out.print("Username: ");
        String username = Utils.input.next();
        String password1 = "a";
        String password2 = "b";
        int numTries = 0;
        while (!password1.equals(password2)) {
            if (numTries >= 3) {
                System.out.println(ConsoleColors.RED + "\n*** Too many failures. You will need to start over. ***" + ConsoleColors.RESET);
                return null;
            }
            System.out.print("\nPassword: ");
            password1 = Utils.input.next();
            System.out.print("Confirm Password: ");
            password2 = Utils.input.next();
            if (!password1.equals(password2)) {
                numTries++;
                if (numTries < 3) {
                    System.out.println(ConsoleColors.RED + "\n*** Passwords do not match. Try again. ***" + ConsoleColors.RESET);
                }
            }
        }
        Utils.empIdPointer++;
        Employee employee = new Employee(firstName, lastName, username, Utils.empIdPointer);
        employeeList.add(employee);
        long passwordLong = hashPassword(password1);
        employeePasswords.put(employee.getUserName(), passwordLong);
        System.out.println(ConsoleColors.YELLOW + "\n\t" + employee.getFirstName() + " " + employee.getLastName() + " added to employee database." +
                ConsoleColors.BLUE + "\n\t\tUser Name: \t\t" + ConsoleColors.RESET + employee.getUserName() +
                ConsoleColors.BLUE + "\n\t\tEmployee ID: \t" + ConsoleColors.RESET + employee.getEmpId());
        return employee;
    }

    private Employee removeEmployee() {
        LinkedList<Integer> searchResults = searchForEmployee();
        Employee[] removeCandidates = new Employee[searchResults.size()];
        Employee removedEmployee;

        int tempPatientIndex;
        int chosenPatient;

        if (searchResults.size() == 0) {
            removedEmployee = null;
        } else if (searchResults.size() == 1) {
            removedEmployee = employeeList.get(searchResults.get(0));
            if (Utils.doubleCheck(removedEmployee, "remove")) {
                employeeList.remove(removedEmployee);
                employeePasswords.remove(removedEmployee.getUserName());
                if (removedEmployee != null)
                    System.out.println(ConsoleColors.YELLOW + "\n\tEmployee " + removedEmployee.getFirstName() + " " + removedEmployee.getLastName() + " has been removed." + ConsoleColors.RESET);
            } else {
                removedEmployee = null;
                System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
            }
        } else {
            while (true) {
                tempPatientIndex = 0;
                System.out.println(ConsoleColors.CYAN + "\nWhich employee would you like to remove?" + ConsoleColors.RESET);
                for (Integer i : searchResults) {
                    removeCandidates[tempPatientIndex] = employeeList.get(i);
                    System.out.println("\t" + (tempPatientIndex + 1) + ". " + removeCandidates[tempPatientIndex]);
                    tempPatientIndex++;
                }
                try {
                    chosenPatient = Integer.parseInt(Utils.input.next());
                    if (chosenPatient < 1 || chosenPatient > tempPatientIndex) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        removedEmployee = removeCandidates[chosenPatient - 1];
                        if (Utils.doubleCheck(removedEmployee, "remove")) {
                            employeeList.remove(removedEmployee);
                            employeePasswords.remove(removedEmployee.getUserName());
                            if (removedEmployee != null)
                                System.out.println(ConsoleColors.YELLOW + "\n\tEmployee " + removedEmployee.getFirstName() + " " + removedEmployee.getLastName() + " has been removed." + ConsoleColors.RESET);
                        } else {
                            removedEmployee = null;
                            System.out.println(ConsoleColors.YELLOW + "\n\tNo changes have been made.");
                        }
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            }
        }
        return removedEmployee;
    }

    private void viewEmployee() {
        LinkedList<Integer> searchResults = searchForEmployee();
        if (searchResults.size() == 0) {
            return;
        } else {
            System.out.println(ConsoleColors.CYAN + "\nFound Employee(s):" + ConsoleColors.RESET);
            for (Integer i : searchResults) {
                System.out.println(ConsoleColors.RED + "\tEmployee: " + ConsoleColors.RESET + employeeList.get(i).getFirstName() + " " + employeeList.get(i).getLastName() +
                        "\n\t\tUsername:\t\t" + employeeList.get(i).getUserName() +
                        "\n\t\tEmployee ID:\t" + employeeList.get(i).getEmpId());
            }
        }
        return;
    }

    private void resetPassword() {
        System.out.print(ConsoleColors.CYAN + "\nWhat is your username?" + ConsoleColors.RESET + "\n\tUsername: " + ConsoleColors.RESET);
        String username = Utils.input.next();
        Employee employee;
        if (employeePasswords.containsKey(username)) {
            employee = employeeList.get(EmployeeBinarySearch.bSearchUsername(employeeList, username, 0, employeeList.size() - 1).get(0));
            System.out.println(ConsoleColors.CYAN + "\nWould you like to reset " + employee.getFirstName() + " " + employee.getLastName() + "'s password?" + ConsoleColors.RESET);
            System.out.println("\t1. Yes\n\t2. No");
        } else {
            System.out.println(ConsoleColors.RED + "\n*** There are no employees with that username. Please try again .***" + ConsoleColors.RESET);
            return;
        }
        int response;
        try {
            response = Integer.parseInt(Utils.input.next());
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
            resetPassword();
            return;
        }
        if (response < 1 || response > 2) {
            System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
            resetPassword();
            return;
        } else {
            switch (response) {
                case 1:
                    String password1 = "a";
                    String password2 = "b";
                    int numTries = 0;
                    while (!password1.equals(password2)) {
                        if (numTries >= 3) {
                            System.out.println(ConsoleColors.RED + "\n*** Too many failures. You will need to start over. ***" + ConsoleColors.RESET);
                            break;
                        }
                        System.out.print("\nNew Password: ");
                        password1 = Utils.input.next();
                        System.out.print("Confirm Password: ");
                        password2 = Utils.input.next();
                        if (!password1.equals(password2)) {
                            numTries++;
                            if (numTries < 3) {
                                System.out.println(ConsoleColors.RED + "\n*** Passwords do not match. Try again. ***" + ConsoleColors.RESET);
                            }
                        }
                    }
                    long passwordLong = hashPassword(password1);
                    employeePasswords.put(employee.getUserName(), passwordLong);
                    System.out.println(ConsoleColors.YELLOW + "\n\t" + employee.getFirstName() + " " + employee.getLastName() + "'s password has been updated." + ConsoleColors.RESET);
                    break;
                case 2:
                    System.out.println(ConsoleColors.YELLOW + "\n\t" + employee.getFirstName() + " " + employee.getLastName() + "'s password has not been updated." + ConsoleColors.RESET);
                    break;
            }
        }
    }

    private LinkedList<Integer> searchForEmployee() {
        LinkedList<Integer> searchResults = new LinkedList<>();
        if (employeeList.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\n*** There are no employees! ***" + ConsoleColors.RESET);
        } else {
            while (true) {
                System.out.println(ConsoleColors.CYAN + "\nSearch by:" + ConsoleColors.RESET);
                System.out.println("\t1. Employee ID");
                System.out.println("\t2. Last Name");
                System.out.println("\t3. Username");
                try {
                    int response = Integer.parseInt(Utils.input.next());
                    if (response < 1 || response > 3) {
                        System.out.println(ConsoleColors.RED + "\n*** Not a valid command. Please try again. ***" + ConsoleColors.RESET);
                        continue;
                    } else {
                        switch (response) {
                            case 1:
                                int id;
                                System.out.print("\nID of Employee: ");
                                id = Integer.parseInt(Utils.input.next());
                                searchResults = EmployeeBinarySearch.bSearchID(employeeList, id, 0, employeeList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no employees with this ID ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 2:
                                String lastName;
                                System.out.print("\nLast Name: ");
                                lastName = Utils.input.next();
                                searchResults = EmployeeBinarySearch.bSearchLastName(employeeList, lastName, 0, employeeList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no employees with this last name ***" + ConsoleColors.RESET);
                                }
                                break;
                            case 3:
                                String username;
                                System.out.print("\nUsername: ");
                                username = Utils.input.next();
                                searchResults = EmployeeBinarySearch.bSearchUsername(employeeList, username, 0, employeeList.size() - 1);
                                if (searchResults.isEmpty()) {
                                    System.out.println(ConsoleColors.RED + "\n*** There are no employees that use this username ***" + ConsoleColors.RESET);
                                }
                                break;
                        }
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "\n*** Not a valid input. Please try again. ***" + ConsoleColors.RESET);
                    continue;
                }
            }
        }
        return searchResults;
    }

    private long hashPassword(String password) {
        long pass = 31;
        for (int i = 0; i < password.length(); i++) {
            pass = (pass * 13) + (31 * Character.valueOf(password.charAt(i)));
        }
        return pass;
    }

    public LinkedList<Employee> getEmployeeList() {
        return employeeList;
    }
}
