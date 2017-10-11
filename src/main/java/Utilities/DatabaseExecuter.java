package Utilities;

import com.sun.xml.internal.ws.spi.db.DatabindingException;

import java.sql.*;
import java.util.ArrayList;
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

    /**
     * Description: Return a name in users table.
     *              DEBUG purposes
     *
     * @param: none
     *
     * @return none
     */
    public static String getNames() throws SQLException
    {
        String resultString = "";

        // Set username and password for MySQL
        Properties connectionProps = new Properties();
        connectionProps.put("user", "Garisian");
        connectionProps.put("password", "password");

        // Get a connection
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dataSource", connectionProps);

        // create a statement
        Statement myStat = myConn.createStatement();

        // execute sql query
        ResultSet myRs = myStat.executeQuery("select * from users");

        // process the result set
        while(myRs.next())
        {
            resultString += myRs.getString("last_name")+myRs.getString("first_name")+"\n";
        }
        return resultString;
    }

    /**
     * Description: Add one user.
     *             DEBUG purposes
     *
     * @param: none
     *
     * @return none
     */
    public static void createSingleUser() throws SQLException
    {
        try
        {
            // create a mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/test";
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

    /**
     * Description: Add one user. DEBUG purposes
     *
     * @param: data: contains all data needed for a database entry
     *
     * @return none
     */
    public static boolean createUser(String data) throws SQLException
    {
        // sample data string:
        // createUser: gary, abc123@gmail.com, garisian, kana
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/dataSource";
            Connection conn = DriverManager.getConnection(myUrl, "root", "password");

            // create a sql date object so we can use it in our INSERT statement
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query = " insert into users (id, first_name, last_name, date_created, is_admin, email)"
                    + " values (?, ?, ?, ?, ?, ?)";

            String[] dataList = data.split(",");
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, dataList[0]);
            preparedStmt.setString (2, dataList[2]);
            preparedStmt.setString (3, dataList[3]);
            preparedStmt.setDate   (4, startDate);
            preparedStmt.setBoolean(5, false);
            preparedStmt.setString (6, dataList[1]);

            // execute the preparedstatement
            int successful = preparedStmt.executeUpdate();
            conn.close();
            if(successful > 0)
            {
                return true;
            }
            return false;

        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Description: Delete one user
     *
     * @param: email: email id that is used as a primary key to delete entry
     *
     * @return none
     */
    public static boolean deleteUser(String email) throws SQLException
    {
        //  "deleteUser: abc123@gmail.com"
        try {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/dataSource";
            Connection conn = DriverManager.getConnection(myUrl, "root", "password");

            // the mysql insert statement
            String query = "delete from users where email=" + email;
            ;

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedstatement
            int successful = preparedStmt.executeUpdate();
            conn.close();
            if (successful == 1) {
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Description: Return data regarding a single user
     *
     * @param: none
     *
     * @return none
     */
    public static ArrayList<String> extractUser(String email) throws SQLException
    {
        // sample data string:
        // createUser: gary, abc123@gmail.com, garisian, kana
        try
        {
            ArrayList<String> resultString = new ArrayList<String>();

            // Set username and password for MySQL
            Properties connectionProps = new Properties();
            connectionProps.put("user", "Garisian");
            connectionProps.put("password", "password");

            // Get a connection
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dataSource", connectionProps);

            // create a statement
            Statement myStat = myConn.createStatement();

            // execute sql query
            String queryString ="SELECT id,first_name,last_name from users where email="+email;
            ResultSet myRs = myStat.executeQuery(queryString);

            // process the result set
            while(myRs.next())
            {
                resultString.add(myRs.getString("last_name")+myRs.getString("first_name")+"\n");
            }
            return resultString;

        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return null;
        }
    }

}
