package com.example.qenawi.bakingap.items;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by QEnawi on 6/15/2017.
 */

public class RecipeItem implements Serializable
{
    private String name;
    private  int servings;

    public void setName(String name) {
        this.name = name;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public RecipeItem() {
    }
    public String get_ingredientSt()
    {
        String e="";
        for(int i=0;i<ingredientItems.size();i++)
        {
            e+=ingredientItems.get(i).getIngredient();
            e+="\n";
            e+=ingredientItems.get(i).getMeasure();
            e+="\t\t\t";
            e+=ingredientItems.get(i).getQuantity()+"\n";
        }
        return e;
    }
    public RecipeItem(String name, int servings, String img_url, @Nullable ArrayList<IngredientItem> ingredientItems, ArrayList<StepItem> stepItems) {

        this.name = name;
        this.servings = servings;
        this.img_url = img_url;
        this.ingredientItems = ingredientItems;
        this.stepItems = stepItems;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setIngredientItems(ArrayList<IngredientItem> ingredientItems) {
        this.ingredientItems = ingredientItems;

    }

    public void setStepItems(ArrayList<StepItem> stepItems) {
        this.stepItems = stepItems;
    }

    private  String img_url;
    private ArrayList<IngredientItem>ingredientItems;

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImg_url() {
        return img_url;
    }

    public ArrayList<IngredientItem> getIngredientItems() {
        return ingredientItems;
    }

    public ArrayList<StepItem> getStepItems() {
        return stepItems;
    }

    private ArrayList<StepItem>stepItems;

}
