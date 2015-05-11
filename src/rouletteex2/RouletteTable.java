/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex2;

import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import rouletteex1.RouletteBet;
import rouletteex1.RouletteGame;

/**
 *
 * @author shay.lugasi
 */
public abstract class RouletteTable implements Initializable {
    public HashMap<String, Label> numbers;
    
    public abstract void InitNumbersHashMap();
    
    @FXML
    public void HotSpotClicked() {
//        System.out.println(args.getX() + " " + args.getY());
    }

    @FXML
    Label num3;
    @FXML
    Label num2;
    @FXML
    Label num1;
    @FXML
    Label num4;
    @FXML
    Label num5;
    @FXML
    Label num6;
    @FXML
    Label num12;
    @FXML
    Label num11;
    @FXML
    Label num10;
    @FXML
    Label num7;
    @FXML
    Label num8;
    @FXML
    Label num9;
    @FXML
    Label num21;
    @FXML
    Label num20;
    @FXML
    Label num19;
    @FXML
    Label num22;
    @FXML
    Label num23;
    @FXML
    Label num24;
    @FXML
    Label num18;
    @FXML
    Label num17;
    @FXML
    Label num16;
    @FXML
    Label num13;
    @FXML
    Label num14;
    @FXML
    Label num15;
    @FXML
    Label num27;
    @FXML
    Label num26;
    @FXML
    Label num25;
    @FXML
    Label num28;
    @FXML
    Label num29;
    @FXML
    Label num30;
    @FXML
    Label num36;
    @FXML
    Label num35;
    @FXML
    Label num34;
    @FXML
    Label num31;
    @FXML
    Label num32;
    @FXML
    Label num33;
    @FXML
    Label colBottom;
    @FXML
    Label colMiddle;
    @FXML
    Label colTop;
    @FXML
    Label num0;

    @FXML
    Label first12;
    @FXML
    Label second12;
    @FXML
    Label third12;
    @FXML
    Label ontToEighteen;
    @FXML
    Label even;
    @FXML
    Label nineteenToThirtySix;
    @FXML
    Label odd;
    @FXML
    Label red;
    @FXML
    Label black;
    @FXML
    Label lblMessage;
    @FXML
    Group grpHotSpots;

    RouletteGame game;

    public void SetGame(RouletteGame game){
        this.game = game;
    }
    
    public RouletteGame GetGame(){
        return this.game;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        InitNumbersHashMap();
        ClearMessageLabel();
    }

    public void ClearMessageLabel() {
        lblMessage.setText("");
    }
    
    public void HotSpotEntered(ImageView img) {
        img.setOpacity(1.0);
    }

    public void HotSpotExited(ImageView img) {
        img.setOpacity(0.1);
    }

    public rouletteex1.RouletteBet GetNumbersArray(double x, double y) {
        // split
        if (x == 53 && y == 26) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"00", "3"});
        } else if (x == 88 && y == 27) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"3", "6"});
        } else if (x == 123 && y == 26) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"6", "9"});
        } else if (x == 158 && y == 27) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"9", "12"});
        } else if (x == 193 && y == 27) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"12", "15"});
        } else if (x == 228 && y == 28) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"15", "18"});
        } else if (x == 263 && y == 27) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"18", "21"});
        } else if (x == 298 && y == 28) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"21", "24"});
        } else if (x == 334 && y == 28) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"24", "27"});
        } else if (x == 369 && y == 29) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"27", "30"});
        } else if (x == 404 && y == 28) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"30", "33"});
        } else if (x == 439 && y == 29) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"33", "36"});
        } else if (x == 88 && y == 64) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"2", "5"});
        } else if (x == 123 && y == 63) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"5", "8"});
        } else if (x == 158 && y == 64) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"8", "11"});
        } else if (x == 193 && y == 64) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"11", "14"});
        } else if (x == 228 && y == 65) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"14", "17"});
        } else if (x == 263 && y == 64) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"17", "20"});
        } else if (x == 298 && y == 65) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"20", "23"});
        } else if (x == 334 && y == 65) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"23", "26"});
        } else if (x == 369 && y == 66) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"26", "29"});
        } else if (x == 404 && y == 65) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"29", "32"});
        } else if (x == 439 && y == 66) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"32", "35"});
        } else if (x == 53 && y == 99) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"0", "1"});
        } else if (x == 88 && y == 99) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"1", "4"});
        } else if (x == 123 && y == 98) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"4", "7"});
        } else if (x == 158 && y == 99) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"7", "10"});
        } else if (x == 193 && y == 99) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"10", "13"});
        } else if (x == 228 && y == 100) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"13", "16"});
        } else if (x == 263 && y == 99) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"16", "19"});
        } else if (x == 298 && y == 100) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"19", "22"});
        } else if (x == 334 && y == 100) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"22", "25"});
        } else if (x == 369 && y == 101) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"25", "28"});
        } else if (x == 404 && y == 100) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"28", "31"});
        } else if (x == 439 && y == 101) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"31", "34"});
        } else if (x == 71 && y == 42) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"3", "2"});
        } else if (x == 70 && y == 80) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"1", "2"});
        } else if (x == 106 && y == 42) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"6", "5"});
        } else if (x == 105 && y == 80) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"5", "4"});
        } else if (x == 141 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"9", "8"});
        } else if (x == 140 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"8", "7"});
        } else if (x == 176 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"12", "11"});
        } else if (x == 175 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"11", "10"});
        } else if (x == 213 && y == 42) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"15", "14"});
        } else if (x == 212 && y == 80) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"13", "14"});
        } else if (x == 248 && y == 42) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"18", "17"});
        } else if (x == 247 && y == 80) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"17", "16"});
        } else if (x == 283 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"21", "20"});
        } else if (x == 282 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"20", "19"});
        } else if (x == 318 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"24", "23"});
        } else if (x == 317 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"23", "22"});
        } else if (x == 351 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"27", "26"});
        } else if (x == 350 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"26", "25"});
        } else if (x == 386 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"30", "29"});
        } else if (x == 385 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"29", "28"});
        } else if (x == 421 && y == 44) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"33", "32"});
        } else if (x == 420 && y == 82) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"32", "31"});
        } else if (x == 456 && y == 44) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"36", "35"});
        } else if (x == 455 && y == 82) {
            return new RouletteBet(RouletteGame.BetType.SPLIT, new String[]{"35", "34"});
        } // street
        else if (x == 70 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"1", "2", "3"});
        } else if (x == 105 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"4", "5", "6"});
        } else if (x == 140 && y == 118) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"7", "8", "9"});
        } else if (x == 175 && y == 118) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"10", "11", "12"});
        } else if (x == 212 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"13", "14", "15"});
        } else if (x == 247 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"16", "17", "18"});
        } else if (x == 282 && y == 118) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"19", "20", "21"});
        } else if (x == 317 && y == 118) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"22", "23", "24"});
        } else if (x == 350 && y == 118) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"25", "26", "27"});
        } else if (x == 385 && y == 118) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"28", "29", "30"});
        } else if (x == 420 && y == 119) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"31", "32", "33"});
        } else if (x == 455 && y == 119) {
            return new RouletteBet(RouletteGame.BetType.STREET, new String[]{"34", "35", "36"});
        } // corner
        else if (x == 88 && y == 42) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"3", "6", "2", "5"});
        } else if (x == 87 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"2", "5", "1", "4"});
        } else if (x == 123 && y == 42) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"6", "9", "5", "8"});
        } else if (x == 122 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"5", "8", "4", "7"});
        } else if (x == 158 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"9", "12", "8", "11"});
        } else if (x == 157 && y == 82) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"8", "11", "7", "10"});
        } else if (x == 193 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"12", "15", "11", "14"});
        } else if (x == 192 && y == 82) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"11", "14", "10", "13"});
        } else if (x == 230 && y == 2) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"15", "18", "14", "17"});
        } else if (x == 229 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"14", "17", "13", "16"});
        } else if (x == 265 && y == 42) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"18", "21", "17", "20"});
        } else if (x == 264 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"17", "20", "16", "19"});
        } else if (x == 300 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"21", "24", "20", "23"});
        } else if (x == 299 && y == 82) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"20", "23", "19", "22"});
        } else if (x == 335 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"24", "27", "23", "26"});
        } else if (x == 334 && y == 82) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"23", "26", "22", "25"});
        } else if (x == 368 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"27", "30", "26", "29"});
        } else if (x == 367 && y == 82) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"26", "29", "25", "28"});
        } else if (x == 403 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"30", "33", "29", "32"});
        } else if (x == 402 && y == 82) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"29", "32", "28", "31"});
        } else if (x == 438 && y == 44) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"33", "36", "32", "35"});
        } else if (x == 437 && y == 83) {
            return new RouletteBet(RouletteGame.BetType.CORNER, new String[]{"32", "35", "31", "34"});
        } // six line
        else if (x == 88 && y == 116) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"1", "2", "3", "4", "5", "6"});
        } else if (x == 123 && y == 116) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"4", "5", "6", "7", "8", "9"});
        } else if (x == 158 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"7", "8", "9", "10", "11", "12"});
        } else if (x == 193 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"10", "11", "12", "13", "14", "15"});
        } else if (x == 230 && y == 116) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"13", "14", "15", "16", "17", "18"});
        } else if (x == 265 && y == 116) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"16", "17", "18", "19", "20", "21"});
        } else if (x == 300 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"19", "20", "21", "22", "23", "24"});
        } else if (x == 335 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"22", "23", "24", "25", "26", "27"});
        } else if (x == 368 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"25", "26", "27", "28", "29", "30"});
        } else if (x == 403 && y == 117) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"28", "30", "31", "32", "33", "34"});
        } else if (x == 438 && y == 118) {
            return new RouletteBet(RouletteGame.BetType.SIX_LINE, new String[]{"31", "32", "33", "34", "35", "36"});
        } //basket
        else if (x == 53 && y == 43) {
            return new RouletteBet(RouletteGame.BetType.BASKET, new String[]{"00", "3", "2"});
        } else if (x == 53 && y == 64) {
            return new RouletteBet(RouletteGame.BetType.BASKET, new String[]{"00", "0", "2"});
        } else if (x == 53 && y == 81) {
            return new RouletteBet(RouletteGame.BetType.BASKET, new String[]{"0", "1", "2"});
        } 
        //top line
        else if (x == 53 && (y == 10 || y == 117)){
            return new RouletteBet(RouletteGame.BetType.TOP_LINE, new String[]{"00", "0", "1", "2", "3"});
        }
        else {
            return null;
        }
    }

}
