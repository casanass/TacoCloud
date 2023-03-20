package com.example.tacocloud.controller;

import com.example.tacocloud.model.IngreType;
import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.model.Order;
import com.example.tacocloud.model.Taco;
import com.example.tacocloud.repository.IngredientRepository;
import com.example.tacocloud.repository.TacoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/taco")
@SessionAttributes("order")
public class TacoController {
    Logger logger = LoggerFactory.getLogger(TacoController.class);

    private TacoRepository tacoRepository;

    private IngredientRepository ingredientRepository;

    @Autowired
    public TacoController(TacoRepository tacoRepository
            , IngredientRepository ingredientRepository) {
        this.tacoRepository = tacoRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    @GetMapping
    public String addIngredient(Model model) {
        logger.info("GetMapping /design");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));
        logger.info("Ingredients Number :{}", ingredients.size());

        IngreType[] types = IngreType.values();
        for(IngreType type: types) {
            model.addAttribute(type.toString().toLowerCase()
                    ,filterByType(ingredients, type));
        }

        return "taco";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, @ModelAttribute Order order, Errors errors) {
        if(errors.hasErrors()) {
            return "taco";
        }
        logger.info("PostMapping /design");
        Taco savedTaco = tacoRepository.save(taco);
        logger.info("Save taco :{}", savedTaco.getId());
        order.addTacos(savedTaco);
        return "redirect:/order/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, IngreType type) {
        return ingredients.stream()
                .filter(ingre -> type.equals(ingre.getType()))
                .collect(Collectors.toList());
    }
}
