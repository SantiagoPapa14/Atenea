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
    private final Map<Trade, Double> mtmCache = new HashMap<>();

    public TradeListController(TradeRepository repo, MarketService marketService) {
        this.repo = repo;
        this.marketService = marketService;
        this.view = new TradeListView();
        setupTable();
        view.getBackButton().setOnAction(_ -> Nav.go(new HomeController(repo, marketService)));
        view.refreshPricesButton().setOnAction(_ -> setupTable());
    }

    private void computeMTMs() {
        mtmCache.clear();
        for (Trade t : repo.findAll()) {
            mtmCache.put(t, t.calculateMTM(marketService));
        }
    }

    private void setupTable() {
        var table = view.getTable();
        computeMTMs();

        TableColumn<Trade, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Trade, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("productType"));

        TableColumn<Trade, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDescription()));

        TableColumn<Trade, String> cpCol = new TableColumn<>("Counterparty");
        cpCol.setCellValueFactory(new PropertyValueFactory<>("counterparty"));

        TableColumn<Trade, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("tradeDate"));

        TableColumn<Trade, String> investmentCol = new TableColumn<>("Investment");
        investmentCol
                .setCellValueFactory(
                        cellData -> new ReadOnlyObjectWrapper<String>(
                                "$" + String
                                        .valueOf(Math.round(cellData.getValue().calculateValue() * 100.0) / 100.0)));

        TableColumn<Trade, String> mtmCol = new TableColumn<>("Payoff ($)");
        mtmCol.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<String>(
                        "$" + String.valueOf(Math.round(mtmCache.get(cellData.getValue()) * 100.0) / 100.0)));

        TableColumn<Trade, String> mtmPercentageCol = new TableColumn<>("Payoff (%)");
        mtmPercentageCol.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<String>(
                        String.valueOf(Math
                                .round((mtmCache.get(cellData.getValue()) / cellData.getValue().calculateValue() * 100)
                                        * 100.0)
                                / 100.0) + "%"));

        table.getColumns().clear();
        table.getColumns().addAll(idCol, typeCol, descCol, cpCol, dateCol, investmentCol, mtmCol, mtmPercentageCol);

        table.getItems().clear();
        table.getItems().addAll(repo.findAll());
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
