package co.in.sample.notificationService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api")
public class NotificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping(path = "/notify", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> notificationProcess(@RequestBody NotificationData notifyDetails) {
        if (ObjectUtils.isEmpty(notifyDetails) ||
                (!ObjectUtils.isEmpty(notifyDetails) && notifyDetails.getMessage() != null && StringUtils.pathEquals("errorProduct", notifyDetails.getMessage()))) {
            LOGGER.error("failed to send notification due to invalid product request");
            return ResponseEntity.badRequest().body("Failed to notify for error product");
        }
        LOGGER.info("Notification request for product done");
        return ResponseEntity.ok("Notification Completed");
    }
}
