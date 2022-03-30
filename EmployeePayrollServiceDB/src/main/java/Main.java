import java.sql.SQLException;

public class Main {

    /***
     * main method for manipulation
     * @param args - default param(not used)
     */
    public static void main(String[] args) {
        /***
         * PROCEDURE
         * 1. created PayrollServiceDB object
         * 2. calling getConnection method
         */

        /***
         * 1. created PayrollServiceDB object
         */
        PayrollServiceDB payrollServiceDB = new PayrollServiceDB();
        /***
         * 2. calling getConnection method
         */
        payrollServiceDB.getConnection();
    }
}
