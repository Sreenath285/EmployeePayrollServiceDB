import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeePayrollServiceTest {

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Assert.assertEquals(4, employeePayrollDataList.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws CustomException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Steve", 500000.00);
        boolean result = employeePayrollService.checkEmployeeInSyncWithDB("Steve");
        Assert.assertTrue(result);
    }

    @Test
    public void givenStartDateRange_WhenMatches_ShouldReturnEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2018, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollService.readEmployeePayrollDateRange(EmployeePayrollService.IOService.DB_IO, startDate, endDate);
        Assert.assertEquals(4, employeePayrollDataList.size());
    }

    @Test
    public void givenPayrollData_WhenAverageSalaryByGender_ShouldReturnProperValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(EmployeePayrollService.IOService.DB_IO);
        Assert.assertTrue(averageSalaryByGender.get("M").equals(466666.6666666667) &&
                            averageSalaryByGender.get("F").equals(400000));
    }
}
