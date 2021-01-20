package service.messaging;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.ejb.Singleton;

@Singleton
public class ConnectionManager {

    private final ConnectionFactory factory;

    private static final String HOST = "localhost";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String VHOST = "delivery-vm";

    public ConnectionManager() {
        this.factory = new ConnectionFactory();
        this.factory.setHost(HOST);
        this.factory.setUsername(USERNAME);
        this.factory.setPassword(PASSWORD);
        this.factory.setVirtualHost(VHOST);
    }

    public Connection getConnection() throws IOException, TimeoutException {
        return this.factory.newConnection();
    }
}
