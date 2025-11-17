package app.model;

import app.service.MarketService;

public interface Priceable {
    double calculateMTM(MarketService marketService);
}
