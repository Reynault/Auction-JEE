package deliverymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class DeliveryManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryManagerApplication.class, args);
    }
}
