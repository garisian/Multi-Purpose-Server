import Utilities.GenerateResponse;
import Utilities.ServerConfigurationException;
import Utilities.ServerVerifier;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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
    OutputStream os;

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
            // Variables used to store data from said POST request
            String messageType = "";
            String messageData = "";

            //socket = serverSocket.accept();
            os = socket.getOutputStream();
            pw = new PrintWriter(os, true);
            //pw.println("What's you name?");

            // read a single line
/*            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = br.readLine();*/

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            StringBuffer jb = new StringBuffer();
            int lineNum = 0;
            while (!line.isEmpty())
            {
                System.out.println(line);
                if (line.matches("^POST.*$")) {
                    String[] splitData = line.split("data=");
                    String firstHalf = splitData[0].split("type=")[1];
                    messageType = firstHalf.substring(0, firstHalf.length() - 1);
                    messageData = splitData[1].split("HTTP/1.1")[0];
                }
                line = reader.readLine();
            }
            String response = serverResponse.generate(messageType, messageData);
            //String response = "HTTP/1.1 200 OK\r\n\r\n";
            System.out.println(response);
            os.write(response.getBytes("UTF-8"));
            //pw.println(response.getBytes("UTF-8"));
            System.out.println("SERVER --- Sent Response: \"" + response.getBytes("UTF-8")+"\"");

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
