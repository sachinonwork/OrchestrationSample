package co.in.sample.orchestratorService;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequest {

    private String productName;
    private String customerName;
    private String deliveryAddress;
    private String notifyEmail;
}
