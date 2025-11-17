package app;

import javafx.application.Application;
import javafx.stage.Stage;

import app.model.MemoryTradeRepository;
import app.model.TradeRepository;
import app.service.MarketService;

import ui.Nav;
import ui.home.HomeController;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Nav.setStage(stage);

        TradeRepository repo = new MemoryTradeRepository();
        MarketService marketService = new MarketService();
        var home = new HomeController(repo, marketService);

        Nav.go(home);
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }
}
