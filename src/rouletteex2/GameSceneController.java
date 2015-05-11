/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import rouletteex1.RouletteGame;
import rouletteex1.RouletteGame.BetType;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class GameSceneController implements Initializable {

    MainGridController parentController;
    RouletteTable tableController;
    RouletteGame game;
    
    @FXML
    VBox pnlBets;
    @FXML
    VBox pnlPlayers;
    @FXML
    Label lblBetMessage;
    @FXML
    Label lblMoney;
    @FXML
    Button btnPlaceBet;
    @FXML
    ComboBox<String> cbxBetType;
    @FXML
    Pane pnlTable;
    @FXML
    TextFlow pnlLog;
    @FXML
    Pane pnlRoulette;
    @FXML
    Pane pnlBottom;
    @FXML
    Slider scrollMoney;
    @FXML 
    Label lblBetTitle;
    @FXML
    Pane pnlBet;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblMoney.textProperty().bind(Bindings.format("%.0f", scrollMoney.valueProperty()));
    }
    
    public void PostInit(){
        AddTable();
        InitBetsType();
        DisableBetPanel();
    }

    void SetParentController(MainGridController controller) {
        this.parentController = controller;
    }

    void SetGame(RouletteGame game) {
        this.game = game;
    }

    private void AddTable() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AmericanTable.fxml"));
        try {
            loader.load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        

        tableController = (RouletteTable)loader.getController();
        pnlTable.getChildren().clear();
        pnlTable.getChildren().add(loader.getRoot());
    }

    private void InitBetsType() {
        BetType[] types = this.game.GetSettings().GetRouletteType().BetsTypes;
        
        for (int i = 0; i < types.length; i++) {
            cbxBetType.getItems().add(types[i].name());
        }
        
        if (types.length > 0)
            cbxBetType.setValue(types[0].name());
    }

    private void DisableBetPanel() {
        lblBetTitle.setText("");
        scrollMoney.setValue(0);
//        pnlBet.setDisable(true);
    }
    
}
