package pl.shelter.shelterbackend.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderRequest {
    private String description;
    private String buyerEmail;
    private String buyerFirstName;
    private String buyerLastName;
    private String productName;
    private Integer productUnitPrice;

}
