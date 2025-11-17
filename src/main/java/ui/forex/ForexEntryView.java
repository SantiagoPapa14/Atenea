package ui.forex;

import ui.HasView;
import ui.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ForexEntryView implements HasView {

    private View view;

    private VBox root = new VBox(12);

    private DatePicker tradeDatePicker = new DatePicker();
    private TextField counterpartyField = new TextField();
    private TextField productTypeField = new TextField();
    private TextField buyCurrency = new TextField();
    private TextField sellCurrency = new TextField();
    private TextField buyAmount = new TextField();
    private TextField rate = new TextField();
    private DatePicker valueDatePicker = new DatePicker();

    private Button submitButton = new Button("Submit Trade");
    private Button backButton = new Button("Back");

    public ForexEntryView() {
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Forex Trade Entry");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        tradeDatePicker.setPromptText("Trade Date");
        counterpartyField.setPromptText("Counterparty");
        productTypeField.setPromptText("Product Type");
        buyCurrency.setPromptText("Buy Currency (USD)");
        sellCurrency.setPromptText("Sell Currency (EUR)");
        buyAmount.setPromptText("Buy Amount");
        rate.setPromptText("Rate");
        valueDatePicker.setPromptText("Value Date");

        root.getChildren().addAll(
                title,
                tradeDatePicker,
                counterpartyField,
                productTypeField,
                buyCurrency,
                sellCurrency,
                buyAmount,
                rate,
                valueDatePicker,
                submitButton,
                backButton);

        view = new View(root);
    }

    public Scene getScene() {
        return new Scene(root, 450, 550);
    }

    public DatePicker getTradeDatePicker() {
        return tradeDatePicker;
    }

    public DatePicker getValueDatePicker() {
        return valueDatePicker;
    }

    public TextField getCounterpartyField() {
        return counterpartyField;
    }

    public TextField getProductTypeField() {
        return productTypeField;
    }

    public TextField getBuyCurrency() {
        return buyCurrency;
    }

    public TextField getSellCurrency() {
        return sellCurrency;
    }

    public TextField getBuyAmount() {
        return buyAmount;
    }

    public TextField getRate() {
        return rate;
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
