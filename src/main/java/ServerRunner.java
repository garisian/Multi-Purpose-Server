import Utilities.ServerConfigurationException;
import Utilities.ServerVerifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private boolean isAlive = true;

    /**
     * Description: Constructor to initialize server configurations
     *
     * @param: none
     *
     * @return none
     */
    public ServerRunner(int portNumber) throws IOException
    {
        this.portNumber = portNumber;
        createSockets();
    }

    /**
     * Description: Stop the server
     *
     * @param: none
     *
     * @return none
     */
    public void killServer()
    {
        System.out.println("Stopping Server...");
        isAlive = false;
    }

    /**
     * Description: Create a ServerSocket listening to the portNumber and listen to clients
     *
     * @param: none
     *
     * @return none
     */
    public void createSockets() throws IOException
    {
        // Create socket and accept a connection from the client
        this.serverSocket = new ServerSocket(portNumber);
        this.clientSocket = serverSocket.accept();
        System.out.println("Starting Server...");
    }

    /**
     * Description: Attempt communication with the client
     *
     * @param: none
     *
     * @return none
     */
    public void communicate() throws IOException
    {
        // Attempt communicates with the client
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        )
        {
            // Keep writing what was written to server back out until "Bye" is written
            String inputLine, outputLine;
            while (isAlive && (inputLine = in.readLine()) != null)
            {
                out.println("\""+inputLine+"\" was written");
                System.out.println("Server received: "+inputLine);
                if (inputLine.equals("Bye"))
                    break;
            }
        }
    }

    public static void main(String[] args)
    {
        // Check if user-arguments are valid
        try
        {
            // If an error occurs during validaton a ServerConfigurationException will be thrown
            ServerVerifier.validateArguments(args);
            ServerRunner server = new ServerRunner(Integer.parseInt(args[0]));

        }
        catch(ServerConfigurationException | IOException error)
        {
            System.err.println(error);
            System.exit(0);
        }

    }


}
