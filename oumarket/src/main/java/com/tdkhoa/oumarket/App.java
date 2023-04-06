package com.tdkhoa.oumarket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private Stage Stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.Stage = stage;
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        
        Button button = new Button("Show New Scene");
        button.setOnAction(event -> showSceneAddProduct());
        
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    private void showSceneAddProduct() {
        StackPane newRoot = new StackPane();
        Scene newScene = new Scene(newRoot, 400, 300);
        Stage.setScene(newScene);
    }

    public static void main(String[] args) {
        launch();
    }

}