/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import rouletteex1.RouletteGame.BetType;
import rouletteex1.RouletteGame.RouletteType;

/**
 *
 * @author Shay
 */
public class RouletteXMLManager {

    private Document document;

    private RouletteXMLManager(String filename) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(filename);
    }

    private ArrayList<RoulettePlayer> ReadPlayers(RouletteGame game) throws Exception {
        ArrayList<RoulettePlayer> res = new ArrayList<>();

        Element root = document.getDocumentElement();
        NodeList players = root.getElementsByTagName("player");
        for (int i = 0; i < players.getLength(); i++) {
            Node node = players.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                RoulettePlayer player = ReadPlayer(game, node);
                res.add(player);
            }
        }

        return res;
    }

    private HashMap<String, ArrayList<RouletteBet>> ReadBets(RouletteGame game, HashMap<String, RoulettePlayer> playersMap) throws Exception {
        HashMap<String, ArrayList<RouletteBet>> res = new HashMap<>();
        String playerName;
        NodeList players = document.getElementsByTagName("player");

        for (int i = 0; i < players.getLength(); i++) {
            Element playerElement = (Element) players.item(i);
            playerName = playerElement.getAttributes().getNamedItem("name").getNodeValue();
            RoulettePlayer player = playersMap.get(playerName);
            if (player != null) // player found
            {
                if ((playerElement.getElementsByTagName("bets")).getLength() > 0) { // not empty
                    Element betsElement = (Element) playerElement.getElementsByTagName("bets").item(0);
                    ArrayList<RouletteBet> bets = ReadBets(betsElement, game, player);
                    res.put(playerName, bets);
                }
            }
        }

        return res;
    }

    private RouletteSettings ReadSettings() throws Exception {

        Element root = document.getDocumentElement();

        String name = root.getAttribute("name");
        RouletteType table_type = RouletteType.Parse(root.getAttribute("table_type"));

        int min_bets_per_player = Integer.parseInt(root.getAttribute("min_bets_per_player"));
        int max_bets_per_player = Integer.parseInt(root.getAttribute("max_bets_per_player"));

        if (max_bets_per_player < min_bets_per_player) {
            throw new Exception("Maximum bets are smaller than minimum bets.");
        }

        int init_sum_of_money = Integer.parseInt(root.getAttribute("init_sum_of_money"));

        int humans = 0, computers = 0;
        NodeList players = root.getElementsByTagName("player");
        for (int i = 0; i < players.getLength(); i++) {
            Node node = players.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                RoulettePlayer.RoulettePlayerType type
                        = RoulettePlayer.RoulettePlayerType.Parse(node.getAttributes().getNamedItem("type").getNodeValue());

                if (type == RoulettePlayer.RoulettePlayerType.COMPUTER) {
                    computers++;
                } else if (type == RoulettePlayer.RoulettePlayerType.HUMAN) {
                    humans++;
                }
            }
        }

        if (humans + computers > 6) {
            throw new Exception("Number of human players and computer players are bigger than 6.");
        }

        return new RouletteSettings(table_type, min_bets_per_player, max_bets_per_player, init_sum_of_money, humans, computers, name);
    }

    private RoulettePlayer ReadPlayer(RouletteGame game, Node node) throws Exception {
        String name;
        RoulettePlayer.RoulettePlayerType playerType;
        int money;

        playerType = RoulettePlayer.RoulettePlayerType.Parse(node.getAttributes().getNamedItem("type").getNodeValue());
        name = node.getAttributes().getNamedItem("name").getNodeValue();
        money = Integer.parseInt(node.getAttributes().getNamedItem("money").getNodeValue());

        return new RoulettePlayer(game, playerType, name, money);
    }

    private ArrayList<RouletteBet> ReadBets(Element betsElement, RouletteGame game, RoulettePlayer player) throws Exception {
        ArrayList<RouletteBet> res;

        if (!betsElement.hasChildNodes()) {
            return null;
        }

        NodeList betsList = betsElement.getElementsByTagName("bet");

        if (betsList.getLength() == 0) {
            return null;
        } else {
            res = new ArrayList<>();
        }

        for (int i = 0; i < betsList.getLength(); i++) {
            Element betElement = (Element) (betsList.item(i));
            RouletteBet bet = ReadBet(betElement, game, player);

            if (bet != null) {
                res.add(bet);
            }
        }

        return res;
    }

    private RouletteBet ReadBet(Element betElement, RouletteGame game, RoulettePlayer player) throws Exception {
        BetType type = BetType.Parse(betElement.getAttribute("type"));
        int money = Integer.parseInt(betElement.getAttribute("amount"));
        ArrayList<String> numbers;

        if (type.NeedsNumbers == false) {
            return new RouletteBet(game, player, type, null, money);
        } else {
            numbers = new ArrayList<>();
            NodeList nodes = betElement.getElementsByTagName("number");
            for (int i = 0; i < nodes.getLength(); i++) {
                String str = nodes.item(i).getTextContent();
                numbers.add(str);
            }

            return new RouletteBet(game, player, type, numbers, money);
        }
    }

    public static RouletteGame Load(String path) throws Exception {
        RouletteGame res = null;

        RouletteXMLManager manager;
        try {
            manager = new RouletteXMLManager(path);
        } catch (Exception e) {
            throw new Exception("Failed to load xml from file." + e.getMessage());
        }

        RouletteSettings settings;
        try {
            settings = manager.ReadSettings();
        } catch (Exception e) {
            throw new Exception("failed to read settings from xml." + e.getMessage());
        }

        res = new RouletteGame(settings);

        ArrayList<RoulettePlayer> players;
        try {
            players = manager.ReadPlayers(res);
        } catch (Exception e) {
            throw new Exception("failed to load players from xml." + e.getMessage());
        }

        for (RoulettePlayer player : players) {
            try {
                res.AddPlayer(player);
            } catch (Exception e) {
                throw new Exception("failed to add player." + e.getMessage());
            }
        }

        res.NewRound();

        HashMap<String, ArrayList<RouletteBet>> bets;

        try {
            bets = manager.ReadBets(res, res.GetPlayers());
        } catch (Exception e) {
            throw new Exception("failed to load bets from xml." + e.getMessage());
        }

        for (RoulettePlayer player : players) {
            ArrayList<RouletteBet> playerBets = bets.get(player.GetName());
            if (playerBets != null) {
                for (RouletteBet playerBet : playerBets) {
                    try {
                        res.GetRound().PlaceBet(player, playerBet);
                    } catch (Exception e) {
                        throw new Exception("faild to place bet for player " + player.GetName() + ", " + e.getMessage());
                    }
                }
            }
        }

        res.SetSavedGame(path);
        return res;
    }

    public static void SaveXML(String path, RouletteGame game) throws Exception {
        Document doc = CreateGameDocument(game);
        Save(doc, path);
    }

    private static Document CreateGameDocument(RouletteGame game) throws Exception{
        RouletteSettings settings = game.GetSettings();
        ArrayList<RoulettePlayer> players = new ArrayList<RoulettePlayer>(game.GetPlayers().values());
        RouletteRound round = game.GetRound();
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        
        Element rootElement = doc.createElement("roulette");
        doc.appendChild(rootElement);
        rootElement.setAttribute("name", settings.GetGameName());
        rootElement.setAttribute("table_type", settings.GetRouletteType().name());
        rootElement.setAttribute("min_bets_per_player", Integer.toString(settings.GetMinimumBetsPerPlayer()));
        rootElement.setAttribute("max_bets_per_player", Integer.toString(settings.GetMaximumBetsPerPlayer()));
        rootElement.setAttribute("init_sum_of_money", Integer.toString(settings.GetInitialAmoutOfMoneyPerPlayer()));

        // players element
        Element playersElement = doc.createElement("players");
        rootElement.appendChild(playersElement);
        for (RoulettePlayer player : players) {
            AddPlayerToDOM(doc, playersElement, player, round.GetBetForPlayer(player.GetName()));
        }
        
        return doc;
    }
    
    private static void AddPlayerToDOM(Document doc, Element playersElement, RoulettePlayer player, ArrayList<RouletteBet> betsOfPlayer) {
        Element newPlayerElement = doc.createElement("player");
        newPlayerElement.setAttribute("type", player.GetPlayerType().name());
        newPlayerElement.setAttribute("name", player.GetName());
        newPlayerElement.setAttribute("money", Integer.toString(player.GetMoney() + GetPlayerTotalBetsAmount(betsOfPlayer)));
        
        if (betsOfPlayer != null){
            Element betsElement = doc.createElement("bets");
            newPlayerElement.appendChild(betsElement);
            
            for (RouletteBet playerBet : betsOfPlayer) {
                Element betElement = doc.createElement("bet");
                betsElement.appendChild(betElement);
                
                betElement.setAttribute("type", playerBet.GetBetType().name());
                betElement.setAttribute("amount", Integer.toString(playerBet.GetMoney()));
                
                if (playerBet.GetBetType().NeedsNumbers == true){
                    ArrayList<String> numbers = playerBet.GetNumbers();
                    for (String number : numbers) {
                        Element numberElement = doc.createElement("number");
                        betElement.appendChild(numberElement);
                        numberElement.setTextContent(number);
                    }
                }
            }
        }
        
        playersElement.appendChild(newPlayerElement);
    }
    
    private static void Save(Document doc, String path) throws Exception{
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }
    
    private static int GetPlayerTotalBetsAmount(ArrayList<RouletteBet> betsOfPlayer) {
        if (betsOfPlayer == null)
            return 0;
        
        int res = 0;
        
        for (RouletteBet bet : betsOfPlayer) {
            res += bet.GetMoney();
        }
        
        return res;
    }

}
