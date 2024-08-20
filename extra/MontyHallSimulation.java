import java.util.Random;

/**
 * Monty Hall Problem Simulation.
 * <p>
 * In this game, there are three boxes, one of which contains a coin. The host knows which box contains the coin,
 * and we must guess which one it is. After we choose a box, the host shows us that this box is empty, and we must decide
 * whether to keep our choice or switch to the other remaining box.
 * <p>
 * By studying conditional probability, it is shown that by switching boxes, we have a 2/3 chance of winning the game 
 * (finding the coin) and only a 1/3 chance if we stick with our original choice.
 * <p>
 * This program simulates 10,000 iterations of the game, always choosing to switch boxes after the host reveals an empty one.
 * The program then counts how many times this strategy results in a win.
 */
public class MontyHallSimulation {
    
    public static void main(String[] args) {
        int totalGames = 100000; // Number of simulations
        int wins = 0; // Counter for wins when switching
        Random random = new Random();

        for (int i = 0; i < totalGames; i++) {
            // Step 1: Randomly generate which box contains the coin
            int coinBox = random.nextInt(3) + 1;

            // Step 2: Choose a box randomly
            int initialChoice = random.nextInt(3) + 1;

            // Step 3: The host discards an empty box that is neither the chosen one nor the one with the coin
            int discardedBox;
            do {
                discardedBox = random.nextInt(3) + 1;
            } while (discardedBox == coinBox || discardedBox == initialChoice);

            // Step 4: Switch the choice to the remaining box
            int newChoice;
            do {
                newChoice = random.nextInt(3) + 1;
            } while (newChoice == initialChoice || newChoice == discardedBox);

            // Step 5: Check if the new choice is the winning one
            if (newChoice == coinBox) {
                wins++;
            }
        }

        // Final results
        System.out.println("Total games: " + totalGames);
        System.out.println("Wins by switching boxes: " + wins);
        System.out.println("Win percentage: " + (wins / (double) totalGames) * 100 + "%");
    }
}
