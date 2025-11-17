package ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Nav {

    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void go(Scene scene) {
        if (mainStage == null) {
            throw new IllegalStateException("Nav: mainStage not set. Call Nav.setStage() first.");
        }
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void go(HasView controller) {
        go(controller.getView().getScene());
    }
}
