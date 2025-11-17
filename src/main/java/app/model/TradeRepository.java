package app.model;

import java.util.List;

public interface TradeRepository {
    void save(Trade trade);

    Trade findById(long id);

    List<Trade> findAll();

    void delete(long id);
}
