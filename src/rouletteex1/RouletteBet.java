/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import rouletteex1.RouletteBetWizard.BetOption;
import rouletteex1.RouletteGame.BetType;

/**
 *
 * @author Shay
 */
public class RouletteBet {
    
    public RouletteBet(BetType type, String[] numbers){
        _betType = type;
        _numbers = new ArrayList(Arrays.asList(numbers));
    }

    private RouletteGame _game;
    private RoulettePlayer _player;
    private BetType _betType;
    private ArrayList<String> _numbers;
    private int _money;

    public RouletteBet(RouletteGame _game, RoulettePlayer _player, BetType _betType, ArrayList<String> _numbers, int _money) throws Exception {
        this._game = _game;
        this._player = _player;
        this._betType = _betType;
        this._numbers = _numbers;
        this._money = _money;

        ValidateBet();

    }

    // GETTERS 
    /**
     * @return the _betType
     */
    public BetType GetBetType() {
        return _betType;
    }

    /**
     * @return the _player
     */
    public RoulettePlayer GetPlayer() {
        return _player;
    }

    /**
     * @return the _numbers
     */
    public ArrayList<String> GetNumbers() {
        return _numbers;
    }

    /**
     * @return the _money
     */
    public int GetMoney() {
        return _money;
    }

    // SETTERS
    /**
     * @param _player the _player to set
     */
    public void SetPlayer(RoulettePlayer _player) {
        this._player = _player;
    }

    /**
     * @param _numbers the _numbers to set
     */
    public void SetNumbers(ArrayList<String> _numbers) {
        this._numbers = _numbers;
    }

    /**
     * @param _money the _money to set
     */
    public void SetMoney(int _money) {
        this._money = _money;
    }

    /**
     * @return the _game
     */
    public RouletteGame GetGame() {
        return _game;
    }

    /**
     * @param _game the _game to set
     */
    public void SetGame(RouletteGame _game) {
        this._game = _game;
    }

    // OVERRIDES
    @Override
    public String toString() {
        return (this._player.GetName() + " bet: " + this._money + ", Bet Type: " + this._betType.name() + GetNumbersString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this._game);
        hash = 79 * hash + Objects.hashCode(this._player);
        hash = 79 * hash + Objects.hashCode(this._betType);
        hash = 79 * hash + Objects.hashCode(this._numbers);
        hash = 79 * hash + this._money;
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
        final RouletteBet other = (RouletteBet) obj;
        if (!Objects.equals(this._game, other._game)) {
            return false;
        }
        if (!Objects.equals(this._player, other._player)) {
            return false;
        }
        if (this._betType != other._betType) {
            return false;
        }
        if (!Objects.equals(this._numbers, other._numbers)) {
            return false;
        }
        if (this._money != other._money) {
            return false;
        }
        return true;
    }
    
    // FUNCS
    
    // RETURNS A STRING WITH THE NUMBERS ARRAY - FOR THE USE OF THE TOSTRING FUNC
    private String GetNumbersString() {
        if (GetBetType().NeedsNumbers == true) {
            return ", Numbers: " + GetNumbersArrayString();
        } else {
            return "";
        }
    }
    
    // CREATES A STRING THAT CONTAINS ALL THE NUMBERS
    private String GetNumbersArrayString() {
        String res = new String();
        for (String object : _numbers) {
            res = res.concat(object + " ");
        }

        return res;
    }
    
    // CHECKS IF THE NUMBERS GIVEN ARE SOME KIND OF A VALID BET FOR THE GIVEN BET TYPE
    private void ValidateBet() throws Exception {
        if (_betType.NeedsNumbers == false) {
            return;
        }

        if (!RouletteBetWizard.BetOption.BetFound(this._game.GetSettings().GetRouletteType(), _betType, this._numbers)) {
            throw new Exception("No bet option for the bet type and numbers given.");
        }
    }
    
    // CHECKS IF THE ROULETTE NUMBER FOUND ON THE CURRENT BET
    private boolean IsNumberFound(RouletteNumber number) {
        if (_betType.NeedsNumbers == false) {
            _numbers = new ArrayList<String>(Arrays.asList(_betType.Numbers));
        }

        for (int i = 0; i < _numbers.size(); i++) {
            if (_numbers.get(i).equals(number.GetName())) {
                return true;
            }
        }

        return false;
    }

    // RETURN THE AMOUNT OF MONEY THIS BET WON
    public int CalculateWinning(RouletteNumber number) {
        boolean won = IsNumberFound(number);
        if (won == true) {
            int factor = _game.GetSettings().GetRouletteType().NumbersOnRoullete / _numbers.size() - 1;
            return (_money + (_money * factor));
        } else {
            return 0;
        }
    }

}
