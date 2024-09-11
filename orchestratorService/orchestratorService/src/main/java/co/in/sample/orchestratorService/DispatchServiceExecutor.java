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
public class DispatchServiceExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatchServiceExecutor.class);
    @Value("${dispatchService.url}")
    private String dispatchServiceUrl;


    public String invokeDispatchService(String dispatchRequest) {
        RestClient dispatchServiceCaller = RestClient.builder()
                .baseUrl(dispatchServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        LOGGER.info("Calling dispatch service at:{}", dispatchServiceUrl);
        ResponseEntity<String> dispatchResponse = dispatchServiceCaller.post().body(dispatchRequest).retrieve().toEntity(String.class);
        if (ObjectUtils.isEmpty(dispatchResponse) ||
                !dispatchResponse.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("dispatch service request failed with empty response.");
            return "ErrorResponse";
        }
        LOGGER.info("dispatch service request is successful.");
        return "dispatch response is received";
    }
}