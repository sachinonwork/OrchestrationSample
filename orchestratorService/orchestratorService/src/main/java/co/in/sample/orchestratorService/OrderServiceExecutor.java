package co.in.sample.orchestratorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;

@Service
public class OrderServiceExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceExecutor.class);
    @Value("${orderService.url}")
    private String orderServiceUrl;


    ResponseEntity<String> invokeOrderService(String orderRequest) {
        RestClient orderServiceCaller = RestClient.builder()
                .baseUrl(orderServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        LOGGER.info("Calling order service at:{}", orderServiceUrl);
        ResponseEntity<String> orderResponse = orderServiceCaller.post().body(orderRequest).retrieve().toEntity(String.class);
        if (ObjectUtils.isEmpty(orderResponse) ||
                !orderResponse.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("order service request failed with empty response.");
            return ResponseEntity.internalServerError().body("Unable to create purchase order");
        }
        LOGGER.error("order service request is successful.");
        return ResponseEntity.ok("Order response is received");
    }
}