package co.in.sample.orderService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api")
public class OrderController {

    @PostMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> orderRequest(@RequestBody OrderDetails orderDetails) {
        if (!ObjectUtils.isEmpty(orderDetails) && StringUtils.hasLength(orderDetails.getOrderDescription())
                && StringUtils.pathEquals("errorProduct", orderDetails.getOrderDescription())) {
            return ResponseEntity.internalServerError().body("Failed to Order for error product");
        }
        return ResponseEntity.ok("Order Completed");
    }
}
