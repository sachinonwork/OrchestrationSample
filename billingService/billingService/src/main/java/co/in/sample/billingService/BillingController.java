package co.in.sample.billingService;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class BillingController {

    @PostMapping(path = "/billing", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> billingRequested(@RequestBody BillingData billing) {
        if(billing != null && billing.getProductName() != null && StringUtils.pathEquals("errorProduct", billing.getProductName())) {
            return ResponseEntity.internalServerError().body("Failed to bill");
        }
        return ResponseEntity.ok("Billing Completed");
    }
}
