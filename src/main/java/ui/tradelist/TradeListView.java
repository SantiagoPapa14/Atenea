package ui.tradelist;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import app.model.Trade;
import ui.HasView;
import ui.View;

public class TradeListView implements HasView {

    private VBox root = new VBox(15);
    private TableView<Trade> table = new TableView<>();
    private Button backBtn = new Button("Back");
    private Button refreshBtn = new Button("Refresh Prices");
    private View view;

    public TradeListView() {
        root.setPadding(new Insets(20));
        root.getChildren().addAll(table, backBtn, refreshBtn);
        view = new View(root);
    }

    @Override
    public View getView() {
        return view;
    }

    public TableView<Trade> getTable() {
        return table;
    }

    public Button getBackButton() {
        return backBtn;
    }

    public Button refreshPricesButton() {
        return refreshBtn;
    }
}
