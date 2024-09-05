package co.in.sample.notificationService;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api")
public class NotificationController {

    @PostMapping(path = "/notify", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> billingRequested(@RequestBody NotificationData notifyDetails) {
        if (ObjectUtils.isEmpty(notifyDetails) ||
                (!ObjectUtils.isEmpty(notifyDetails) && notifyDetails.getMessage() != null && StringUtils.pathEquals("errorProduct", notifyDetails.getMessage()))) {
            return ResponseEntity.internalServerError().body("Failed to Notify for error product");
        }
        return ResponseEntity.ok("Notification Completed");
    }
}
