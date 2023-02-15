package com.nttdata.demoproject.repository;

import com.nttdata.demoproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    Product findByName(String name);

}
