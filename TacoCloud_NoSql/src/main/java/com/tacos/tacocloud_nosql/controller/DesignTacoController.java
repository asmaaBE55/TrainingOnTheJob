package com.tacos.tacocloud_nosql.controller;


import com.tacos.tacocloud_nosql.model.Ingredient;
import com.tacos.tacocloud_nosql.model.Taco;
import com.tacos.tacocloud_nosql.model.TacoOrder;
import com.tacos.tacocloud_nosql.service.IngredientService;
import com.tacos.tacocloud_nosql.service.TacoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientService ingredientService;

    @Autowired
    public DesignTacoController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @Autowired
    private TacoService tacoService;

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        Ingredient.Type[] types = Ingredient.Type.values();

        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        model.addAttribute("taco", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder order) {
        if (errors.hasErrors()) {
            return "design";
        }

       Taco savedTaco = tacoService.save(taco);
        order.addDesign(savedTaco);

        return "redirect:/orders/current";
    }


    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }
}
