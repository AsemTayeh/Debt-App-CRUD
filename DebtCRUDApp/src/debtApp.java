import java.sql.*;
import java.util.Scanner;

public class debtApp {

    private Connection mainConnector;
    private int currentUserID;
    private int userChoice;
    private int userLoginChoice;

    debtApp() {

        userChoice = -1;
        currentUserID = -1;
        userLoginChoice = -1;

        try {

            mainConnector = DriverManager.getConnection("jdbc:mysql://localhost:3306/debtApp", "root", "root");
        } catch (SQLException e) {

            System.out.println("Error connecting to db (At constructor) / " + e.getMessage());
            mainConnector = null;
        }
    }

    int getUserLoginChoice() {

        return userLoginChoice;
    }
    public void listDebt() {

        Connection listDebtCnctn = mainConnector;
        PreparedStatement listDebtStmt;
        PreparedStatement joinStatement;

        try {
            listDebtStmt = listDebtCnctn.prepareStatement("SELECT * FROM Debt_Table");
            ResultSet listSet = listDebtStmt.executeQuery();
            joinStatement = listDebtCnctn.prepareStatement("SELECT user_name AS joined_name FROM users INNER JOIN Debt_Table ON users.user_ID = Debt_Table.user_ID");
            ResultSet joinSet = joinStatement.executeQuery();

            System.out.println("Record ID\t" + "User Name\t" + "Record Amount\t" + "Transaction Date\t" + "Record note\t");

            while (listSet.next() && joinSet.next()) {
                System.out.printf("%-13s%-13s%-14s%-21s%-13s\n",
                        listSet.getInt("record_ID"),
                        joinSet.getString("joined_name"),
                        listSet.getDouble("record_Amount"),
                        listSet.getDate("record_ID_Date"),
                        listSet.getString("record_note"));
            }
            System.out.println();

        } catch (SQLException e) {

            System.out.println("Error connecting statement (at listDebt function) / " + e.getMessage());
            return;
        }
    }

    public void insertDebtRecord(double recordAmount, String note) {

        Connection insertDebtConnection = mainConnector;
        PreparedStatement insertDebtStmt;
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());

        try {
            insertDebtStmt = insertDebtConnection.prepareStatement("INSERT INTO Debt_Table(record_Amount, record_ID_DATE, record_note, user_ID) VALUES (?,?,?,?)");
            insertDebtStmt.setDouble(1, recordAmount);
            insertDebtStmt.setDate(2, date);
            insertDebtStmt.setString(3, note);
            insertDebtStmt.setInt(4,currentUserID);
            insertDebtStmt.executeUpdate();
            System.out.println("Record inserted successfully");
        } catch (SQLException e) {

            System.out.println("Error connecting statement (at insertDebtRecord function) / " + e.getMessage());
        }
    }

    public void payOffDebt(int recordID) throws SQLException {

        Connection payOff = mainConnector;
        PreparedStatement payOffStmt;
        PreparedStatement checker = payOff.prepareStatement("SELECT * FROM Debt_Table WHERE record_ID = ?");
        checker.setInt(1,recordID);
        ResultSet checkIfRecordExists = checker.executeQuery();

        if (!checkIfRecordExists.isBeforeFirst()) {

            System.out.println("Record " + recordID + " doesn't exist");
            return;
        }

        checkIfRecordExists.next();

        if (checkIfRecordExists.getInt("user_ID") != currentUserID) {

            System.out.println("Cannot delete record (Record doesn't belong to your user)");
            return;
        }

        try {

            payOffStmt = payOff.prepareStatement("DELETE FROM Debt_Table WHERE record_ID = ?");
            payOffStmt.setInt(1,recordID);
            payOffStmt.executeUpdate();
            System.out.println("Record " + recordID + " Deleted successfully");

        } catch (SQLException e) {

            System.out.println("Error connecting statement (at payOffDebt function) / " + e.getMessage());
        }
    }

    public double getDebtTotalByUser(int userID) {

        Connection getTotalCnctn = mainConnector;
        PreparedStatement getTotalStmt;
        double totalDebtToPay = 0;

        try {

            getTotalStmt = getTotalCnctn.prepareStatement("SELECT SUM(record_Amount) AS TOTAL FROM Debt_Table WHERE user_ID = ?");
            getTotalStmt.setInt(1,userID);
            ResultSet totalDebt = getTotalStmt.executeQuery();

            if (totalDebt.next()) {

                totalDebtToPay = totalDebt.getDouble("TOTAL");
            } else {

                System.out.println("No records found in Debt_Table");
            }
        } catch (SQLException e) {

            System.out.println("Error connecting statement (at getDebtTotal function) / " + e.getMessage());
        }

        return totalDebtToPay;
    }

     public void updateRecord(int ID, double amount, String note) {

        Connection update = mainConnector;
        PreparedStatement updateStmt;
        PreparedStatement validator;
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());

        try {

            validator = update.prepareStatement("SELECT user_ID FROM Debt_Table WHERE record_ID = ?");
            validator.setInt(1,ID);
            ResultSet valSet = validator.executeQuery();

            if (valSet.isBeforeFirst()) {
                valSet.next();
                if (valSet.getInt("user_ID") != currentUserID) {

                    System.out.println("Cannot update record (Record belongs to a different user)");
                    return;
                }
            }

            else {

                System.out.println("No debt record with such ID exists");
                return;
            }

            updateStmt = update.prepareStatement("UPDATE Debt_Table SET record_Amount = record_Amount - ?, record_note = ?, record_ID_date = ? WHERE record_ID = ?");
            updateStmt.setDouble(1,amount);
            updateStmt.setString(2,note);
            updateStmt.setDate(3,date);
            updateStmt.setInt(4,ID);
            updateStmt.executeUpdate();
            System.out.println("Record " + ID + " Updated successfully");

        } catch (SQLException e) {

            System.out.println("Error connecting statement (at updateRecord function) / " + e.getMessage());
        }
    }

    void getMaxDebtByUser() {

        Connection getDebt = mainConnector;
        PreparedStatement debtStatement;

        try {

            debtStatement = getDebt.prepareStatement("SELECT MAX(record_Amount) AS max_amount, user_name FROM Debt_Table INNER JOIN users ON users.user_ID = Debt_Table.user_ID GROUP BY user_name");
            ResultSet resultSet = debtStatement.executeQuery();
            if(resultSet.isBeforeFirst()) {

                resultSet.next();
                System.out.println("User "+ resultSet.getString("user_name") + " Has max debt of value: " + resultSet.getDouble("max_amount"));
            }

            else {
                System.out.println("There are no debts...");
                return;
            }

        } catch (SQLException e) {

            System.out.println("Error connecting statement at (getMaxDebtWithUser function) / " + e.getMessage());
        }
    }

    int getUserChoice() {

        return userChoice;
    }

    int getCurrentUserID() {

        return currentUserID;
    }

    void addUser(String userName, int verificationNo) {

        Connection userCon = mainConnector;
        PreparedStatement userStmt;
        PreparedStatement validator;
        try {

            userStmt = userCon.prepareStatement("INSERT INTO USERS (user_name, user_verification_number) VALUES (?,?)");
            userStmt.setString(1,userName);
            userStmt.setInt(2,verificationNo);
            userStmt.executeUpdate();

            System.out.println("User " + userName + " added successfully");

            validator = userCon.prepareStatement("SELECT user_ID FROM users WHERE user_Name = ? AND user_verification_number = ?");
            validator.setString(1,userName);
            validator.setInt(2, verificationNo);
            ResultSet val = validator.executeQuery();

            while (val.next()) {

                currentUserID = val.getInt("user_ID");
            }

            System.out.println("YOUR USER ID IS: " + getCurrentUserID());

        } catch (SQLException e) {

            System.out.println("Error connecting statement at (addUser function) / " + e.getMessage());
        }
    }

    void listUsers() {

        Connection listCon = mainConnector;
        PreparedStatement listStmt;

        try {

            listStmt = listCon.prepareStatement("SELECT * FROM users");
            ResultSet listSet = listStmt.executeQuery();
            System.out.printf("%-10s %-15s\n", "User_ID", "User_Name");

            while (listSet.next()) {

                int userId = listSet.getInt("user_ID");
                String userName = listSet.getString("user_name");
                System.out.printf("%-10d %-15s\n", userId, userName);
            }

        } catch (SQLException e) {

            System.out.println("Error connecting statement at (listUsers) / " + e.getMessage());
        }
    }

    void updateUser(String updatedName) throws SQLException {

        Connection updateCon = mainConnector;
        PreparedStatement updatedStmt;
        PreparedStatement validator = updateCon.prepareStatement("SELECT user_name FROM users WHERE user_ID = ?");
        validator.setInt(1,currentUserID);
        ResultSet val = validator.executeQuery();

        if (!val.isBeforeFirst()) {

            System.out.println("Invalid user ID, cannot update non-existing users");
            return;
        }

        try {

            updatedStmt = updateCon.prepareStatement("UPDATE users SET user_name = ? WHERE user_ID = ?");
            updatedStmt.setString(1,updatedName);
            updatedStmt.setInt(2,currentUserID);
            updatedStmt.executeUpdate();
            System.out.println("User " + currentUserID + " Updated successfully");
        } catch (SQLException e) {

            System.out.println("Error connecting statement at (updateUser) / " + e.getMessage());
        }
    }

    boolean logIn(int userID, int verNo) {

        Connection logCon = mainConnector;
        PreparedStatement logStmt;
        String name = null;

        try {

            logStmt = logCon.prepareStatement("SELECT user_ID, user_verification_number, user_name FROM users WHERE user_ID = ? AND user_verification_number = ?");
            logStmt.setInt(1,userID);
            logStmt.setInt(2,verNo);
            ResultSet logInChecker = logStmt.executeQuery();

            if (!logInChecker.isBeforeFirst()) {
                System.out.println("Incorrect user ID or verification number, try again");
                return false;
            }

            while (logInChecker.next()) {

                if (logInChecker.getInt("user_ID") != userID) {

                    System.out.println("Incorrect user ID, try again");
                    return false;
                }

                else if (logInChecker.getInt("user_verification_number") != verNo) {

                    System.out.println("Incorrect verification Number, try again");
                    return false;
                }
                name = logInChecker.getString("user_name");
            }

            System.out.println("Logged in as " + name + " Successfully");
            currentUserID = userID;
            return true;

        } catch (SQLException e) {

            System.out.println("Error connecting statement at (logIn) / " + e.getMessage());
        }

        return false;
    }
    boolean deleteUser(int userID) {

        Connection deleteCon = mainConnector;
        PreparedStatement deleteStmt;
        PreparedStatement validator;

        try {
            validator = deleteCon.prepareStatement("SELECT * FROM users WHERE user_ID = ?");
            validator.setInt(1,userID);
            ResultSet val = validator.executeQuery();

            if (!val.next()) {

                System.out.println("User " + userID + " doesn't exist");
                return false;
            }

            if (userID != currentUserID) {

                System.out.println("This is not the current active user, you cannot delete user " + userID);
                return false;
            }

            deleteStmt = deleteCon.prepareCall("DELETE FROM users WHERE user_ID = ?");
            deleteStmt.setInt(1,userID);
            deleteStmt.executeUpdate();
            System.out.println("User " + userID + " deleted successfully");

        } catch (SQLException e) {

            System.out.println("Error connecting statement at (deleteUser) / " + e.getMessage());
        } // AFTER DELETE YOU NEED TO PROMPT LOGIN/ADDING USER, CANNOT CONTINUE WITHOUT.

        return true;
    }

    boolean userLoginEventHandler() {

        System.out.println("\n\t\tLog-In Page\n");
        System.out.println("Pick action: \n");
        System.out.println("I) Log-in");
        System.out.println("II) Create user\n");
        System.out.print("Enter action here: ");

        Scanner userInput = new Scanner(System.in);
        int inputValue = userInput.nextInt();

        if (inputValue == 1) {

            System.out.print("Enter user ID: ");
            int userID = userInput.nextInt();
            System.out.print("Enter your unique verification number: ");
            int userVerification = userInput.nextInt();

            if (logIn(userID, userVerification)) {
                userLoginChoice = 3;
                return true;
            }
            else {

                return false;
            }
        }

        else if (inputValue == 2) {

            userInput.nextLine();
            System.out.print("Enter your user name: ");

            String userName = userInput.nextLine();

            System.out.print("Enter your verification number (TIP: Do not share with anyone): ");

            int verNo = userInput.nextInt();
            addUser(userName,verNo);
            userLoginChoice = 3;
            return true;
        }
        else {

            System.out.println("Incorrect value, please choose from 1-2");
            userLoginChoice = -1;
            return false;
        }
    }
    public void eventHandler() throws SQLException {

        System.out.println("\n\t\tDebt Tracker\n");
        System.out.println("Select action to take: \n");
        System.out.println("1) Insert debt record (Date is logged automatically)");
        System.out.println("2) Update debt record");
        System.out.println("3) List all debt records");
        System.out.println("4) Delete debt record");
        System.out.println("5) See total debt by user");
        System.out.println("6) See user with highest debt record");
        System.out.println("7) Delete user");
        System.out.println("8) List all users");
        System.out.println("9) Update current user");
        System.out.println("10) Change user");
        System.out.println("11) Exit application \n");
        System.out.print("Enter action here: ");

        Scanner userInput = new Scanner(System.in);
        int inputValue = userInput.nextInt();

        System.out.println();

        if (inputValue == 1) {
            System.out.print("Enter the debt amount: ");
            double debtOne = userInput.nextDouble();
            userInput.nextLine(); // Consume newline character after nextDouble

            System.out.print("Enter your note for this record: ");
            String noteOne = userInput.nextLine();

            insertDebtRecord(debtOne, noteOne);
        }

        else if (inputValue == 2) {
            System.out.print("Enter the record ID you wish to update from the following records: \n");
            listDebt();
            int recIDTwo = userInput.nextInt();

            if (recIDTwo != currentUserID) {

                System.out.println("This record belongs to another user, cannot update.");
                return;
            }

            System.out.print("Enter the amount you wish to debit from this record: ");
            double debtTwo = userInput.nextDouble();
            userInput.nextLine(); // Consume newline character after nextDouble

            System.out.print("Enter your updated note: ");
            String noteTwo = userInput.nextLine();

            updateRecord(recIDTwo, debtTwo, noteTwo);
        }

        else if (inputValue == 3) {
            listDebt();
        }

        else if (inputValue == 4) {
            System.out.println("Select a record you'd like to delete from the following records:");
            listDebt();
            System.out.print("Enter Record here: ");

            int recIDFour = userInput.nextInt();
            payOffDebt(recIDFour);
        }

        else if (inputValue == 5) {

            System.out.println("Enter user ID to see total debt: ");
            int userID = userInput.nextInt();
            System.out.println("Debt total is: " + getDebtTotalByUser(userID));
        }

        else if (inputValue == 6) {
            getMaxDebtByUser();
        }

        else if (inputValue == 7) {

            System.out.println("Enter the user id you'd like to delete, from the list of users below: ");
            listUsers();
            System.out.print("Enter user ID to delete here: ");
            int userID = userInput.nextInt();
            if (deleteUser(userID))
                userLoginEventHandler();
        }

        else if (inputValue == 8) {

            listUsers();
        }

        else if (inputValue == 9) {

            System.out.print("Enter your updated name: ");
            userInput.nextLine();
            String input = userInput.nextLine();
            updateUser(input);
        }

        else if (inputValue == 10) {

            userLoginEventHandler();
        }

        else if (inputValue == 11) {

            System.out.println("Exiting application...");
            userChoice = 11;
        }

        else {

            System.out.println("Incorrect value, please choose a value from 1-10");
        }
    }
}
