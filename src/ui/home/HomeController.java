package ui.home;

import app.model.TradeRepository;
import ui.Nav;
import ui.HasView;
import ui.View;
import ui.tradelist.TradeListController;
import ui.stock.StockEntryController;
import ui.forex.ForexEntryController;

public class HomeController implements HasView {

    private final HomeView view;
    private final TradeRepository repo;

    public HomeController(TradeRepository repo) {
        this.repo = repo;
        this.view = new HomeView();

        // Event handlers
        view.getTradeListBtn().setOnAction(e -> Nav.go(new TradeListController(repo)));
        view.getNewStockBtn().setOnAction(e -> Nav.go(new StockEntryController(repo)));
        view.getNewFxBtn().setOnAction(e -> Nav.go(new ForexEntryController(repo)));
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
