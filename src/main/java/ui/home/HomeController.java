package ui.home;

import app.model.TradeRepository;
import app.service.MarketService;

import ui.Nav;
import ui.HasView;
import ui.View;
import ui.tradelist.TradeListController;
import ui.stock.StockEntryController;
import ui.forex.ForexEntryController;

public class HomeController implements HasView {

    private final HomeView view;
    private final TradeRepository repo;
    private final MarketService marketService;

    public HomeController(TradeRepository repo, MarketService marketService) {
        this.repo = repo;
        this.marketService = marketService;
        this.view = new HomeView();

        // Event handlers
        view.getTradeListBtn().setOnAction(e -> Nav.go(new TradeListController(repo, marketService)));
        view.getNewStockBtn().setOnAction(e -> Nav.go(new StockEntryController(repo, marketService)));
        view.getNewFxBtn().setOnAction(e -> Nav.go(new ForexEntryController(repo, marketService)));
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
