package app.service;

import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import org.json.JSONObject;

public abstract class MarketService {
    protected String apiKey = "API_KEY";

    public MarketService() {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
            this.apiKey = props.getProperty("API_KEY");
            System.out.println("API KEY: " + apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract JSONObject makeApiCall(String url);

    public abstract String getCompanyByTicker(String ticker);

    public abstract Double getStockPrice(String ticker);

    public abstract Double getFxRate(String fromCurrency, String toCurrency);
}
