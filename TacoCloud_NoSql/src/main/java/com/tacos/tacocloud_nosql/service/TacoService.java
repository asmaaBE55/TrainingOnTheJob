package com.tacos.tacocloud_nosql.service;

import com.tacos.tacocloud_nosql.model.Taco;
import com.tacos.tacocloud_nosql.repository.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacoService {

    private final TacoRepository tacoRepository;

    @Autowired
    public TacoService(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    public Taco save(Taco taco) {
        return tacoRepository.save(taco);
    }

    public Taco getTaco(String id) {
        return tacoRepository.findById(id).orElse(null);
    }

    public List<Taco> getAllTacos() {
        return tacoRepository.findAll();
    }

    public void deleteTaco(String id) {
        tacoRepository.deleteById(id);
    }
}