package com.tacos.repository;


import com.tacos.model.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Ingredient findById(String id);

    Ingredient save(Ingredient ingredient);

}