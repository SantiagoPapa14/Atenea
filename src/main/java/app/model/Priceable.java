package app.model;

import app.service.MarketService;

public interface Priceable {
    Double calculateMTM(MarketService marketService);
}
