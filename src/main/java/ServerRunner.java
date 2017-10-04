import Utilities.ServerConfigurationException;
import Utilities.ServerVerifier;

/**
 * ServerRunner.java
 * Purpose:  Wait for post requests to assign a unique thread and process it
 *
 * @author Garisian Kana
 * @version 1.0
 *
 * Created on 2017-10-03
 */
public class ServerRunner
{
    // Variables required to run the server
    private int portNumber;

    /**
     * Description: Constructor to initialize server configurations
     *
     * @param: none
     *
     * @return none
     */
    public ServerRunner(int portNumber)
    {
        this.portNumber = portNumber;
    }

    public static void main(String[] args)
    {
        // Check if user-arguments are valid
        try
        {
            ServerVerifier.validateArguments(args);
        }
        catch(ServerConfigurationException error)
        {
            System.err.println(error);
            System.exit(0);
        }

        ServerRunner server = new ServerRunner(Integer.parseInt(args[0]));
    }


}
