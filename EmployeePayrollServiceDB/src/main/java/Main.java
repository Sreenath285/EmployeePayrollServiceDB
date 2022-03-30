import java.sql.SQLException;

public class Main {

    /***
     * main method for manipulation
     * @param args - default param(not used)
     */
    public static void main(String[] args) {
        /***
         * PROCEDURE
         * 1. created employeePayrollService object
         * 2. calling readEmployeePayrollData method
         */

        /***
         * 1. created employeePayrollService object
         */
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        /***
         * 2. calling readEmployeePayrollData method
         */
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
    }
}
