/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex2;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Shay
 */
public class RouletteEx2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource(MainGridController.MAIN_SCENE));
            loader.load();
            
            Scene scene = new Scene(loader.getRoot());
            MainGridController controller = (MainGridController) loader.getController();
            stage.setScene(scene);
            controller.SetRoot(stage);

            stage.show();
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            System.out.println(e.getMessage());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
