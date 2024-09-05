package co.in.sample.orderService;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetails {
    private String orderId;
    private String orderDescription;
    private List<String> orderItems;
}
