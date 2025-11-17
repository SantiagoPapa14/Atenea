package app.model;

import java.util.*;
import java.time.*;
import java.math.*;

public class MemoryTradeRepository implements TradeRepository {

    private final Map<Long, Trade> storage = new HashMap<>();

    public MemoryTradeRepository() {
        Trade tradeStock = new StockTrade((long) 1, LocalDate.now(), "NN", "MSFT", 100, 100, "NYSE", "USD");
        Trade tradeStock404 = new StockTrade((long) 2, LocalDate.now(), "NN", "NOT_EXISTING", 10, 150, "NASDAQ", "USD");
        Trade tradeFx = new FxTrade((long) 3, LocalDate.now(), "NN", FxProductType.SPOT, "EUR", "USD",
                BigDecimal.valueOf(100), BigDecimal.valueOf(1.5),
                LocalDate.now());
        Trade tradeFx404 = new FxTrade((long) 4, LocalDate.now(), "NN", FxProductType.SPOT, "ARS", "NOT_EXISTING",
                BigDecimal.valueOf(150), BigDecimal.valueOf(1.2),
                LocalDate.now());

        storage.put(tradeStock.getId(), tradeStock);
        storage.put(tradeStock404.getId(), tradeStock404);
        storage.put(tradeFx.getId(), tradeFx);
        storage.put(tradeFx404.getId(), tradeFx404);
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
