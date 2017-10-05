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
    private int portNumber = 24;
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
        testServer.killServer();
    }

    /**
     * Description: Write single byte to server and see if it returns proper response
     */
    public void testAcceptSingleByte() throws Exception
    {
        Socket socket = null;
        try {
            // Create socket and set up listening and writing streams
            socket = new Socket("localhost", portNumber);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send a single char and wait for response
            out.println("a");
            System.out.println("Wrote a");
            System.out.println("Server respone: \"" + br.readLine()+"\"");

        } catch (Exception e) {
            System.err.print(e);
            throw new Exception(e);
        } finally
        {
            socket.close();
        }
    }

}