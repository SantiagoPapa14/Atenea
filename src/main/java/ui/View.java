package ui;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class View {

    private final Parent root;
    private final Scene scene;

    public View(Parent root) {
        this.root = root;
        this.scene = new Scene(root);
    }

    public Parent getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }
}
