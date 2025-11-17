package ui.tradelist;

import app.model.TradeRepository;
import app.model.Trade;
import app.model.StockTrade;
import app.service.MarketService;

import ui.Nav;
import ui.HasView;
import ui.View;
import ui.home.HomeController;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.util.Map;
import java.util.HashMap;

public class TradeListController implements HasView {
    private final TradeListView view;
    private final TradeRepository repo;
    private final MarketService marketService;
    private final Map<String, Double> priceCache = new HashMap<>();

    public TradeListController(TradeRepository repo, MarketService marketService) {
        this.repo = repo;
        this.marketService = marketService;
        this.view = new TradeListView();
        setupTable();
        view.getBackButton().setOnAction(_ -> Nav.go(new HomeController(repo, marketService)));
        view.refreshPricesButton().setOnAction(_ -> setupTable());
    }

    private void refreshPrices() {
        for (Trade trade : repo.findAll()) {
            if (trade.getProductType().equals("STOCK")) {
                StockTrade stockTrade = (StockTrade) trade;
                String ticker = stockTrade.getTicker();
                Double price = marketService.getStockPrice(ticker);
                priceCache.put(ticker, price);
                System.out.println("PRICE OF " + ticker + ": " + price);
            }
        }
    }

    private void setupTable() {
        var table = view.getTable();
        refreshPrices();

        TableColumn<Trade, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Trade, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("productType"));

        TableColumn<Trade, String> cpCol = new TableColumn<>("Counterparty");
        cpCol.setCellValueFactory(new PropertyValueFactory<>("counterparty"));

        TableColumn<Trade, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("tradeDate"));

        TableColumn<Trade, Double> mtmCol = new TableColumn<>("MTM");
        mtmCol.setCellValueFactory(cellData -> {
            Trade trade = cellData.getValue();
            if (trade.getProductType().equals("STOCK")) {
                StockTrade stockTrade = (StockTrade) trade;
                Double price = priceCache.get(stockTrade.getTicker());
                return new ReadOnlyObjectWrapper<>(cellData.getValue().calculateMTM(price));
            } else {
                return new ReadOnlyObjectWrapper<>(null);
            }
        });

        table.getColumns().clear();
        table.getColumns().addAll(idCol, typeCol, cpCol, dateCol, mtmCol);

        table.getItems().clear();
        table.getItems().addAll(repo.findAll());
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
