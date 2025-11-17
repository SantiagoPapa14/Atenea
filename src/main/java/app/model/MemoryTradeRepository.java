package app.model;

import java.util.*;

public class MemoryTradeRepository implements TradeRepository {

    private final Map<Long, Trade> storage = new HashMap<>();

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
