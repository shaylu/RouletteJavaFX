/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex1;

import java.util.Objects;

/**
 *
 * @author Shay
 */
public class RoulettePlayer {

    // EMUMS
    public enum RoulettePlayerType {

        COMPUTER, HUMAN;

        /**
         *
         * @param string
         * @return
         * @throws Exception
         */
        public static RoulettePlayerType Parse(String string) throws Exception {
            switch (string.toUpperCase()) {
                case "COMPUTER":
                    return RoulettePlayerType.COMPUTER;
                case "HUMAN":
                    return RoulettePlayerType.HUMAN;
                default:
                    throw new Exception("Failed to parse " + string + " to RoulettePlayerType.");
            }
        }
    }

    // EXCEPTIONS TYPE
    public static class PlayerNameAlreadyTakenException extends Exception {

        public PlayerNameAlreadyTakenException() {
            super("A player with the selected name is already exist.");
        }
    }

    public static class NotEnoughtMoneyException extends Exception {

        public NotEnoughtMoneyException() {
            super("The player doesn't have enought money.");
        }
    }

    // CTOR    
    public RoulettePlayer(RouletteGame game, RoulettePlayerType playerType, String name, int initialMoneyAmount) {
        this._game = game;
        this._playerType = playerType;
        this._name = name;
        this._money = initialMoneyAmount;
        this._isPlaying = true;
    }

    // VARS
    private RoulettePlayerType _playerType;
    private RouletteGame _game;
    private String _name;
    private int _money;
    private boolean _isPlaying;

    // GETTERS
    public String GetName() {
        return _name;
    }

    public int GetMoney() {
        return _money;
    }

    public RoulettePlayerType GetPlayerType() {
        return _playerType;
    }

    public boolean GetIsPlaying() {
        return _isPlaying;
    }

    // SETTERS
    public void SetIsPlaying(boolean isPlaying) {
        _isPlaying = isPlaying;
    }

    // OVERRIDES
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this._playerType);
        hash = 43 * hash + Objects.hashCode(this._game);
        hash = 43 * hash + Objects.hashCode(this._name);
        hash = 43 * hash + this._money;
        hash = 43 * hash + (this._isPlaying ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoulettePlayer other = (RoulettePlayer) obj;
        if (this._playerType != other._playerType) {
            return false;
        }
        if (!Objects.equals(this._game, other._game)) {
            return false;
        }
        if (!Objects.equals(this._name, other._name)) {
            return false;
        }
        if (this._money != other._money) {
            return false;
        }
        if (this._isPlaying != other._isPlaying) {
            return false;
        }
        return true;
    }

    // FUNCS
    boolean IsHuman() {
        if (_playerType == RoulettePlayerType.HUMAN) {
            return true;
        } else {
            return false;
        }
    }

    public void PayMoney(int money) throws NotEnoughtMoneyException {
        if (money > _money) {
            throw new NotEnoughtMoneyException();
        }

        _money -= money;
    }
    
    public void RecieveMoney(int money) {
        _money += money;
    }

}
