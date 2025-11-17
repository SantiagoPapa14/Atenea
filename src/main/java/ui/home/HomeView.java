package ui.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ui.HasView;
import ui.View;

public class HomeView implements HasView {

    private VBox root = new VBox(15);
    private Button newStockBtn = new Button("New Stock Trade");
    private Button newFxBtn = new Button("New Forex Trade");
    private Button tradeListBtn = new Button("Trade List");
    private View view;

    public HomeView() {
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Trading App Home");
        // Center items
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        root.getChildren().addAll(title, newStockBtn, newFxBtn, tradeListBtn);

        view = new View(root);
    }

    @Override
    public View getView() {
        return view;
    }

    public Button getNewStockBtn() {
        return newStockBtn;
    }

    public Button getNewFxBtn() {
        return newFxBtn;
    }

    public Button getTradeListBtn() {
        return tradeListBtn;
    }
}
