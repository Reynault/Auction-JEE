package service.messaging.reveiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import dao.delivery.DeliveryDAOLocal;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class MyDeliveryCallback implements MyDeliveryCallbackLocal {

    @EJB
    private DeliveryDAOLocal delivery;

    @Override
    public void handle(String string, Delivery dlvr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        model.Delivery d = mapper.readValue(dlvr.getBody(), model.Delivery.class);

        delivery.validateDelivery(d);

        System.out.println("------ NEW DELIVERY RECEIVED -----");
        System.out.println(d.getId());
    }

}
