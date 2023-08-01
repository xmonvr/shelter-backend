package pl.shelter.shelterbackend.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.shelter.shelterbackend.payment.auth.OAuthService;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    @Value("${payu.pos_id}")
    private String POS_ID;
    private final OAuthService oAuthService;
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public OrderResponse/*ResponseEntity<String>*/ createOrder(OrderRequest orderRequest, String ipAddress) {
        OrderCreate orderCreate = prepareOrderCreate(orderRequest, ipAddress);
        String token = oAuthService.getAccessTokenFromPayUResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.info("payu access token --> " + token);
        headers.set("Authorization", "Bearer " + token); // token oAuth
        HttpEntity<OrderCreate> httpEntity = new HttpEntity<>(orderCreate, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://secure.snd.payu.com/api/v2_1/orders/", httpEntity, String.class);
        log.info("createOrder response --> " + response);

//            return ResponseEntity
//                    .status(response.getStatusCode())
//                    .body(response.getBody());
        OrderResponse orderResponse;
        try {
            orderResponse = objectMapper.readValue(response.getBody(), OrderResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return orderResponse;
    }

    private OrderCreate prepareOrderCreate(OrderRequest orderRequest, String ipAddress) {
        log.info("request data --> " + orderRequest.toString());
//        String notifyUrl = null;
        String customerIp = ipAddress;
        String merchantPosId = POS_ID;
        String description = orderRequest.getDescription();
        String currencyCode = "PLN";
        Integer unitPrice = orderRequest.getProductUnitPrice() * 100;
        Integer totalAmount = unitPrice;
        String email = orderRequest.getBuyerEmail();
        String firstName = orderRequest.getBuyerFirstName();
        String lastName = orderRequest.getBuyerLastName();
        String language = "pl";
        String name = orderRequest.getProductName();
        Integer quantity = 1;
        Product product = new Product(name, unitPrice, quantity); // zawsze jeden produkt

        return OrderCreate.builder()
//                .notifyUrl(notifyUrl)
                .customerIp(customerIp)
                .merchantPosId(merchantPosId)
                .description(description)
                .currencyCode(currencyCode)
                .totalAmount(totalAmount)
                .buyer(new Buyer(email, firstName, lastName, language))
                .products(Collections.singletonList(product))
                .build();
    }
}
