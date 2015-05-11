/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import rouletteex1.RouletteGame.RouletteType;

/**
 *
 * @author Shay
 */
public class RouletteGameManager {

    // VARS
    public RouletteGame game;

    // GAME
    public void Start() {
        int action;
        boolean keepRunning = true;
        Scanner scanner = new Scanner(System.in);

        final int NEW_GAME = 1;
        final int LOAD_FROM_XML = 2;
        final int EXIT = 3;

        while (keepRunning == true) {
            printMainMenu();
            System.out.print("Selection: ");
            try {
                action = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (action) {
                case NEW_GAME: // new game
                    NewGame(true, true); // true is to read settings from console and read bets
                    break;
                case LOAD_FROM_XML: // load from xml
                    if (LoadGameFromXML() == true) {
                        NewGame(false, false); // not to read settings and bets but start the round
                    }
                    break;
                case EXIT: // exit
                    keepRunning = false;
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

    private void NewGame(boolean readSettingsFromConsole, boolean readBets) {
        System.out.println("\n======== NEW GAME ========");

        boolean keepRunning = true;
        boolean exitGame = false;

        if (readSettingsFromConsole == true) {
            RouletteSettings settings;
            try {
                settings = ReadSettings();
            } catch (Exception e) {
                System.out.println("Failed to read game settings." + e.getMessage());
                return;
            }

            game = new RouletteGame(settings);
            ReadPlayers(game);
            game.CreateComputerizedPlayers();
        }

        while (game.GetActivePlayersNumbers() > 0 && keepRunning == true) {
//        while (game.GetActiveHumanPlayersNumber() > 0 && keepRunning == true) {
            System.out.println("\n******* NEW ROUND *******");

            if (readBets == true) {
                game.NewRound();

                // place bets
                for (Entry<String, RoulettePlayer> entry : game.GetPlayers().entrySet()) {

                    RoulettePlayer player = entry.getValue();
                    if (player.GetIsPlaying() == true) {
                        System.out.println(player.GetName() + " turn...");
                        CheckWithPlayerToSaveGame(player);
                    }

                    boolean playerPlacedABet = PlaceBets(game, game.GetRound(), player);

                    if (playerPlacedABet == false) {
                        final String EXIT = "2";

                        ShowGameMenuAfterPlayerDidntBet();
                        System.out.print("\nSelection: ");
                        Scanner scanner = new Scanner(System.in);
                        String selection = scanner.nextLine();

                        switch (selection) {
                            case EXIT:
                                keepRunning = false;
                                exitGame = true;
                                break;
                            default:
                                break;
                        }

                        if (exitGame != true) {
                            IsPlayerMadeEnoughtBets(player);
                        }
                    }
                }
            } else {
                readBets = true;
                HashMap<String, RoulettePlayer> players = game.GetPlayers();
                for (Entry<String, RoulettePlayer> playerSet : players.entrySet()) {
                    String name = playerSet.getKey();
                    RoulettePlayer player = playerSet.getValue();
                    ArrayList<RouletteBet> bets = game.GetRound().GetBetForPlayer(name);
                    if (bets != null) {
                        for (RouletteBet bet : bets) {
                            System.out.println(bet);
                        }
                    } else {
                        System.out.println(name + " Didn't bet.");
                    }
                }

            }

            if (exitGame != true) {
                // get number from wheel
                RouletteNumber number = game.TurnWheel();
                System.out.println("\nTurning wheel...... \nThe roulette number won is: " + number.GetName());
                System.out.println("==================================");
                // get winning money
                for (Entry<String, RoulettePlayer> playerEntry : game.GetPlayers().entrySet()) {
                    RoulettePlayer player = playerEntry.getValue();

                    int winningMoney = game.GetRound().CalculateWinningForPlayer(player.GetName(), number);

                    if (winningMoney > 0) {
                        player.RecieveMoney(winningMoney);
                        System.out.println(player.GetName() + " won " + winningMoney + ". Current balance: " + player.GetMoney());
                    } else {
                        System.out.println(player.GetName() + " didn't win nothing. Current balance: " + player.GetMoney());
                    }
                }

                game.EndRound();
            }
        }

        // System.out.println("\nNo more active human players left.");
        System.out.println("\nGame is finished.");
        ShowScoreBoard(game);
    }

    private boolean LoadGameFromXML() {
        System.out.println("\n== LOAD GAME FROM XML FILE ==");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter XML file path: ");
        String path = scanner.nextLine();

        try {
            game = RouletteXMLManager.Load(path);
            System.out.println("== GAME LOADED SUCCESSFULLY ==");
            return true;
        } catch (Exception e) {
            System.out.println("failed to load game from xml. " + e.getMessage());
            return false;
        }
    }

    private void SaveGame() {
        try {
            if (game.IsGameAlreadySaved() != true) {
                SaveAsGame();
            } else {
                game.SaveGame();
                System.out.println("\n== GAME SAVED ==");
            }
        } catch (Exception e) {
            System.out.println("Failed to save game. " + e.getMessage());
        }

    }

    private void SaveAsGame() {
        System.out.print("Please enter the path to save the xml: ");

        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();

        try {
            game.SaveGame(path);
            System.out.println("\n== GAME SAVED ==");

        } catch (Exception e) {
            System.out.println("Failed to save game. " + e.getMessage());
        }
    }

    private boolean PlaceBets(RouletteGame game, RouletteRound round, RoulettePlayer player) {
        if (player.GetIsPlaying() == false) {
            return true;
        }

        if (player.GetMoney() <= 0) {
            System.out.println(player.GetName() + ", you don't have any more money left.");
            return true;
        }

        if (round.GetNumberOfBetsOfPlayer(player) == game.GetSettings().GetMaximumBetsPerPlayer()) {
            System.out.println(player.GetName() + ", you've reached the maximum bets you can make.");
            return true;
        }

        RouletteBet bet;

        // player is computer
        if (player.IsHuman() == false) {
            try {
                bet = round.GetBetForComputerPlayer(player);
                round.PlaceBet(player, bet);
                System.out.println(bet);
                return true;
            } catch (Exception e) {
                System.out.println("Failed to place bet for computer player (" + player.GetName() + ").");
            }
        }

        // player is human
        boolean keepPlaying = AskPlayerIfHeWantsToKeepPlaying(player);
        if (keepPlaying == false) {
            player.SetIsPlaying(false);
            return false;
        }

        RouletteGame.BetType type = GetBetTypeFromConsole(game.GetSettings().GetRouletteType());
        int money = ReadAmountOfMoneyFromPlayer(player.GetMoney());
        ArrayList<String> numbers = null;

        while (true) {
            if (type.NeedsNumbers == true) {
                numbers = ReadNumbersFromConsole(game, type);
            }

            try {
                bet = new RouletteBet(game, player, type, numbers, money);
                break;
            } catch (Exception ex) {
                System.out.println("Bet invalid! " + ex.getMessage());
            }
        }

        try {
            round.PlaceBet(player, bet);
            System.out.println("\n" + bet);
        } catch (RoulettePlayer.NotEnoughtMoneyException e) {
            return PlaceBets(game, round, player);
        }

        System.out.println("\nPlace another bet (y/n)?");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        if (str.equals("y")) {
            return PlaceBets(game, round, player);
        } else {
            return true;
        }
    }

    private void SortPlayersByMoney(RoulettePlayer[] players) {
        int n = players.length;
        RoulettePlayer temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (players[j - 1].GetMoney() < players[j].GetMoney()) {
                    //swap the elements!
                    temp = players[j - 1];
                    players[j - 1] = players[j];
                    players[j] = temp;
                }
            }
        }
    }

    // READ FROM CONSOLE
    private RouletteSettings ReadSettings() throws Exception {
        Scanner scanner = new Scanner(System.in);
        RouletteType type;
        int minimumBetPerPlayer;
        int maximumBetsPerPlater;
        int initialMoney;
        int computerizedPlayer, humanPlayers;

        // roulette type
        while (true) {
            System.out.print("Roulette type - French (1), American (2)? ");
            int typeInt = Integer.parseInt(scanner.nextLine());
            if (typeInt != 1 && typeInt != 2) {
                System.out.println("Invalid selection!");
                continue;
            }

            if (typeInt == 1) {
                type = RouletteType.FRENCH;
            } else {
                type = RouletteType.AMERICAN;
            }

            break;
        }

        while (true) {
            System.out.print("Minimum bets per player (0 or 1)? ");
            int num = Integer.parseInt(scanner.nextLine());
            if (num != 0 && num != 1) {
                System.out.println("Invalid selection!");
                continue;
            }

            minimumBetPerPlayer = num;
            break;
        }

        while (true) {
            System.out.print("Maximum bets per player (1 to 10)? ");
            int num = Integer.parseInt(scanner.nextLine());
            if (num < 1 || num > 10) {
                System.out.println("Invalid selection!");
                continue;
            }

            maximumBetsPerPlater = num;
            break;
        }

        while (true) {
            System.out.print("Initial amount of money per player (10 to 100)? ");
            int num = Integer.parseInt(scanner.nextLine());
            if (num < 10 || num > 100) {
                System.out.println("Invalid selection!");
                continue;
            }

            initialMoney = num;
            break;
        }

        while (true) {
            while (true) {
                System.out.print("Number of computerized players (0 to 6)? ");
                int num = Integer.parseInt(scanner.nextLine());
                if (num < 0 || num > 6) {
                    System.out.println("Invalid selection!");
                    continue;
                }

                computerizedPlayer = num;
                break;
            }

            while (true) {
                System.out.print("Number of human players (0 to 6)? ");
                int num = Integer.parseInt(scanner.nextLine());
                if (num < 0 || num > 6) {
                    System.out.println("Invalid selection!");
                    continue;
                }

                humanPlayers = num;
                break;
            }

            if (humanPlayers + computerizedPlayer > 6) {
                System.out.println("Computerized players and human players are bigger than 6.");
                continue;
            } else {
                break;
            }
        }

        System.out.print("Game Name? ");
        String name = scanner.nextLine();

        RouletteSettings res;

        try {
            res = new RouletteSettings(type, minimumBetPerPlayer, maximumBetsPerPlater, initialMoney, computerizedPlayer, humanPlayers, name);

        } catch (Exception ex) {
            System.out.println("Invalid game settings: " + ex.getMessage());
            return ReadSettings();
        }

        return res;
    }

    private void ReadPlayers(RouletteGame game) {
        Scanner s = new Scanner(System.in);

        for (int i = 0; i < game.GetSettings().GetNumOfRealPlayers(); i++) {
            System.out.print("Please enter Player " + (i + 1) + " name: ");
            try {
                game.AddPlayer(RoulettePlayer.RoulettePlayerType.HUMAN, s.nextLine());
            } catch (RoulettePlayer.PlayerNameAlreadyTakenException ex) {
                System.out.println(ex.getMessage());
                i--;
            }
        }

    }

    private RouletteGame.BetType GetBetTypeFromConsole(RouletteGame.RouletteType type) {
        System.out.println("\nChoose bet type: ");

        RouletteGame.BetType[] betTypes = type.BetsTypes;
        for (int i = 0; i < betTypes.length; i++) {
            System.out.print((i + 1) + ". " + betTypes[i].name() + "\t");

            if (i % 5 == 0) {
                System.out.println("");
            }
        }
        System.out.print("\n");

        System.out.print("Your selection: ");
        Scanner scanner = new Scanner(System.in);
        int selection = Integer.parseInt(scanner.nextLine());

        while (true) {
            if (selection >= 1 && selection <= betTypes.length) {
                return betTypes[selection - 1];
            } else {
                return GetBetTypeFromConsole(type);
            }
        }
    }

    private ArrayList<String> ReadNumbersFromConsole(RouletteGame game, RouletteGame.BetType type) {
        System.out.print("\nHow many numbers do you want to give? ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        Integer numbersToGet = Integer.parseInt(str);
        ArrayList<String> res = new ArrayList<>();

        if (numbersToGet <= 0) {
            System.out.println("Please give a positive number.");
            return ReadNumbersFromConsole(game, type);
        }

        for (int i = 0; i < numbersToGet; i++) {
            System.out.print("Number " + (i + 1) + " of " + numbersToGet + ": ");
            String num = scanner.nextLine();
            if (game.IsNumInWheel(num) == true && !res.contains(num)) {
                res.add(num);
            } else {
                System.out.println("Number invalid!");
                i--;
            }
        }

        return res;
    }

    private int ReadAmountOfMoneyFromPlayer(int max) {
        while (true) {
            System.out.println("\nPlace a bet between 0 to " + max + ": ");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            Integer money = Integer.parseInt(str);

            if (money >= 0 && money <= max) {
                return money;
            }
        }
    }

    private boolean AskPlayerIfHeWantsToKeepPlaying(RoulettePlayer player) {
        System.out.print("\n" + player.GetName() + ", would you like to place a bet (y) or game out (n)? ");

        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        if (str.equals("y") == true) {
            return true;
        } else {
            return false;
        }
    }

    // PRINT TO CONSOLE
    private void ShowScoreBoard(RouletteGame game) {
        System.out.println("\n//////// SCORE BOARD ////////");
        RoulettePlayer[] players = game.GetPlayers().values().toArray(new RoulettePlayer[game.GetPlayers().size()]);
        SortPlayersByMoney(players);
        for (int i = 0; i < players.length; i++) {
            RoulettePlayer player = players[i];
            System.out.println(player.GetName() + ": " + player.GetMoney());
        }

        System.out.println("");
    }

    private void printMainMenu() {
        System.out.println("== MENU == ");
        System.out.println("1. NEW GAME");
        System.out.println("2. LOAD GAME");
        System.out.println("3. EXIT");
    }

    private void ShowGameMenuAfterPlayerDidntBet() {
        System.out.println("== NO MORE BETS? ==");
        System.out.println("1. KEEP WATCHING GAME WITHOUT PLAYING");
        System.out.println("2. EXIT GAME");
    }

    private void CheckWithPlayerToSaveGame(RoulettePlayer player) {
        final String SAVE_GAME = "1";
        final String SAVE_AS_GAME = "2";

        // save options
        if (player.IsHuman() == true) {
            System.out.println("\n== PLAYER OPTIONS ==");
            System.out.println("1. SAVE GAME");
            System.out.println("2. SAVE AS GAME");
            System.out.println("3. CONTINUE (can press ENTER key)");
            System.out.print("\nSelection: ");

            Scanner scanner = new Scanner(System.in);
            String selection = scanner.nextLine();

            switch (selection) {
                case SAVE_GAME:
                    SaveGame();
                    break;
                case SAVE_AS_GAME:
                    SaveAsGame();
                    break;
                default:
                    break;
            }
        }
    }

    // FUNCS 
    private void IsPlayerMadeEnoughtBets(RoulettePlayer player) {
        int numOfBetOfThePlayer = game.GetRound().GetNumberOfBetsOfPlayer(player);
        int minBetsPerPlayer = game.GetSettings().GetMinimumBetsPerPlayer();

        if (numOfBetOfThePlayer < minBetsPerPlayer) {
            System.out.println("You have not reached the minimum bets, you are out.");
            player.SetIsPlaying(false);
        }
    }

}
