package com.tacos.tacocloud_nosql.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
public class TacoOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private Date placedAt = new Date();
    // other properties omitted for brevity's sake
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}