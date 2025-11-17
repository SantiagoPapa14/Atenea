package app.model;

import java.time.LocalDate;

import app.service.MarketService;

public abstract class Trade implements Priceable {

    protected long id;
    protected LocalDate tradeDate;
    protected String counterparty;
    protected TradeStatus status;

    public Trade(long id, LocalDate tradeDate, String counterparty, TradeStatus status) {
        this.id = id;
        this.tradeDate = tradeDate;
        this.counterparty = counterparty;
        this.status = status;
    }

    // --- Getters ---
    public long getId() {
        return id;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    // --- Abstract methods ---
    public abstract String getProductType();

    public abstract void validate();

    public abstract double calculateValue();

    public abstract String getDescription();

    @Override
    public abstract Double calculateMTM(MarketService marketService);

    @Override
    public String toString() {
        return "[" + getProductType() + " Trade] ID=" + id + ", Counterparty=" + counterparty
                + ", TradeDate=" + tradeDate + ", Status=" + status;
    }
}
