import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        debtApp myDebtObj = new debtApp();

        while (myDebtObj.getUserLoginChoice() != 3) {

            myDebtObj.userLoginEventHandler();
        }

        while (myDebtObj.getUserChoice() != 11) {

           myDebtObj.eventHandler();
       }
    }

    //password hashing, store hashed password
    //create a git repositry.
}