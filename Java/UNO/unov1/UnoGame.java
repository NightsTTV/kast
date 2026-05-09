package uno;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import uno.UnoGame.InvalidColorSubmissionException;
import uno.UnoGame.InvalidPlayerTurnException;
import uno.UnoGame.InvalidValueSubmissionException;

import java.awt.Font;

public class UnoGame {

    private int currentPlayer;
    private String[] playerIds;

    private UnoDeck deck;
    private ArrayList<ArrayList<UnoCard>> playerHand; // array of array of players' hands
    private ArrayList<UnoCard> stockPile;

    private UnoCard.Color validColor;
    private UnoCard.Value validValue;

    private boolean gameDirection; // left --> right (clockwise) right --> left (counterclockwise)
    
    public UnoGame(String[] pids) {
        deck = new UnoDeck();
        deck.shuffle();
        stockPile = new ArrayList<UnoCard>();

        playerIds = pids;
        currentPlayer = 0;
        gameDirection = false;

        playerHand = new ArrayList<ArrayList<UnoCard>>();

        for (int i = 0; i < pids.length; i++){
            ArrayList<UnoCard> hand = new ArrayList<UnoCard>(Arrays.asList(deck.drawCard(7)));
            playerHand.add(hand);
        }
    }

    public void start(UnoGame game) {
        UnoCard card;
        while (true){
            card = deck.drawCard();
            if (card.getValue() == UnoCard.Value.Wild ||
                card.getValue() == UnoCard.Value.WildFour ||
                card.getValue() == UnoCard.Value.DrawTwo) {
                continue;
            }
            break;
        }

        validColor = card.getColor();
        validValue = card.getValue();
        stockPile.add(card);

        if(card.getValue() == UnoCard.Value.Wild) {
            start(game);
        }

        if (card.getValue() == UnoCard.Value.WildFour || card.getValue() == UnoCard.Value.DrawTwo) {
            start(game);
        }

        if (card.getValue() == UnoCard.Value.Skip) {
            JLabel message = new JLabel(playerIds[currentPlayer] + "was skippd!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);

            if(gameDirection == false) {
                currentPlayer = (currentPlayer + 1) % playerIds.length;
            } else if(gameDirection == true) {
                currentPlayer = (currentPlayer - 1) % playerIds.length;
                if (currentPlayer == -1) {
                    currentPlayer = playerIds.length -1
                }
            }
        }

        if(card.getValue() == UnoCard.Value.Reverse) {
            JLabel message = new JLabel(playerIds[currentPlayer] + "The game direction has switched!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            gameDirection ^= true;
            currentPlayer = playerIds.length - 1;
        }

        stockPile.add(card);
    }

    public UnoCard getTopCard() {
        return new UnoCard(validColor, validValue)
    }

    public ImageIcon getTopImageIcon() {
        return new ImageIcon(validColor + "-" + validValue + ".png");
    }

    public boolean isGameOver() {
        for(String player : this.playerIds) {
            if (hasEmptyHand(player)) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentPlayer() {
        return this.playerIds[this.currentPlayer];
    }

    public String getPreviousPlayer(int i) {
        int index = this.currentPlayer - i;
        if (index == -1) {
            index = playerIds.length - 1;
        }
        return this.playerIds[index];
    }

    public String[] getPlayers() {
        return playerIds;
    }

    public ArrayList<UnoCard> getPlayerHand(String pid) {
        int index = Arrays.asList(playerIds).indexOf(pid);
        return playerHand.get(index);
    }

    public int getPlayerHandSize(String pid) {
        return getPlayerHand(pid).size();
    }

    public UnoCard getPlayerCard(String pid, int choice) {
        ArrayList<UnoCard> hand = getPlayerHand(pid);
        return hand.get(choice);
    }

    public boolean hasEmptyHand(String pid) {
        return getPlayerHand(pid).isEmpty();
    }

    public boolean validCardPlay(UnoCard card) {
       if(card.getColor() == UnoCard.Color.Wild) {
            return true;
       }
       return (card.getColor() == validColor || card.getValue() == validValue);
    }

    public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException {
        if(this.playerIds[this.currentPlayer] != pid) {
            throw new InvalidPlayerTurnException("it is not " + pid + "'s turn", pid);
        }
    }
    public void advanceTurn() {
        int direction = gameDirection ? -1 : 1;
        currentPlayer = (currentPlayer + direction + playerIds.length) % playerIds.length;
    }

    public void submitDraw(String pid) throws InvalidPlayerTurnException {
        checkPlayerTurn(pid);

        if(deck.isEmpty()) {
            UnoCard currentTop = stockPile.remove(stockPile.size() - 1);
            deck.replaceDeckWith(stockPile);
            deck.shuffle();
            stockPile.clear();
            stockPile.add(currentTop);
        }

        getPlayerHand(pid).add(deck.drawCard());
        advanceTurn();
    }

    public void setCardColor(UnoCard.Color color) {
        validColor = color;
    }

    public void submitPlayerCard(String pid, UnoCard card, UnoCard.Color declaredColor) throws InvalidColorSubmissionException, InvalidValueSubmissionException, InvalidPlayerTurnException{
        checkPlayerTurn(pid);

        ArrayList<UnoCard> pHand = getPlayerHand(pid);

        if(!validCardPlay(card)) {
            if (card.getColor() == UnoCard.Color.Wild) {
                validColor = card.getColor();
                validValue = card.getValue();
            }

            if (card.getColor() != validColor) {
                JLabel message = new JLabel("Invalid player move, expected color" + validColor + " but got color " + card.getColor());
                message.setFont(new Font("Arial", Font.BOLD, 48));
                JOptionPane.showMessageDialog(null, message);
                throw new InvalidColorSubmissionException(message,card.getColor(), validColor);
            }

            else if (card.getValue() != validValue) {
                JLabel message2 = new JLabel("Invalid player move, expected value: " + validValue + " but got color " + card.getValue());
                message2.setFont(new Font("Arial", Font.BOLD, 48));
                throw new InvalidValueSubmissionException(message2, card.getValue(), validValue);
            }
        }

        pHand.remove(card);
        stockPile.add(card);

        if (hasEmptyHand((this.playerIds[currentPlayer]))) {
            JLabel message3 = new JLabel(this.playerIds[currentPlayer] + " won the game! Thank you for playing!");
                message3.setFont(new Font("Arial", Font.BOLD, 48));
                JOptionPane.showMessageDialog(null, message3);
                System.exit(0);
        }

        validColor = card.getColor();
        validValue = card.getValue();
        stockPile.add(card);
        // if WILD CARD
        if (card.getColor() == UnoCard.Color.Wild) {
            validColor = declaredColor;
        }
        // Advance turn
        advanceTurn();
            // if SKIP
        if (card.getValue() == UnoCard.Value.Skip) {
            JLabel message = new JLabel(playerIds[currentPlayer] + " was skipped! ");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            advanceTurn(); // Skip them
            // if REVERSE
        } else if (card.getValue() == UnoCard.Value.Reverse) {
            JLabel message = new JLabel(playerIds[currentPlayer] + " initiated Reverse! ");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            gameDirection = !gameDirection;
            // If more than 2 players, we need to adjust because we already advanced.
            // If only 2 players, Reverse is effectively a Skip.
            if (playerIds.length > 2) {
                advanceTurn(); 
                advanceTurn(); 
            }
            // if DRAWTWO
        } else if (card.getValue() == UnoCard.Value.DrawTwo) {
            JLabel message = new JLabel(pid + " drew 2 cards!");
            for(int i = 0; i < 2; i++) getPlayerHand(playerIds[currentPlayer]).add(deck.drawCard());
            advanceTurn(); // Skip their turn
            // if WILDFOUR
        } else if (card.getValue() == UnoCard.Value.WildFour) {
            JLabel message = new JLabel(pid + " drew 4 cards!");
            for(int i = 0; i < 4; i++) getPlayerHand(playerIds[currentPlayer]).add(deck.drawCard());
            advanceTurn(); // Skip their turn
        }
}

class InvalidPlayerTurnException extends Exception {
    String playerId;
    
    public InvalidPlayerTurnException(String message, String pid) {
        super(message);
        playerId = pid;
    }

    public String getPid() {
        return playerId;
    }
}

class InvalidColorSubmissionException extends Exception {
    private UnoCard.Color expected;
    private UnoCard.Color actual;

    public InvalidColorSubmissionException(String message, UnoCard.Color actual, UnoCard.Color expected) {
        this.actual = actual;
        this.expected = expected;
    }
}

class InvalidValueSubmissionException extends Exception {
    private UnoCard.Value expected;
    private UnoCard.Value actual;

    public InvalidValueSubmissionException(String message, UnoCard.Value actual, UnoCard.Value expected) {
        this.actual = actual;
        this.expected = expected;
    }
}