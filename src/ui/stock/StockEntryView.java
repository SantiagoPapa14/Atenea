package ui.stock;

import ui.HasView;
import ui.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class StockEntryView implements HasView {

    private View view;

    private VBox root = new VBox(12);

    private TextField tickerField = new TextField();
    private TextField qtyField = new TextField();
    private TextField priceField = new TextField();
    private TextField cpField = new TextField();
    private TextField exchangeField = new TextField();
    private TextField currencyField = new TextField();
    private DatePicker datePicker = new DatePicker();

    private Button submitButton = new Button("Submit Trade");
    private Button backButton = new Button("Back");

    public StockEntryView() {
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Stock Trade Entry");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        tickerField.setPromptText("Ticker (AAPL)");
        qtyField.setPromptText("Quantity");
        priceField.setPromptText("Price");
        cpField.setPromptText("Counterparty");
        exchangeField.setPromptText("Exchange (NASDAQ)");
        currencyField.setPromptText("Currency (USD)");
        datePicker.setPromptText("Trade Date");

        root.getChildren().addAll(
                title,
                tickerField,
                qtyField,
                priceField,
                cpField,
                exchangeField,
                currencyField,
                datePicker,
                submitButton,
                backButton);

        view = new View(root);
    }

    public Scene getScene() {
        return new Scene(root, 450, 550);
    }

    public TextField getTickerField() {
        return tickerField;
    }

    public TextField getQtyField() {
        return qtyField;
    }

    public TextField getPriceField() {
        return priceField;
    }

    public TextField getCounterpartyField() {
        return cpField;
    }

    public TextField getExchangeField() {
        return exchangeField;
    }

    public TextField getCurrencyField() {
        return currencyField;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    @Override
    public View getView() {
        return view;
    }

}
