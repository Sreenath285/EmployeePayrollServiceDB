import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {

    List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
    private PayrollServiceDB payrollServiceDB;

    public EmployeePayrollService() {
        payrollServiceDB = PayrollServiceDB.getInstance();
    }

    /***
     * created IOService enum to give services
     */
    public enum IOService {
        CONSOLE_IO, FILE_IO, DB_IO, REST_IO;
    }

    /***
     * created readEmployeePayrollData to read data
     * @param ioService - passing ioService
     * @return - returning employeePayrollData
     */
    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) {
        if (ioService.equals(IOService.DB_IO)) {
            this.employeePayrollDataList = payrollServiceDB.readData();
        }
        return employeePayrollDataList;
    }

    /***
     * created updateEmployeeSalary method to update salary for particular employee
     * @param employeeName - passing employee name
     * @param salary - passing salary
     * @throws CustomException - throwing custom exception
     */
    public void updateEmployeeSalary(String employeeName, double salary) throws CustomException {
        int result = payrollServiceDB.updateEmployeeSalaryPayroll(employeeName, salary);
        if (result == 0) {
            return;
        }
        EmployeePayrollData employeePayrollData = this.getEmployeeData(employeeName);
        if (employeePayrollData != null) {
            employeePayrollData.salary = salary;
        }
    }

    /***
     * created getEmployeeData method to get employee from the list
     * @param employeeName - passing employee name
     * @return - returning employee
     */
    private EmployeePayrollData getEmployeeData(String employeeName) {
        return this.employeePayrollDataList.stream()
                   .filter(EmployeePayrollDataItem -> EmployeePayrollDataItem.employeeName.equals(employeeName))
                   .findFirst()
                   .orElse(null);
    }

    /***
     * created checkEmployeeInSyncWithDB method to the employee in database
     * @param employeeName - passing employee
     * @return - employee
     */
    public boolean checkEmployeeInSyncWithDB(String employeeName) {
        List<EmployeePayrollData> employeePayrollDataList = payrollServiceDB.getEmployeeData(employeeName);
        return employeePayrollDataList.get(0).equals(getEmployeeData(employeeName));
    }

    /***
     * created readEmployeePayrollDateRange method to get employees with in the date range
     * @param ioService - passing service
     * @param startDate - passing start date
     * @param endDate - passing end date
     * @return
     */
    public List<EmployeePayrollData> readEmployeePayrollDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
        if (ioService.equals(IOService.DB_IO)) {
            return payrollServiceDB.getEmployeeDataForDateRangeDB(startDate, endDate);
        }
        return null;
    }
}
