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
            try
            {
                boolean success = DatabaseExecuter.addData(removeASCII.trim());
                //boolean success = DatabaseExecuter.addData(new String[]{});
                System.out.println(success);
                if(success)
                {


                    String kindaworks = "200 OK\n" +
                            "Content-Type: application/json\n" +
                            " \n" +
                            "{\"args\": {},"+"" +
                            "\"data\": \"\","+
                            "\"files\": {},"+
                            "\"form\": {},"+
                            "\"headers\": "+
                                    "{\"Accept\": \"*/*\","+
                                    "\"Accept-Encoding\": \"gzip, deflate\","+
                                    "\"Cache-Control\": \"no-cache\","+
                                    "\"Connection\": \"close\","+
                                    "\"Content-Length\": \"0\","+
                                    "\"Host\": \"httpbin.org\",},"+
                            "\"json\": null,"+
                            "\"origin\": \"--.--.--.--\","+
                            "\"url\": \"localhost:9635/\"}";
                    String another = "HTTP/1.1 200 OKAY\n" +
                            "Upgrade: TLS/1.0, HTTP/1.1\n" +
                            "Connection: Upgrade" +
                            "{}";
                    return another;
                }
                return "HTTP 400 OK";
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
