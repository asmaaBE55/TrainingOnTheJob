package com.tacos.tacocloud_nosql.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "orders")
public class TacoOrder {

    private String id;
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
    private List<Taco> tacos;

    public TacoOrder() {}

    public void addDesign(Taco taco) {
        if (this.tacos == null) {
            this.tacos = new ArrayList<>();
        }
        this.tacos.add(taco);
    }

}
