package com.nttdata.demoproject.service;

import com.nttdata.demoproject.entity.Product;
import com.nttdata.demoproject.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class ProductService{
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product saveProduct(Product product){
      return  productRepo.save(product);
    }

    public List<Product> saveProducts(List<Product> products){
        return productRepo.saveAll(products);
    }

    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    public Product getProductById(int id){
        return (Product) productRepo.findById(id).orElse(null);
    }

    public Product getProductByName(String name){
        return productRepo.findByName(name);
    }

    public String deleteProduct(int id){
        productRepo.deleteById(id);
        return "Product deleted !! "+id;
    }

    public Product updateProduct(Product product){
        Product existingProduct= (Product) productRepo.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        return productRepo.save(existingProduct);
    }
}
