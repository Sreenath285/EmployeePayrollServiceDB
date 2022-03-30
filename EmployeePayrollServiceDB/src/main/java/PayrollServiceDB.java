import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class PayrollServiceDB {

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
}
