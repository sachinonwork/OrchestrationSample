package co.in.sample.orchestratorService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BillingServiceExecutor billingServiceExecutor;
    @Autowired
    private NotificationServiceExecutor notificationServiceExecutor;
    @Autowired
    private DispatchServiceExecutor dispatchServiceExecutor;


    @PostMapping(path = "/purchase", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> orderRequest(@RequestBody PurchaseRequest purchaseRequest) {
        if (ObjectUtils.isEmpty(purchaseRequest) || !StringUtils.hasLength(purchaseRequest.getProductName())) {
            LOGGER.error("Error due to request object for purchase is empty or product name is empty");
            return ResponseEntity.internalServerError().body("Product purchase flow failing due to incomplete details");
        }
        String orderRequest = "{\"orderId\": \"ord91239\", \"orderDescription\": \"" + purchaseRequest.getProductName() + "\", \"orderItems\":[\"23ASD\"]}";
        String billingRequest = "{\"customerId\": \"sampleCust1\", \"orderId\": \"ord91239\", \"productName\":\"" + purchaseRequest.getProductName() + "\", \"amount\":\"234234.23\", \"taxNumber\":\"SD23ASD\"}";
        String dispatchRequest = "{\"dispatchId\": \"sending2234\", \"addressForDelivery\": \"" + purchaseRequest.getDeliveryAddress() + "\", \"description\": \"" + purchaseRequest.getProductName() + "\"}";
        String notificationRequest = "{\"message\": \"sample lane, KI Stra\", \"priority\": \"LOW\", \"emailAddress\":\"" + purchaseRequest.getNotifyEmail() + "\", \"productName\":\"" + purchaseRequest.getProductName() + "\"}";


        ResponseEntity<String> orderServiceResponse = orderServiceExecutor.invokeOrderService(orderRequest);
        if (orderServiceResponse != null && orderServiceResponse.getStatusCode().is2xxSuccessful()) {
            ResponseEntity<String> billingServiceResponse = billingServiceExecutor.invokeBillingService(billingRequest);
            if (billingServiceResponse != null && billingServiceResponse.getStatusCode().is2xxSuccessful()) {
                ResponseEntity<String> dispatchServiceResponse = dispatchServiceExecutor.invokeDispatchService(dispatchRequest);
                if (dispatchServiceResponse != null && dispatchServiceResponse.getStatusCode().is2xxSuccessful()) {
                    ResponseEntity<String> notificationServiceResponse = notificationServiceExecutor.invokeNotificationService(notificationRequest);
                    if (notificationServiceResponse != null && notificationServiceResponse.getStatusCode().is2xxSuccessful()) {
                        return ResponseEntity.ok("Purchase request completed");
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body("Unable to create purchase order");
    }
}
