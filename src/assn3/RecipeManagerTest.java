/**
 * File name: RecipeManagerTest.java
 * @author Ola Assaad, ID# : 041139407
 * Course: CST8284 – OOP
 * Assignment: 3
 * Date: 2024-12-03
 * Professor: Reginald Dyer
 * Purpose: Testing the RecipeManager class functionality, including adding recipes, creating shopping lists, and handling user interactions through a command-line interface.
 */

package assn3;

import java.util.*;

/**
 * RecipeManagerTest is a test class that simulates a user interaction with the Recipe Manager system.
 * It allows the user to view available recipes, order them, generate shopping lists, and checkout.
 */
public class RecipeManagerTest {
    private static Scanner input = new Scanner(System.in);
    private static RecipeManager manager = new RecipeManager();

    /**
     * The main method runs the Recipe Manager system, displaying a menu for user interaction.
     * The user can choose from options such as displaying recipes, creating a shopping list, 
     * printing the shopping list, or quitting the program.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Ola Assaad Recipe Manager.");

        manager.loadRecipes();

        boolean exit = false;
        do {
            displayUserOptions();
            int choice = promptUserChoice("Please enter your choice:", 0, 4);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    displayMenu();
                    break;
                case 2:
                    int recipeNumber = promptUserChoice("Which bread would you like?", 1, manager.recipeCount());
                    int quantity = promptUserChoice("How much of this bread would you like?", -1000, 1000);
                    order(recipeNumber, quantity);
                    break;
                case 3:
                    checkout();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Bye!");
                    break;
            }
        } while (!exit);
    }

    /**
     * Displays the available user options for interaction with the Recipe Manager.
     */
    private static void displayUserOptions() {
        System.out.println("Please select one of the following options:");
        System.out.println("1. Show available recipes.");
        System.out.println("2. Create Shopping List.");
        System.out.println("3. Print Shopping List.");
        System.out.println("4. Quit Program.");
        System.out.println("0. to reprint this menu.");
        System.out.println();
    }

    /**
     * Prompts the user to input an integer within valid bounds (between min and max inclusive).
     *
     * @param prompt The question to display to the user.
     * @param min The minimum integer value allowed.
     * @param max The maximum integer value allowed.
     * @return The integer choice entered by the user.
     */
    private static int promptUserChoice(String prompt, int min, int max) {
        System.out.printf("%s ", prompt);
        String errorMessage = String.format("Please enter a number between %d and %d: ", min, max);

        do {
            try {
                int choice = input.nextInt();
                /* Flush buffer after int read */
                input.nextLine();

                if (choice < min || choice > max) {
                    System.out.print(errorMessage);
                } else {
                    return choice;
                }
            } catch (Exception ex) {
                System.out.print(errorMessage);
            }
        } while (true);
    }

    /**
     * Displays the list of available recipes by calling the displayRecipes method from the RecipeManager.
     */
    private static void displayMenu() {
        System.out.println("Available Recipes:");
        manager.displayRecipes();
        System.out.println();
    }

    /**
     * Adds a specified quantity of a recipe to the cart.
     *
     * @param recipeNumber The number of the recipe to order (1-based index).
     * @param quantity The quantity to add to the cart.
     */
    private static void order(int recipeNumber, int quantity) {
        manager.addQuantity(recipeNumber - 1, quantity);
    }

    /**
     * Handles the checkout process by printing the shopping list summary and allowing the user to save it.
     * If the cart is empty, it notifies the user and skips the checkout process.
     */
    private static void checkout() {
        /* Skip printing the cart if it's empty */
        if (manager.isCartEmpty()) {
            System.out.println("Cart is empty.\n");
            return;
        }

        /* Print the cart summary */
        System.out.println(manager.getCartSummary());

        /* Prompt user if they want to save the list */
        System.out.print("Do you want to save this list (Y/n)? ");
        String decision = input.nextLine();

        /* Save the list if user inputs 'Y' */
        if (decision.equalsIgnoreCase("Y")) {
            manager.writeShoppingList();
        }
        System.out.println();
    }
}
