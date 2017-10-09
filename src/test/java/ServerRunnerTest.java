import com.sun.org.apache.xpath.internal.operations.Mult;
import junit.framework.TestCase;

import javax.swing.plaf.multi.MultiLabelUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    private int portNumber = 29;
    private ServerRunner testServer;
    private boolean setupDone = false;

    public void setUp() throws Exception
    {
        super.setUp();
        if(setupDone)
        {
            return;
        }
        Thread thread = new Thread(){
            public void run(){
                try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
                    while (true) {
                        new ServerRunner(portNumber,serverSocket.accept()).start();
                        //new KKMultiServerThread(serverSocket.accept()).start();
                    }
                }
                catch(Exception e)
                {
                    System.err.println(e);
                    System.out.println("Server Crashed in Testcase Setup");
                    System.out.println(e);
                }
            }
        };

        thread.start();
        setupDone = false;
    }

    public void tearDown() throws Exception
    {
        //sleep(4000);
        //new ServerRunner(portNumber,new Socket()).killServer();
    }

    /**
     * Description: Write a name to server and see if it returns proper response
     */
    public void test_sendName_GetHelloName() throws IOException
    {
        final String host = "localhost";
        final String testMessage = "basic: garisian";

        System.out.println("Creating socket to '" + host + "' on port " + portNumber);
        Socket socket = new Socket(host, portNumber);
        String response = sendMessage(socket,testMessage);

        if(!response.equals("Hello, garisian"))
        {
            assert false;
        }
    }

    /**
     * Description: Write a name to server and see if it returns proper response from multiple different clients
     *              to test concurrency
     */
    public void test_sendMultipleName_GetHelloName() throws IOException
    {
        final String host = "localhost";

        ArrayList<Socket> listOfSockets = new ArrayList<Socket>();
        Socket socketChild1 = new Socket(host, portNumber);
        Socket socketChild2 = new Socket(host, portNumber);
        Socket socketChild3 = new Socket(host, portNumber);
        Socket socketChild4 = new Socket(host, portNumber);
        Socket socketChild5 = new Socket(host, portNumber);
        listOfSockets.add(socketChild1);
        listOfSockets.add(socketChild2);
        listOfSockets.add(socketChild3);

        try
        {
            new MultipleClientTester(socketChild1,"basic: aaaa", "Hello, aaaa").start();
            new MultipleClientTester(socketChild2,"basic: bbb", "Hello, bbb").start();
            new MultipleClientTester(socketChild3,"basic: ccc", "Hello, ccc").start();
            new MultipleClientTester(socketChild4,"basic: ddd", "Hello, dddd").start();
            new MultipleClientTester(socketChild5,"basic: eee", "Hello, eee").start();
        }
        catch(AssertionError e)
        {
            assert false;
        }

/*        if(!response.equals("Hello, garisian"))
        {
            assert false;
        }*/
    }

    /**
     * Description: Write message into socket and return response
     */
    private String sendMessage(Socket socket, String message) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        //System.out.println("TEST: server says:" + br.readLine());

        //System.out.println("TESTFILE --- client sent: \"" + message+"\"");
        out.println(message);

        String response = br.readLine();
        //socket.close();
        //System.out.println("TESTFILE ---  server responded: \"" + response+"\"");
        return response;
    }


    // Create multiple clients that cun in parallel to test concurrancy
    class MultipleClientTester extends Thread
    {
        private String message;
        private Socket server;
        private Socket parent;
        private String expectedResult;

        public MultipleClientTester(Socket server, String message, String expectedResult)
        {
            this.message = message;
            this.server = server;
            this.expectedResult = expectedResult;
           // this.parent = parent;
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
                System.out.println("Creating socket to '" + server + "' on port " + portNumber);
                String response = sendMessage(server,message);

                if(!response.equals(expectedResult))
                {
                    assert false;
                    throw new AssertionError();
                }
                //PrintWriter out = new PrintWriter(parent.getOutputStream(), true);
                //out.println(response);

                //System.out.println("TEST: server says:" + br.readLine());
            }
            catch (IOException e)  {  }
        }
    }

}