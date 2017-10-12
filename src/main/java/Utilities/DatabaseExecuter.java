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
    private static final int port = 3306;

    // Key used to divide elements in json
    private final String devisor = ":=:";

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
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:"+port+"/dataSource", connectionProps);

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
            String myUrl = "jdbc:mysql://localhost:"+port+"/test";
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
            String myUrl = "jdbc:mysql://localhost:"+port+"/dataSource";
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
            preparedStmt.setString (1, dataList[0].trim());
            preparedStmt.setString (2, dataList[2].trim());
            preparedStmt.setString (3, dataList[3].trim());
            preparedStmt.setDate   (4, startDate);
            preparedStmt.setBoolean(5, false);
            preparedStmt.setString (6, dataList[1].trim());

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
            String myUrl = "jdbc:mysql://localhost:"+port+"/dataSource";
            Connection conn = DriverManager.getConnection(myUrl, "root", "password");

            // the mysql insert statement
            String query = "delete from users where email=" + email;

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
     * Description: Checks if a user already exists for provided email
     *
     * @param: email: email id that is used as a primary key to delete entry
     *
     * @return none
     */
    public static boolean existsUser(String email) throws SQLException
    {
        String result = null;
        //  "existsUser: abc123@gmail.com"
        try {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:"+port+"/dataSource";
            Connection conn = DriverManager.getConnection(myUrl, "root", "password");

            // the mysql insert statement
            String query = "SELECT first_name from users WHERE email = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, email);

            // execute the preparedstatement
            ResultSet queryResults = preparedStmt.executeQuery();
            conn.close();
            if(queryResults.next()) {
                result = queryResults.getString(1);
            }
            if(result.equals("null"))
            {
                return false;
            }
            return true;
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
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:"+port+"/dataSource", connectionProps);

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

    /**
     * Description: Add data to user's profile from BookMeNow
     *
     * @param: data: Insert a data entry with tags
     *
     * @return none
     */
    public static boolean addData(String[] data) throws SQLException
    {
        // sample data string:
        // addData: abc123@gmail.com, sampleTitle, sampleSummary, sampleURL, sampleTags
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:"+port+"/dataSource";
            Connection conn = DriverManager.getConnection(myUrl, "root", "password");

            // create a sql date object so we can use it in our INSERT statement
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query = " insert into bookedInfo (email, title, summary, url, tags)"
                    + " values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, data[0]);
            preparedStmt.setString (2, data[1]);
            preparedStmt.setString (3, data[2]);
            preparedStmt.setString (4, data[3]);
            preparedStmt.setString (5, data[4]);


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
     * Description: Return all the data user saved for BookMeNow
     *
     * @param: data: email ID used for database GET
     *
     * @return none
     */
    public static String extractUserData(String email) throws SQLException
    {
        String resultString = "";

        // Set username and password for MySQL
        Properties connectionProps = new Properties();
        connectionProps.put("user", "Garisian");
        connectionProps.put("password", "password");

        // Get a connection
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:"+port+"/dataSource", connectionProps);

        // create a statement
        Statement myStat = myConn.createStatement();

        // execute sql query
        ResultSet myRs = myStat.executeQuery("select * from bookedInfo where email ="+email);

        // process the result set and create JSON response
        while(myRs.next())
        {
            resultString += "{\"title\":{"+myRs.getString("title")+
                    "},\"summary\":{"+ myRs.getString("summary")+
                    "},\"url\":{"+ myRs.getString("url")+
                    "},\"tags\":{"+myRs.getString("tags")+
                    "}}";
            
        }
        return resultString;
    }

}
