package co.in.sample.billingService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingController.class);

    @PostMapping(path = "/billing", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> billingRequested(@RequestBody BillingData billing) {

        if (ObjectUtils.isEmpty(billing) || ObjectUtils.isEmpty(billing.getOrderId())) {
            LOGGER.error("Found request or order Id blank in request");
            return ResponseEntity.badRequest().body("Failed to Order for error product");
        }
        if (StringUtils.pathEquals("errorBillingProduct", billing.getProductName())) {
            LOGGER.error("Found request of order for invalid product");
            return ResponseEntity.badRequest().body("Failed to bill due to invalid product");
        }
        LOGGER.info("Billing id request and done for order");
        return ResponseEntity.ok("Billing Completed");
    }
}
