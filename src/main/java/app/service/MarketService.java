package app.service;

import java.util.List;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class MarketService {
    private String apiKey = "FINNHUB_API_KEY";

    public MarketService() {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
            this.apiKey = props.getProperty("API_KEY");
            System.out.println(apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject makeApiCall(String url) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            JSONObject obj = null;

            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                obj = new JSONObject(content.toString());
            } else {
                System.err.println("Error: " + status);
            }
            con.disconnect();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCompanyByTicker(String ticker) {
        String url = "https://finnhub.io/api/v1/search?q=" + ticker
                + "&exchange=US&token=" + apiKey;

        JSONObject obj = makeApiCall(url);
        return obj.getJSONArray("result").getJSONObject(0).getString("description");
    }

    public double getStockPrice(String ticker) {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + apiKey;

        JSONObject obj = makeApiCall(url);
        return obj.getDouble("c");
    }
}
