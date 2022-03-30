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
}
