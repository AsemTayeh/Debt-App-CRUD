DebtApp README
Introduction
DebtApp is a Java application designed for managing debt records using a MySQL database backend. This README provides an overview of the functions implemented in the debtApp class and their functionalities.

Functions Overview

debtApp Class Constructor

Functionality: Initializes the debtApp class and establishes a connection to the MySQL database.
Purpose: The constructor ensures that the application has access to the database upon instantiation.

getUserLoginChoice() Method

Functionality: Retrieves the user's choice regarding login or user creation.
Purpose: Allows the application to determine the user's desired action during the login process.

listDebt() Method

Functionality: Retrieves and displays all debt records from the Debt_Table, including the associated user names.
Purpose: Enables users to view their debt records conveniently within the application.

insertDebtRecord(double recordAmount, String note) Method

Functionality: Inserts a new debt record into the Debt_Table with the provided amount and note.
Purpose: Allows users to add new debt records to their profile, facilitating the tracking of financial obligations.

payOffDebt(int recordID) Method

Functionality: Deletes a specific debt record from the Debt_Table based on the provided record ID.
Purpose: Provides users with the ability to remove paid-off or erroneous debt entries from their records.

getDebtTotalByUser(int userID) Method

Functionality: Calculates the total debt amount for a specified user.
Purpose: Offers users insights into their overall financial liabilities within the application.

updateRecord(int ID, double amount, String note) Method

Functionality: Updates an existing debt record in the Debt_Table with the provided ID, modifying the amount and note.
Purpose: Allows users to adjust the details of their debt records to reflect changes in financial circumstances.

getMaxDebtByUser() Method

Functionality: Identifies the user with the highest debt amount.
Purpose: Helps users identify individuals with the most significant financial obligations within the application.

getUserChoice() Method

Functionality: Retrieves the user's selected action from the application menu.
Purpose: Enables the application to execute the desired functionality based on the user's input.

getCurrentUserID() Method

Functionality: Retrieves the ID of the currently logged-in user.
Purpose: Provides the application with the user context necessary for performing user-specific operations.

addUser(String userName, int verificationNo) Method

Functionality: Creates a new user profile in the Users table of the database with the provided name and verification number.
Purpose: Facilitates the registration process for new users, granting them access to the application's features.

listUsers() Method

Functionality: Retrieves and displays a list of all users along with their respective IDs.
Purpose: Allows users to view the existing user base within the application environment.

updateUser(String updatedName) Method

Functionality: Updates the name of the currently logged-in user with the provided new name.
Purpose: Enables users to modify their profile information, including their displayed name within the application.

logIn(int userID, int verNo) Method

Functionality: Validates user credentials during the login process, granting access upon successful authentication.
Purpose: Verifies user identity and ensures secure access to the application's features.

deleteUser(int userID) Method

Functionality: Removes a user profile from the Users table based on the provided user ID.
Purpose: Allows users to deactivate their accounts or remove obsolete user profiles from the application.

userLoginEventHandler() Method

Functionality: Manages the user login process and user creation, guiding users through the initial interaction with the application.
Purpose: Facilitates the establishment of user sessions and new user registration within the application environment.

eventHandler() Method

Functionality: Handles various user actions within the application menu, executing the corresponding functionalities based on user input.
Purpose: Enables users to interact with the application's features seamlessly, facilitating debt management tasks and user profile operations.
