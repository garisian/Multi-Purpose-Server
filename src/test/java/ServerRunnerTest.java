import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * ServerRunner.java
 * Purpose:  Test server functionality
 *
 * @author Garisian Kana
 * @version 1.0
 *
 * Created on 2017-10-04
 */
public class ServerRunnerTest extends TestCase
{
    // Port which will be used for all testcases
    private int portNumber = 23;
    private ServerRunner testServer;

    public void setUp() throws Exception
    {
        super.setUp();
        Thread thread = new Thread(){
            public void run(){
                try{
                    testServer = new ServerRunner(portNumber);
                }
                catch(Exception e)
                {
                    System.err.println(e);
                    System.out.println("Server Crashed");

                }
            }
        };

        thread.start();
    }

    public void tearDown() throws Exception
    {
       // testServer.killServer();
    }

    /**
     * Description: Write single byte to server and see if it returns proper response
     */
    public void test_SendName_GetHelloName() throws Exception
    {
        final String host = "localhost";
        final int portNumber = 33;
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

            Socket socket = new Socket(host, portNumber);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("server says:" + br.readLine());

            String userInput = "garisian";
            out.println(userInput);

            System.out.println("server says:" + br.readLine());


            socket.close();

    }

}