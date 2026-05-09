import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class GameRoom {
    // instance variables & data structs
    private String roomId;
    private ScheduledExecutorService scheduler;
    private List<Player> players;
    private List<Card> deck;
    private List<Card> discardPile;
    private int currentPlayerIndex;
    private boolean clockwise;
    private boolean gameStarted;
    private Card.Color currentColor;
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;
    private static final int WAIT_TIME = 10;
    private int countdownSeconds = WAIT_TIME;
    private static final int INITIAL_HAND_SIZE = 7;

    public GameRoom(String roomId) {
        this.roomId = roomId;
        this.currentPlayerIndex = 0;
        this.discardPile = new ArrayList<>(); 
        this.deck = new ArrayList<>();
        this.players = new ArrayList<>();
        this.clockwise = true;
        this.gameStarted = false;
        initializeDeck();
    }

    // -----------  Initialize the Deck
    public void initializeDeck() {

        // INITRIALIZE number cards 
        for (Card.Color color : new Card.Color[] {  // For every Card (of each color) that is new
            Card.Color.RED, Card.Color.YELLOW, Card.Color.GREEN, Card.Color.BLUE }) {

                // Add ONE 0 for each color
                deck.add(new Card(color, Card.Type.NUMBER, 0));

                // Add TWO 1-9 for each color
                for (int i = 1; i <= 9; i++) {
                    deck.add(new Card(color, Card.Type.NUMBER, i));
                    deck.add(new Card(color, Card.Type.NUMBER, i));
                }

                // Add special cards (skip, reverse, draw_two) TWO for each color
                for (int i = 0; i < 2; i++) {
                    deck.add(new Card(color, Card.Type.SKIP, -1));
                    deck.add(new Card(color, Card.Type.DRAW_TWO, -1));
                    deck.add(new Card(color, Card.Type.REVERSE, -1));
                }
            }
        // Add Wild Cards 4 of each total
        for (int i = 0; i < 4; i++) {
            deck.add(new Card(Card.Color.WILD, Card.Type.WILD, -1));
            deck.add(new Card(Card.Color.WILD, Card.Type.WILD_FOUR, -1));
        }

        Collections.shuffle(deck);
    }
    // -----------  Add Player
    public synchronized boolean addPlayer(Player player) {
        if (players.size() < MAX_PLAYERS && !gameStarted) {
            players.add(player);
            System.out.println("DEBUG: Player " + player.getName() + " added. Total: " + players.size());
            broadcastMessage("PLAYER_JOINED|" + player.getName() + "|" + players.size());

            if (players.size() == MAX_PLAYERS) {
                System.out.println("DEBUG: Max players reached. Starting game immediately.");
                stopTimer();
                startGame();
            }
            else if (players.size() >= MIN_PLAYERS) {
                System.out.println("DEBUG: Min players met. Starting/resetting timer.");
                resetAndStartTimer();
            }
            return true;
        }
        return false;
    }
    // -----------  Start Game
    public synchronized void startGame() {
        if (players.size() < 2 || gameStarted){
            return;
        }
        stopTimer();
        gameStarted = true;
        System.out.println("DEBUG: Game starting with " + players.size() + " players");
        
        // Deal initial hands
        for(Player player : players) {
            for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
                player.addCard(drawFromDeck());
            }
        }
        // Flip first card (cant be a wild)
        Card firstCard;
        do { firstCard = drawFromDeck(); } 
        while (firstCard.isWild());

        discardPile.add(firstCard);
        currentColor = firstCard.getColor();

        System.out.println("DEBUG: Top card is: " + firstCard.toString());
        System.out.println("DEBUG: Current color is: " + currentColor);
        System.out.println("DEBUG: Discard pile size: " + discardPile.size());

        // Broadcast to all
        broadcastGameState(); // Announce current game state
        notifyCurrentPlayer(); // Notify player

        // Send each player their hand
        for (Player p : players) {
            broadcastHandUpdate(p);
        }
    }
    private void resetAndStartTimer() {
        // Stop any existing timer first
        stopTimer();

        // Reset the counter to 10
        countdownSeconds = WAIT_TIME;
        broadcastMessage("TIMER_RESET|" + countdownSeconds);

        // Create a new scheduler
        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            synchronized (this) {
                // If game already started, stop timer
                if (gameStarted) {
                   stopTimer();
                   return;
               }
               //Countdown
               if (countdownSeconds <= 0) {
                   stopTimer();
                   startGame();
               } else {
                   broadcastMessage("TIMER_TICK|" + countdownSeconds);
                   countdownSeconds--;
               }
            }
        }, 1, 1, TimeUnit.SECONDS); // Start after 1 second, repeat every 1 second
    }

    private void stopTimer() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
            try {
                scheduler.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            scheduler = null;  // ADDED: Clear the reference
        }
    }
    // ----------- Remove topCard 
    private Card drawFromDeck() {
        if (deck.isEmpty()) {
            reshuffleDeck();
        }
        return deck.isEmpty() ? null : deck.remove(0);
    }
    // ----------- Reshuffle Deck
    private void reshuffleDeck() {
        if (discardPile.size() <= 1) {
            return; // Keep top card
        }
        // Move every card EXCEPT topCard back to discardPile
        Card topCard = discardPile.remove(discardPile.size() - 1); // Find topCard
        deck.addAll(discardPile); // Append (ADD) discardPile to deck
        discardPile.clear(); // remove all cards from discardPile
        discardPile.add(topCard); // add topCard to discardPile
    }
    // ----------- Play Card
    public synchronized String playCard(Player player, int cardIndex, Card.Color chosenColor) {
        if (!gameStarted) return "ERROR: GAME HAS NOT BEGUN!";
        if (players.get(currentPlayerIndex) != player) return "ERROR: NOT YOUR TURN!"; 

        Card card = player.getHand().get(cardIndex);
        Card topCard = getTopCard();

        // FIX: Use the updated canPlayOn with currentColor
        if (!card.canPlayOn(topCard, currentColor)) {
            return "ERROR: CANNOT PLAY " + card.toShortString() + " ON " + topCard.toShortString();
        }

        Card playedCard = player.removeCard(cardIndex);
        discardPile.add(playedCard);

        // Update the room's color
        currentColor = (playedCard.isWild()) ? chosenColor : playedCard.getColor();

        if (player.getHandSize() == 0) { 
            broadcastMessage("GAME_WON|" + player.getName());
            gameStarted = false;
            return "SUCCESS|WINNER";
        }

        executeCardEffects(playedCard);
        advanceTurn();
        broadcastGameState();
        notifyCurrentPlayer();
        return "SUCCESS|CARD_PLAYED";
    }

    private void executeCardEffects(Card card) {
        switch (card.getType()) {
            case SKIP:
                advanceTurn();
                break;

            case DRAW_TWO:
                // Advance to the victim so they receive the cards
                advanceTurn();
                Player victim = players.get(currentPlayerIndex);
                victim.addCard(drawFromDeck());
                victim.addCard(drawFromDeck());
                broadcastHandUpdate(victim);
                // The final advanceTurn() in playCard() will then move 
                // the turn to the player AFTER the victim.
                break;
            
            case WILD_FOUR:
                advanceTurn();
                Player target = players.get(currentPlayerIndex);
                for (int i = 0; i < 4; i++) {
                    target.addCard(drawFromDeck());
                }
                broadcastHandUpdate(target);
                break; 
            
            case REVERSE:
                if (players.size() == 2) {
                    advanceTurn();
                } else {
                    clockwise = !clockwise;
                }
                break;
        }
    }
    // ----------- (Validate) Player Drawing Card (topCard)
    public synchronized String drawCard(Player player) {
        if (!gameStarted) {
            return "ERROR|GAME NOT STARTED";
        }

        if (players.get(currentPlayerIndex) != player) {
            return "ERROR|Not your turn";
        }

        Card drawnCard = drawFromDeck();
        if(drawnCard == null) {
            return "ERROR|No cards left";
        }

        player.addCard(drawnCard);

        // After drawing, turn passes to next player
        advanceTurn();
        broadcastGameState();
        notifyCurrentPlayer();
        saveGameState("IN_PROGRESS");

        return "SUCCESS|Card drawn " + drawnCard.toString();
    }
    // send client Hand update
    private void broadcastHandUpdate(Player p) {
        StringBuilder handMsg = new StringBuilder("YOUR_HAND|");
        for (int i = 0; i < p.getHand().size(); i++) {
            if (i > 0) handMsg.append(",");
            // Use toShortString to ensure the GUI can parse the colors!
            handMsg.append(i).append(":").append(p.getHand().get(i).toShortString());
        }
        p.sendMessage(handMsg.toString());
    }
    // ----------- Advance Turn
    private void advanceTurn() {
        if (clockwise) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); 
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
    }
    // ----------- Announce Game State
        private void broadcastGameState() {
            Card topCard = getTopCard();
            StringBuilder sb = new StringBuilder("GAME_STATE|");
            sb.append(players.get(currentPlayerIndex).getName()).append("|");
            sb.append(topCard.toShortString()).append("|"); 
            sb.append(currentColor).append("|");         

            for (Player p : players) {
                sb.append(p.getName()).append(":").append(p.getHandSize()).append(",");
            }
        
            String message = sb.toString();             
            broadcastMessage(message);                   
        }
    // ----------- Notification (YOUR TURN)
    private void notifyCurrentPlayer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.sendMessage("YOUR_TURN");
    }
    // ----------- Announce Message
    private void broadcastMessage(String message) {
        for (Player p : players) {
            p.sendMessage(message);
        }
    }
    // ----------- Getters
    public Card getTopCard() {
        return discardPile.isEmpty() ? null : discardPile.get(discardPile.size() - 1);
    }

    public String getRoomId() {
        return roomId;
    }

    public List<Player> getPlayers() {
        return players;
    }
    // ----------- startGame boolean check
    public boolean isGameStarted() {
        return gameStarted;
    }
    // ----------- isGameFull boolean check
    public boolean isGameFull() {
        return players.size() >= MAX_PLAYERS;
    }
    // ----------- Remove Player
    public synchronized void removePlayer(Player player) {
        players.remove(player);
        
        // NEW: Stop the timer if we drop below MIN_PLAYERS before the game starts
        if (!gameStarted && players.size() < MIN_PLAYERS) {
            stopTimer();
            broadcastMessage("TIMER_CANCELLED|WAITING_FOR_PLAYERS");
        }

        if (gameStarted && players.size() < 2) {
            gameStarted = false;
            broadcastMessage("GAME_ENDED|NOT ENOUGH PLAYERS");
        }
    }
    // ----------- Game State persistence (Snapshot of Game data)
    public void saveGameState(String status) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("game_" + roomId + ".txt"))) {
            writer.println("STATUS:" + status);
            writer.println("ROOM_ID:" + roomId);
            writer.println("CURRENT_PLAYER:" + currentPlayerIndex);
            writer.println("DIRECTION:" + (clockwise ? "CLOCKWISE" : "COUNTER"));
            writer.println("CURRENT_COLOR:" + currentColor);
            writer.println("TOP_CARD:" + getTopCard());
            writer.println("Players:");
            for (Player p : players) {
                writer.println(" " + p.getName() + ":" + p.getHandSize());
            }
        } catch (IOException e) {
            System.err.println("Error saving game state:" + e.getMessage());
        }
    }
}