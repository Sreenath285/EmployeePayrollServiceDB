import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayrollData {

    public int employeeID;
    public String employeeName;
    public String gender;
    public double salary;
    public LocalDate startDate;

    public EmployeePayrollData(int employeeID, String employeeName, String gender, double salary, LocalDate startDate) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.gender = gender;
        this.salary = salary;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "\nEmployeePayrollData{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", gender=" + gender +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return employeeID == that.employeeID
                             && gender == that.gender
                             && Double.compare(that.salary, salary) == 0
                             && Objects.equals(employeeName, that.employeeName)
                             && Objects.equals(startDate, that.startDate);
    }
}
