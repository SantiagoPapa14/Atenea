package app.service;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class AlphaVantageService extends MarketService {

    public AlphaVantageService() {
        super();
    }

    @Override
    protected JSONObject makeApiCall(String url) {
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

    @Override
    public String getCompanyByTicker(String ticker) {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker
                + "&apikey=" + this.apiKey;

        JSONObject obj = makeApiCall(url);
        return null;
    }

    @Override
    public Double getStockPrice(String ticker) {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker
                + "&apikey=" + this.apiKey;
        JSONObject obj = makeApiCall(url);

        if (obj != null && obj.has("Global Quote")) {
            return obj.getJSONObject("Global Quote").getDouble("05. price");
        }

        System.err.println("\n\nFailed to get price for " + ticker);
        System.err.println("\n" + obj.toString() + "\n\n");
        return null;
    }

    @Override
    public Double getFxRate(String fromCurrency, String toCurrency) {
        String url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + fromCurrency
                + "&to_currency=" + toCurrency + "&apikey=" + this.apiKey;

        JSONObject obj = makeApiCall(url);
        if (obj == null) {
            System.err.println("Failed to get FX rate for " + fromCurrency + " to " + toCurrency);
            return null;
        }

        if (obj.has("Realtime Currency Exchange Rate")) {
            return obj.getJSONObject("Realtime Currency Exchange Rate").getDouble("5. Exchange Rate");
        }

        return null;
    }

}
