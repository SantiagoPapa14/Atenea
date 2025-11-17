package app;

import javafx.application.Application;
import javafx.stage.Stage;
import app.model.MemoryTradeRepository;
import app.model.TradeRepository;
import ui.Nav;
import ui.home.HomeController;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Nav.setStage(stage);

        TradeRepository repo = new MemoryTradeRepository();
        var home = new HomeController(repo);

        Nav.go(home);
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }
}
