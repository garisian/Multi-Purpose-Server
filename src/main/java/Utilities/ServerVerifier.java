package Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ServerVerifier.java
 * Purpose:  Verify all user arguments whens tarting a server
 *
 * @author Garisian Kana
 * @version 1.0
 *
 * Created on 2017-10-03
 */
public class ServerVerifier
{
    // List of configurations and the order they'll appear on the command line
    enum ServerVariables { PortNumber, SomethingElse }
    private static List<ServerVariables> places = Arrays.asList(
            ServerVariables.PortNumber,
            ServerVariables.SomethingElse
    );

    /**
     * Description: Validate whether user arguments exist when starting up server
     *
     * @param: args parameter from main method
     *
     * @return none
     */
    public static boolean validateArguments(String[] args) throws ServerConfigurationException
    {
        // Check how many arguments were passed in
        if(args.length == 0)
        {
            System.out.println("Proper Usage is: \"java ServerRunner portNumber\"");
            System.exit(0);
        }

        int argumentNum = 0;
        for(String input: args)
        {
            ServerVariables currentArgument = places.get(argumentNum);
            switch (currentArgument)
            {
                case PortNumber:
                    return validatePortNumber(input);
                case SomethingElse:
                    break;
                default:
                    return false;
            }
        }

        // Should never reach this point
        return false;
    }

    /**
     * Description: Validate whether port number consists only of digits
     *
     * @param: String representation of a potential portNumber
     *
     * @return boolean indicating that portNumber is a String version of a positive integer
     */
    public static boolean validatePortNumber(String portNumber) throws ServerConfigurationException
    {
        if (portNumber.matches("[0-9]+"))
        {
            return true;
        }
        throw new ServerConfigurationException("\""+portNumber+"\""+" isn't a valid Port Number");
    }
}
