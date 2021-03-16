package direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {
        // 1.获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2.获取连接通道对象
        Channel channel = connection.createChannel();
        // 3.将通道声明到指定的交换机
        String exchangeName = "logs_direct";
        channel.exchangeDeclare(exchangeName,"direct");
        // 4.发布消息
        String routingKey = "warning";
        channel.basicPublish(exchangeName, routingKey, null, ("这是direct模型发布的基于routing key:[" + routingKey + "]发送的消息").getBytes());
        // 5.释放资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
