import Utilities.GenerateResponse;
import Utilities.ServerConfigurationException;
import Utilities.ServerVerifier;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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
public class ServerRunner extends Thread
{
    // Variables used during message transfer
    private GenerateResponse serverResponse = new GenerateResponse();
    Socket socket;
    private static boolean keepAlive = true;
    private int portNumber;
    PrintWriter pw;

    // Socket used to tell server to end
    Socket killServer;

    /**
     * Description: Constructor to initialize server configurations
     *
     * @param: none
     *
     * @return none
     */
    public ServerRunner(int portNumber, Socket socket) throws IOException
    {
        super("SingleServerThread");
        this.portNumber = portNumber;
        //serverSocket = new ServerSocket(portNumber);
        this.socket = socket;
        //System.out.println("Creating server socket on port \"" + portNumber+"\"");
    }

    /**
     * Description: Initiates the thread
     *
     * @param: none
     *
     * @return none
     */
    public void run()
    {
        try
        {
            startServer();
        }
        catch (IOException e)
        {
            try
            {
               // serverSocket.close();
                pw.close();
                socket.close();
            }
            catch(IOException ee)
            {
                // Everything's alerady closed
            }
            e.printStackTrace();
        }
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


    /**
     * Description: Receive messages and send the appropriate response
     *
     * @param: none
     *
     * @return none
     */
    private void startServer() throws IOException
    {
        while (keepAlive) {
            //socket = serverSocket.accept();
            OutputStream os = socket.getOutputStream();
            pw = new PrintWriter(os, true);
            //pw.println("What's you name?");

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = br.readLine();

            String response = serverResponse.generate(str);

            pw.println(response);
            System.out.println("SERVER --- Sent Response: \"" + response+"\"");
        }
        //serverSocket.close();
        //pw.close();
        //socket.close();

    }

    public static void main(String args[]) throws IOException
    {
        // Check if user-arguments are valid
        try
        {
            // If an error occurs during validaton a ServerConfigurationException will be thrown
            ServerVerifier.validateArguments(args);

            // Create a new thread for every incoming connection
            try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));) {
                while (keepAlive)
                {
                    // If something is written to server port, kill\ it

                    new ServerRunner(Integer.parseInt(args[0]),serverSocket.accept()).start();
                }
            } catch (IOException e) {
                System.err.println("Could not listen on port " + Integer.parseInt(args[0]));
                System.exit(-1);
            }

        }
        catch(ServerConfigurationException error)
        {
            System.err.println(error);
            System.exit(0);
        }
    }
}
