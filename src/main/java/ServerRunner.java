import Utilities.ServerConfigurationException;
import Utilities.ServerVerifier;

import java.io.*;
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
    private ServerSocket serverSocket;
    private boolean keepAlive = true;
    private int portNumber;

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
        serverSocket = new ServerSocket(portNumber);
        System.out.println("Creating server socket on port \"" + portNumber+"\"");
        System.out.println("Starting Server...");
        startServer();
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
        keepAlive = false;
    }

    private void startServer() throws IOException
    {
        while (keepAlive) {
            Socket socket = serverSocket.accept();
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("What's you name?");

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = br.readLine();

            pw.println("Hello, " + str);
            pw.close();
            socket.close();

            System.out.println("Just said hello to:" + str);
        }
        serverSocket.close();
    }

    public static void main(String args[]) throws IOException
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
