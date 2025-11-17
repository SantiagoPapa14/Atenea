package app.service;

import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import java.util.Map;
import java.util.HashMap;

class FiatToStablecoinMap {
    public static final Map<String, String> FIAT_TO_STABLECOIN = new HashMap<>();
    static {
        FIAT_TO_STABLECOIN.put("USD", "USDT");
        FIAT_TO_STABLECOIN.put("EUR", "EURC");
        FIAT_TO_STABLECOIN.put("GBP", "tGBP");
        FIAT_TO_STABLECOIN.put("JPY", "JPYC");
        FIAT_TO_STABLECOIN.put("CHF", "XCHF");
        FIAT_TO_STABLECOIN.put("AUD", "TAUD");
        FIAT_TO_STABLECOIN.put("CAD", "TCAD");
        FIAT_TO_STABLECOIN.put("SGD", "XSGD");
        FIAT_TO_STABLECOIN.put("CNY", "CNYT");
    }
}

// Combines both Finnhub and Binance because I won't pay and API for this
public class FinnanceService extends MarketService {

    public FinnanceService() {
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
        String url = "https://finnhub.io/api/v1/search?q=" + ticker
                + "&exchange=US&token=" + apiKey;

        JSONObject obj = makeApiCall(url);
        return obj.getJSONArray("result").getJSONObject(0).getString("description");
    }

    @Override
    public Double getStockPrice(String ticker) {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + apiKey;
        JSONObject obj = makeApiCall(url);

        if (obj != null && obj.has("c")) {
            if (obj.getDouble("c") > 0)
                return obj.getDouble("c");
        }

        return null;
    }

    @Override
    public Double getFxRate(String fromCurrency, String toCurrency) {
        String toCurrencyStable = FiatToStablecoinMap.FIAT_TO_STABLECOIN.get(toCurrency);
        String url = "https://api.binance.com/api/v3/ticker/price?symbol=" + fromCurrency + toCurrencyStable;
        JSONObject obj = makeApiCall(url);

        if (obj != null && obj.has("price")) {
            return obj.getDouble("price");
        }

        return null;
    }

}
