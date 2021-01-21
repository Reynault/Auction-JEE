package service.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

@Singleton
public class ConnectionManager {

    private ConnectionFactory factory;

    private static final String HOST = "localhost";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String VHOST = "delivery-vm";

    private Connection connection;

    @PostConstruct
    public void init() {
        try {
            this.factory = new ConnectionFactory();
            this.factory.setHost(HOST);
            this.factory.setUsername(USERNAME);
            this.factory.setPassword(PASSWORD);
            this.factory.setVirtualHost(VHOST);
            this.connection = this.factory.newConnection();
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    public void delete() {
        try {
            connection.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Channel getChannel() throws IOException {
        return this.connection.createChannel();
    }
}
