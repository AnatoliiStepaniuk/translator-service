package in.ramanujam.common.messaging;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import in.ramanujam.common.properties.RabbitMQProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtils {
    private static final int MAX_TRIES = 200;
    private static final int RETRY_DELAY_MS = 200;

    public static Connection getConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQProperties.getInstance().getRabbitMQContainerHost());
        factory.setPort(RabbitMQProperties.getInstance().getRabbitMQContainerExternalPort());
        return createConnection(factory);
    }

    private static Connection createConnection(ConnectionFactory factory) {
        int count = 0;
        while (true) {
            try {
                Thread.sleep(RETRY_DELAY_MS); // wait for RabbitMQ docker to start
                return factory.newConnection();
            } catch (IOException | TimeoutException | InterruptedException e) {
                if (++count == MAX_TRIES) throw new RuntimeException(e);
            }
        }
    }
}
