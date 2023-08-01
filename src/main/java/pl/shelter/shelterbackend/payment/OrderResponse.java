package pl.shelter.shelterbackend.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderResponse {
    @JsonProperty("status")
    private Status status;
    @JsonProperty("redirectUri")
    private String redirectUri;
    @JsonProperty("orderId")
    private String orderId;

    @Getter
    @NoArgsConstructor
    public class Status {
        @JsonProperty("statusCode")
        private String statusCode;

    }
}
