package com.example.tacocloud.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Taco {

    private Long id;

    private Date createDt;

    @NotNull
    //@Size(min=5, message = "name must at least 5")
    private String name;

    //@Size(min=1, message = "You must hcoose at least one ingredient")
    private List<Ingredient> ingredients;
}
