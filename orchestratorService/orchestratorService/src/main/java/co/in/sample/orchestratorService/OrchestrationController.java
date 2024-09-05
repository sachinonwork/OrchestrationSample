package co.in.sample.orchestratorService;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class OrchestrationController {


    @PostMapping(path = "/purchase", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> orderRequest(@RequestBody PurchaseRequest purchaseRequest) {
        if (!ObjectUtils.isEmpty(purchaseRequest) && StringUtils.hasLength(purchaseRequest.getProductName())
                && StringUtils.pathEquals("errorProduct", purchaseRequest.getProductName())) {
            return ResponseEntity.internalServerError().body("Product purchase flow failed");
        }
        return ResponseEntity.ok("Purchase request completed");
    }
}
