package ui.forex;

import app.model.Trade;
import app.model.FxTrade;
import app.model.TradeRepository;
import app.service.MarketService;

import ui.Nav;
import ui.HasView;
import ui.View;
import ui.home.HomeController;

import java.math.BigDecimal;

public class ForexEntryController implements HasView {

    private ForexEntryView view;
    private TradeRepository repo;
    private MarketService marketService;

    public ForexEntryController(TradeRepository repo, MarketService marketService) {
        this.repo = repo;
        this.marketService = marketService;
        this.view = new ForexEntryView();

        view.getSubmitButton().setOnAction(e -> handleSubmit());
        view.getBackButton().setOnAction(e -> Nav.go(new HomeController(repo, marketService)));

    }

    private void handleSubmit() {
        try {
            var tradeDate = view.getTradeDatePicker().getValue();
            var counterparty = view.getCounterpartyField().getText();
            var productType = view.getProductTypeField().getText();
            var buyCurrency = view.getBuyCurrency().getText();
            var sellCurrency = view.getSellCurrency().getText();
            var buyAmount = new BigDecimal(view.getBuyAmount().getText());
            var rate = new BigDecimal(view.getRate().getText());
            var valueDate = view.getValueDatePicker().getValue();
            var date = view.getValueDatePicker().getValue();
            int id = repo.findAll().size() + 1;

            FxTrade trade = new FxTrade(
                    id,
                    tradeDate,
                    counterparty,
                    productType,
                    buyCurrency,
                    sellCurrency,
                    buyAmount,
                    rate,
                    valueDate);

            trade.validate();
            repo.save(trade);

            System.out.println("Saved trade: " + trade);

        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
