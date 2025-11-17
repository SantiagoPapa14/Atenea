package app.model;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class FxTrade extends Trade implements SettlementApplicable {

    private String buyCurrency;
    private String sellCurrency;
    private BigDecimal buyAmount;
    private BigDecimal sellAmount;
    private BigDecimal rate;
    private LocalDate valueDate;
    private String productType;

    public FxTrade(long id,
            LocalDate tradeDate,
            String counterparty,
            String productType,
            String buyCurrency,
            String sellCurrency,
            BigDecimal buyAmount,
            BigDecimal rate,
            LocalDate valueDate) {

        super(id, tradeDate, counterparty, TradeStatus.BOOKED);
        this.productType = productType;
        this.buyCurrency = buyCurrency;
        this.sellCurrency = sellCurrency;
        this.buyAmount = buyAmount;
        this.rate = rate;

        // auto-calc sellAmount
        this.sellAmount = buyAmount.multiply(rate);
        this.valueDate = valueDate;
    }

    // --- Abstract overrides ---
    @Override
    public String getProductType() {
        return "FX-" + productType;
    }

    @Override
    public void validate() {
        if (buyAmount == null || buyAmount.doubleValue() <= 0)
            throw new IllegalArgumentException("Buy amount must be positive.");

        if (rate.doubleValue() <= 0)
            throw new IllegalArgumentException("Rate must be positive.");
    }

    @Override
    public double calculateValue() {
        return sellAmount.doubleValue();
    }

    // --- Settlement logic ---
    @Override
    public LocalDate calculateSettlementDate() {
        if (productType == "SPOT") {
            LocalDate sd = tradeDate.plusDays(2);
            if (sd.getDayOfWeek() == DayOfWeek.SATURDAY)
                sd = sd.plusDays(2);
            if (sd.getDayOfWeek() == DayOfWeek.SUNDAY)
                sd = sd.plusDays(1);

            return sd;
        }
        return valueDate;
    }

    // --- MTM calculation ---
    @Override
    public double calculateMTM(double marketRate) {
        double currentSellAmount = buyAmount.doubleValue() * marketRate;
        return currentSellAmount - sellAmount.doubleValue();
    }
}
