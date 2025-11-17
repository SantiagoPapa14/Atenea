package ui.stock;

import app.model.StockTrade;
import app.model.TradeRepository;

import ui.Nav;
import ui.HasView;
import ui.View;
import ui.home.HomeController;

public class StockEntryController implements HasView {

    private StockEntryView view;
    private TradeRepository repo;

    public StockEntryController(TradeRepository repo) {
        this.repo = repo;
        this.view = new StockEntryView();

        view.getSubmitButton().setOnAction(e -> handleSubmit());
        view.getBackButton().setOnAction(e -> Nav.go(new HomeController(repo)));

    }

    private void handleSubmit() {
        try {
            String ticker = view.getTickerField().getText();
            int qty = Integer.parseInt(view.getQtyField().getText());
            double price = Double.parseDouble(view.getPriceField().getText());
            String counterparty = view.getCounterpartyField().getText();
            String exchange = view.getExchangeField().getText();
            String currency = view.getCurrencyField().getText();
            var date = view.getDatePicker().getValue();
            int id = repo.findAll().size() + 1;

            StockTrade trade = new StockTrade(
                    id,
                    date,
                    counterparty,
                    ticker,
                    qty,
                    price,
                    exchange,
                    currency);

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
