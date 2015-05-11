/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex2;

/**
 *
 * @author shay.lugasi
 */
public class RouletteSecondaryController {
    private MainGridController parentController;
    
    public void SetParentController(MainGridController controller){
        this.parentController = controller;
    }
    
    public MainGridController GetParentController(){
        return parentController;
    }
}
