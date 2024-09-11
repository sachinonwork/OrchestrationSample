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
public class NotificationServiceExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceExecutor.class);
    @Value("${notificationService.url}")
    private String notificationServiceUrl;


    public String invokeNotificationService(String notificationRequest) {
        RestClient notificationServiceCaller = RestClient.builder()
                .baseUrl(notificationServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        LOGGER.info("Calling Notification service at:{}", notificationServiceUrl);
        ResponseEntity<String> notificationResponse;
        try {
            notificationResponse = notificationServiceCaller.post().body(notificationRequest).retrieve().toEntity(String.class);
        } catch (HttpClientErrorException ex) {
            LOGGER.error("Notification service request failed with empty response.");
            return "ErrorResponse";
        }

        if (ObjectUtils.isEmpty(notificationResponse) ||
                !notificationResponse.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("Notification service request failed with empty response.");
            return "ErrorResponse";
        }
        LOGGER.info("Notification service request is successful.");
        return "Notification response is received";
    }
}