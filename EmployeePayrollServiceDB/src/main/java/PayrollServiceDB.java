import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PayrollServiceDB {

    private static PayrollServiceDB payrollServiceDB;

    /***
     * created constructor
     */
    private PayrollServiceDB() {
    }

    /***
     * created getInstance static method to create instance of class
     * @return
     */
    public static PayrollServiceDB getInstance() {
        if (payrollServiceDB == null) {
            payrollServiceDB = new PayrollServiceDB();
        }
        return payrollServiceDB;
    }

    /***
     * created getConnection method to connect database
     * @return - returning connection
     */
    public Connection getConnection() {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
        String userName = "root";
        String password = "SreeSree@285";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // calling listOfDrivers methods
        listOfDrivers();
        try {
            System.out.println("Connecting database : " + jdbcURL);
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connected database : " + connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /***
     * created listOfDrivers method to display loaded database drivers
     */
    private void listOfDrivers() {
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while (driverEnumeration.hasMoreElements()) {
            Driver driver = driverEnumeration.nextElement();
            System.out.println("Loaded Drivers : " + driver.getClass().getName());
        }
    }

    /***
     * created readData method to read data
     * @return
     */
    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_payroll";
        return this.getEmployeePayrollDataUsingDB(sql);
    }

    /***
     * created getEmployeePayrollDataUsingDB method to establish DB connection
     * creating statement and executing query
     * @param sql - passing string sql
     * @return - returning employeePayrollData
     */
    private List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String sql) {
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    /***
     * created getEmployeePayrollData to get data from DB
     * @param resultSet - passing resultSet
     * @return - returning employeePayrollData
     */
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int employeeID = resultSet.getInt("id");
                String employeeName = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(employeeID, employeeName, gender, salary, startDate));
            }
            System.out.println(employeePayrollDataList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }
}
