package com.tacos.tacocloud_nosql.repository;

import com.tacos.tacocloud_nosql.model.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}