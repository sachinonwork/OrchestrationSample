package co.in.sample.billingService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingData {

    private String customerId;
    private String orderId;
    private String productName;
    private String taxNumber;
    private double amount;
}
