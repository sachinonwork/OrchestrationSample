package co.in.sample.notificationService;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationData {

    private String message;
    private String emailAddress;
    private String priority;
    private String customerName;
    private String productName;
}
