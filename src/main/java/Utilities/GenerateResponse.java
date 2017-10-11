package Utilities;

/**
 * ServerRunner.java
 * Purpose:  Create an response based on the client request
 *
 * @author Garisian Kana
 * @version 1.0
 *
 * Created on 2017-10-05
 */
import java.sql.*;
import java.util.Properties;

public class GenerateResponse
{
    public GenerateResponse() {}

    public String generate(String message)
    {
        String[] data = message.split(":");
        // basic: randomName
        if(data[0].equals("basic"))
        {
            return "Hello, "+data[1].trim();
        }

        // sqlTest: nonsense
        if(data[0].equals("sqlTest"))
        {
            try
            {
                DatabaseExecuter.getNames();
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        // createUser: gary, abc123@gmail.com, garisian, kana
        if(data[0].equals("createUser"))
        {
            try
            {
                boolean success = DatabaseExecuter.createUser(data[1]);
                return Boolean.toString(success);
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        if(data[0].equals( "deleteUser"))
        {
            try
            {
                boolean success = DatabaseExecuter.deleteUser(data[1]);
                return Boolean.toString(success);
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        return null;
    }
}
