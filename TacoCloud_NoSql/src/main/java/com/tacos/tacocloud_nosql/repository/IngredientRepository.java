package com.tacos.tacocloud_nosql.repository;

import com.tacos.tacocloud_nosql.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
}
