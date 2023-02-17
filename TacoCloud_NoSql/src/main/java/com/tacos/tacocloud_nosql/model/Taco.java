package com.tacos.tacocloud_nosql.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document(collection = "tacos")
public class Taco {

    private String id;
    private String name;
    private List<String> ingredients;

    public Taco() {
    }

    public Taco(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

}
