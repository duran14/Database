import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection1 {

    //1. Defining a method to connect with the database previously created in mySQL
    public java.sql.Connection connect() throws ClassNotFoundException, SQLException {

    //2. Call the driver from mySQL
        Class.forName("com.mysql.jdbc.Driver");

    //3. Connect to the database by declaring an object named here con1
    //the object connects to the local host, the name of the database, and a default username with no password here
        java.sql.Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/inventory",
                                                                "root", "" );
    //3. A method that returns an object, so we need to return it
        return con1;
    }
}
