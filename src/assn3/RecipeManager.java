/**
 * File name: RecipeManager.java
 * @author Ola Assaad, ID# : 041139407
 * Course: CST8284 – OOP
 * Assignment: 3
 * Date: 2024-12-03
 * Professor: Reginald Dyer
 * Purpose: Manages the recipes, including loading, displaying, and managing quantities for the shopping list.
 */

package assn3;

import java.io.*;
import java.util.*;

/**
 * RecipeManager is responsible for managing a list of recipes, adding them to a cart, and generating a shopping list.
 * It allows loading recipes from a file, adding quantities to the cart, and generating a shopping list with ingredient quantities.
 */
public class RecipeManager {
    private static final String RECIPE_LIST_FILE_PATH = "./recipelist.txt";
    private static final String SHOPPING_LIST_FILE_PATH = "./shoppinglist.txt";

    private static final String BUTTER = "grams of butter";
    private static final String EGGS = "egg(s)";
    private static final String FLOUR = "grams of flour";
    private static final String SUGAR = "grams of sugar";
    private static final String YEAST = "grams of yeast";

    /** List to store all recipes **/
    private List<Recipe> recipes = new ArrayList<>();
    /** Keeps track of ordered recipes (recipe number -> quantity) **/
    private Map<Integer, Integer> cart = new HashMap<>();

    /**
     * Returns the number of recipes loaded in the manager.
     *
     * @return the size of the recipes list.
     */
    public int recipeCount() {
        return recipes.size();
    }

    /**
     * Checks whether the cart is empty.
     *
     * @return true if the cart is empty, false otherwise.
     */
    public boolean isCartEmpty() {
        return cart.isEmpty();
    }

    /**
     * Reads the recipe file and loads the contents into Recipe instances.
     * Each recipe is processed and stored in the recipes list.
     */
    public void loadRecipes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECIPE_LIST_FILE_PATH))) {
            String line = null;
            Recipe recipe = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Recipe")) {
                    /* Start new Recipe */
                    String name = line.replace("Recipe ", "").trim();
                    recipe = new Recipe(name);
                } else if (line.trim().isEmpty()) {
                    /* Save current recipe */
                    recipes.add(recipe);
                    recipe = null;
                } else {
                    /* Process Ingredient */
                    processIngredient(line, recipe);
                }
            }

            /* Save last recipe in case there isn't a line return after the last ingredient */
            if (recipe != null) {
                recipes.add(recipe);
            }
        } catch (IOException ex) {
            System.err.println("Problem reading the recipe list file: " + ex.getMessage());
        }
    }

    /**
     * Writes the shopping list to a file, summarizing the ingredients needed for the recipes.
     */
    public void writeShoppingList() {
        try (FileWriter fileWriter = new FileWriter(SHOPPING_LIST_FILE_PATH)) {
            fileWriter.write(getCartSummary());
        } catch (IOException ex) {
            System.err.println("Problem writing the shopping list: " + ex.getMessage());
        }
    }

    /**
     * Adds the specified quantity of a recipe to the cart.
     *
     * @param recipeNumber the number of the recipe.
     * @param quantity the quantity to add to the cart.
     */
    public void addQuantity(int recipeNumber, int quantity) {
        int currentQuantity = cart.getOrDefault(recipeNumber, 0);
        int newQuantity = currentQuantity + quantity;

        if (newQuantity > 0) {
        	cart.put(recipeNumber, newQuantity);
        } else {
        	cart.remove(recipeNumber);
        }
    }

    /**
     * Displays the list of recipes and their corresponding numbers.
     */
    public void displayRecipes() {
        for (int i = 0; i < recipes.size(); i++) {
            int number = i + 1;
            String name = recipes.get(i).getName();
            System.out.printf("%d. %s%n", number, name);
        }
    }

    /**
     * Generates a summary of the cart, including the quantities of each recipe and ingredient required.
     *
     * @return a string summary of the cart.
     */
    public String getCartSummary() {
        List<String> cartSummary = new ArrayList<>();

        /* Recipe quantities */
        for (Integer recipeNumber : cart.keySet()) {
            int quantity = cart.getOrDefault(recipeNumber, 0);
            
            String name = recipes.get(recipeNumber).getName();
            String lineItem = String.format("%d %s loaf/loaves.", quantity, name);
            cartSummary.add(lineItem);
        }

        cartSummary.add("\nYou will need a total of:");

        /* Ingredient Quantities */
        cartSummary.addAll(buildShoppingList());

        return String.join("\n", cartSummary);
    }

    /**
     * Builds the shopping list according to the cart.
     *
     * @return a list of strings where each line represents an ingredient and its quantity.
     */
    private List<String> buildShoppingList() {
        /** Keeps track of the ingredients totals (ingredient -> quantity) **/
        Map<String, Float> ingredients = new HashMap<>();

        for (Integer recipeNumber : cart.keySet()) {
            int recipeQuantity = cart.getOrDefault(recipeNumber, 0);
            Recipe recipe = recipes.get(recipeNumber);

            float butterQ = ingredients.getOrDefault(BUTTER, 0f) + (recipe.getButter() * recipeQuantity);
            ingredients.put(BUTTER, butterQ);

            float eggsQ = ingredients.getOrDefault(EGGS, 0f) + (recipe.getEggs() * recipeQuantity);
            ingredients.put(EGGS, eggsQ);

            float flourQ = ingredients.getOrDefault(FLOUR, 0f) + (recipe.getFlour() * recipeQuantity);
            ingredients.put(FLOUR, flourQ);

            float sugarQ = ingredients.getOrDefault(SUGAR, 0f) + (recipe.getSugar() * recipeQuantity);
            ingredients.put(SUGAR, sugarQ);

            float yeastQ = ingredients.getOrDefault(YEAST, 0f) + (recipe.getYeast() * recipeQuantity);
            ingredients.put(YEAST, yeastQ);
        }

        List<String> shoppingList = new ArrayList<>();
        for (String ingredientName : ingredients.keySet()) {
            float quantity = ingredients.getOrDefault(ingredientName, 0f);
            String lineItem = String.format("%.0f %s", quantity, ingredientName);
            shoppingList.add(lineItem);
        }

        return shoppingList;
    }

    /**
     * Processes an ingredient line, parsing the ingredient name and quantity, 
     * and updating the corresponding recipe object with the ingredient's data.
     *
     * @param line the ingredient line to process.
     * @param recipe the recipe object to update with the ingredient data.
     */
    private void processIngredient(String line, Recipe recipe) {
        String[] ingredientLine = line.split(" ");
        String ingredientName = ingredientLine[0];
        float quantity = Float.parseFloat(ingredientLine[1]);

        switch (ingredientName.toLowerCase().trim()) {
            case "butter":
                recipe.setButter(quantity);
                break;
            case "eggs":
                recipe.setEggs(quantity);
                break;
            case "flour":
                recipe.setFlour(quantity);
                break;
            case "sugar":
                recipe.setSugar(quantity);
                break;
            case "yeast":
                recipe.setYeast(quantity);
                break;
            default:
                System.out.println("Unexpected ingredient encountered: " + ingredientName);
                break;
        }
    }
}
