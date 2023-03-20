package com.example.tacocloud.utils;

import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private static final Logger logger = LoggerFactory.getLogger(IngredientByIdConverter.class);

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        try{
            Ingredient ingredient = ingredientRepo.findById(id);
            logger.info("convert ingredinet by id:{}", id);
            return ingredient;
        } catch(Exception e) {
            logger.error("Error in Convert Ingredient by id: {}", id);
            return new Ingredient();
        }
    }
}
