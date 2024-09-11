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
public class OrderServiceExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceExecutor.class);
    @Value("${orderService.url}")
    private String orderServiceUrl;


    public String invokeOrderService(String orderRequest) {
        RestClient orderServiceCaller = RestClient.builder()
                .baseUrl(orderServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        LOGGER.info("Calling order service at:{}", orderServiceUrl);
        ResponseEntity<String> orderResponse;
        try {
            orderResponse = orderServiceCaller.post().body(orderRequest).retrieve().toEntity(String.class);
        } catch (HttpClientErrorException ex) {
            LOGGER.error("order service request failed with empty response.");
            return "ErrorResponse";
        }
        if (ObjectUtils.isEmpty(orderResponse) ||
                !orderResponse.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("order service request failed with empty response.");
            return "ErrorResponse";
        }
        LOGGER.error("order service request is successful.");
        return "Order response is received";
    }

    public String invokeOrderReversal(String orderRequest) {
        RestClient orderServiceCaller = RestClient.builder()
                .baseUrl(orderServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        LOGGER.info("Calling order reverseservice at:{}", orderServiceUrl);
        ResponseEntity<String> orderResponse = orderServiceCaller.put().body(orderRequest).retrieve().toEntity(String.class);
        if (ObjectUtils.isEmpty(orderResponse) ||
                !orderResponse.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("order reverse request failed with empty response.");
            return "ErrorResponse";
        }
        LOGGER.error("order reverse request is successful.");
        return "Order reverse is completed";
    }
}