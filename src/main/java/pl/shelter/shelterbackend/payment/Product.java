package pl.shelter.shelterbackend.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Product {
    private String name;
    private Integer unitPrice;
    private Integer quantity;
}
