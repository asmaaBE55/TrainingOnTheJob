package com.tacos.tacocloud_nosql.repository;

import com.tacos.tacocloud_nosql.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}