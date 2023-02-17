package com.tacos.tacocloud_nosql.repository;

import com.tacos.tacocloud_nosql.model.Taco;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TacoRepository extends MongoRepository<Taco, String> {
}