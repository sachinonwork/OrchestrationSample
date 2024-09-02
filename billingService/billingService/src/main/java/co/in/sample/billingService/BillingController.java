package co.in.sample.billingService;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BillingController {

    @PostMapping("/api/billing")
    public ResponseEntity<String> billingRequested(@RequestBody BillingData billing) {
        if(billing != null && "errorProduct".equalsIgnoreCase(billing.getProductName())) {
            return ResponseEntity.internalServerError().body("Failed to bill");
        }
        return ResponseEntity.ok("Billing Completed");

    }
}
