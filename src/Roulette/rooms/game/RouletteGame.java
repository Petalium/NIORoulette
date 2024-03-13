package Roulette.rooms.game;

import Roulette.Colors;
import Roulette.config.properties.GameProperties;
import Roulette.player.Player;
import Roulette.utils.RoomUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RouletteGame {
    private final String normalNumberSymbol;
    private final String randomNumberSymbol;
    private final String formattedSubtract;
    private final String formattedAdd;
    private final int numberEliminated;
    private final int multiplier;   // TODO: Create play again options
    private final Map<Player, Integer> playerBalMap;
    private final Map<Player, Integer> playerBetMap;
    private final GameRoom gameRoom;
    private final List<Integer> numbersGuessed;
    private final List<Object> numberList;
    private final int min;
    private final int max;
    private int rnd;
    private List<Player> playersPlaying;
    private States state;

    public RouletteGame(GameRoom gameRoom) {
        normalNumberSymbol = Colors.GREEN.ansiCode + GameProperties.NORMAL_SYMBOL + Colors.RESET.ansiCode;
        randomNumberSymbol = Colors.RED.ansiCode + GameProperties.RANDOM_SYMBOL + Colors.RESET.ansiCode;
        formattedSubtract = Colors.RED.ansiCode + "-" + Colors.RESET.ansiCode;
        formattedAdd = Colors.GREEN.ansiCode + "+" + Colors.RESET.ansiCode;
        numberEliminated = GameProperties.NUMBERS_ELIMINATED;
        multiplier = GameProperties.MULTIPLIER;
        playerBalMap = new LinkedHashMap<>();
        playerBetMap = new LinkedHashMap<>();
        this.gameRoom = gameRoom;
        numbersGuessed = new ArrayList<>();
        numberList = new ArrayList<>();
        min = GameProperties.MINIMUM;
        max = GameProperties.MAXIMUM;
        playersPlaying = new ArrayList<>();
        state = States.GAME_IDLING;
    }

    public void runGame(@NotNull List<Player> players) {
        playersPlaying = players;
        Collections.shuffle(playersPlaying);

        gameRoom.broadcastToRoom("Game starting...");
        this.collectBets();
        this.genNumberList();
        this.collectGuesses();
        this.cleanUp();
    }

    public void processIntegerInput(Player player, int input) {
        if (state.equals(States.ROULETTE_COLLECTING_BETS)) {
            int balance = playerBalMap.get(player);

            if (input > balance)
                player.serverWriteToPlayer("Your bet can not exceed your balance: " + balance);
            else if (input < 1)
                player.serverWriteToPlayer("Your bet can not be less than 1.");
            else {
                player.setState(States.PLAYER_CHATTING);
                putPlayerBet(player, input);
            }
        } else if (state.equals(States.ROULETTE_COLLECTING_GUESSES)) {
            if (input > max || input < min)
                player.serverWriteToPlayer("Your guess must be in the range: " + min + "-" + max);
            else if (numbersGuessed.contains(input))
                player.serverWriteToPlayer("Number " + input + " has already been guessed.");
            else {
                player.setState(States.PLAYER_CHATTING);
                numbersGuessed.add(input);
            }
        }
    }

    public States getState() {
        return state;
    }

    private void collectBets() {
        try {
            Thread.startVirtualThread(this::collectBetsInner).join();
        } catch (InterruptedException e) {
            System.out.printf("Error collecting bets. | %s | ", e);
        }
    }

    private void collectBetsInner() {
        this.state = States.ROULETTE_COLLECTING_BETS;
        int startingBalance = GameProperties.STARTING_BALANCE;

        for (Player player : playersPlaying) {
            if (!playerBalMap.containsKey(player))
                playerBalMap.put(player, startingBalance);

            player.setState(States.PLAYER_INPUTTING_INT);
            player.serverWriteToPlayer("Current balance: " + formatCredits(playerBalMap.get(player)) +
                    ", place your bet:");
        }

        while (true) {  // TODO: Notify, dont poll
            if (playerBetMap.size() >= playersPlaying.size() || gameRoom.getPlayers().isEmpty())
                break;
            else
                Thread.onSpinWait();
        }

        gameRoom.broadcastToRoom("All bets have been collected; Beginning game.");
    }

    private void collectGuesses() {
        try {
            Thread.startVirtualThread(this::collectGuessesInner).join();
        } catch (InterruptedException e) {
            System.out.printf("Error collecting guesses. | %s | ", e);
        }
    }

    private void collectGuessesInner() {
        this.state = States.ROULETTE_COLLECTING_GUESSES;
        Iterator<Player> playerIterator = playersPlaying.iterator();

        for (int i = 0; i < numberEliminated; i++) {
            gameRoom.broadcastToRoom(numberListToString() + " | Remaining guesses: " + (numberEliminated - i));

            if (playersPlaying.isEmpty())
                return;
            else if (!playerIterator.hasNext())
                playerIterator = playersPlaying.iterator();

            Player currentPlayer = playerIterator.next();
            gameRoom.broadcastToRoom(currentPlayer.getDisplayName() + " Is guessing.");

            currentPlayer.setState(States.PLAYER_INPUTTING_INT);
            currentPlayer.serverWriteToPlayer("Input guess: ");

            while (true) {
                if (numbersGuessed.size() > i)
                    break;
                else if (!playersPlaying.contains(currentPlayer)) {
                    gameRoom.broadcastToRoom("Player guessing has left; Abruptly stopping the game...");
                    return;     // TODO: Handle properly, dont just stop the game.
                }
                else
                    Thread.onSpinWait();
            }

            int guess = numbersGuessed.getLast();

            if (guess == rnd) {
                numberList.set(guess - 1, randomNumberSymbol);
                gameRoom.broadcastToRoom(currentPlayer.getDisplayName() + " Incorrectly guessed " + guess);
                gameRoom.broadcastToRoom(numberListToString());
                break;
            } else {
                gameRoom.broadcastToRoom(currentPlayer.getDisplayName() + " Correctly guessed " + guess);
                numberList.set(guess - 1, normalNumberSymbol);
            }

            currentPlayer.setState(States.PLAYER_CHATTING);

            if (i == (numberEliminated - 1)) {
                numberList.set(rnd - 1, randomNumberSymbol);
                gameRoom.broadcastToRoom(numberListToString());

                for (Player player : playersPlaying) {
                    int wonAmount = playerBetMap.get(player) * multiplier;
                    playerBalMap.replace(player, playerBalMap.get(player) + wonAmount);
                    player.serverWriteToPlayer(formattedAdd + formatCredits(wonAmount) + " | " +
                            "New Balance: " + formatCredits(playerBalMap.get(player)));
                }
            }
        }

        gameRoom.broadcastToRoom("Game over.");
    }

    private void cleanUp() {
        playerBetMap.clear();
        numbersGuessed.clear();
        numberList.clear();
        this.state = States.GAME_IDLING;
    }

    private void putPlayerBet(@NotNull Player player, int bet) {
        int newBalance = playerBalMap.get(player) - bet;

        player.serverWriteToPlayer(formattedSubtract + formatCredits(bet) + " | New balance: " + formatCredits(newBalance));
        gameRoom.broadcastToRoom(player.getDisplayName() + " Placed a bet of " + formatCredits(bet) + ".");

        playerBetMap.put(player, bet);
        playerBalMap.replace(player, newBalance);

        if (playerBetMap.size() < playersPlaying.size())
            player.serverWriteToPlayer("Waiting on other players...");
    }

    private void genNumberList() {
        rnd = RoomUtils.genRandomNumber(min, max);

        for (int i = min; i <= max; i++)
            numberList.add(i);
    }

    private @NotNull String numberListToString() {
        StringBuilder stringBuilder = new StringBuilder("[");

        for (Object object : numberList)
            stringBuilder.append(object).append(" | ");

        return stringBuilder.append("\b\b\b]").toString();
    }

    @Contract(pure = true)
    private @NotNull String formatCredits(int num) {
        return Colors.BRIGHT_YELLOW.ansiCode + num + Colors.RESET.ansiCode;
    }
}