package app.model;

import java.util.*;
import java.time.*;

public class MemoryTradeRepository implements TradeRepository {

    private final Map<Long, Trade> storage = new HashMap<>();

    public MemoryTradeRepository() {
        Trade trade = new StockTrade((long) 1, LocalDate.now(), "NN", "MSFT", 100, 100, "NYSE", "USD");
        storage.put(trade.getId(), trade);
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
