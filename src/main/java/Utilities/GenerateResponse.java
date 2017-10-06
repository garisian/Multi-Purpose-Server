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
public class GenerateResponse
{
    public GenerateResponse()
    {
    }

    public String generate(String message)
    {
        String[] data = message.split(":");
        if(data[0].equals("basic"))
        {
            return "Hello, "+data[1].trim();
        }
        return null;
    }
}
