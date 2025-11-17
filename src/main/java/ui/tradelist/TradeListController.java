package ui.tradelist;

import app.model.TradeRepository;
import app.model.Trade;
import app.service.MarketService;
import ui.Nav;
import ui.HasView;
import ui.View;
import ui.home.HomeController;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class TradeListController implements HasView {

    private final TradeListView view;
    private final TradeRepository repo;
    private final MarketService marketService;

    public TradeListController(TradeRepository repo, MarketService marketService) {
        this.repo = repo;
        this.marketService = marketService;
        this.view = new TradeListView();

        setupTable();

        view.getBackButton().setOnAction(e -> Nav.go(new HomeController(repo, marketService)));
    }

    private void setupTable() {
        var table = view.getTable();

        TableColumn<Trade, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Trade, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("productType"));

        TableColumn<Trade, String> cpCol = new TableColumn<>("Counterparty");
        cpCol.setCellValueFactory(new PropertyValueFactory<>("counterparty"));

        TableColumn<Trade, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("tradeDate"));

        TableColumn<Trade, Double> mtmCol = new TableColumn<>("MTM");
        mtmCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().calculateMTM(100)));

        table.getColumns().addAll(idCol, typeCol, cpCol, dateCol, mtmCol);
        table.getItems().addAll(repo.findAll());
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
