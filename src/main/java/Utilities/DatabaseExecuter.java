package Utilities;

import com.sun.xml.internal.ws.spi.db.DatabindingException;

import java.sql.*;
import java.util.Calendar;
import java.util.Properties;

/**
 * DatabaseExectuer.java
 * Purpose:  List of commands that can modifiy/extract data from MySQL database
 *
 * @author Garisian Kana
 * @version 1.0
 *
 * Created on 2017-10-09
 */
public class DatabaseExecuter
{
    public static String getNames() throws SQLException
    {
        String resultString = "";

        // Set username and password for MySQL
        Properties connectionProps = new Properties();
        connectionProps.put("user", "Garisian");
        connectionProps.put("password", "password");

        // Get a connection
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userTable", connectionProps);

        // create a statement
        Statement myStat = myConn.createStatement();

        // execute sql query
        ResultSet myRs = myStat.executeQuery("select * from employees");

        // process the result set
        while(myRs.next())
        {
            resultString += myRs.getString("last_name")+myRs.getString("first_name")+"\n";
        }
        return resultString;
    }

    public static void createUser() throws SQLException
    {
        try
        {
            // create a mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/test";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            // create a sql date object so we can use it in our INSERT statement
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query = " insert into users (first_name, last_name, date_created, is_admin, num_points)"
                    + " values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, "Barney");
            preparedStmt.setString (2, "Rubble");
            preparedStmt.setDate   (3, startDate);
            preparedStmt.setBoolean(4, false);
            preparedStmt.setInt    (5, 5000);

            // execute the preparedstatement
            preparedStmt.execute();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
