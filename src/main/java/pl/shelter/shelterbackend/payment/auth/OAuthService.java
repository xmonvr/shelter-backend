package pl.shelter.shelterbackend.payment.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OAuthService {

    @Value("${payu.client_id}")
    private String CLIENT_ID;
    @Value("${payu.client_secret}")
    private String CLIENT_SECRET;
    @Value("${payu.grant_type}")
    private String GRANT_TYPE;
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper mapper = new ObjectMapper();

    public String preparePayUAuth() {
        String url = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize?grant_type="
                + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;
        String response = restTemplate.postForEntity(url, null, String.class).getBody();
        return response;
    }

    public String getAccessTokenFromPayUResponse() {
        try {
            OAuthResponse oAuthResponse = mapper.readValue(preparePayUAuth(), OAuthResponse.class);
            return oAuthResponse.getAccessToken();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
