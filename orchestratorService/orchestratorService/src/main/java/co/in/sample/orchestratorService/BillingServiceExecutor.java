package co.in.sample.orchestratorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class BillingServiceExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingServiceExecutor.class);
    @Value("${billingService.url}")
    private String billingServiceUrl;


    public String invokeBillingService(String billingRequest) {
        RestClient orderServiceCaller = RestClient.builder()
                .baseUrl(billingServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        LOGGER.info("Calling billing service at:{}", billingServiceUrl);
        ResponseEntity<String> billingResponse;
        try {
            billingResponse = orderServiceCaller.post().body(billingRequest).retrieve().toEntity(String.class);
        } catch (
                HttpClientErrorException ex) {
            LOGGER.error("order service request failed with empty response.");
            return "ErrorResponse";
        }
        if (ObjectUtils.isEmpty(billingResponse) ||
                !billingResponse.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("billing service request failed with empty response.");
            return "ErrorResponse";
        }
        LOGGER.info("billing service request is successful.");
        return "Billing response is received";
    }
}