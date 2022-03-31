import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PayrollServiceDB {

    private static PayrollServiceDB payrollServiceDB;
    private PreparedStatement preparedStatement;

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
    public Connection getConnection() throws CustomException {
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
            throw new CustomException(e.getMessage(), CustomException.ExceptionType.TYPE_CONNECTION);
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
        } catch (SQLException | CustomException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    /***
     * created getEmployeePayrollData to get data from DB
     * @param resultSet - passing resultSet
     * @return - returning employeePayrollData
     */
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) throws CustomException {
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
            throw new CustomException(e.getMessage(), CustomException.ExceptionType.TYPE_RETRIEVAL);
        }
        return employeePayrollDataList;
    }

    /***
     * created updateEmployeeSalaryPayroll method to update salary
     * @param employeeName - passing employee name
     * @param salary - passing salary
     * @return
     * @throws CustomException
     */
    public int updateEmployeeSalaryPayroll(String employeeName, double salary) throws CustomException {
        return this.updateEmployeeSalaryDB(employeeName, salary);
    }

    /***
     * created updateEmployeeSalaryDB method to update salary in database
     * @param employeeName - passing employee name
     * @param salary - passing salary
     * @return
     * @throws CustomException
     */
    private int updateEmployeeSalaryDB(String employeeName, double salary) throws CustomException {
        String sql = String.format("UPDATE employee_payroll SET salary = %.2f WHERE name = '%s';", salary, employeeName);
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomException(e.getMessage(), CustomException.ExceptionType.TYPE_UPDATE);
        }
    }

    /***
     * created getEmployeeData method to get employee data from list
     * @param employeeName - passing employee name
     * @return
     */
    public List<EmployeePayrollData> getEmployeeData(String employeeName) {
        List<EmployeePayrollData> employeePayrollDataList = null;
        if (this.preparedStatement == null) {
            this.preparedStatementForData();
        }
        try {
            preparedStatement.setString(1, employeeName);
            ResultSet resultSet = preparedStatement.executeQuery();
            employeePayrollDataList = this.getEmployeeDBData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    /***
     * created getEmployeeDBData method to get employee data from database
     * @param resultSet - passing result set
     * @return
     */
    private List<EmployeePayrollData> getEmployeeDBData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            while(resultSet.next()) {
                int employeeID = resultSet.getInt("id");
                String employeeName = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(employeeID, employeeName, gender, salary, startDate));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    /***
     * created preparedStatementForData method to retrieve data
     */
    private void preparedStatementForData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_payroll WHERE name = ?";
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException | CustomException e) {
            e.printStackTrace();
        }
    }

    /***
     * created getEmployeeDataForDateRangeDB to get employee in the date range from database
     * @param startDate - passing start date
     * @param endDate - passing end date
     * @return
     */
    public List<EmployeePayrollData> getEmployeeDataForDateRangeDB(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM employee_payroll WHERE START BETWEEN '%s' AND '%s';",
                                    Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getEmployeePayrollDataUsingDB(sql);
    }
}
