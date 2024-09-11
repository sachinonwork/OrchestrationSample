package co.in.sample.orchestratorService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class OrchestrationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrchestrationController.class);
    @Autowired
    private OrderServiceExecutor orderServiceExecutor;


    @Value("${billingService.url}")
    private String billingServiceUrl;

    @Value("${dispatchService.url}")
    private String dispatchServiceUrl;

    @Value("${notificationService.url}")
    private String notificationServiceUrl;


    @PostMapping(path = "/purchase", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> orderRequest(@RequestBody PurchaseRequest purchaseRequest) {
        if (ObjectUtils.isEmpty(purchaseRequest) || !StringUtils.hasLength(purchaseRequest.getProductName())) {
            LOGGER.error("Error due to request object for purchase is empty or product name is empty");
            return ResponseEntity.internalServerError().body("Product purchase flow failing due to incomplete details");
        }
        String orderRequest = "{\"orderId\": \"ord91239\", \"orderDescription\": \"" + purchaseRequest.getProductName() + "\", \"orderItems\":[\"23ASD\"]}";
        String billingRequest = "{\"customerId\": \"sampleCust1\", \"orderId\": \"ord91239\", \"productName\":\"" + purchaseRequest.getProductName() + "\", \"amount\":\"234234.23\", \"taxNumber\":\"SD23ASD\"}";
        String dispatchRequest = "{\"dispatchId\": \"sending2234\", \"addressForDelivery\": \"" + purchaseRequest.getDeliveryAddress() + "\", \"description\": \"" + purchaseRequest.getProductName() + "\"}";
        String notificationRequest = "{\"message\": \"sample lane, KI Stra\", \"priority\": \"LOW, \"emailAddress\":\"" + purchaseRequest.getNotifyEmail() + "\", \"productName\":" + purchaseRequest.getProductName() + "}";


        ResponseEntity<String> orderServiceResponse = orderServiceExecutor.invokeOrderService(orderRequest);
        if (orderServiceResponse != null) return orderServiceResponse;


        return ResponseEntity.ok("Purchase request completed");
    }

    private ResponseEntity<String> invokeOrderService(String orderRequest) {
        return orderServiceExecutor.invokeOrderService(orderRequest);
    }
}
