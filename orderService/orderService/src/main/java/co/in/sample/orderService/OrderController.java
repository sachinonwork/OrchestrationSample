package co.in.sample.orderService;

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
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @PostMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> orderRequest(@RequestBody OrderDetails orderDetails) {
        if (ObjectUtils.isEmpty(orderDetails) || ObjectUtils.isEmpty(orderDetails.getOrderId())) {
            LOGGER.error("Found request or order Id blank in request");
            return ResponseEntity.badRequest().body("Failed to Order for error product");
        }
        if (!ObjectUtils.isEmpty(orderDetails) && StringUtils.hasLength(orderDetails.getOrderDescription())
                && StringUtils.pathEquals("errorProduct", orderDetails.getOrderDescription())) {
            LOGGER.error("Failed due to invalid product order request");
            return ResponseEntity.badRequest().body("Failed to Order for invalid product");
        }
        LOGGER.error("Order request processed successfully");
        return ResponseEntity.ok("Order Completed");
    }
}
