package com.tacos.controller;

import com.tacos.model.Ingredient;
import com.tacos.model.Taco;
import com.tacos.model.TacoOrder;
import com.tacos.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/*  is a Lombok-provided annotation that, at compilation time, will automatically generate an SLF4J (Simple Logging Facade for Java,
 <a href="https://www.slf4j.org/">...</a>) Logger static property in the class **/
//@Slf4j

/* This annotation serves to identify this class as a controller and to mark it as a candidate for
 component scanning **/
//@Controller
/* @RequestMapping annotation, when applied at the class level, specifies the kind of requests that
this controller handles. In this case, it specifies that DesignTacoController will handle requests whose path begins with /design
**/
//@RequestMapping("/design")

/* This indicates that the TacoOrder object that is put into the model a
little later in the class should be maintained in session.**/
//@SessionAttributes("tacoOrder")
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType((List<Ingredient>) ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors,
                              @ModelAttribute TacoOrder tacoOrder) {

        if (errors.hasErrors()) {
            return "design";
        }
        tacoOrder.addTaco(taco);
        return "redirect:/orders/current";
    }
}