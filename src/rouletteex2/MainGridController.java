/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import rouletteex1.RouletteGame;
import rouletteex2.SceneManager;

/**
 * FXML Controller class
 *
 * @author Shay
 */
public class MainGridController implements Initializable {
    
    public static final String MAIN_SCENE = "MainGrid.fxml";
    public static final String NEW_GAME_CONTROL = "NewGame.fxml";
    public static final String GAME_SCENE = "GameScene.fxml";

    
    public FXMLLoader GetSecondary(String name) throws IOException {
        return FXMLLoader.load(getClass().getResource(name));
    }
    
    @FXML 
    private Pane pnlMain;
    
    @FXML
    MenuItem mnuGameNewGame;
    
    private Stage stage;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            NewGame();
        } catch (Exception e) {
        }
    }
    
    @FXML
    public void NewGame(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MainGridController.NEW_GAME_CONTROL));
        try {
            loader.load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        
        NewGameController controller = (NewGameController)loader.getController();
        controller.SetParentController(this);

        SetSecondary(loader);
    }
    
    public void SetSecondary(String name){
        FXMLLoader loader = null;
        
        try {
            loader = GetSecondary(name);
        } catch (Exception e) {
            return;
        }

        SetSecondary(loader);
    }

    void SetRoot(Stage stage) {
        this.stage = stage;
    }
    
    Stage GetStage() {
        return stage;
    }

    private void SetSecondary(FXMLLoader loader) {
        pnlMain.getChildren().clear();
        pnlMain.getChildren().add(loader.getRoot());
    }

    void StartGame(RouletteGame game)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MainGridController.GAME_SCENE));
            loader.load();
            
            GameSceneController controller = (GameSceneController)loader.getController();
            controller.SetParentController(this);
            controller.SetGame(game);
            controller.PostInit();
            
            SetSecondary(loader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    
    
}
