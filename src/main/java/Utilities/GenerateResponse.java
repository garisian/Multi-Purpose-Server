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
import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Properties;

public class GenerateResponse
{
    public GenerateResponse() {}

    public String generate(String messagetype, String messageData)
    {
        // basic: randomName
        String removeASCII = "";
        try
        {
            removeASCII = recreateString(messageData);
        }
        catch(UnsupportedEncodingException e)
        {}
        //System.out.println(removeASCII);
        if(messagetype.equals("basic"))
        {
            return "Hello, "+messageData.trim();
        }

        // sqlTest: nonsense
        if(messagetype.equals("sqlTest"))
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
        if(messagetype.equals("createUser"))
        {
            try
            {
                boolean success = DatabaseExecuter.createUser(removeASCII);
                return Boolean.toString(success);
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        if(messagetype.equals( "deleteUser"))
        {
            try
            {
                boolean success = DatabaseExecuter.deleteUser("\""+removeASCII.trim()+"\"");
                return Boolean.toString(success);
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        if(messagetype.equals("existsUser"))
        {
            try
            {
                boolean success = DatabaseExecuter.existsUser(removeASCII.trim());
                return Boolean.toString(success);
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        if(messagetype.equals("addData"))
        {
            // addData: {"email":abc123@gmail.com, "title":sampleTitle, "summary":sampleSummary, "url":sampleURL, "tags":"sampleTags}
            Date currentDate = new Date();
            try
            {
                boolean success = DatabaseExecuter.addData(removeASCII.trim());
                if(success)
                {

                    String responseString =
                            "HTTP/1.1 200 OK\n"+
                            "Date: "+currentDate+"\n"+
                            "Server: "+"Gary's Custom Server"+"\n"+
                            "Content-Length: 44"+"\n"+
                            "Connection: close"+"\n"+
                            "Content-Type: text/html"+"\n"+
                            "\n"+
                            "<html><body><h1>It works!</h1></body></html>";

                    return responseString;
                }
                String responseString =
                        "HTTP/1.1 403 Forbidden\n"+
                                "Date: "+currentDate+"\n"+
                                "Server: "+"Gary's Custom Server"+"\n"+
                                "Content-Length: 44"+"\n"+
                                "Connection: close"+"\n"+
                                "Content-Type: text/html"+"\n"+
                                "\n"+
                                "<html><body><h1>It Fails!</h1></body></html>";

                return responseString;
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        if(messagetype.equals("emailData"))
        {
            // addData: abc123@gmail.com, sampleTitle, sampleSummary, sampleURL, sampleTags
            Date currentDate = new Date();
            try
            {
                String success = DatabaseExecuter.extractUserData(removeASCII.trim());
                if(success.equals("success"))
                {
                    String responseString =
                            "HTTP/1.1 200 OK\n"+
                                    "Date: "+currentDate+"\n"+
                                    "Server: "+"Gary's Custom Server"+"\n"+
                                    "Content-Length: 43"+"\n"+
                                    "Connection: close"+"\n"+
                                    "Content-Type: text/html"+"\n"+
                                    "\n"+
                                    "<html><body><h1>Emailed!</h1></body></html>";

                    return responseString;
                }
                String responseString =
                        "HTTP/1.1 403 Forbidden\n"+
                                "Date: "+currentDate+"\n"+
                                "Server: "+"Gary's Custom Server"+"\n"+
                                "Content-Length: 45"+"\n"+
                                "Connection: close"+"\n"+
                                "Content-Type: text/html"+"\n"+
                                "\n"+
                                "<html><body><h1>No Emails!</h1></body></html>";

                return responseString;
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        if(messagetype.equals("extractData"))
        {
            // addData: abc123@gmail.com, sampleTitle, sampleSummary, sampleURL, sampleTags
            try
            {
                String success = DatabaseExecuter.extractUserData(removeASCII.trim());
                //return Boolean.toString(success);
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        return null;
    }

    private String recreateString(String reqeust) throws UnsupportedEncodingException {
        return java.net.URLDecoder.decode(reqeust, "UTF-8");
    }
}
