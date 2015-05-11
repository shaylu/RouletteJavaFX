/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rouletteex1.RouletteGame;
import rouletteex1.RoulettePlayer;
import rouletteex1.RouletteSettings;

/**
 * FXML Controller class
 *
 * @author Shay
 */
public class NewGameController implements Initializable {
    
    private RouletteSettings ReadSettings() throws Exception{
        RouletteGame.RouletteType type = ParseRouletteType();
        int min = Integer.parseInt(cbxMinimumBets.getValue());
        int max = Integer.parseInt(cbxMaximumBets.getValue());
        int money = Integer.parseInt(txtInitialAmount.getText());
        int humans = (int)players.entrySet().stream().filter(x -> x.getValue() == RoulettePlayer.RoulettePlayerType.HUMAN).count();
        int computer = (int)players.entrySet().stream().filter(x -> x.getValue() == RoulettePlayer.RoulettePlayerType.COMPUTER).count();
        String name = txtGameName.getText();

        return new RouletteSettings(type, min, max, money, computer, humans, name);
    }

    private RouletteGame.RouletteType ParseRouletteType() {
        if (rdAmerican.isSelected() == true)
            return RouletteGame.RouletteType.AMERICAN;
        else
            return RouletteGame.RouletteType.FRENCH;
    }

    private void SetError(String text) {
        lblPlayersErrorMessage.setText(text);
    }
    
    private void ClearError() {
        lblPlayersErrorMessage.setText("");
    }
    
    private MainGridController parentController;
    
    @FXML
    private ComboBox<String> cbxMinimumBets;
    @FXML
    private ComboBox<String> cbxMaximumBets;
    @FXML
    private TextField txtInitialAmount;
    @FXML
    private TextField txtGameName;
    @FXML
    private TextField txtPlayerName;
    @FXML 
    private RadioButton rdAmerican;
    @FXML 
    private RadioButton rdFrench;
    
    @FXML
    private ToggleGroup grpRouletteType;
    
    
    @FXML
    private VBox pnlPlayers;
    
    @FXML
    private Label lblPlayersErrorMessage;
    
    HashMap<String, RoulettePlayer.RoulettePlayerType> players;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        players = new HashMap<>();
        
        InitMinimumBets();
        InitMaximumBets();
    }  
    
    public void SetParentController(MainGridController controller){
        this.parentController = controller;
    }

    private void InitMinimumBets() {
        String[] values = {"0", "1"};
        SetComboboxValues(cbxMinimumBets, values);
        
    }

    private void SetComboboxValues(ComboBox<String> cbx, String[] values) {
        cbx.getItems().clear();
        cbx.getItems().addAll(values);
        
        if (values.length > 0)
            cbx.setValue(values[0]);
    }

    private void InitMaximumBets() {
        String[] values = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        SetComboboxValues(cbxMaximumBets, values);
    }
    
    private void AddPlayer(RoulettePlayer.RoulettePlayerType type){
        String name = txtPlayerName.getText();
        if (ValidatePlayerName() == false)
            return;
        else {
            ClearError();
            players.put(name, type);
            
            Label lbl = new Label();
            lbl.setText(txtPlayerName.getText() + " (" + type.toString() + ")");
            pnlPlayers.getChildren().add(lbl);
        }
    }
    
    public void AddHumanPlayer() { 
        AddPlayer(RoulettePlayer.RoulettePlayerType.HUMAN);
    }
    
    public void AddComputerPlayer() { 
        AddPlayer(RoulettePlayer.RoulettePlayerType.COMPUTER);
    }
    
    @FXML
    private void StartGame(){
        if (parentController == null)
            return;

        rouletteex1.RouletteSettings settings = null;
        
        if (players.size() == 0)
        {
            SetError("No Players found.");
            return;
        }
        else
        {
            ClearError();
        }
        
        try {
            settings = ReadSettings();
        } catch (Exception e) {
            SetError("Game settings invalid: " + e.getMessage());
            return;
        }
        
        rouletteex1.RouletteGame game = new RouletteGame(settings);
        
        try {
            game.AddPlayers(players);
        } catch (Exception e) {
            SetError("Failed to add players: " + e.getMessage());
            return;
        }
        
        parentController.StartGame(game);
    }
    
    private boolean ValidatePlayerName() {
        String name = txtPlayerName.getText();

        if (name.equals(""))
        {
            SetError("Player name can't be empty.");
            return false;
        }
        
        if (players.containsKey(name))
        {
            SetError("Player name must be unique.");
            return false;
        }
        
        return true;
    }

}
