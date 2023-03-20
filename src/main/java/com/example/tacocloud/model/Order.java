package com.example.tacocloud.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Order {

    private Long id;

    private Date createDt;

    @NotBlank(message = "name is required")
    private String orderName;

    @NotBlank(message = "street is required")
    private String orderStreet;

    @NotBlank(message = "city is required")
    private String orderCity;

    @NotBlank(message = "state is required")
    private String orderState;

    @Pattern(regexp = "^(0[1-9]|1[0-9])", message = "ccNumber must be 2 number")
    private String orderCcNumber;

    private List<Taco> tacos = new ArrayList<>();

    public void addTacos(Taco taco) {
         this.tacos.add(taco);
    }
}
