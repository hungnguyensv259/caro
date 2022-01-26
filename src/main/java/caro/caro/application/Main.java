/**
 * Lop khoi chay ung dung
 */
package caro.caro.application;

import caro.caro.view.View;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    // Bat dau khoi tao stage
    @Override
    public void start(Stage primaryStage) {
        try {
            View view = new View();
            view.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handle(WindowEvent event) {
        System.exit(0);
    }
}
