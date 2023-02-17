package com.tacos.tacocloud_nosql.repository;

import com.tacos.tacocloud_nosql.model.TacoOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<TacoOrder, String> {
}
