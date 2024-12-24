/**
 * File name: Recipe.java
 * @author Ola Assaad, ID# : 041139407
 * Course: CST8284 – OOP
 * Assignment:  3
 * Date: 2024-12-03
 * Professor: Reginald Dyer
 * Purpose: This class represents a recipe with various ingredients and provides getter and setter methods for each ingredient.
 */

package assn3;
/**
 * This class represents a recipe with various ingredients and provides getter and setter methods for each ingredient.
 * */
public class Recipe {
    /** The name of the recipe */
    private String name;
    
    /** The amount of sugar in the recipe */
    private float sugar;
    
    /** The number of eggs in the recipe */
    private float eggs;
    
    /** The amount of flour in the recipe */
    private float flour;
    
    /** The amount of yeast in the recipe */
    private float yeast;
    
    /** The amount of butter in the recipe */
    private float butter;

    /**
     * Constructor to initialize the recipe with a name and default ingredient amounts.
     *
     * @param name The name of the recipe
     */
    public Recipe(String name) {
        this.name = name;
        this.sugar = 0f;
        this.eggs = 0f;
        this.flour = 0f;
        this.yeast = 0f;
        this.butter = 0f;
    }

    /**
     * Gets the name of the recipe.
     *
     * @return The name of the recipe
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the recipe.
     *
     * @param name The name to set for the recipe
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the amount of sugar in the recipe.
     *
     * @return The amount of sugar in grams
     */
    public float getSugar() {
        return sugar;
    }

    /**
     * Sets the amount of sugar in the recipe.
     *
     * @param sugar The amount of sugar in grams
     */
    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    /**
     * Gets the number of eggs in the recipe.
     *
     * @return The number of eggs
     */
    public float getEggs() {
        return eggs;
    }

    /**
     * Sets the number of eggs in the recipe.
     *
     * @param eggs The number of eggs
     */
    public void setEggs(float eggs) {
        this.eggs = eggs;
    }

    /**
     * Gets the amount of flour in the recipe.
     *
     * @return The amount of flour in grams
     */
    public float getFlour() {
        return flour;
    }

    /**
     * Sets the amount of flour in the recipe.
     *
     * @param flour The amount of flour in grams
     */
    public void setFlour(float flour) {
        this.flour = flour;
    }

    /**
     * Gets the amount of yeast in the recipe.
     *
     * @return The amount of yeast in grams
     */
    public float getYeast() {
        return yeast;
    }

    /**
     * Sets the amount of yeast in the recipe.
     *
     * @param yeast The amount of yeast in grams
     */
    public void setYeast(float yeast) {
        this.yeast = yeast;
    }

    /**
     * Gets the amount of butter in the recipe.
     *
     * @return The amount of butter in grams
     */
    public float getButter() {
        return butter;
    }

    /**
     * Sets the amount of butter in the recipe.
     *
     * @param butter The amount of butter in grams
     */
    public void setButter(float butter) {
        this.butter = butter;
    }
}
