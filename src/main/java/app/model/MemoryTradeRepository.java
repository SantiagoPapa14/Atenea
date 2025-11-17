package app.model;

import java.util.*;
import java.time.*;
import java.math.*;

public class MemoryTradeRepository implements TradeRepository {

    private final Map<Long, Trade> storage = new HashMap<>();

    public MemoryTradeRepository() {
        Trade tradeStock = new StockTrade((long) 1, LocalDate.now(), "NN", "MSFT", 100, 100, "NYSE", "USD");
        Trade tradeFx = new FxTrade((long) 2, LocalDate.now(), "NN", FxProductType.SPOT, "EUR", "USD",
                BigDecimal.valueOf(100), BigDecimal.valueOf(1.5),
                LocalDate.now());
        storage.put(tradeStock.getId(), tradeStock);
        storage.put(tradeFx.getId(), tradeFx);
    }

    @Override
    public void save(Trade trade) {
        storage.put(trade.getId(), trade);
    }

    @Override
    public Trade findById(long id) {
        return storage.get(id);
    }

    @Override
    public List<Trade> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(long id) {
        storage.remove(id);
    }
}
