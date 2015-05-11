/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex2;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author shay.lugasi
 */
public class FrenchTableController extends RouletteTable {
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        InitNumbersHashMap();
        ClearMessageLabel();
    }

    @Override
    public void InitNumbersHashMap() {
        numbers = new HashMap();
        numbers.put("0", num0);
        numbers.put("1", num1);
        numbers.put("2", num2);
        numbers.put("3", num3);
        numbers.put("4", num4);
        numbers.put("5", num5);
        numbers.put("6", num6);
        numbers.put("7", num7);
        numbers.put("8", num8);
        numbers.put("9", num9);
        numbers.put("10", num10);
        numbers.put("11", num11);
        numbers.put("12", num12);
        numbers.put("13", num13);
        numbers.put("14", num14);
        numbers.put("15", num15);
        numbers.put("16", num16);
        numbers.put("17", num17);
        numbers.put("18", num18);
        numbers.put("19", num19);
        numbers.put("20", num20);
        numbers.put("21", num21);
        numbers.put("22", num22);
        numbers.put("23", num23);
        numbers.put("24", num24);
        numbers.put("25", num25);
        numbers.put("26", num26);
        numbers.put("27", num27);
        numbers.put("28", num28);
        numbers.put("29", num29);
        numbers.put("30", num30);
        numbers.put("31", num31);
        numbers.put("32", num32);
        numbers.put("33", num33);
        numbers.put("34", num34);
        numbers.put("35", num35);
        numbers.put("36", num36);
    }
}
