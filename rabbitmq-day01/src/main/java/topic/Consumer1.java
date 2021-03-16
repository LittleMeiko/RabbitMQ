package topic;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "topics";
        channel.exchangeDeclare(exchangeName,"topic");
        String queue = channel.queueDeclare().getQueue();
        // 使用通配符的routing key将队列和交换机绑定
        String routingKey = "*.user.*";
        channel.queueBind(queue, exchangeName, routingKey);
        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body));
            }
        });
    }
}
