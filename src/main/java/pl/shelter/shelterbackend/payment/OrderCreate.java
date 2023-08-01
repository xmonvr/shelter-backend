package pl.shelter.shelterbackend.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderCreate {
//    private String notifyUrl;       //todo niewymagane
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private Integer totalAmount;
    private Buyer buyer;
    private List<Product> products;
}
