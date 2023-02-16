package com.tacos.model;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**
 * A converter is any class that implements
 * Spring’s Converter interface and implements its convert() method to take one
 * value and convert it to another. To convert a String to an Ingredient
 **/

/**
 IngredientByIdConverter is annotated with @Component to make
 it discoverable as a bean in the Spring application context
 **/
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    /**
     Because we don’t yet have a database from which to pull Ingredient objects,
     the constructor of IngredientByIdConverter
     creates a Map keyed on a String that is the
     ingredient ID and whose values are Ingredient objects.
     **/
    public IngredientByIdConverter() {
        ingredientMap.put("FLTO",
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        ingredientMap.put("COTO",
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        ingredientMap.put("GRBF",
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        ingredientMap.put("CARN",
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        ingredientMap.put("TMTO",
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
        ingredientMap.put("LETC",
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
        ingredientMap.put("CHED",
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        ingredientMap.put("JACK",
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        ingredientMap.put("SLSA",
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        ingredientMap.put("SRCR",
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
    }

    /**
     The convert() method takes a String that is the ingredient ID and
     uses it to look up the Ingredient from the map.
     **/
    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
