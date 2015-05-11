/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import rouletteex1.RouletteGame.BetType;

/**
 *
 * @author Shay
 */
public class RouletteRound {

    // VARS
    private RouletteGame _game;
    private HashMap<String, ArrayList<RouletteBet>> _bets;

    // CTOR
    public RouletteRound(RouletteGame game) {
        _game = game;
        _bets = new HashMap<String, ArrayList<RouletteBet>>();
    }

    // FUNCS
    // BETS
    void PlaceComputerizedBets() throws Exception {
        for (Entry<String, RoulettePlayer> entry : _game.GetPlayers().entrySet()) {
            RoulettePlayer player = entry.getValue();
            if (player.GetPlayerType() == RoulettePlayer.RoulettePlayerType.COMPUTER && player.GetMoney() > 0) {
                RouletteBet bet = GetBetForComputerPlayer(player);
                PlaceBet(player, bet);
            }
        }
    }

    public RouletteBet GetBetForComputerPlayer(RoulettePlayer player) throws Exception {
        if (player.GetPlayerType() != RoulettePlayer.RoulettePlayerType.COMPUTER) {
            throw new Exception("Player is not a computer.");
        }

        if (player.GetIsPlaying() == false || player.GetMoney() <= 0) {
            throw new Exception("Player is not playing or doesn'y have any money.");
        }

        Random rnd = new Random();
        RouletteBet res;

        int index = rnd.nextInt(_game.GetWheel().length); // get a random number for the wheel array
        int money = rnd.nextInt(player.GetMoney() + 1); // set how much money to bet on

        ArrayList<String> numbers = new ArrayList<>();
        numbers.add(_game.GetWheel()[index]); // adds the random number to the bets

        try {
            BetType betType = BetType.STRAIGHT;
            res = new RouletteBet(this._game, player, betType, numbers, money);
        } catch (ExceptionInInitializerError e) {
            throw e;
        }

        return res;
    }

    public void PlaceBet(RoulettePlayer player, RouletteBet bet) throws RoulettePlayer.NotEnoughtMoneyException {
        String playerName = player.GetName();

        if (_bets.containsKey(playerName) == false) {
            _bets.put(playerName, new ArrayList<RouletteBet>());
        }

        player.PayMoney(bet.GetMoney());
        _bets.get(playerName).add(bet);

    }

    public int CalculateWinningForPlayer(String name, RouletteNumber number) {
        int res = 0;

        if (_bets.containsKey(name) == true) {
            ArrayList<RouletteBet> betsPerPlayer = _bets.get(name);
            for (int i = 0; i < betsPerPlayer.size(); i++) {
                res += betsPerPlayer.get(i).CalculateWinning(number);
            }
        }

        return res;
    }

    // ROUND
    public void EndRound() {
        SetBankruppedPlayersAsNotPlaying();
    }

    // PLAYERS
    void SetBankruppedPlayersAsNotPlaying() {
        for (Entry<String, RoulettePlayer> pair : _game.GetPlayers().entrySet()) {
            RoulettePlayer player = pair.getValue();

            if (player.GetMoney() == 0) {
                player.SetIsPlaying(false);
            }
        }
    }

    int GetNumberOfBetsOfPlayer(RoulettePlayer player) {
        ArrayList<RouletteBet> bets = _bets.get(player.GetName());
        if (bets != null) {
            return bets.size();
        } else {
            return 0;
        }
    }

    public ArrayList<RouletteBet> GetBetForPlayer(String playerName) {
        if (_bets.containsKey(playerName) == true) {
            return _bets.get(playerName);
        } else {
            return null;
        }
    }
    
    // OVERRIDES

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this._game);
        hash = 83 * hash + Objects.hashCode(this._bets);
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
        final RouletteRound other = (RouletteRound) obj;
        if (!Objects.equals(this._game, other._game)) {
            return false;
        }
        if (!Objects.equals(this._bets, other._bets)) {
            return false;
        }
        return true;
    }
    
}
