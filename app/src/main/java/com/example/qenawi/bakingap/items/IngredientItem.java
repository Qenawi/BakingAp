package com.example.qenawi.bakingap.items;

import java.io.Serializable;

/**
 * Created by QEnawi on 6/15/2017.
 */

public class IngredientItem implements Serializable
{
    private int quantity;
    private String measure;

    public IngredientItem(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public IngredientItem() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    private String ingredient;
}
