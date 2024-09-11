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
    public ResponseEntity<String> purchaseOrderFlow(@RequestBody PurchaseRequest purchaseRequest) {
        if (ObjectUtils.isEmpty(purchaseRequest) || !StringUtils.hasLength(purchaseRequest.getProductName())) {
            LOGGER.error("Error due to request object for purchase is empty or product name is empty");
            return ResponseEntity.internalServerError().body("Product purchase flow failing due to incomplete details");
        }
        String orderRequest = "{\"orderId\": \"ord91239\", \"orderDescription\": \"" + purchaseRequest.getProductName() + "\", \"orderItems\":[\"23ASD\"]}";
        String billingRequest = "{\"customerId\": \"sampleCust1\", \"orderId\": \"ord91239\", \"productName\":\"" + purchaseRequest.getProductName() + "\", \"amount\":\"234234.23\", \"taxNumber\":\"SD23ASD\"}";
        String dispatchRequest = "{\"dispatchId\": \"sending2234\", \"addressForDelivery\": \"" + purchaseRequest.getDeliveryAddress() + "\", \"description\": \"" + purchaseRequest.getProductName() + "\"}";
        String notificationRequest = "{\"message\": \"sample lane, KI Stra\", \"priority\": \"LOW\", \"emailAddress\":\"" + purchaseRequest.getNotifyEmail() + "\", \"productName\":\"" + purchaseRequest.getProductName() + "\"}";

        String orderServiceResponse = orderServiceExecutor.invokeOrderService(orderRequest);
        if (StringUtils.hasLength(orderServiceResponse) && StringUtils.pathEquals("ErrorResponse", orderServiceResponse)) {
            LOGGER.error("Error to request order, can not process with purchase");
            return ResponseEntity.badRequest().body("Unable to create purchase order");
        }
        String billingServiceResponse = billingServiceExecutor.invokeBillingService(billingRequest);
        if (StringUtils.hasLength(billingServiceResponse) && StringUtils.pathEquals("ErrorResponse", billingServiceResponse)) {
            LOGGER.error("Error for billing, will invoke reversal of order");
            String orderRevertResponse = orderServiceExecutor.invokeOrderReversal(orderRequest);
            if (StringUtils.hasLength(orderRevertResponse) && StringUtils.pathEquals("ErrorResponse", orderRevertResponse)) {
                LOGGER.error("Error to reverse order after billing failure");
                return ResponseEntity.badRequest().body("Unable to create purchase order");
            }
        }
        String dispatchServiceResponse = dispatchServiceExecutor.invokeDispatchService(dispatchRequest);
        if (StringUtils.hasLength(dispatchServiceResponse) && StringUtils.pathEquals("ErrorResponse", dispatchServiceResponse)) {
            LOGGER.error("Error in dispatch of order, will be existing");
            return ResponseEntity.badRequest().body("Unable to create purchase order");
        }

        String notificationServiceResponse = notificationServiceExecutor.invokeNotificationService(notificationRequest);
        if (StringUtils.hasLength(notificationServiceResponse) && StringUtils.pathEquals("ErrorResponse", notificationServiceResponse)) {
            LOGGER.error("Error due to notification call for order, cna not proceed");
            return ResponseEntity.badRequest().body("Unable to create purchase order");
        }

        return ResponseEntity.ok("Purchase request completed.");
    }
}
