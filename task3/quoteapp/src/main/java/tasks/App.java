package tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class App 
{
    private static String getQuote() throws IOException {
        final String MY_URL = "https://api.kanye.rest/";
        HttpGet request = new HttpGet(MY_URL);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response;
        String result = "s";
        try {
            response = client.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                String jsonString = EntityUtils.toString(entity);
                JSONObject jsonObj = new JSONObject(jsonString);
                result = jsonObj.getString("quote");
                result = "\"" + result + "\" ~Kanye";
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
        

        return result;
    } 
    public static void main( String[] args )
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        HashSet<String> alreadySeenQuotes = new HashSet<String>();

        final int TRY_LIMIT = 120;

        while (true) {
            try {
                int tryCounter = 0;
                String newQuote = getQuote();
                while (alreadySeenQuotes.contains(newQuote) && tryCounter < TRY_LIMIT) {
                    tryCounter++;
                    newQuote = getQuote();
                }
                if (tryCounter == TRY_LIMIT) {
                    System.out.println("Could not find any new quotes in a resonable amount of tries (" + String.valueOf(TRY_LIMIT) + ").");
                    return;
                }
                alreadySeenQuotes.add(newQuote);
                System.out.println(newQuote);
            } catch (Exception e) {
                System.out.println("Exception occured, do you want to try again?\nType \"yes\" below: ");
                try {
                    String input = reader.readLine();
                    if (!input.equals("yes")) {
                        return;
                    } else {
                        continue;
                    }
                } catch (IOException e2) {
                    System.out.print("Exception occured again.");
                    e2.printStackTrace();
                    return;
                }
            }
            System.out.println("Do you want another quote?\nType \"next\" below: ");
            try {
                String input = reader.readLine();
                if (!input.equals("next")) {
                    return;
                }
            } catch (IOException e) {
                System.out.print("Exception occured.");
                e.printStackTrace();
                return;
            }
        }
    }
}
