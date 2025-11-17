package app.model;

import java.time.LocalDate;

public class StockTrade extends Trade {

    private String ticker;
    private int quantity;
    private double price;
    private String exchange;
    private String currency;

    public StockTrade(long id,
            LocalDate tradeDate,
            String counterparty,
            String ticker,
            int quantity,
            double price,
            String exchange,
            String currency) {

        super(id, tradeDate, counterparty, TradeStatus.BOOKED);
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
        this.exchange = exchange;
        this.currency = currency;
    }

    @Override
    public String getProductType() {
        return "STOCK";
    }

    @Override
    public void validate() {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive.");
        if (price <= 0)
            throw new IllegalArgumentException("Price must be positive.");
    }

    @Override
    public double calculateValue() {
        return quantity * price;
    }

    @Override
    public double calculateMTM(double marketPrice) {
        return (marketPrice - price) * quantity;
    }
}
