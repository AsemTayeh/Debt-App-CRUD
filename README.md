# DebtApp README
## Introduction
DebtApp is a Java application designed for managing debt records using a MySQL database backend. This README provides an overview of the functions implemented in the debtApp class and their functionalities.

### TABLES OVERVIEW:

users table: Consists of a user_ID column: int (Primary key) (Auto_Increment) / user_name column: varchar(25) / user_verification_number: int.

Debt_Table: record_ID column: int (Primary key) (Auto_Increment) / record_Amount column: numeric(8,2) / record_ID_Date: date / record_note: varchar(255) / user_ID: int (Foreign key, References users, Cascade on delete).

### User Login Process:

The program enters a while loop, which continues as long as the user's login choice is not equal to 3.

Inside the loop, the userLoginEventHandler() method of the debtApp class is called.

The user is prompted to either log in or create a new user profile.

If the user chooses to log in, they provide their user ID and verification number.

The provided credentials are validated against the database.

If the login is successful, the user is logged in and the loop continues.

If the user chooses to create a new user profile, they provide a username and a verification number.

A new user profile is created in the database, and the user is automatically logged in.

Once the user chooses to log in or create a user profile, the loop terminates, with the variable userLoginChoice being updated to 3.


### Log-in (Option I):

User selects option I from the login page.

User is prompted to enter their user ID.

User enters their user ID.

User is prompted to enter their verification number.

User enters their verification number.

The logIn() method is called with the provided user ID and verification number.

The program verifies the user's credentials against the database.

If the credentials are valid, the user is logged in, and a success message is displayed, and the user is forwarded to the main application.

If the credentials are invalid, an error message is displayed, and the user is prompted to try again.


### Create User (Option II):

User selects option II from the login page.

User is prompted to enter their desired user name.

User enters their desired user name.

User is prompted to enter a verification number.

User enters a verification number.

The addUser() method is called with the provided user name and verification number.

A new user profile is created in the database with the provided details.

The user is automatically logged in using the newly created profile, and a success message is displayed.

The user is informed of their newly assigned user ID.


### Invalid Input:

If the user enters an invalid option (neither I nor II), an error message is displayed.

The user is prompted to choose a valid option (either I or II).

### Main Application Loop:

After successful login, the program enters another while loop, which continues as long as the user's choice is not equal to 11.

Inside this loop, the eventHandler() method of the debtApp class is called.

The user is presented with a menu of actions they can perform within the application.

Based on the user's choice, the corresponding functionality is executed.

This loop allows the user to perform various actions such as inserting or updating debt records, viewing debt totals, managing users, and exiting the application.

Once the user chooses to exit the application by selecting option 11, the loop terminates, and the program exits.


### Insert Debt Record (Option 1):

User selects option 1 from the menu.

User is prompted to enter the amount of debt and a note for the record.

The insertDebtRecord() method is called with the provided amount and note.

The debt record is added to the database, and a success message is displayed.



### Update Debt Record (Option 2):

User selects option 2 from the menu.
User is presented with a list of debt records to choose from.

User selects the record they want to update by entering its ID.

User enters the updated amount and note for the record.

The updateRecord() method is called with the provided ID, amount, and note.

The specified debt record is updated in the database, and a success message is displayed.



### List All Debt Records (Option 3):

User selects option 3 from the menu.

The listDebt() method is called, retrieving all debt records from the database.

The debt records are displayed to the user, showing the record ID, user name, amount, date, and note.


### Delete Debt Record (Option 4):

User selects option 4 from the menu.

User is presented with a list of debt records to choose from.

User selects the record they want to delete by entering its ID.

The payOffDebt() method is called with the provided record ID.

The specified debt record is deleted from the database, and a success message is displayed.



### See Total Debt by User (Option 5):

User selects option 5 from the menu.

User enters the ID of the user they want to check the total debt for.

The getDebtTotalByUser() method is called with the provided user ID.

The total debt amount for the specified user is retrieved from the database and displayed to the user.



### See User with Highest Debt Record (Option 6):

User selects option 6 from the menu.

The getMaxDebtByUser() method is called, identifying the user with the highest debt amount.

The user with the highest debt amount is displayed to the user.



### Delete User (Option 7):

User selects option 7 from the menu.

User is presented with a list of users to choose from.

User selects the user they want to delete by entering their ID.

The deleteUser() method is called with the provided user ID.

The specified user profile is deleted from the database, and a success message is displayed.



### List All Users (Option 8):

User selects option 8 from the menu.

The listUsers() method is called, retrieving all user profiles from the database.

The user profiles are displayed to the user, showing the user ID and user name.



### Update Current User (Option 9):

User selects option 9 from the menu.

User enters their updated name.

The updateUser() method is called with the provided updated name.

The name of the currently logged-in user is updated in the database, and a success message is displayed.



### Change User (Option 10):

User selects option 10 from the menu.

The userLoginEventHandler() method is called, initiating the user login or user creation process again.


### Exit Application (Option 11):

User selects option 11 from the menu.

The program exits, terminating the application.


Invalid input:

If the user chooses invalid input (Not 1-11)

The app will prompt the user to try again.



## Functions Overview

### debtApp Class Constructor

Functionality: Initializes the debtApp class and establishes a connection to the MySQL database.

Purpose: The constructor ensures that the application has access to the database upon instantiation.

### getUserLoginChoice() Method

Functionality: Retrieves the user's choice regarding login or user creation.

Purpose: Allows the application to determine the user's desired action during the login process.


### listDebt() Method

Functionality: Retrieves and displays all debt records from the Debt_Table, including the associated user names.

Purpose: Enables users to view their debt records conveniently within the application.


### insertDebtRecord(double recordAmount, String note) Method

Functionality: Inserts a new debt record into the Debt_Table with the provided amount and note.

Purpose: Allows users to add new debt records to their profile, facilitating the tracking of financial obligations.


### payOffDebt(int recordID) Method

Functionality: Deletes a specific debt record from the Debt_Table based on the provided record ID.

Purpose: Provides users with the ability to remove paid-off or erroneous debt entries from their records.


### getDebtTotalByUser(int userID) Method

Functionality: Calculates the total debt amount for a specified user.

Purpose: Offers users insights into their overall financial liabilities within the application.


### updateRecord(int ID, double amount, String note) Method

Functionality: Updates an existing debt record in the Debt_Table with the provided ID, modifying the amount and note.

Purpose: Allows users to adjust the details of their debt records to reflect changes in financial circumstances.


### getMaxDebtByUser() Method

Functionality: Identifies the user with the highest debt amount.

Purpose: Helps users identify individuals with the most significant financial obligations within the application.


### getUserChoice() Method

Functionality: Retrieves the user's selected action from the application menu.

Purpose: Enables the application to execute the desired functionality based on the user's input.


### getCurrentUserID() Method

Functionality: Retrieves the ID of the currently logged-in user.

Purpose: Provides the application with the user context necessary for performing user-specific operations.


### addUser(String userName, int verificationNo) Method

Functionality: Creates a new user profile in the Users table of the database with the provided name and verification number.

Purpose: Facilitates the registration process for new users, granting them access to the application's features.


### listUsers() Method

Functionality: Retrieves and displays a list of all users along with their respective IDs.

Purpose: Allows users to view the existing user base within the application environment.


### updateUser(String updatedName) Method

Functionality: Updates the name of the currently logged-in user with the provided new name.

Purpose: Enables users to modify their profile information, including their displayed name within the application.


### logIn(int userID, int verNo) Method

Functionality: Validates user credentials during the login process, granting access upon successful authentication.

Purpose: Verifies user identity and ensures secure access to the application's features.


### deleteUser(int userID) Method

Functionality: Removes a user profile from the Users table based on the provided user ID.

Purpose: Allows users to deactivate their accounts or remove obsolete user profiles from the application.


### userLoginEventHandler() Method

Functionality: Manages the user login process and user creation, guiding users through the initial interaction with the application.

Purpose: Facilitates the establishment of user sessions and new user registration within the application environment.


### eventHandler() Method

Functionality: Handles various user actions within the application menu, executing the corresponding functionalities based on user input.

Purpose: Enables users to interact with the application's features seamlessly, facilitating debt management tasks and user profile operations.
