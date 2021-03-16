package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        // 声明交换机及交换机类型 topic
        String exchangeName = "topics";
        channel.exchangeDeclare(exchangeName, "topic");
        String routingKey = "save.user.delete";
        channel.basicPublish(exchangeName, routingKey, null, ("这是topic动态路由模型，routing key:[" + routingKey + "]").getBytes());

        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
